package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.MemberDTO;

public interface MemberService {
	
	public int getMemberCount(MemberDTO params);
	
	public List<MemberDTO> getMemberList(MemberDTO params);
	
	public boolean registerMember(MemberDTO params);
	
	public MemberDTO getMemberDetail(String memId);
	
}
