package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.ApplicationDTO;
import com.vegemil.mapper.ApplicationMapper;
import com.vegemil.paging.PaginationInfo;

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
	public List<ApplicationDTO> getApplicationList(ApplicationDTO params) {
		List<ApplicationDTO> recruitList = Collections.emptyList();

		int recruitTotalCount = applicationMapper.selectApplicationCount(params.getMemNo());

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(recruitTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (recruitTotalCount > 0) {
			recruitList = applicationMapper.selectApplicationList(params);
		}

		return recruitList;
	}
	
	@Override
	public boolean registerApplication(ApplicationDTO params) {
		int queryResult = 0;
		
		int applicationCount = applicationMapper.selectApplicationCount(params.getMemNo());

		if (applicationCount == 0) {
			queryResult = applicationMapper.savePersonalInformation(params);
		} else {
			queryResult = applicationMapper.updatePersonalInformation(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public ApplicationDTO getApplicationDetail(Long idx, Long memNo) {
		return applicationMapper.selectApplicationByIdx(idx, memNo);
	}
	
}
