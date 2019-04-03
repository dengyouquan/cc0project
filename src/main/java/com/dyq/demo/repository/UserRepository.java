package com.dyq.demo.repository;

import com.dyq.demo.model.Authority;
import com.dyq.demo.model.User;
import com.dyq.demo.util.MyMapper;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserRepository extends MyMapper<User> {
    @Select("select count(*) from user")
    int getCount();
    @Select("select * from user where tel=#{tel}")
    User findByTel(String tel);
    @Select("select * from user where email=#{email}")
    User findByEmail(String email);
    @Select("select * from user where username=#{username}")
    User findByUsername(String username);
    @Select("select a.id,a.created_at,a.updated_at,a.`name` from user u join join_user_authority ua on u.id=ua.user_id join authority a on a.id=ua.authority_id where u.id=#{id}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "createAt",column = "create_at"),
            @Result(property = "updateAt",column = "update_at"),
            @Result(property = "name",column = "name")
    })
    List<Authority> findAuthoritysByUserId(Long userid);
}