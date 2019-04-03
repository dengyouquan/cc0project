package com.dyq.demo.controller.api;

import com.dyq.demo.model.Authority;
import com.dyq.demo.service.AuthorityService;
import com.dyq.demo.service.UserService;
import com.dyq.demo.vo.Response;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * CreatedDate:2018/5/29
 * Author:dyq
 */
//@RestController
@Controller
@RequestMapping("/api/authority")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public List<Authority> authoritys(@PathVariable Long id){
        return userService.findAuthoritysById(id);
    }

    @GetMapping("/view1/{id}")
    public Collection<? extends GrantedAuthority> authoritys2(@PathVariable Long id){
        //todo 会报错，因为我是从service的loadUserName中获得权限信息，而不是从实体类中，实体类中没法获取权限消息
        return userService.findById(id).getAuthorities();
    }

    @RequestMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value="page",required=false,defaultValue="1") int pageIndex,
                                           @RequestParam(value="limit",required=false,defaultValue="10") int pageSize){
        List<Authority> authorityList = authorityService.findAll(pageIndex,pageSize);
        // todo 可以通过pageHelper的PageInfo得到总数量
        int authorityNum = authorityService.getCount();
        System.out.println("authorityNum:"+authorityNum);
        return ResponseEntity.ok().body(new Response(0,"权限列表",authorityNum,authorityList));
    }

    @RequestMapping(value = "/add")
    public String add() {
        return "api/addAuthority";
    }

    @ResponseBody
    @RequestMapping(value = "/view/{id}")
    public Authority view(@PathVariable Long id) {
        ModelAndView result = new ModelAndView();
        Authority authority = authorityService.findById(id);
        return authority;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        Authority authority = authorityService.findById(id);
        System.out.println("删除权限："+id);
        authorityService.remove(id);
        return ResponseEntity.ok().body(new Response(0,"删除成功",0,null));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Response> save(Authority authority) {
        String msg = authority.getId() == null ? "新增成功!" : "更新成功!";
        Authority updateAuthority = null;
        if(authority.getId()!=null) {
            updateAuthority = authorityService.findById(authority.getId());
            updateAuthority.setUpdatedAt(new Date());
            updateAuthority.setName("ROLE_"+authority.getName());
            authority = updateAuthority;
        }
        authorityService.save(authority);
        return ResponseEntity.ok().body(new Response(0,msg,0,null));
    }
}
