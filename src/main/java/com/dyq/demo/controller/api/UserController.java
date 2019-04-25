package com.dyq.demo.controller.api;

import com.dyq.demo.model.Authority;
import com.dyq.demo.model.Image;
import com.dyq.demo.model.User;
import com.dyq.demo.model.UserPasswordUpdate;
import com.dyq.demo.repository.AuthorityRepository;
import com.dyq.demo.repository.UserRepository;
import com.dyq.demo.service.AuthorityService;
import com.dyq.demo.service.UserService;
import com.dyq.demo.vo.Response;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

//@RestController
@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityService authorityService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @RequestMapping("list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "page", required = false, defaultValue = "1") int pageIndex,
                                           @RequestParam(value = "limit", required = false, defaultValue = "10") int pageSize) {
        List<User> userList = userService.findAll(pageIndex, pageSize);
        // todo 可以通过pageHelper的PageInfo得到总数量
        int userNum = userService.getCount();
        System.out.println("userNum:" + userNum);
        return ResponseEntity.ok().body(new Response(0, "用户列表", userNum, userList));

    }

    @RequestMapping(value = "/add")
    public String add() {
        return "api/addUser";
    }

    @ResponseBody
    @RequestMapping
    public User user() {
        return userService.getUserByPrincipal();
    }

    @ResponseBody
    @RequestMapping(value = "/view/{id}")
    public User view(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        User user = userService.findById(id);
        System.out.println("删除用户：" + id);
        userService.remove(id);
        return ResponseEntity.ok().body(new Response(0, "删除成功", 0, null));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Response> save(User user) {
        String msg = user.getId() == null ? "新增成功!" : "更新成功!";
        User updateUser = null;
        if (user.getId() != null) {
            updateUser = userService.findById(user.getId());
            updateUser.setAvatar(user.getAvatar());
            updateUser.setEmail(user.getEmail());
            updateUser.setIntroduction(user.getIntroduction());
            updateUser.setTel(user.getTel());
            updateUser.setUsername(user.getUsername());
            updateUser.setName(user.getName());
            updateUser.setSex(0);
            updateUser.setUpdatedAt(new Date());
            user = updateUser;
        } else {
            //新增
            user.setSex(0);
        }
        System.out.println("user:" + user);
        userService.save(user);
        return ResponseEntity.ok().body(new Response(0, msg, 0, null));
    }

    @ResponseBody
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        //密码 账号不可修改
        user.setPassword(null);
        user.setUsername(null);
        userRepository.updateByPrimaryKeySelective(user);
        //更新session中用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User query = new User();
        query.setId(id);
        List<Authority> authorities = authorityService.selectByUserId(id);
        User updateUser = userRepository.selectOne(query);
        updateUser.setAuthorities(authorities);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(updateUser, authentication.getCredentials(), authorities);
        auth.setDetails(authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return user;
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<Boolean> update(@RequestBody UserPasswordUpdate userPasswordUpdate) {
        User principal = userService.getUserByPrincipal();
        boolean passwordMatch = bCryptPasswordEncoder.matches(userPasswordUpdate.getOldPassword(), principal.getPassword());
        if (passwordMatch) {
            User user = new User();
            user.setId(principal.getId());
            user.setPassword(bCryptPasswordEncoder.encode(userPasswordUpdate.getUpdatePassword()));
            userRepository.updateByPrimaryKeySelective(user);
            return new ResponseEntity<>(true, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
