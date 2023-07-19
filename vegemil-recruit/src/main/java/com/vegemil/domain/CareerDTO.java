package com.vegemil.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class CareerDTO extends ApplicationDTO {
	
	private MultipartFile fileName;
	//인턴
	private String internName;
	private String internIng;
	private String internSDate;
	private String internEDate;
	private String internDuty;
	
	//경력
	private String hisCount;
	private String hisCoName1;
	private String hisIng1;
	private String hisSDate1;
	private String hisEDate1;
	private String hisContent1;
	private String hisDuty1;
	private String hisPay1;
	private String hisCoName2;
	private String hisIng2;
	private String hisSDate2;
	private String hisEDate2;
	private String hisContent2;
	private String hisDuty2;
	private String hisPay2;
	private String hisCoName3;
	private String hisIng3;
	private String hisSDate3;
	private String hisEDate3;
	private String hisContent3;
	private String hisDuty3;
	private String hisPay3;
	
	//포트폴리오
	private String portFile;
	private String portFileSaved;
	private String portUrl;
}
