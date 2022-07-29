package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.RecruitDTO;
import com.vegemil.mapper.RecruitMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class RecruitServiceImpl implements RecruitService {

	@Autowired
	private RecruitMapper RecruitMapper;
	
	@Override
	public int getRecruitCount(RecruitDTO params) {
		int recruitTotalCount = RecruitMapper.selectRecruitTotalCount(params);
		
		if(recruitTotalCount < 0 ) {
			recruitTotalCount = 0;
		}
		
		return recruitTotalCount;
	}
	
	@Override
	public List<RecruitDTO> getRecruitList(RecruitDTO params) {
		List<RecruitDTO> recruitList = Collections.emptyList();

		int recruitTotalCount = RecruitMapper.selectRecruitTotalCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(recruitTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (recruitTotalCount > 0) {
			recruitList = RecruitMapper.selectRecruitList(params);
		}

		return recruitList;
	}
	
	@Override
	public boolean registerRecruit(RecruitDTO params) {
		int queryResult = 0;

		if (params.getSTh() == null) {
			queryResult = RecruitMapper.insertRecruit(params);
		} else {
			queryResult = RecruitMapper.updateRecruit(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public RecruitDTO getRecruitDetail(Long sTh) {
		return RecruitMapper.selectRecruitDetail(sTh);
	}
	
}
