package com.vegemil.service;

import java.util.List;
import java.util.Map;

import com.vegemil.domain.QnaDTO;
import com.vegemil.domain.RecruitDTO;

public interface AdminRecruitService {
	
	public List<RecruitDTO> getRecruitList();
	
	public List<RecruitDTO> getRecruitList2(Map<String, Object> paramMap);
	
	public RecruitDTO getRecruit(String sTh);
	
	public List<RecruitDTO> getRecruitDt(String sTh);
	
	public RecruitDTO getVolunteerData(String sTh);
	
	public List<RecruitDTO> getDateApplyData(String sTh);
	
	public List<RecruitDTO> getFieldData(String sTh);
	
	public boolean registerRecruitNotice(RecruitDTO recruitDto, List<RecruitDTO> list);
	
	public boolean registerRecruitCopy(RecruitDTO recruitDto);
	
	public List<RecruitDTO> getVolunteerList(Map<String, Object> paramMap);
	
	public boolean updateVolunteerList(RecruitDTO recruitDto);
	
	public RecruitDTO getVolunteerAllData(RecruitDTO recruitDto);
	
	public boolean deleteVolunteer(Map<String, Object> paramMap);
	
	public boolean deleteNotice(Map<String, Object> paramMap);
	
	public List<QnaDTO> getQnaList(Map<String, Object> paramMap);
	
	public boolean deleteQnaList(Map<String, Object> paramMap);
	
	public QnaDTO getQna(QnaDTO qnaDto);
	
	public boolean registerQnaDetail(QnaDTO qnaDto);
	
	public RecruitDTO getPortfolioSaveName(Long idx);
}
