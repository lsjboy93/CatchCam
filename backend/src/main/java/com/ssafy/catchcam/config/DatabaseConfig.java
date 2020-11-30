package com.ssafy.catchcam.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
		basePackages = "com.ssafy.catchcam.repository"
)
public class DatabaseConfig {}
