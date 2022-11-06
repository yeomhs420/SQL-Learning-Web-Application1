package com.example.demo.controller;

import com.example.demo.entity.sampledata.Bbs;
import com.example.demo.entity.sampledata.Comment;
import com.example.demo.service.BbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/generalforum")
public class GeneralForumController {

    @Autowired
    BbsService bbsService;
    @RequestMapping({"/", ""})
    public String generalforum(Model model, HttpServletRequest request){

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
            if(bbsService.getBbsList(request, i).hasNext())   // 다음 페이지에 게시글이 존재할 시 index 표시
                pList.add(i + 1);
            else
                break;
        }


        model.addAttribute("bbsList", bbsService.getBbsList(request, page));   // 현재 페이지 게시글 list
        model.addAttribute("pageIndexList", pList);


        if(fpage != 1)
            model.addAttribute("preIndex", fpage - 5);  // 이전 페이지

        if(bbsService.getBbsList(request, fpage + 4).hasNext())
            model.addAttribute("nextIndex", fpage + 5); // 다음 페이지

        if(request.getParameter("keyword") != null && !(request.getParameter("keyword").equals(""))) {
            String name = request.getParameter("name");
            String keyword = request.getParameter("keyword");
            searchExist = true;

            model.addAttribute("searchExist", searchExist);
            model.addAttribute("name", name);
            model.addAttribute("keyword", keyword);
        }   // 검색 내용이 있을 시


        return "/board/generalforum";
    }

    @RequestMapping("/bbs_view")
    public String bbsView(Model model, HttpServletRequest request){

        Long bbsId = Long.parseLong(request.getParameter("bbs_id"));

        Bbs bbs = bbsService.getBbs(bbsId);

        List<Comment> comments = bbsService.getComments(bbsId);

        model.addAttribute("bbs", bbs);
        model.addAttribute("comments", comments);
        model.addAttribute("cmtCount", comments.size());

        return "/board/bbs_view";
    }

    @RequestMapping("/write")
    public String bbsWrite(Model model){

        return "/board/write";
    }

    @RequestMapping("/writeAction")
    public String bbsWriteAction(Model model, HttpServletRequest request){

        if(bbsService.insertBbs(request, model))
            return "redirect:/generalforum";

        else
            return "/board/write";
    }

    @RequestMapping("/bbs_update")
    public String bbsUpdate(@RequestParam Long bbs_id, Model model){

        Bbs bbs = bbsService.getBbs(bbs_id);

        model.addAttribute("bbs", bbs);

        return "/board/bbs_update";
    }

    @RequestMapping("/bbs_updateAction")
    public String bbsUpdateAction(HttpServletRequest request, Model model){

        model.addAttribute("bbsId", request.getParameter("bbs_id"));

        if(bbsService.updateBbs(request, model))
            return "redirect:bbs_view?bbs_id=" + request.getParameter("bbs_id");

        else
            return "/board/bbs_update";

    }


    @RequestMapping("/bbs_delete")
    public String bbsDelete(@RequestParam Long bbs_id){

        bbsService.deleteBbs(bbs_id);

        return "redirect:/generalforum";
    }


    @RequestMapping("/commentAction")
    public String commentAction(HttpServletRequest request, Model model){

        model.addAttribute("bbsId", request.getParameter("bbs_id"));

        if(bbsService.insertComment(request, model))
            return "redirect:bbs_view?bbs_id=" + request.getParameter("bbs_id");
        else
            return "/board/bbs_view";

    }

    @RequestMapping("/comment_delete")
    public String commentDelete(@RequestParam Long comment_id){

        Long bbs_id = bbsService.deleteComment(comment_id);

        return "redirect:/generalforum/bbs_view?bbs_id=" + bbs_id;

    }
}
