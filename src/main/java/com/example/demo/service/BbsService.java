package com.example.demo.service;

import com.example.demo.entity.user.Bbs;
import com.example.demo.entity.user.Comment;
import com.example.demo.entity.user.User;
import com.example.demo.jpa.repository.user.BbsRepository;
import com.example.demo.jpa.repository.user.CommentRepository;
import com.example.demo.jpa.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BbsService {
    @Autowired
    BbsRepository bbsRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;


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
    public Page<Bbs> getBbsList(HttpServletRequest request, int p){

        Page<Bbs> Bbs;

        if(request.getParameter("keyword") != null && !(request.getParameter("keyword").equals(""))) {
            String name = request.getParameter("name");
            String keyword = request.getParameter("keyword");

            PageRequest pageable = PageRequest.of(p - 1,10, Sort.by(Sort.Direction.DESC, "id"));

            if(name.equals("Title")){   // Title 로 검색할 경우
                List<Bbs> bbsList = bbsRepository.findAll().stream().filter(x -> x.getTitle().contains(keyword)).collect(Collectors.toList());

                Bbs = listToPage(bbsList, pageable);   // List -> Page 변환
            }

            else if (name.equals("Writer")) {
                List<Bbs> bbsList = bbsRepository.findAll().stream().filter(x -> x.getUser().getUserName().contains(keyword)).collect(Collectors.toList());

                Bbs = listToPage(bbsList, pageable);
            }

            else{
                List<Bbs> bbsList = bbsRepository.findAll().stream().filter(x -> x.getContent().contains(keyword)).collect(Collectors.toList());

                Bbs = listToPage(bbsList, pageable);
            }

            for(Bbs bbs : Bbs){
                bbs.setDatetime(bbs.getCreatedAt().toString().replace("T", " "));
            }

            return Bbs;

        }

        Bbs = bbsRepository.findAll(PageRequest.of(p - 1,10, Sort.by(Sort.Direction.DESC, "id")));

        for(com.example.demo.entity.user.Bbs bbs : Bbs){
            bbs.setDatetime(bbs.getCreatedAt().toString().replace("T", " "));
        }

        return Bbs;
    }

    public boolean insertBbs(HttpServletRequest request,String userID, RedirectAttributes re) {   // 게시판 글 등록

        if(request.getParameter("Title").isEmpty() || request.getParameter("Content").isEmpty())
            return false;

        else {
            User user = userRepository.findByUserId(userID).get(0);

            Bbs bbs = new Bbs();
            bbs.setUser(user);
            bbs.setTitle(request.getParameter("Title"));
            bbs.setContent(request.getParameter("Content"));

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

    public boolean updateBbs(HttpServletRequest request) {

        if(request.getParameter("Title").isEmpty() || request.getParameter("Content").isEmpty())
            return false;


        else {
            Long bbsID = Long.parseLong(request.getParameter("bbs_id"));
            String title = request.getParameter("Title");
            String content = request.getParameter("Content");

            String userID = bbsRepository.findById(bbsID).get().getUser().getUserID();
            User user = userRepository.findByUserId(userID).get(0);

            Bbs bbs = bbsRepository.findById(bbsID).orElseThrow(() -> new IllegalArgumentException("게시글 수정 실패: 해당 게시글이 존재하지 않습니다."));
            Bbs updatedBbs = new Bbs(bbsID, title, user, content, null, commentRepository.findByBbsId(bbsID));

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

    public boolean insertComment(HttpServletRequest request, User user, Model model) {

        if(request.getParameter("body").isEmpty())
            return false;

        else{
            User User = userRepository.findByUserId(user.getUserID()).get(0);
            Bbs bbs = bbsRepository.findById(Long.parseLong(request.getParameter("bbs_id"))).orElseThrow(() -> 
                    new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다."));
            Comment comment = new Comment(null, bbs, User, User.getUserName(), request.getParameter("body"), null);

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

