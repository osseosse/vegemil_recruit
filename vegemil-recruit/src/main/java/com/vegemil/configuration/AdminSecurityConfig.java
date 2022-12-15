package com.vegemil.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vegemil.service.AdminService;
import com.vegemil.service.MemberService;

import lombok.RequiredArgsConstructor;

//@Configuration
//@EnableWebSecurity
//@Order(1)
public class AdminSecurityConfig  {

//	@Autowired
//    private AdminService adminService;
//	
//	@Autowired
//	BCryptPasswordEncoder bCryptPasswordEncoder;
//	
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
////        		.requestMatcher(new AntPathRequestMatcher("/admin/recruit/**"))
////        		.csrf().disable()
////        		.authorizeRequests()
////        		.antMatchers("/admin/recruit/**").hasAuthority("ADMIN")
//        
//		        .csrf().disable()
//		        .authorizeRequests()
//		        .antMatchers("/admin/auth/**").permitAll()
//		        .antMatchers("/admin/recruit/**").hasAuthority("ADMIN")
//        		
////		        .antMatcher("/admin/recruit/**")
////					.authorizeRequests().anyRequest().hasAuthority("ADMIN")
////	        	.authorizeRequests()
////	        		.antMatchers("/admin/recruit/**").hasAuthority("ADMIN")
////	        		.antMatchers("/admin/auth/**").permitAll()
////	        	.anyRequest()
////	        		.permitAll()
//	            .and()
////	            	.csrf().disable()
////	        .csrf().disable()
////	        .httpBasic()
////	        .and()
////	        .requestMatchers()  // 아래 명시한 path 는 CustomerSecurityConfig에서 담당
////	        .antMatchers("/admin/recruit/**")  // 필요 시 상황에 맞게 /**, /* 등 추가
////	        .and()
////	        .authorizeRequests()
////	        .antMatchers("/admin/auth/**").permitAll()
////	        .antMatchers("/admin/recurit/**").hasAuthority("ADMIN")
////	        .anyRequest().authenticated()
//	            .formLogin()
//		            .usernameParameter("aId")
//	                .passwordParameter("aPwd")
//	                .loginPage("/admin/auth/login")
//	                .loginProcessingUrl("/admin/auth/loginProc")
//	                .failureUrl("/admin/auth/login?error=true")
//	                .defaultSuccessUrl("/admin/recruit/volunteerList")
//            .and()
//                .logout()
//	                .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout")) // 로그아웃 시 URL 재정의
//	                .logoutSuccessUrl("/") // 로그아웃 성공 시 redirect 이동
//	                .invalidateHttpSession(true) // HTTP Session 초기화
//	                .deleteCookies("JSESSIONID") // 특정 쿠키 제거
//            .and()
//                .exceptionHandling()
//                .accessDeniedPage("/accessDenied");
//    }
//    
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(adminService).passwordEncoder(bCryptPasswordEncoder);
//        //어드민으로 수정
//        //auth.userDetailsService(adminService).passwordEncoder(new BCryptPasswordEncoder());
//    }

}
