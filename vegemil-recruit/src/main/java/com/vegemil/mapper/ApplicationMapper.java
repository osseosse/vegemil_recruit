package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.RecruitDTO;

@Mapper
public interface ApplicationMapper {

	public int insertRecruit(RecruitDTO params);

	public RecruitDTO selectRecruitDetail(Long custNo);

	public int updateRecruit(RecruitDTO params);

	public List<RecruitDTO> selectRecruitList(RecruitDTO params);

	public int selectRecruitTotalCount(RecruitDTO params);

}
