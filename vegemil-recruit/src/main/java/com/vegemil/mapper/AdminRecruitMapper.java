package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.QnaDTO;
import com.vegemil.domain.RecruitDTO;

@Mapper
public interface AdminRecruitMapper {
	public List<RecruitDTO> selectRecruitList();
	
	public List<RecruitDTO> selectRecruitList2(Map<String, Object> paramMap);
	
	public RecruitDTO selectRecruitData(String sTh);
	
	public List<RecruitDTO> selectRecruitDtData(String sTh);

	public RecruitDTO selectVolunteerData(String sTh);
	
	public List<RecruitDTO> selectDateApplyData(String sTh);
	
	public List<RecruitDTO> selectFieldData(String sTh);
	
	public int insertNotice(RecruitDTO recruitDTO);
	
	public int updateNotice(RecruitDTO recruitDTO);
	
	public int copyNotice(RecruitDTO recruitDTO);
	
	public List<RecruitDTO> selectVolunteerList(Map<String, Object> paramMap);
	
	public int updateVolunteer(RecruitDTO recruitDTO);
	
	public RecruitDTO selectVolunteerAllData(RecruitDTO recruitDTO);
	
	public int deleteVolunteer(Map<String, Object> paramMap);
	
	public int deleteNotice(Map<String, Object> paramMap);

	public int deleteNoticeDt(Map<String, Object> paramMap);
	
	public int insertNoticeDt(List<RecruitDTO> list);
	
	public int selectNoticeDtTotalCount(RecruitDTO recruitDto);
	
	public List<QnaDTO> selectQnaList(Map<String, Object> paramMap);
	
	public int deleteQnaList(Map<String, Object> paramMap);
	
	public QnaDTO selectQna(QnaDTO qnaDto);
	
	public int updateQnaDetail(QnaDTO qnaDto);
}
