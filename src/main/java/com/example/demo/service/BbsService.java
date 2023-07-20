package com.example.demo.service;

import com.example.demo.entity.user.Bbs;
import com.example.demo.entity.user.Comment;
import com.example.demo.entity.user.User;
import com.example.demo.jpa.repository.user.BbsRepository;
import com.example.demo.jpa.repository.user.CommentRepository;
import com.example.demo.jpa.repository.user.UserRepository;
import com.example.demo.vo.BbsDto;
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
            User user = userRepository.findByUserID(userID).get(0);

            Bbs bbs = new Bbs();
            bbs.setUser(user);
            bbs.setTitle(request.getTitle());
            bbs.setContent(request.getContent());

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

            String userId = bbsRepository.findById(bbsId).get().getUser().getUserID();
            User user = userRepository.findByUserID(userId).get(0);

            Bbs bbs = bbsRepository.findById(bbsId).orElseThrow(() -> new IllegalArgumentException("게시글 수정 실패: 해당 게시글이 존재하지 않습니다."));
            Bbs updatedBbs = new Bbs(bbsId, title, user, content, null, commentRepository.findByBbsId(bbsId));

            try {
                if(bbs != null) {
                    bbsRepository.save(updatedBbs); // 게시판 수정
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

    public List<Comment> getComments(Long id){

        List<Comment> comments =  commentRepository.findByBbsId(id);

        for(Comment c : comments){
            c.setDatetime(c.getCreatedAt().toString().replace("T", " "));
        }

        return comments;
    }

    public boolean insertComment(BbsDto.CommentRequest request, User user) {

        if(request.getBody().isEmpty())
            return false;

        else{
            User User = userRepository.findByUserID(user.getUserID()).get(0);
            Bbs bbs = bbsRepository.findById(request.getBbs_id()).orElseThrow(() ->
                    new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다."));
            Comment comment = new Comment(null, bbs, User, User.getUserName(), request.getBody(), null);

            try {
                commentRepository.save(comment);
            }catch (Exception e){
                e.printStackTrace();
            }

            return true;
        }
    }

    public void deleteComment(Long commentId) {
        Long bbsId = commentRepository.findById(commentId).get().getBbs().getId();

        Comment comment = commentRepository.findById(commentId).orElse(null);

        if(comment != null){
            try {
                commentRepository.delete(comment);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}

