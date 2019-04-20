package com.dyq.demo;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dyq.demo.repository")

//FastDFS
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)//为了解决JMX重复注册bean的问题
@Import(FdfsClientConfig.class)//只需要一行注解 @Import(FdfsClientConfig.class)就可以拥有带有连接池的FastDFS Java客户端了
//选择spring security提供的方法权限控制，有更多的功能
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CC0ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CC0ProjectApplication.class, args);
	}
}
