package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vegemil.domain.ApplicationDTO;


@Mapper
public interface ApplicationMapper {

	public int savePersonalInformation(ApplicationDTO params);

	public ApplicationDTO selectApplicationByIdx(@Param("idx") Long idx,@Param("memNo") Long memNo);

	public int updatePersonalInformation(ApplicationDTO params);

	public List<ApplicationDTO> selectApplicationList(ApplicationDTO params);

	public int selectApplicationCount(Long memNo);

}
