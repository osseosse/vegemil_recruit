package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.AcademyInfoDTO;
import com.vegemil.domain.ApplicationDTO;
import com.vegemil.domain.CareerDTO;
import com.vegemil.domain.IntroduceDTO;
import com.vegemil.domain.PersonalInfoDTO;
import com.vegemil.domain.QualificationDTO;
import com.vegemil.domain.ResumeDTO;
import com.vegemil.mapper.ApplicationMapper;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationMapper applicationMapper;
	
	@Override
	public int getApplicationCount(ApplicationDTO params) {
		int recruitTotalCount = applicationMapper.selectApplicationCount(params.getMemNo());
		
		if(recruitTotalCount < 0 ) {
			recruitTotalCount = 0;
		}
		
		return recruitTotalCount;
	}
	
	@Override
	public ApplicationDTO getApplication(Long idx, Long memNo) {
		ApplicationDTO application = new ApplicationDTO();

		int recruitTotalCount = applicationMapper.selectApplicationCount(memNo);

		if (recruitTotalCount > 0) {
			application = applicationMapper.selectApplication(idx);
		}

		return application;
	}
	
	@Override
	public ResumeDTO getResume(Long idx, Long memNo) {
		ResumeDTO application = new ResumeDTO();

		int recruitTotalCount = applicationMapper.selectApplicationCount(memNo);

		if (recruitTotalCount > 0) {
			application = applicationMapper.selectResume(idx);
		}

		return application;
	}
	
	@Override
	public List<ResumeDTO> getResumeList(ResumeDTO params) {
		List<ResumeDTO> resumeList = Collections.emptyList();

		int recruitTotalCount = applicationMapper.selectApplicationCount(params.getMemNo());

		if (recruitTotalCount > 0) {
			resumeList = applicationMapper.selectResumeList(params.getMemNo());
		}

		return resumeList;
	}
	
	@Override
	public List<ApplicationDTO> getApplicationList(ApplicationDTO params) {
		List<ApplicationDTO> recruitList = Collections.emptyList();

		int recruitTotalCount = applicationMapper.selectApplicationCount(params.getMemNo());

		if (recruitTotalCount > 0) {
			recruitList = applicationMapper.selectApplicationList(params.getMemNo());
		}

		return recruitList;
	}
	
	@Override
	@Transactional
	public boolean registerPersonalInfo(PersonalInfoDTO params) {
		int queryResult = 0;
		
		int applicationCount = applicationMapper.selectApplicationCntBySth(params.getMemNo(), params.getSetupTh());

		if (applicationCount == 0) {
			queryResult = applicationMapper.savePersonalInformation(params);
		} else {
			queryResult = applicationMapper.updatePersonalInformation(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	@Transactional
	public boolean registerAcademyInfo(AcademyInfoDTO params) {
		int queryResult = 0;
		
		queryResult = applicationMapper.updateAcademy(params);

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	@Transactional
	public boolean registerQualification(QualificationDTO params) {
		int queryResult = 0;
		
		queryResult = applicationMapper.updateQualification(params);

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	@Transactional
	public boolean registerCareer(CareerDTO params) {
		int queryResult = 0;
		
		queryResult = applicationMapper.updateCareer(params);

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	@Transactional
	public boolean registerIntroduce(IntroduceDTO params) {
		int queryResult = 0;
		
		queryResult = applicationMapper.updateIntroduce(params);

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public PersonalInfoDTO selectPersonalInfo(Long idx, Long memNo) {
		return applicationMapper.selectPersonalInfo(idx, memNo);
	}
	
	@Override
	public AcademyInfoDTO selectAcademy(Long idx, Long memNo) {
		return applicationMapper.selectAcademy(idx, memNo);
	}
	
	@Override
	public QualificationDTO selectQualification(Long idx, Long memNo) {
		return applicationMapper.selectQualification(idx, memNo);
	}
	
	@Override
	public CareerDTO selectCareer(Long idx, Long memNo) {
		return applicationMapper.selectCareer(idx, memNo);
	}
	
	@Override
	public IntroduceDTO selectIntroduce(Long idx, Long memNo) {
		return applicationMapper.selectIntroduce(idx, memNo);
	}
	
	
}
