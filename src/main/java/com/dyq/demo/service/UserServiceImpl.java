package com.dyq.demo.service;

import com.dyq.demo.model.Authority;
import com.dyq.demo.repository.UserRepository;
import com.dyq.demo.model.User;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl extends BaseModelServiceImpl<User> implements UserService, UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public int getCount() {
        return userRepository.getCount();
    }

    @Override
    public User findByTel(String tel) {
        User user = userRepository.findByTel(tel);
        if (user == null) return null;
        List<Authority> authorities = findAuthoritysById(user.getId());
        user.setAuthorities(authorities);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) return null;
        List<Authority> authorities = findAuthoritysById(user.getId());
        user.setAuthorities(authorities);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;
        List<Authority> authorities = findAuthoritysById(user.getId());
        user.setAuthorities(authorities);
        return user;
    }

    @Override
    public User findByUsernameOrEmailOrPhone(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null || (user = userRepository.findByEmail(username)) != null ||
                (user = userRepository.findByTel(username)) != null) {
            List<Authority> authorities = findAuthoritysById(user.getId());
            user.setAuthorities(authorities);
            return user;
        }
        return null;
    }

    @Override
    public User getUserByPrincipal() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByUsernameOrEmailOrPhone(principal.getUsername());
    }

    @Override
    public List<Authority> findAuthoritysById(Long id) {
        return userRepository.findAuthoritysByUserId(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            //用邮箱尝试
            User emailUser = userRepository.findByEmail(username);
            if (emailUser == null) {
                //用手机号尝试
                User phoneUser = userRepository.findByTel(username);
                if (phoneUser == null) {
                    throw new UsernameNotFoundException("用户不存在");
                } else {
                    List<SimpleGrantedAuthority> grantedAuthorities = getAuthorities(phoneUser);
                    LOGGER.info("手机号密码登录》phoneUser:{}", phoneUser);
                    return new org.springframework.security.core.userdetails.User(phoneUser.getTel(), phoneUser.getPassword(), phoneUser.isEnabled(), phoneUser.isAccountNonExpired(), phoneUser.isCredentialsNonExpired(), phoneUser.isAccountNonLocked()
                            , grantedAuthorities);
                    /*return new org.springframework.security.core.userdetails.User(phoneUser.getTel(), phoneUser.getPassword(), phoneUser.isEnabled(), phoneUser.isAccountNonExpired(), phoneUser.isCredentialsNonExpired(), phoneUser.isAccountNonLocked()
                            ,phoneUser.getAuthorities());*/
                }
            } else {
                List<SimpleGrantedAuthority> grantedAuthorities = getAuthorities(emailUser);
                LOGGER.info("邮箱号密码登录》emailUser:" + emailUser);
                return new org.springframework.security.core.userdetails.User(emailUser.getEmail(), emailUser.getPassword(), emailUser.isEnabled(), emailUser.isAccountNonExpired(), emailUser.isCredentialsNonExpired(), emailUser.isAccountNonLocked()
                        , grantedAuthorities);
            }
        } else {
            List<SimpleGrantedAuthority> grantedAuthorities = getAuthorities(user);
            LOGGER.info("用户名密码登录》user:" + user);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked()
                    , grantedAuthorities);
        }
        //return user;
    }

    public List<SimpleGrantedAuthority> getAuthorities(User user) {
        List<Authority> authorities = findAuthoritysById(user.getId());
        //在这里设置权限，不在实体类中
        user.setAuthorities(authorities);
        System.out.println("authorities:" + authorities);
        //  需将 List<Authority> 转成 List<SimpleGrantedAuthority>，否则前端拿不到角色列表名称
        List<SimpleGrantedAuthority> simpleAuthorities = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            simpleAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return simpleAuthorities;
    }
}
