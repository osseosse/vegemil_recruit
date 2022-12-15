package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.RecruitDTO;

@Mapper
public interface RecruitMapper {

	public int insertRecruit(RecruitDTO params);

	public RecruitDTO selectRecruitDetail(String custNo);

	public int updateRecruit(RecruitDTO params);

	public List<RecruitDTO> selectRecruitList();
	
	public RecruitDTO getRecruitLatest();

	public int selectRecruitTotalCount();
	
	public List<String> getMajorList(String majorName);
	
	public int getRecruitEndYn(String setupTh);

}
