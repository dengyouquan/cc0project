package com.dyq.demo.service;

import com.dyq.demo.model.Authority;
import com.dyq.demo.model.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService extends BaseModelService<User> {
    int getCount();

    User findByTel(String tel);

    User findByEmail(String email);

    User findByUsername(String username);

    User findByUsernameOrEmailOrPhone(String username);

    User getUserByPrincipal();

    List<Authority> findAuthoritysById(Long id);
}
