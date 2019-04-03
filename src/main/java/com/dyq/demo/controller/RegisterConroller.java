package com.dyq.demo.controller;

import com.dyq.demo.model.Authority;
import com.dyq.demo.model.User;
import com.dyq.demo.service.AuthorityService;
import com.dyq.demo.service.UserService;
import com.dyq.demo.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.*;

@RestController
public class RegisterConroller {
    private static final Long ROLE_USER_AUTHORITY_ID = 2L;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/login/setauthority")
    public ResponseEntity<Response> setauthority() {
        Authority authority = new Authority("ROLE_ADMIN");
        authority.setId(1L);
        Authority authority1 = new Authority("ROLE_USER");
        authority1.setId(2L);
        Authority authority2 = new Authority("ROLE_TEACHER");
        authority2.setId(3L);
        authorityService.save(authority);
        authorityService.save(authority1);
        authorityService.save(authority2);
        return ResponseEntity.ok().body(new Response(0, "建立角色成功",0,"建立角色成功"));
    }

    /**
     * 注册用户
     * @param user
     * @param result
     * @param redirect
     * @return
     */
    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public String registerUser(User user) {
        List<Authority> authorities = new ArrayList<>();
        Authority a= authorityService.findById(ROLE_USER_AUTHORITY_ID);
        authorities.add(a);
        user.setAuthorities(authorities);
        user.setAvatar("/images/avatar/large/elliot.jpg");
        System.out.println(user.toString());
        userService.save(user);
        return "redirect:/login";
    }

    /**
     * 新建用户
     * @param user
     * @param result
     * @param redirect
     * @return
     */
    @PostMapping("/user")
    public ModelAndView create(User user, Long authorityId) {
        List<Authority> authorities = new ArrayList<>();
        Authority a= authorityService.findById(ROLE_USER_AUTHORITY_ID);
        authorities.add(a);
        user.setAuthorities(authorities);
        user.setAvatar("/images/avatar/large/elliot.jpg");
        if(user.getId() == null) {
            user.setEncodePassword(user.getPassword()); // 加密密码
        }else {
            User originalUser = userService.findById(user.getId());
            //重新赋值create_at
            user.setCreatedAt(originalUser.getCreatedAt());
            // 判断密码是否做了变更
            String rawPassword = originalUser.getPassword();
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodePasswd = encoder.encode(user.getPassword());
            boolean isMatch = encoder.matches(rawPassword, encodePasswd);
            if (!isMatch) {
                user.setEncodePassword(user.getPassword());
            }else {
                user.setPassword(user.getPassword());
            }
        }
        System.out.println("save:"+user.toString()+",userid:"+user.getId());
        try {
            userService.save(user);
        }  catch (ConstraintViolationException e)  {
            return new ModelAndView("redirect:/index");
             }
        return new ModelAndView("redirect:/index");
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping(value = "user/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model) {
        try {
            userService.remove(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(0, e.getMessage(),0,""));
        }
        return  ResponseEntity.ok().body( new Response(0, "处理成功",0,""));
    }
}
