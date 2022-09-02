package com.vegemil.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ApplicationDTO extends MemberDTO {
	
	private Long idx;
	private String joinNum;
	private String setupTh;
	private String joinOk;
	private String joinType;
	private String joinDate;
	private Timestamp updateDate;
	private String joinCompany;
	private String joinField1;
	private String joinArea1;
	
}
