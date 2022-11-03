package com.vegemil.domain;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;


@SuppressWarnings("serial")
@Getter
@Setter
public class MemberDTO implements UserDetails {

	private Long   memNo;		/** 번호 (PK) */
	private String memName;		/** 고객명 */
	private String sex;			/** 성별 */
	private String birthday;	/** 생일 */
	private String auth;		/** 인증 */
	private int    active;		/** 활성화 체크 */
	private String password;	/** 비밀번호 */
	private String emailAddr;	/** Email 주소 */
	private String smsYn;		/** SMS수신여부 */
	private String phoneNo;		/** 핸드폰번호 */
	private String sleepYn;		/** 휴면여부 */
	private String withdrawalYn;/** 탈퇴여부 */
	private String joinDate;
	private String adAgreeDate;
	private Timestamp pwdModifyDate;/** 비밀번호 변경일 */
	private Timestamp activeDate;/** 계정 활성화 날짜 */
	private Timestamp lastLoginDate;/** 마지막 로그인날짜 */
	private Timestamp sleepDate;/** 휴면처리날짜 */

	 @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.auth));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // 시큐리티의 userName
    // -> 따라서 얘는 인증할 때 id를 봄
    @Override
    public String getUsername() {
        return this.emailAddr;
    }

    // Vo의 userName !
    public String getUserName(){
        return this.memName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
	
}
