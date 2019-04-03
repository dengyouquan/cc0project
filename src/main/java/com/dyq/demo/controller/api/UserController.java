package com.dyq.demo.controller.api;

import com.dyq.demo.model.Image;
import com.dyq.demo.model.User;
import com.dyq.demo.service.UserService;
import com.dyq.demo.vo.Response;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * CreatedDate:2018/6/2
 * Author:dyq
 */
//@RestController
@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("list")
    public ResponseEntity<Response> getAll(@RequestParam(value="page",required=false,defaultValue="1") int pageIndex,
                                 @RequestParam(value="limit",required=false,defaultValue="10") int pageSize){
        List<User> userList = userService.findAll(pageIndex,pageSize);
        // todo 可以通过pageHelper的PageInfo得到总数量
        int userNum = userService.getCount();
        System.out.println("userNum:"+userNum);
        return ResponseEntity.ok().body(new Response(0,"用户列表",userNum,userList));

    }

    @RequestMapping(value = "/add")
    public String add() {
        return "api/addUser";
    }

    @ResponseBody
    @RequestMapping(value = "/view/{id}")
    public User view(@PathVariable Long id) {
        ModelAndView result = new ModelAndView();
        User user = userService.findById(id);
        return user;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        User user = userService.findById(id);
        System.out.println("删除用户："+id);
        userService.remove(id);
        return ResponseEntity.ok().body(new Response(0,"删除成功",0,null));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Response> save(User user) {
        String msg = user.getId() == null ? "新增成功!" : "更新成功!";
        User updateUser = null;
        if(user.getId()!=null){
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
        }else {
            //新增
            user.setSex(0);
        }
        System.out.println("user:"+user);
        userService.save(user);
        return ResponseEntity.ok().body(new Response(0,msg,0,null));
    }
}
