package com.dyq.demo.authentication.discard;

import com.dyq.demo.model.User;
import com.dyq.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

//判断登录次数，是否锁定
public class CustomAuthenticationProvider1 extends DaoAuthenticationProvider {
    @Autowired
    UserService userService;
    private static final int MAX_FAILTIME = 3;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authResult = null;
        try{
            System.out.println(authentication.getName());
            authResult =  super.authenticate(authentication);
            try{//验证成功，重置密码错误次数。
                User user = (User) userService.findByUsername(authentication.getName());
                user.setFailtime(0);
                this.userService.save(user);
            }
            catch(Exception exp){
                exp.printStackTrace();
            }
        }
        catch(BadCredentialsException ex){//密码错误,增加密码错误次数，达到最大次数时锁定账户。
            try{
                User user = (User) userService.findByUsername(authentication.getName());
                int num = user.getFailtime()+1;
                if(num==MAX_FAILTIME){
                    user.setFailtime(0);
                    user.setAccountNonLocked(false);
                }else{
                    user.setFailtime(num);
                }
                this.userService.save(user);
            }
            catch(Exception exp){
                //没有此账号
                exp.printStackTrace();
            }
            throw ex;

        }
        catch(AuthenticationException ex){
            throw ex;
        }
        return authResult;
    }
}
