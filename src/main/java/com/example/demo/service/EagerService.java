package com.example.demo.service;

import com.example.demo.entity.user.Bbs;
import com.example.demo.entity.user.BugBbs;
import com.example.demo.entity.user.Comment;
import com.example.demo.entity.user.User;
import com.example.demo.jpa.repository.user.BbsRepository;
import com.example.demo.jpa.repository.user.BugBbsRepository;
import com.example.demo.jpa.repository.user.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional("userTransactionManager")
public class EagerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BbsRepository bbsRepository;

    @Autowired
    BugBbsRepository bugBbsRepository;

    public User getUserWithEagerProgress(String userId) {
        User user = userRepository.findByUserID(userId).get(0);
        Hibernate.initialize(user.getProgress());
        return user;
    }

    public Bbs getBbs(Long id){
        Bbs bbs = bbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

//        Hibernate.initialize(bbs.getComments());  // 강제 초기화 대신 Fetch join 을 활용했으므로 주석 처리
        return bbs;
    }

    public BugBbs getBugBbsWithEagerComments(Long id){
        BugBbs bbs = bugBbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        return bbs;
    }

    public List<Bbs> getBbsListWithEagerComments(String name, String keyword){

        List<Bbs> bbsList;

        if(name.equals("Title")){   // Title 로 검색할 경우
            bbsList = bbsRepository.findByTitleWithComments(keyword);   // join fetch comments
        }

        else if (name.equals("Writer")) {
            bbsList = bbsRepository.findByWriterWithComments(keyword);
        }

        else{
            bbsList = bbsRepository.findByContentWithComments(keyword);
        }

        return bbsList;
    }

    public List<BugBbs> getBugBbsListWithEagerComments(String name, String keyword){

        List<BugBbs> bbsList;

        if(name.equals("Title")){   // Title 로 검색할 경우
            bbsList = bugBbsRepository.findByTitleWithComments(keyword);
        }

        else if (name.equals("Writer")) {
            bbsList = bugBbsRepository.findByWriterWithComments(keyword);
        }

        else{
            bbsList = bugBbsRepository.findByContentWithComments(keyword);
        }

        return bbsList;
    }

    public Page<Bbs> getPagedBbsWithEagerComments(PageRequest pageable){
        return bbsRepository.findAll(pageable);
    }

    public Page<BugBbs> getPagedBugBbsWithEagerComments(PageRequest pageable){
        return bugBbsRepository.findAll(pageable);
    }

}
