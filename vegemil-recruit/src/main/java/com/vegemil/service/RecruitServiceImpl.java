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
	public int getRecruitCount() {
		int recruitTotalCount = RecruitMapper.selectRecruitTotalCount();
		
		if(recruitTotalCount < 0 ) {
			recruitTotalCount = 0;
		}
		
		return recruitTotalCount;
	}
	
	@Override
	public List<RecruitDTO> getRecruitList() {
		List<RecruitDTO> recruitList = Collections.emptyList();

		int recruitTotalCount = RecruitMapper.selectRecruitTotalCount();

		if (recruitTotalCount > 0) {
			recruitList = RecruitMapper.selectRecruitList();
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
	public RecruitDTO getRecruitDetail(String sTh) {
		return RecruitMapper.selectRecruitDetail(sTh);
	}
	
	@Override
	public RecruitDTO getRecruitLatest() {
		return RecruitMapper.getRecruitLatest();
	}
	
	@Override
	public List<String> getMajorList(String majorName) {
		
		List<String> majorList = Collections.emptyList();
		majorList = RecruitMapper.getMajorList(majorName);

		return majorList;
	}
	
	@Override
	public int checkRecruitEnd(String setupTh) {
		
		int recruitEndCheck = RecruitMapper.getRecruitEndYn(setupTh);
		
		if(recruitEndCheck > 0 ) {
			recruitEndCheck = 0;
		}
		
		return recruitEndCheck;
	}
	
}
