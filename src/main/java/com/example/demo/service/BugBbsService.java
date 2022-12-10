package com.example.demo.service;

import com.example.demo.entity.user.*;
import com.example.demo.jpa.repository.user.BugBbsRepository;
import com.example.demo.jpa.repository.user.BugCommentRepository;
import com.example.demo.jpa.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BugBbsService {

    @Autowired
    BugBbsRepository bugBbsRepository;

    @Autowired
    BugCommentRepository bugCommentRepository;

    @Autowired
    UserRepository userRepository;

    public BugBbs getBbs(Long id){
        BugBbs bbs = bugBbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        return bbs;
    }

    public Page<BugBbs> listToPage(List<BugBbs> bbsList, PageRequest pagable){

        Page<BugBbs> Bbs;

        int start = (int)pagable.getOffset();
        int end = Math.min((start + pagable.getPageSize()), bbsList.size());

        if(start > end)
            start = end;

        Bbs = new PageImpl<BugBbs>(bbsList.subList(start, end), pagable, bbsList.size());

        return Bbs;
    }
    public Page<BugBbs> getBbsList(HttpServletRequest request, int p){

        Page<BugBbs> Bbs;

        if(request.getParameter("keyword") != null && !(request.getParameter("keyword").equals(""))) {
            String name = request.getParameter("name");
            String keyword = request.getParameter("keyword");

            PageRequest pageable = PageRequest.of(p - 1,10, Sort.by(Sort.Direction.DESC, "id"));

            if(name.equals("Title")){   // Title 로 검색할 경우
                List<BugBbs> bbsList = bugBbsRepository.findAll().stream().filter(x -> x.getTitle().contains(keyword)).collect(Collectors.toList());

                Bbs = listToPage(bbsList, pageable);   // List -> Page 변환
            }

            else if (name.equals("Writer")) {
                List<BugBbs> bbsList = bugBbsRepository.findAll().stream().filter(x -> x.getUser().getUserName().contains(keyword)).collect(Collectors.toList());

                Bbs = listToPage(bbsList, pageable);
            }

            else{
                List<BugBbs> bbsList = bugBbsRepository.findAll().stream().filter(x -> x.getContent().contains(keyword)).collect(Collectors.toList());

                Bbs = listToPage(bbsList, pageable);
            }

            for(BugBbs bbs: Bbs){
                bbs.setDatetime(bbs.getCreatedAt().toString().replace("T", " "));
            }

            return Bbs;

        }

        Bbs = bugBbsRepository.findAll(PageRequest.of(p - 1,10, Sort.by(Sort.Direction.DESC, "id")));

        for(BugBbs bbs: Bbs){
            bbs.setDatetime(bbs.getCreatedAt().toString().replace("T", " "));
        }

        return Bbs;
    }

    public boolean insertBbs(HttpServletRequest request,String userID, Model model) {   // 게시판 글 등록

        if(request.getParameter("Title").isEmpty() || request.getParameter("Content").isEmpty())
            return false;

        else {
            User user = userRepository.findByUserId(userID).get(0);
            BugBbs bbs = new BugBbs();

            bbs.setUser(user);
            bbs.setTitle(request.getParameter("Title"));
            bbs.setContent(request.getParameter("Content"));

            try {
                bugBbsRepository.save(bbs);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public String getUserIdByBbsId(Long bbs_id){
        return bugBbsRepository.findById(bbs_id).get().getUser().getUserID();
    }

    public String getUserIdByCommentId(Long comment_id) {
        return bugCommentRepository.findById(comment_id).get().getUser().getUserID();
    }

    public boolean updateBbs(HttpServletRequest request) {

        if(request.getParameter("Title").isEmpty() || request.getParameter("Content").isEmpty())
            return false;

        else {
            Long bbsID = Long.parseLong(request.getParameter("bbs_id"));
            String title = request.getParameter("Title");
            String content = request.getParameter("Content");

            String userID = bugBbsRepository.findById(bbsID).get().getUser().getUserID();
            User user = userRepository.findByUserId(userID).get(0);

            BugBbs bbs = bugBbsRepository.findById(bbsID).orElse(null);
            BugBbs updatedBbs = new BugBbs(bbsID, title, user, content, null, bugCommentRepository.findByBbsId(bbsID));

            try {
                if(bbs != null) {
                    bugBbsRepository.save(updatedBbs); // 게시판 수정
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }


    public void deleteBbs(Long bbsId) {

        BugBbs bbs = bugBbsRepository.findById(bbsId).orElse(null);

        if(bbs != null){
            try {
                bugBbsRepository.delete(bbs);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public List<BugComment> getComments(Long id){

        List<BugComment> comments =  bugCommentRepository.findByBbsId(id);

        for(BugComment c : comments){
            c.setDatetime(c.getCreatedAt().toString().replace("T", " "));
        }

        return comments;
    }

    public boolean insertComment(HttpServletRequest request, User user, Model model) {

        if(request.getParameter("body").isEmpty())
            return false;

        else{
            User User = userRepository.findByUserId(user.getUserID()).get(0);
            BugBbs bbs = bugBbsRepository.findById(Long.parseLong(request.getParameter("bbs_id"))).get();
            BugComment comment = new BugComment(null, bbs, User, User.getUserName(), request.getParameter("body"), null);

            try {
                bugCommentRepository.save(comment);
            }catch (Exception e){
                e.printStackTrace();
            }

            return true;
        }
    }

    public void deleteComment(Long commentId) {
        Long bbsId = bugCommentRepository.findById(commentId).get().getBbs().getId();

        BugComment comment = bugCommentRepository.findById(commentId).orElse(null);

        if(comment != null){
            try {
                bugCommentRepository.delete(comment);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
