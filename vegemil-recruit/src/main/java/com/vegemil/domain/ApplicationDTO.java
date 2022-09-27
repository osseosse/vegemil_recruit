package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ApplicationDTO extends MemberDTO {
	
	private Long idx;
	private String joinNum;
	private String setupTh;
	private String setupTitle;
	private String setupDate;
	private String joinOk;
	private String submitOk;
	private String joinDate;
	private String updateDate;
	private String joinCompany;
	private String joinField1;
	private String joinArea1;
	
}
