package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.AcademyInfoDTO;
import com.vegemil.domain.ApplicationDTO;
import com.vegemil.domain.PersonalInfoDTO;

public interface ApplicationService {
	
	public int getApplicationCount(ApplicationDTO params);
	
	public List<ApplicationDTO> getApplicationList(ApplicationDTO params);
	
	public boolean registerPersonalInfo(PersonalInfoDTO params);
	
	public boolean registerAcademyInfo(AcademyInfoDTO params);
	
	public PersonalInfoDTO selectPersonalInfo(Long idx, Long memNo);
	
	public AcademyInfoDTO selectAcademy(Long idx, Long memNo);
	
}
