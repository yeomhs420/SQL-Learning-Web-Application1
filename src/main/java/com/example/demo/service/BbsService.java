package com.example.demo.service;

import com.example.demo.entity.user.Bbs;
import com.example.demo.entity.user.Comment;
import com.example.demo.entity.user.User;
import com.example.demo.jpa.repository.user.BbsRepository;
import com.example.demo.jpa.repository.user.CommentRepository;
import com.example.demo.jpa.repository.user.UserRepository;
import com.example.demo.vo.BbsDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BbsService {
    @Autowired
    BbsRepository bbsRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EagerService eagerService;

    @Autowired
    ModelMapper modelMapper;


    public Bbs getBbs(Long id){
        Bbs bbs = bbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        return bbs;
    }

    public Page<Bbs> listToPage(List<Bbs> bbsList, PageRequest pagable){

        Page<Bbs> Bbs;

        int start = (int)pagable.getOffset();
        int end = Math.min((start + pagable.getPageSize()), bbsList.size());

        if(start > end)
            start = end;

        Bbs = new PageImpl<Bbs>(bbsList.subList(start, end), pagable, bbsList.size());

        return Bbs;
    }
    public Page<Bbs> getBbsList(BbsDto.SearchRequest request, int p){

        Page<Bbs> Bbs;

        PageRequest pageable = PageRequest.of(p - 1,10, Sort.by(Sort.Direction.DESC, "id"));

        if(request.getKeyword() != null && !(request.getKeyword().equals(""))) {
            String name = request.getName();
            String keyword = request.getKeyword();


            List<Bbs> bbsList = eagerService.getBbsListWithEagerComments(name, keyword);

            Bbs = listToPage(bbsList, pageable);   // List -> Page 변환

            for(Bbs bbs : Bbs){
                bbs.setDatetime(bbs.getCreatedAt().toString().replace("T", " "));
            }

            return Bbs;

        }

        Bbs = eagerService.getPagedBbsWithEagerComments(pageable);

        for(com.example.demo.entity.user.Bbs bbs : Bbs){
            bbs.setDatetime(bbs.getCreatedAt().toString().replace("T", " "));
        }

        return Bbs;
    }

    public boolean insertBbs(BbsDto.PostRequest request, String userID) {   // 게시판 글 등록

        if(request.getTitle().isEmpty() || request.getContent().isEmpty())
            return false;

        else {
            Bbs bbs = modelMapper.map(request, Bbs.class);

            User user = userRepository.findByUserID(userID).get(0);

            bbs.setUser(user);

            try {
                bbsRepository.save(bbs);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public String getUserIdByBbsId(Long bbs_id){
        return bbsRepository.findById(bbs_id).get().getUser().getUserID();
    }

    public String getUserIdByCommentId(Long comment_id) {return commentRepository.findById(comment_id).get().getUser().getUserID();}

    public boolean updateBbs(BbsDto.UpdateRequest request) {

        if(request.getTitle().isEmpty() || request.getContent().isEmpty())
            return false;


        else {
            Long bbsId = request.getBbs_id();
            String title = request.getTitle();
            String content = request.getContent();

            Bbs bbs = bbsRepository.findById(bbsId).orElseThrow(() -> new IllegalArgumentException("게시글 수정 실패: 해당 게시글이 존재하지 않습니다."));
            bbs.setTitle(title);
            bbs.setContent(content);

            try {
                if(bbs != null) {
                    bbsRepository.save(bbs); // 게시판 수정
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }


    public void deleteBbs(Long bbsId) {

        Bbs bbs = bbsRepository.findById(bbsId).orElse(null);

        if(bbs != null){
            try {
                bbsRepository.delete(bbs);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public List<Comment> getComments(Long id){  // @Transactional 로 감싸 proxy 객체를 사용하는 방법과 Fetch join 을 사용하여 실제 객체를 가져오는 방법 2가지 사용가능

        Bbs bbs = bbsRepository.findByIdWithComments(id);   // @Transactional 이 없으면 Fetch join 을 사용하여 실제 Comment 객체를 함께 로드해야함
        List<Comment> comments = bbs.getComments(); // Fetch join 을 사용하면 proxy 가 아닌 실제 객체를 가져오게 됨

        for(Comment c : comments){  // @Transactional 를 사용하게 될 경우, DB 로부터 로드되어 트랜잭션 범위 내에서 프록시 객체(지연 로딩된 comments)가 초기화됨 = 실제 엔티티로 로딩
            c.setDatetime(c.getCreatedAt().toString().replace("T", " "));
        }

        return comments;
    }

    @Transactional
    public boolean insertComment(BbsDto.CommentRequest request, User user) {

        if(request.getBody().isEmpty())
            return false;

        else{
            Bbs bbs = bbsRepository.findById(request.getBbs_id()).orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다."));

            Comment comment = modelMapper.map(request, Comment.class);
            comment.setUser(user);
            comment.setNickname(user.getUserName());
            comment.setBbs(bbs);

            try {
                commentRepository.save(comment);
            }catch (Exception e){
                e.printStackTrace();
            }

            return true;
        }
    }

    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if(comment != null){
            try {
                commentRepository.delete(comment);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}

