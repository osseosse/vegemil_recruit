package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.RecruitDTO;
import com.vegemil.mapper.ApplicationMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationMapper ApplicationMapper;
	
	@Override
	public int getRecruitCount(RecruitDTO params) {
		int recruitTotalCount = ApplicationMapper.selectRecruitTotalCount(params);
		
		if(recruitTotalCount < 0 ) {
			recruitTotalCount = 0;
		}
		
		return recruitTotalCount;
	}
	
	@Override
	public List<RecruitDTO> getRecruitList(RecruitDTO params) {
		List<RecruitDTO> recruitList = Collections.emptyList();

		int recruitTotalCount = ApplicationMapper.selectRecruitTotalCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(recruitTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (recruitTotalCount > 0) {
			recruitList = ApplicationMapper.selectRecruitList(params);
		}

		return recruitList;
	}
	
	@Override
	public boolean registerRecruit(RecruitDTO params) {
		int queryResult = 0;

		if (params.getSTh() == null) {
			queryResult = ApplicationMapper.insertRecruit(params);
		} else {
			queryResult = ApplicationMapper.updateRecruit(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public RecruitDTO getRecruitDetail(Long sTh) {
		return ApplicationMapper.selectRecruitDetail(sTh);
	}
	
}
