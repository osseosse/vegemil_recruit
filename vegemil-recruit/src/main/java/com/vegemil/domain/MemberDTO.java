package com.vegemil.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO extends CommonDTO {

	private Long   memNo;		/** 번호 (PK) */
	private String memName;		/** 고객명 */
	private String sex;			/** 성별 */
	private String birthday;	/** 생일 */
	private String memId;		/** 고객ID */
	private int    active;		/** 활성화 체크 */
	private String password;	/** 비밀번호 */
	private String regidentNo;	/** 주민번호 */
	private String country;		/** 국적 */
	private String emailAddr;	/** Email 주소 */
	private String emailYn;		/** Email 수신여부 */
	private String smsYn;		/** SMS수신여부 */
	private String phoneNo;		/** 핸드폰번호 */
	private String diKey;		/** 중복가입키 */
	private String sleepYn;		/** 휴면여부 */
	private String withdrawalYn;/** 탈퇴여부 */
	private Timestamp pwdModifyDate;/** 비밀번호 변경일 */
	private Timestamp activeDate;/** 계정 활성화 날짜 */
	private Timestamp lastLoginDate;/** 마지막 로그인날짜 */
	private Timestamp sleepDate;/** 휴면처리날짜 */
	private Timestamp adAgreeDate;/** 마케팅 수신동의 날짜 */

}
