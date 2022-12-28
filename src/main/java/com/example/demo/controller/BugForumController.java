package com.example.demo.controller;

import com.example.demo.entity.user.Bbs;
import com.example.demo.entity.user.BugBbs;
import com.example.demo.entity.user.BugComment;
import com.example.demo.entity.user.User;
import com.example.demo.service.BugBbsService;
import com.example.demo.service.EagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bugforum")
public class BugForumController {

    @Autowired
    BugBbsService bugBbsService;

    @Autowired
    HttpSession session;

    @Autowired
    EagerService eagerService;

    @RequestMapping({"/", ""})
    public String bugforum(Model model, HttpServletRequest request){

        boolean searchExist = false;    // 검색 유무
        List<Integer> pList = new ArrayList<>();
        int page = 1;

        if(request.getParameter("p") != null)
            page = Integer.parseInt(request.getParameter("p"));    // 현재 페이지

        int fpage = page - (page - 1) % 5;    // 1~5 -> 1 , 6~10 -> 6

        for(int i = fpage; i <= page; i++){
            pList.add(i);   // 현재 index(p)까지 표시
        }

        for(int i = page; i < fpage + 4; i++){
            if(bugBbsService.getBbsList(request, i).hasNext())   // 다음 페이지에 게시글이 존재할 시 index 표시
                pList.add(i + 1);
            else
                break;
        }


        model.addAttribute("bbsList", bugBbsService.getBbsList(request, page));   // 현재 페이지 게시글 list
        model.addAttribute("pageIndexList", pList);


        if(fpage != 1)
            model.addAttribute("preIndex", fpage - 5);  // 이전 페이지

        if(bugBbsService.getBbsList(request, fpage + 4).hasNext())
            model.addAttribute("nextIndex", fpage + 5); // 다음 페이지

        if(request.getParameter("keyword") != null && !(request.getParameter("keyword").equals(""))) {
            String name = request.getParameter("name");
            String keyword = request.getParameter("keyword");
            searchExist = true;

            model.addAttribute("searchExist", searchExist);
            model.addAttribute("name", name);
            model.addAttribute("keyword", keyword);
        }   // 검색 내용이 있을 시


        return "/board/bugforum";
    }

    @RequestMapping("/bbs_view")
    public String bbsView(Model model, HttpServletRequest request){

        Long bbsId = Long.parseLong(request.getParameter("bbs_id"));

        BugBbs bbs = eagerService.getBugBbsWithEagerComments(bbsId);

        List<BugComment> comments = bugBbsService.getComments(bbsId);

        model.addAttribute("bbs", bbs);
        model.addAttribute("comments", comments);
        model.addAttribute("cmtCount", comments.size());

        return "/board/bugbbs_view";
    }

    @RequestMapping("/write")
    public String bbsWrite(Model model){

        return "/board/bugwrite";
    }

    @RequestMapping("/writeAction")
    public String bbsWriteAction(RedirectAttributes re, HttpServletRequest request){
        User User = (User)session.getAttribute("user");

        if(bugBbsService.insertBbs(request,User.getUserID(), re))
            return "redirect:/bugforum";

        else {
            re.addFlashAttribute("msg", "입력이 안된 사항이 있습니다.");
            return "redirect:/bugforum/write";
        }
    }

    @RequestMapping("/bbs_update")
    public String bbsUpdate(@RequestParam Long bbs_id, Model model, RedirectAttributes re){

        User user = (User)session.getAttribute("user");

        if(user == null) {
            re.addFlashAttribute("msg", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        String sessionID = user.getUserID();

        if(!sessionID.equals(bugBbsService.getUserIdByBbsId(bbs_id))){
            re.addFlashAttribute("msg", "권한이 없습니다");
            return "redirect:/bugforum/bbs_view?bbs_id="+bbs_id;
        }

        BugBbs bbs = bugBbsService.getBbs(bbs_id);

        model.addAttribute("bbs", bbs);

        return "/board/bugbbs_update";
    }

    @RequestMapping("/bbs_updateAction")
    public String bbsUpdateAction(HttpServletRequest request, RedirectAttributes re){

        if(bugBbsService.updateBbs(request))
            return "redirect:bbs_view?bbs_id=" + request.getParameter("bbs_id");

        else {
            re.addFlashAttribute("msg", "입력이 안된 사항이 있습니다.");
            return "redirect:/bugforum/bbs_update?bbs_id=" + request.getParameter("bbs_id");
        }

    }

    @RequestMapping("/bbs_delete")
    public String bbsDelete(@RequestParam Long bbs_id, RedirectAttributes re){

        User user = (User)session.getAttribute("user");

        if(user == null) {
            re.addFlashAttribute("msg", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        String sessionID = user.getUserID();

        if(!sessionID.equals(bugBbsService.getUserIdByBbsId(bbs_id))){
            re.addFlashAttribute("msg", "권한이 없습니다");
            return "redirect:/bugforum/bbs_view?bbs_id="+bbs_id;
        }

        bugBbsService.deleteBbs(bbs_id);

        return "redirect:/bugforum";
    }

    @RequestMapping("/commentAction")
    public String commentAction(HttpServletRequest request, Model model, RedirectAttributes re){

        model.addAttribute("bbsId", request.getParameter("bbs_id"));

        User user = (User)session.getAttribute("user");

        if(user == null) {
            re.addFlashAttribute("msg", "로그인이 필요합니다.");
            return "redirect:bbs_view?bbs_id=" + request.getParameter("bbs_id");
        }

        if(bugBbsService.insertComment(request, user, model))
            return "redirect:bbs_view?bbs_id=" + request.getParameter("bbs_id");
        else {
            re.addFlashAttribute("msg", "내용을 입력해 주세요.");
            return "redirect:bbs_view?bbs_id=" + request.getParameter("bbs_id");
        }

    }

    @RequestMapping("/comment_delete")
    public String commentDelete(@RequestParam Long bbs_id, @RequestParam Long comment_id, RedirectAttributes re){

        User user = (User)session.getAttribute("user");

        if(user == null) {
            re.addFlashAttribute("msg", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        String sessionID = user.getUserID();

        if(!sessionID.equals(bugBbsService.getUserIdByCommentId(comment_id))){
            re.addFlashAttribute("msg", "권한이 없습니다");
            return "redirect:/bugforum/bbs_view?bbs_id="+ bbs_id;
        }

        bugBbsService.deleteComment(comment_id);

        return "redirect:/bugforum/bbs_view?bbs_id=" + bbs_id;

    }
}
