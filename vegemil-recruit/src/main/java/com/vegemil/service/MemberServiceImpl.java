package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vegemil.domain.MemberDTO;
import com.vegemil.mapper.MemberMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public int getMemberCount(MemberDTO params) {
		int memberTotalCount = memberMapper.selectMemberTotalCount(params);
		
		if(memberTotalCount < 0 ) {
			memberTotalCount = 0;
		}
		
		return memberTotalCount;
	}
	
	@Override
	public List<MemberDTO> getMemberList(MemberDTO params) {
		List<MemberDTO> memberList = Collections.emptyList();

		int memberTotalCount = memberMapper.selectMemberTotalCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(memberTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (memberTotalCount > 0) {
			memberList = memberMapper.selectMemberList(params);
		}

		return memberList;
	}
	
	@Override
	public boolean registerMember(MemberDTO member) {
		
		int queryResult = 0;
		int memCount = 0;
		
		memCount = memberMapper.selectMemberCount(member);
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		
		if (memCount == 0) {
			queryResult = memberMapper.insertMember(member);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public boolean activeMember(MemberDTO member) {
		
		int queryResult = 0;
		queryResult = memberMapper.selectMemberCount(member);
		
		if (queryResult != 0) {
			queryResult = memberMapper.activeMember(member);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public MemberDTO getMemberDetail(String memId) {
		return memberMapper.selectMemberDetail(memId);
	}
	
}
