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
	private String sStartdate;
	private String sEnddate;
	private Long   sDday;
	private String submitOk;
	private String submitDate;
	private String joinDate;
	private String updateDate;
	private String joinCompany1;
	private String joinCompany2;
	private String joinField1;
	private String joinArea1;
	private String joinField2;
	private String joinArea2;
}
