package com.example.demo.service;

import com.example.demo.config.MyConfig;
import com.example.demo.entity.sampledata.Bbs;
import com.example.demo.entity.sampledata.Comment;
import com.example.demo.jpa.repository.BbsRepository;
import com.example.demo.jpa.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BbsService {
    @Autowired
    BbsRepository bbsRepository;

    @Autowired
    CommentRepository commentRepository;


    public Bbs getBbs(Long id){
        Bbs bbs = bbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        return bbs;
    }


    public Page<Bbs> getBbsList(HttpServletRequest request, int p){

        if(request.getParameter("keyword") != null && !(request.getParameter("keyword").equals(""))) {
            String name = request.getParameter("name");
            String keyword = request.getParameter("keyword");

            Page<Bbs> Bbs;

            int start, end;

            PageRequest pageable = PageRequest.of(p - 1,10, Sort.by(Sort.Direction.DESC, "id"));

            if(name.equals("Title")){
                List<Bbs> bbsList = bbsRepository.findAll().stream().filter(x -> x.getTitle().contains(keyword)).collect(Collectors.toList());

                start = (int)pageable.getOffset();
                end = Math.min((start + pageable.getPageSize()), bbsList.size());

                if(start > end)
                    start = end;

                Bbs = new PageImpl<Bbs>(bbsList.subList(start, end), pageable, bbsList.size());
                // List -> Page 변환
            }

            else{
                List<Bbs> bbsList = bbsRepository.findAll().stream().filter(x -> x.getContent().contains(keyword)).collect(Collectors.toList());

                start = (int)pageable.getOffset();
                end = Math.min((start + pageable.getPageSize()), bbsList.size());

                if(start > end)
                    start = end;

                Bbs = new PageImpl<Bbs>(bbsList.subList(start, end), pageable, bbsList.size());
            }

            for(Bbs bbs: Bbs){
                bbs.setDatetime(bbs.getCreatedAt().toString().replace("T", " "));
            }

            return Bbs;

        }


        Page<Bbs> Bbs = bbsRepository.findAll(PageRequest.of(p - 1,10, Sort.by(Sort.Direction.DESC, "id")));

        for(Bbs bbs: Bbs){
            bbs.setDatetime(bbs.getCreatedAt().toString().replace("T", " "));
        }

        return Bbs;
    }


    public boolean insertBbs(HttpServletRequest request, Model model) {   // 게시판 글 등록

        if(request.getParameter("Title").isEmpty() || request.getParameter("Content").isEmpty()){
            model.addAttribute("msg", "입력이 안된 사항이 있습니다.");
            return false;
        }

        else {
            Bbs bbs = new Bbs();

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

    public boolean updateBbs(HttpServletRequest request, Model model) {

        if(request.getParameter("Title").isEmpty() || request.getParameter("Content").isEmpty()){
            model.addAttribute("msg", "입력이 안된 사항이 있습니다.");
            return false;
        }

        else {
            Long id = Long.parseLong(request.getParameter("bbs_id"));
            String title = request.getParameter("Title");
            String content = request.getParameter("Content");

            Bbs bbs = bbsRepository.findById(id).orElse(null);
            Bbs updatedBbs = new Bbs(id, title, content, null, commentRepository.findByBbsId(id));

            try {
                if(bbs != null) {
                    bbsRepository.save(updatedBbs);
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

    public boolean insertComment(HttpServletRequest request, Model model) {

        if(request.getParameter("nickname").isEmpty() || request.getParameter("body").isEmpty()){
            model.addAttribute("msg", "내용을 입력해 주세요.");
            return false;
        }

        else{
            Bbs bbs = bbsRepository.findById(Long.parseLong(request.getParameter("bbs_id"))).get();
            Comment comment = new Comment(null, bbs, request.getParameter("nickname"), request.getParameter("body"), null);

            try {
                commentRepository.save(comment);
            }catch (Exception e){
                e.printStackTrace();
            }

            return true;
        }
    }

    public Long deleteComment(Long commentId) {
        Long bbsId = commentRepository.findById(commentId).get().getBbs().getId();

        Comment comment = commentRepository.findById(commentId).orElse(null);

        if(comment != null){
            try {
                commentRepository.delete(comment);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return bbsId;
    }


}
