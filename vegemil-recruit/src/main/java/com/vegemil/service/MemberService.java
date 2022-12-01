package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.MemberDTO;
import com.vegemil.mapper.MemberMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class MemberService implements UserDetailsService  {

	@Autowired
	private MemberMapper memberMapper;
	
	public int getMemberCount(MemberDTO params) {
		int memberTotalCount = memberMapper.selectMemberTotalCount(params);
		
		if(memberTotalCount < 0 ) {
			memberTotalCount = 0;
		}
		
		return memberTotalCount;
	}
	
	public int isMemberCount(MemberDTO params) {
		int memCount = memberMapper.isMemberCount(params);
		
		if(memCount < 0 ) {
			memCount = 0;
		}
		
		return memCount;
	}
	
	public String searchEmail(MemberDTO member) throws Exception{
		
		String emailAddr = memberMapper.getEmailAddr(member);
		
		return emailAddr;
	}
	
	public int getMemberCountByEmail(MemberDTO params) {
		int memCount = memberMapper.selectMemberCount(params);
		
		if(memCount < 0 ) {
			memCount = 0;
		}
		
		return memCount;
	}
	
	public List<MemberDTO> getMemberList(MemberDTO params) {
		List<MemberDTO> memberList = Collections.emptyList();

		int memberTotalCount = memberMapper.selectMemberTotalCount(params);

		if (memberTotalCount > 0) {
			memberList = memberMapper.selectMemberList(params);
		}

		return memberList;
	}
	
	@Transactional
	public boolean registerMember(MemberDTO member) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		int queryResult = 0;
		int memCount = 0;
		
		memCount = memberMapper.selectMemberCount(member);
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		member.setAuth("USER");
		
		
		if (memCount == 0) {
			queryResult = memberMapper.saveMember(member);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Transactional
	public boolean resetPassword(MemberDTO member) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		int queryResult = 0;
		int memCount = 0;
		
		memCount = memberMapper.selectMemberCount(member);
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		
		if (memCount == 1) {
			queryResult = memberMapper.updateMemPwd(member);
		}

		return (queryResult == 1) ? true : false;
	}
	
	public boolean activeMember(MemberDTO member) {
		
		int queryResult = 0;
		queryResult = memberMapper.selectMemberCount(member);
		
		if (queryResult != 0) {
			queryResult = memberMapper.activeMember(member);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
    public MemberDTO loadUserByUsername(String emailAddr) throws UsernameNotFoundException {
        //여기서 받은 유저 패스워드와 비교하여 로그인 인증
		MemberDTO member = memberMapper.getMemberAccount(emailAddr);
        if (member == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
        return member;
    }
	
}
