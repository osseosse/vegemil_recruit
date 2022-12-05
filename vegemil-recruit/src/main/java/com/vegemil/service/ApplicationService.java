package com.vegemil.service;

import java.util.List;
import java.util.Map;

import com.vegemil.domain.AcademyInfoDTO;
import com.vegemil.domain.ApplicationDTO;
import com.vegemil.domain.CareerDTO;
import com.vegemil.domain.IntroduceDTO;
import com.vegemil.domain.PersonalInfoDTO;
import com.vegemil.domain.QualificationDTO;
import com.vegemil.domain.ResumeDTO;

public interface ApplicationService {
	
	public int getApplicationCount(ApplicationDTO params);
	
	public int getIdx(ApplicationDTO params);
	
	public int getIdxCount(ApplicationDTO params);
	
	public List<ResumeDTO> getResumeList(ResumeDTO params);
	
	public List<ApplicationDTO> getApplicationList(ApplicationDTO params);
	
	public boolean registerPersonalInfo(PersonalInfoDTO params);
	
	public boolean updateAcademyInfo(Map<String, Object> params);
	
	public boolean registerAcademyInfo(AcademyInfoDTO params);
	
	public boolean registerQualification(QualificationDTO params);
	
	public boolean registerCareer(CareerDTO params);
	
	public boolean registerIntroduce(IntroduceDTO params);
	
	public ResumeDTO getResume(Long idx, Long memNo);
	
	public ApplicationDTO getApplication(Long idx, Long memNo);
	
	public IntroduceDTO selectIntroduce(Long idx, Long memNo);
	
	public PersonalInfoDTO selectPersonalInfo(Long idx, Long memNo);
	
	public AcademyInfoDTO selectAcademy(Long idx, Long memNo);
	
	public QualificationDTO selectQualification(Long idx, Long memNo);
	
	public CareerDTO selectCareer(Long idx, Long memNo);
	
	public boolean deleteResume(Long idx);
	
}
