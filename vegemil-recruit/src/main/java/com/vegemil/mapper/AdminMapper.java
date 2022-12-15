package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminDTO;

@Mapper
public interface AdminMapper {

	public int insertAdmin(AdminDTO adminDTO);

	public AdminDTO findAdminById(String aId);

	public int updateAdminPwd(AdminDTO adminDTO);

	public List<AdminDTO> selectAdminList(AdminDTO adminDTO);

	public int selectAdminTotalCount(AdminDTO adminDTO);
	
	public int checkMember(AdminDTO adminDTO);
	
	public int selectAdminCount(AdminDTO adminDTO);
	
	public int activeAdmin(AdminDTO adminDTO);

}
