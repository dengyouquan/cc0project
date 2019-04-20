package com.dyq.demo.service;

import com.dyq.demo.model.Authority;
import com.dyq.demo.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl extends BaseModelServiceImpl<Authority> implements AuthorityService {
    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public int getCount() {
        return authorityRepository.getCount();
    }

    @Override
    public List<Authority> selectByUserId(Long userId) {
        return authorityRepository.selectByUserId(userId);
    }
}
