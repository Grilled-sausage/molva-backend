package com.grilledsausage.molva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MolvaApplication {

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//						.allowedOrigins("http://localhost:3000")
//						.allowedMethods("GET", "POST", "PUT", "DELETE")
//						.allowedHeaders("*")
//						.allowCredentials(true)
//						.maxAge(3000);
//			}
//		};
//	}

	public static void main(String[] args) {
		SpringApplication.run(MolvaApplication.class, args);
	}

}
