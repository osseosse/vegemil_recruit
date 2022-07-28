package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.MemberDTO;

@Mapper
public interface MemberMapper {

	public int insertMember(MemberDTO params);

	public MemberDTO selectMemberDetail(Long custNo);

	public int updateMemPwd(MemberDTO params);

	public List<MemberDTO> selectMemberList(MemberDTO params);

	public int selectMemberTotalCount(MemberDTO params);

}
