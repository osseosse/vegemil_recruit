package com.vegemil.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vegemil.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;
    
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                //.antMatchers("/member/**").authenticated()
                .antMatchers("/info/**").permitAll()
            	.antMatchers("/member/login").permitAll()
            	.antMatchers("/member/join").permitAll()
            	.antMatchers("/member/register").permitAll()
            	.antMatchers("/member/joinConfirm").permitAll()
            	.antMatchers("/member/passwordReset").permitAll()
            	.antMatchers("/mypage/qna").permitAll()
                .antMatchers("/member/**").hasAuthority("USER") // user 권한의 유저만  접근가능
                .antMatchers("/application/**").hasAuthority("USER") // user 권한의 유저만  접근가능
                .antMatchers("/mypage/**").hasAuthority("USER") // user 권한의 유저만  접근가능
                .antMatchers("/recruit/join", "/recruit/register").hasAuthority("USER") // user 권한의 유저만  접근가능
                .antMatchers("/**").permitAll()
            .anyRequest()
                .authenticated()
                .and().csrf().disable()
            .formLogin()
                .loginPage("/member/login")
                .loginProcessingUrl("/member/loginProc")
                .failureUrl("/member/login?error=true")
                .defaultSuccessUrl("/")
            .and()
                .logout()
	                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 시 URL 재정의
	                .logoutSuccessUrl("/") // 로그아웃 성공 시 redirect 이동
	                .invalidateHttpSession(true) //세션 삭제
	                .deleteCookies("remember-me", "JSESSIONID") //자동 로그인 쿠키, Tomcat이 발급한 세션 유지 쿠키 삭제
            .and()
                .exceptionHandling()
                .accessDeniedPage("/error/error")
            .and()
                .rememberMe()
                .key("vegemil") //쿠키에 사용되는 값을 암호화하기 위한 키(key)값
                .tokenRepository(persistentTokenRepository()) //DataSource 추가
                .tokenValiditySeconds(604800); //토큰 유지 시간(초단위) - 일주일
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    // tokenRepository의 구현체
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
    	JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
    	repo.setDataSource(dataSource);
    	return repo;
    }

}