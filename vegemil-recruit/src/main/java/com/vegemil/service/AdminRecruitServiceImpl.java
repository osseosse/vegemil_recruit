package com.vegemil.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.AdminDTO;
import com.vegemil.domain.QnaDTO;
import com.vegemil.domain.RecruitDTO;
import com.vegemil.mapper.AdminRecruitMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class AdminRecruitServiceImpl implements AdminRecruitService {

	@Autowired
	private AdminRecruitMapper adminRecruitMapper;

	@Override
	public List<RecruitDTO> getRecruitList() {
		List<RecruitDTO> recruitList = adminRecruitMapper.selectRecruitList();

		return recruitList;
	}
	
	@Override
	public List<RecruitDTO> getRecruitList2(Map<String, Object> paramMap) {
		List<RecruitDTO> recruitList = adminRecruitMapper.selectRecruitList2(paramMap);

		return recruitList;
	}
	
	
	@Override
	public RecruitDTO getRecruit(String sTh) {
		return adminRecruitMapper.selectRecruitData(sTh);
	}
	
	@Override
	public List<RecruitDTO> getRecruitDt(String sTh) {
		List<RecruitDTO> recruitList = adminRecruitMapper.selectRecruitDtData(sTh); 
		return recruitList;
	}
	
	@Override
	public RecruitDTO getVolunteerData(String sTh) {
		return adminRecruitMapper.selectVolunteerData(sTh);
	}
	
	@Override
	public List<RecruitDTO> getDateApplyData(String sTh) {

		return adminRecruitMapper.selectDateApplyData(sTh);
	}
	
	@Override
	public List<RecruitDTO> getFieldData(String sTh) {

		return adminRecruitMapper.selectFieldData(sTh);
	}
	
	@Override
    public boolean registerRecruitNotice(RecruitDTO recruitDto, List<RecruitDTO> list) { 
        int queryResult = 0;
        
        if(recruitDto.getSTh() == null || "".equals(recruitDto.getSTh())) {
	        LocalDate nowDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	        String sTh1 = nowDate.format(formatter);
	        
	        LocalTime nowTime = LocalTime.now();
	        formatter = DateTimeFormatter.ofPattern("HHmmss");
	        String sTh2 = nowTime.format(formatter);
	        
	        recruitDto.setSTh(recruitDto.getNewSTh());
	        
			queryResult = adminRecruitMapper.insertNotice(recruitDto);
			if(queryResult > 0) {
				queryResult = adminRecruitMapper.insertNoticeDt(list);
			}
			
        }
        else {
        	queryResult = adminRecruitMapper.updateNotice(recruitDto);
        	if(queryResult > 0) {
        		queryResult = adminRecruitMapper.selectNoticeDtTotalCount(recruitDto);
        		if(queryResult != list.size()) {
        			Map<String, Object> paramMap = new HashMap<String, Object>();
        			ArrayList<String> arrayList = new ArrayList<>();
        			arrayList.add(recruitDto.getSTh());
        			paramMap.put("list", arrayList);
        			adminRecruitMapper.deleteNoticeDt(paramMap);
        		}
      			queryResult = adminRecruitMapper.insertNoticeDt(list);
			}
        }
		if(queryResult > 0) {
			return true;
		} else {
			return false;
		}
               
    }
	
	@Override
    public boolean registerRecruitCopy(RecruitDTO recruitDto) { 
        int queryResult = 0;
        
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String sTh1 = nowDate.format(formatter);
        
        LocalTime nowTime = LocalTime.now();
        formatter = DateTimeFormatter.ofPattern("HHmmss");
        String sTh2 = nowTime.format(formatter);
        
        recruitDto.setNewSTh(sTh1 + sTh2);
        
		queryResult = adminRecruitMapper.copyNotice(recruitDto);
        
		if(queryResult == 1) {
			return true;
		} else {
			return false;
		}
               
    }
	
	@Override
	public List<RecruitDTO> getVolunteerList(Map<String, Object> paramMap) {
		List<RecruitDTO> volunteerList = adminRecruitMapper.selectVolunteerList(paramMap);

		return volunteerList;
	}
	
	@Override
    public boolean updateVolunteerList(RecruitDTO recruitDto) { 
        int queryResult = 0;
        queryResult = adminRecruitMapper.updateVolunteer(recruitDto);
		if(queryResult == 1) {
			return true;
		} else {
			return false;
		}
               
    }
	
	@Override
	public RecruitDTO getVolunteerAllData(RecruitDTO recruitDto) {
		return adminRecruitMapper.selectVolunteerAllData(recruitDto);
	}
	
	@Override
	public boolean deleteVolunteer(Map<String, Object> paramMap) { 
        int queryResult = 0;
        queryResult = adminRecruitMapper.deleteVolunteer(paramMap);
		if(queryResult > 0) {
			return true;
		} else {
			return false;
		}
               
    }
	
	@Override
	public boolean deleteNotice(Map<String, Object> paramMap) { 
        int queryResult = 0;
        queryResult = adminRecruitMapper.deleteNotice(paramMap);
		if(queryResult > 0) {
			queryResult = adminRecruitMapper.deleteNoticeDt(paramMap);
			return true;
		}
		
		return false;
               
    }
	
	@Override
	public List<QnaDTO> getQnaList(Map<String, Object> paramMap) {
		List<QnaDTO> faqList = adminRecruitMapper.selectQnaList(paramMap);

		return faqList;
	}
	
	@Override
	public boolean deleteQnaList(Map<String, Object> paramMap) { 
        int queryResult = 0;
        queryResult = adminRecruitMapper.deleteQnaList(paramMap);
        if(queryResult > 0) {
			return true;
		} else {
			return false;
		}
               
    }
	
	@Override
	public QnaDTO getQna(QnaDTO qnaDto) {
		return adminRecruitMapper.selectQna(qnaDto);
	}
	
	@Override
    public boolean registerQnaDetail(QnaDTO qnaDto) { 
        int queryResult = 0;
        
		queryResult = adminRecruitMapper.updateQnaDetail(qnaDto);
        
		if(queryResult == 1) {
			return true;
		} else {
			return false;
		}
               
    }
	
}
