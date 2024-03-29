package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ResumeDTO extends ApplicationDTO {
	
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
		private String famCount;
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
		
		//어학
		private String langCount;
		private String langName1;
		private String langLicName1;
		private String langPoint1;
		private String langLv1;
		private String langLicNum1;
		private String langGet1;
		private String langTalk1;
		private String langMake1;
		private String langRead1;
		private String langName2;
		private String langLicName2;
		private String langPoint2;
		private String langLv2;
		private String langLicNum2;
		private String langGet2;
		private String langTalk2;
		private String langMake2;
		private String langRead2;
		private String langName3;
		private String langLicName3;
		private String langPoint3;
		private String langLv3;
		private String langLicNum3;
		private String langGet3;
		private String langTalk3;
		private String langMake3;
		private String langRead3;
		
		//IT능력
		private String swCount;
		private String swName1;
		private String swLv1;
		private String swName2;
		private String swLv2;
		private String swName3;
		private String swLv3;
		
		//자격 및 면허
		private String licCount;
		private String licName1;
		private String licNum1;
		private String licGetDate1;
		private String licPub1;
		private String licName2;
		private String licNum2;
		private String licGetDate2;
		private String licPub2;
		private String licName3;
		private String licNum3;
		private String licGetDate3;
		private String licPub3;
		private String licName4;
		private String licNum4;
		private String licGetDate4;
		private String licPub4;
		private String licName5;
		private String licNum5;
		private String licGetDate5;
		private String licPub5;
		private String licName6;
		private String licNum6;
		private String licGetDate6;
		private String licPub6;
		private String licName7;
		private String licNum7;
		private String licGetDate7;
		private String licPub7;
		private String licName8;
		private String licNum8;
		private String licGetDate8;
		private String licPub8;
		private String licName9;
		private String licNum9;
		private String licGetDate9;
		private String licPub9;
		private String licName10;
		private String licNum10;
		private String licGetDate10;
		private String licPub10;
		
		//수상경력
		private String medalCount;
		private String medalName1;
		private String medalDate1;
		private String medalContent1;
		private String medalName2;
		private String medalDate2;
		private String medalContent2;
		private String medalName3;
		private String medalDate3;
		private String medalContent3;
		
		//해외원수 대외활동
		private String forCount;
		private String forState1;
		private String forSDate1;
		private String forEDate1;
		private String forName1;
		private String forContent1;
		private String forState2;
		private String forSDate2;
		private String forEDate2;
		private String forName2;
		private String forContent2;
		
		//학력
		private String lastAcademy;
		//고등
		private String schGrad1;
		private String schEnterYm1;
		private String schGradYm1;
		private String schName1;
		private String schAdmis1;
		private String schMajor1;
		private String schSite1;
		private String schNight1;
		private String schPointEvg1;
		//전문학사
		private String schGrad2;
		private String schEnterYm2;
		private String schGradYm2;
		private String schName2;
		private String schAdmis2;
		private String schDepType2;
		private String schMajor2;
		private String schSite2;
		private String schNight2;
		private String schPointEvg2;
		private String schSubMajor2;
		private String schSubPointEvg2;
		//편입전 대학교
		private String transGrad;
		private String transEnterYm;
		private String transGradYm;
		private String transName;
		private String transAdmis;
		private String transDepType;
		private String transMajor;
		private String transSite;
		private String transNight;
		private String transPointEvg;
		
		//학사
		private String schGrad3;
		private String schEnterYm3;
		private String schGradYm3;
		private String schName3;
		private String schTransfer3;
		private String schAdmis3;
		private String schDepType3;
		private String schMajor3;
		private String schSite3;
		private String schNight3;
		private String schPointEvg3;
		private String schSubAdmis3;
		private String schSubMajor3;
		private String schSubPointEvg3;
		
		//석사
		private String schGrad4;
		private String schEnterYm4;
		private String schGradYm4;
		private String schName4;
		private String schAdmis4;
		private String schDepType4;
		private String schMajor4;
		private String schSite4;
		private String schNight4;
		private String schPointEvg4;
		
		//박사
		private String schGrad5;
		private String schEnterYm5;
		private String schGradYm5;
		private String schName5;
		private String schAdmis5;
		private String schDepType5;
		private String schMajor5;
		private String schSite5;
		private String schNight5;
		private String schPointEvg5;
		
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
		
		private String hisCoName4;
		private String hisIng4;
		private String hisSDate4;
		private String hisEDate4;
		private String hisContent4;
		private String hisDuty4;
		private String hisPay4;
		
		private String hisCoName5;
		private String hisIng5;
		private String hisSDate5;
		private String hisEDate5;
		private String hisContent5;
		private String hisDuty5;
		private String hisPay5;
		
		private String hisCoName6;
		private String hisIng6;
		private String hisSDate6;
		private String hisEDate6;
		private String hisContent6;
		private String hisDuty6;
		private String hisPay6;
		
		//포트폴리오
		private String portFile;
		private String portUrl;
		
		//자소서
		private String introduce1;
		private String introduce2;
		private String introduce3;
		private String introduce4;
		private String introduce5;
		private String introduce6;
	
}
