package com.javastriker69.blog;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RexcoBlogApplication {

	@Bean
	public ModelMapper getMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(RexcoBlogApplication.class, args);
	}

}
