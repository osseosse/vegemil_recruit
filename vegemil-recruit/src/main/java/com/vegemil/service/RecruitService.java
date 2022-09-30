package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.RecruitDTO;

public interface RecruitService {
	
	public int getRecruitCount();
	
	public List<RecruitDTO> getRecruitList();
	
	public RecruitDTO getRecruitLatest();
	
	public boolean registerRecruit(RecruitDTO params);
	
	public RecruitDTO getRecruitDetail(Long sTh);
	
	public List<String> getMajorList(String majorName);
	
}
