package com.dyq.demo;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dyq.demo.repository")

//FastDFS
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)//为了解决JMX重复注册bean的问题
@Import(FdfsClientConfig.class)//只需要一行注解 @Import(FdfsClientConfig.class)就可以拥有带有连接池的FastDFS Java客户端了
public class CC0ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CC0ProjectApplication.class, args);
	}
}
