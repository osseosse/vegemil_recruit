package com.vegemil.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class PersonalInfoDTO extends ApplicationDTO {
	
	private MultipartFile fileName;
	//개인정보
	private String nameHan;
	private String nameEng;
	private String age;
	private String photo;
	private String zip1;
	private String addr11;
	private String addr12;
	private String handicap;
	private String bohun;
	private String bohunNo;
	private String religoin;
	private String ability;
	private String hobby;
	
	//군복무
	private String milClass;
	private String milReason;
	private String milType;
	private String milLv;
	private String milArm;
	private String milMCount;
	private String milTicket;
	private String milSDate;
	private String milEdate;
	
	//가족
	private int famCount;
	private String famCon1;
	private String famName1;
	private String famAge1;
	private String famCon2;
	private String famName2;
	private String famAge2;
	private String famCon3;
	private String famName3;
	private String famAge3;
	private String famCon4;
	private String famName4;
	private String famAge4;
	
	
}
