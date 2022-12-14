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
////	        .requestMatchers()  // ?????? ????????? path ??? CustomerSecurityConfig?????? ??????
////	        .antMatchers("/admin/recruit/**")  // ?????? ??? ????????? ?????? /**, /* ??? ??????
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
//	                .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout")) // ???????????? ??? URL ?????????
//	                .logoutSuccessUrl("/") // ???????????? ?????? ??? redirect ??????
//	                .invalidateHttpSession(true) // HTTP Session ?????????
//	                .deleteCookies("JSESSIONID") // ?????? ?????? ??????
//            .and()
//                .exceptionHandling()
//                .accessDeniedPage("/accessDenied");
//    }
//    
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(adminService).passwordEncoder(bCryptPasswordEncoder);
//        //??????????????? ??????
//        //auth.userDetailsService(adminService).passwordEncoder(new BCryptPasswordEncoder());
//    }

}
