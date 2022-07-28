package com.vegemil.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitDTO extends CommonDTO {

	private Long   sTh;
	private String sTitle;
	private String sType;
	private String sCompany;
	private Timestamp sStartdate;
	private Timestamp sEnddate;
	private Timestamp s1;
	private Timestamp s2;
	private Timestamp s3;	
	private String sClose;
	private String sCategory;
	private String sCategory2;
	private String sCollect;
	private String sDday;
	private Timestamp sInsertDate;
	private Timestamp sModifyDate;

}
