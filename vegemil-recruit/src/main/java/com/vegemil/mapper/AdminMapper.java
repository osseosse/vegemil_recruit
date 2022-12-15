package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminDTO;
import com.vegemil.domain.MemberDTO;

@Mapper
public interface AdminMapper {

	public int insertAdmin(MemberDTO memberDTO);

	public MemberDTO findAdminById(String aId);

	public int updateAdminPwd(MemberDTO adminDTO);

	public List<AdminDTO> selectAdminList(AdminDTO adminDTO);

	public int selectAdminTotalCount(AdminDTO adminDTO);
	
	public int checkMember(MemberDTO memberDTO);
	
	public int selectAdminCount(MemberDTO adminDTO);
	
	public int activeAdmin(MemberDTO adminDTO);

}
