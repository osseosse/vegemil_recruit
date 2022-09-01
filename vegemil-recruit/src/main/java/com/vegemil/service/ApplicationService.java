package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.ApplicationDTO;

public interface ApplicationService {
	
	public int getApplicationCount(ApplicationDTO params);
	
	public List<ApplicationDTO> getApplicationList(ApplicationDTO params);
	
	public boolean registerApplication(ApplicationDTO params);
	
	public ApplicationDTO getApplicationDetail(Long idx, Long memNo);
	
}
