package com.dyq.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
//@EnableWebMvc 会使得@EnableAutoConfiguration无效
// 直接实现WebMvcConfigurer （官方推荐）
//直接继承WebMvcConfigurationSupport
public class MVCConfig implements WebMvcConfigurer {

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //自定义静态资源映射目录 static可以不用映射
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/course").setViewName("course");
        registry.addViewController("/course/main").setViewName("coursedetail");
        registry.addViewController("/course/study").setViewName("courseLearn");
        registry.addViewController("/course/list").setViewName("courses");
        //登录视图映射
        //registry.addViewController("/api/login").setViewName("login/login");
        registry.addViewController("/login/reg").setViewName("login/reg");
        registry.addViewController("/login/getpwd").setViewName("login/getpwd");
        registry.addViewController("/login/protocol").setViewName("login/protocol");
        //api视图映射
        registry.addViewController("/api/image").setViewName("api/image");
        registry.addViewController("/images").setViewName("images");
        registry.addViewController("/api/image/add").setViewName("uploadimage");
        registry.addViewController("/api/resource").setViewName("api/resource");
        registry.addViewController("/api/resource/add").setViewName("uploadresource");
        registry.addViewController("/api/authority").setViewName("api/authority");
        registry.addViewController("/api/authority/add").setViewName("addAuthority");
        registry.addViewController("/api/user").setViewName("api/user");
        registry.addViewController("/api/user/add").setViewName("addUser");

        registry.addViewController("/search/image").setViewName("search/image");
        registry.addViewController("/error").setViewName("error");
    }


    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

/*配置http自动转为https*/
/*@Bean
public ServletWebServerFactory servletWebServerFactory(){
    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory(){
        @Override
        protected void postProcessContext(Context context) {
            SecurityConstraint securityConstraint = new SecurityConstraint();
            securityConstraint.setUserConstraint("CONFIDENTIAL");//机密的
            SecurityCollection securityCollection = new SecurityCollection();
            securityCollection.addPattern("/*");
            securityConstraint.addCollection(securityCollection);
            context.addConstraint(securityConstraint);
        }
    };
    factory.addAdditionalTomcatConnectors(httpConnector());
    return factory;
}

@Bean
public Connector httpConnector(){
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    connector.setPort(8888);
    connector.setSecure(false);
    connector.setRedirectPort(8080);
    return  connector;
}*/
}
