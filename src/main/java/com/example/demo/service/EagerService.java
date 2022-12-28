package com.example.demo.service;

import com.example.demo.entity.user.Bbs;
import com.example.demo.entity.user.BugBbs;
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
        User user = userRepository.findByUserId(userId).get(0);
        Hibernate.initialize(user.getProgress());
        return user;
    }

    public Bbs getBbsWithEagerComments(Long id){
        Bbs bbs = bbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Hibernate.initialize(bbs.getComments());

        return bbs;
    }

    public BugBbs getBugBbsWithEagerComments(Long id){
        BugBbs bbs = bugBbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Hibernate.initialize(bbs.getComments());

        return bbs;
    }

    public List<Bbs> getBbsListWithEagerComments(String name, String keyword){

        List<Bbs> bbsList;

        if(name.equals("Title")){   // Title 로 검색할 경우
            bbsList = bbsRepository.findAll().stream().filter(x -> x.getTitle().contains(keyword)).collect(Collectors.toList());

            for(Bbs bbs : bbsList){Hibernate.initialize(bbs.getComments());}
        }

        else if (name.equals("Writer")) {
            bbsList = bbsRepository.findAll().stream().filter(x -> x.getUser().getUserName().contains(keyword)).collect(Collectors.toList());

            for(Bbs bbs : bbsList){Hibernate.initialize(bbs.getComments());}
        }

        else{
            bbsList = bbsRepository.findAll().stream().filter(x -> x.getContent().contains(keyword)).collect(Collectors.toList());

            for(Bbs bbs : bbsList){Hibernate.initialize(bbs.getComments());}
        }

        return bbsList;

    }

    public List<BugBbs> getBugBbsListWithEagerComments(String name, String keyword){

        List<BugBbs> bbsList;

        if(name.equals("Title")){   // Title 로 검색할 경우
            bbsList = bugBbsRepository.findAll().stream().filter(x -> x.getTitle().contains(keyword)).collect(Collectors.toList());

            for(BugBbs bbs : bbsList){Hibernate.initialize(bbs.getComments());}
        }

        else if (name.equals("Writer")) {
            bbsList = bugBbsRepository.findAll().stream().filter(x -> x.getUser().getUserName().contains(keyword)).collect(Collectors.toList());

            for(BugBbs bbs : bbsList){Hibernate.initialize(bbs.getComments());}
        }

        else{
            bbsList = bugBbsRepository.findAll().stream().filter(x -> x.getContent().contains(keyword)).collect(Collectors.toList());

            for(BugBbs bbs : bbsList){Hibernate.initialize(bbs.getComments());}
        }


        return bbsList;

    }

    public Page<Bbs> getPagedBbsWithEagerComments(PageRequest pageable){
        Page<Bbs> Bbs = bbsRepository.findAll(pageable);

        for(Bbs bbs : Bbs){Hibernate.initialize(bbs.getComments());}

        return Bbs;

    }

    public Page<BugBbs> getPagedBugBbsWithEagerComments(PageRequest pageable){
        Page<BugBbs> Bbs = bugBbsRepository.findAll(pageable);

        for(BugBbs bbs : Bbs){Hibernate.initialize(bbs.getComments());}

        return Bbs;

    }

}
