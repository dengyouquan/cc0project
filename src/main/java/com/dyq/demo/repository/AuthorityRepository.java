package com.dyq.demo.repository;

import com.dyq.demo.model.Authority;
import com.dyq.demo.model.User;
import com.dyq.demo.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AuthorityRepository extends MyMapper<Authority> {
    @Select("select count(*) from authority")
    int getCount();

    List<Authority> selectByUserId(Long userId);
}