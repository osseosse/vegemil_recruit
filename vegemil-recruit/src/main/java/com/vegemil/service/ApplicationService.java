package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.RecruitDTO;

public interface ApplicationService {
	
	public int getRecruitCount(RecruitDTO params);
	
	public List<RecruitDTO> getRecruitList(RecruitDTO params);
	
	public boolean registerRecruit(RecruitDTO params);
	
	public RecruitDTO getRecruitDetail(Long sTh);
	
}
