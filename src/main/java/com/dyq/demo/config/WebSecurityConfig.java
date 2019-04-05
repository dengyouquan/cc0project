package com.dyq.demo.config;

import com.dyq.demo.authentication.MyAuthenticationSuccessHandler;
import com.dyq.demo.authentication.SmsCodeAuthenticationProvider;
import com.dyq.demo.authentication.SmsCodeAuthenticationSecurityConfig;
import com.dyq.demo.authentication.discard.CustomAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    CustomAuthenticationDetailsSource customAuthenticationDetailsSource;
    @Autowired
    SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();   // 使用 BCrypt 加密
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        //CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
        SmsCodeAuthenticationProvider authenticationProvider = new SmsCodeAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder); // 设置密码加密方式
        return authenticationProvider;
    }

    //因为Spring-Security从4+升级到5+，不加passwordEncoder导致There is no PasswordEncoder mapped for the id “null”错误。
   /* @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    */
   //Encoded password does not look like BCrypt
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http
                .authorizeRequests()
                .antMatchers(
                        //URL
                        "/**","/login/**","/check/**","/register/**","/index",
                        "/api/**","/download/**","/search/**"
                        //static
                        ,"/css/**","/js/**","/layui/**","/layer/**", "/semantic/**","/images/**",
                        "/favicon.ico","/font/**", "/login/**","/video/**","/videojs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .successForwardUrl("/login/success")
                .permitAll()
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                //在SmsCodeAuthenticationSecurityConfig设置successForwardUrl("/login/success") 方法不行，设置myAuthenticationSuccessHandler
                //.authenticationDetailsSource(customAuthenticationDetailsSource)//自定义authenticationDetails源，login可以传递验证码
                .and()
                .logout()
                //默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/logout")
                //设置注销成功后跳转页面，默认是跳转到登录页面
                .logoutSuccessUrl("/index")
                .permitAll()
                .and()
                //开启cookie保存用户数据
                .rememberMe()
                //设置cookie有效期
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                //设置cookie的私钥
                .key("www.dengyouquan.cn")
                .rememberMeCookieName("cc0project")
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**","/upload/image","/uploadfile")
                .and()
        //只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
                .sessionManagement().maximumSessions(1).expiredUrl("/login");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth
                .inMemoryAuthentication()
                .withUser("dyq").password(new BCryptPasswordEncoder().encode("dyq")).roles("USER")
                .and()
                .withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("USER")
                .and();*/
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/static/**");
        //权限控制需要忽略所有静态资源，不然登录页面未登录状态无法加载css等静态资源
        /*web.ignoring().antMatchers("/css/**","/js/**","/layui/**",
                "/semantic/**","/images/**","/favicon.ico","/font/**",
                "/login/**","/video/**","/videojs/**");*/
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
