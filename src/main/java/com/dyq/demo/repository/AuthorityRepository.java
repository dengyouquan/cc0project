package com.dyq.demo.repository;

import com.dyq.demo.model.Authority;
import com.dyq.demo.model.User;
import com.dyq.demo.util.MyMapper;
import org.apache.ibatis.annotations.*;

public interface AuthorityRepository extends MyMapper<Authority> {
    @Select("select count(*) from authority")
    int getCount();
}