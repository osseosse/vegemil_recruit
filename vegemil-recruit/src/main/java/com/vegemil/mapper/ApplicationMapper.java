package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vegemil.domain.AcademyInfoDTO;
import com.vegemil.domain.ApplicationDTO;
import com.vegemil.domain.PersonalInfoDTO;


@Mapper
public interface ApplicationMapper {

	public int savePersonalInformation(ApplicationDTO params);

	public PersonalInfoDTO selectPersonalInfo(@Param("idx") Long idx,@Param("memNo") Long memNo);
	
	public AcademyInfoDTO selectAcademy(@Param("idx") Long idx,@Param("memNo") Long memNo);

	public int updatePersonalInformation(PersonalInfoDTO params);
	
	public int updateAcademy(AcademyInfoDTO params);

	public List<ApplicationDTO> selectApplicationList(ApplicationDTO params);

	public int selectApplicationCount(@Param("idx") Long idx,@Param("memNo") Long memNo);

}
