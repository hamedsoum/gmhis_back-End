package com.gmhis_backk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import static com.gmhis_backk.constant.FileConstant.APP_PARAM_FOLDER;
import static com.gmhis_backk.constant.FileConstant.ARTICLE_FOLDER;
import static com.gmhis_backk.constant.FileConstant.CUSTOMER_GUARANTEE_FOLDER;
import static com.gmhis_backk.constant.FileConstant.IMAGE_FOLDER;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@CrossOrigin(origins = "*")
@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableJpaRepositories(enableDefaultTransactions = false)
public class GmhisApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GmhisApplication.class, args);
		new File(IMAGE_FOLDER).mkdirs();
		new File(APP_PARAM_FOLDER).mkdirs();
		new File(ARTICLE_FOLDER).mkdirs();
		new File(CUSTOMER_GUARANTEE_FOLDER).mkdirs();

	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token",
				"Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
