package com.dyq.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import javax.persistence.*;

//@Getter isAccountNonLocked会冲突
@Setter
@ToString(callSuper = true)
public class User extends BaseModel implements UserDetails {

    private String avatar;
    private Date birth;
    private String classify;
    private String email;
    private Integer failtime;
    private Integer integral;
    private String introduction;
    private String name;
    private String password;
    private Integer sex;
    private String tel;
    private String username;
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked = true;
    @Transient
    private Boolean accountNonExpired = true;
    @Transient
    private Boolean credentialsNonExpired = true;
    private Boolean enable = true;
    //不JSON序列化年龄属性
    @JsonIgnore
    private List<Authority> authorities;

    //无法在此处获得权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //  需将 List<Authority> 转成 List<SimpleGrantedAuthority>，否则前端拿不到角色列表名称
       List<SimpleGrantedAuthority> simpleAuthorities = new ArrayList<>();
        for(GrantedAuthority authority : this.authorities){
            simpleAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return simpleAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setEncodePassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(password);
        this.password = encodePasswd;
    }

    public User(){
        setUpdatedAt(new Date());
        if(getId()==null)
            setCreatedAt(new Date());
    }

    public String getAvatar() {
        return avatar;
    }

    public Date getBirth() {
        return birth;
    }

    public String getClassify() {
        return classify;
    }

    public String getEmail() {
        return email;
    }

    public Integer getFailtime() {
        return failtime;
    }

    public Integer getIntegral() {
        return integral;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getName() {
        return name;
    }

    public Integer getSex() {
        return sex;
    }

    public String getTel() {
        return tel;
    }
}