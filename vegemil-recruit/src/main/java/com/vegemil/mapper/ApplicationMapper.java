package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vegemil.domain.AcademyInfoDTO;
import com.vegemil.domain.ApplicationDTO;
import com.vegemil.domain.CareerDTO;
import com.vegemil.domain.IntroduceDTO;
import com.vegemil.domain.PersonalInfoDTO;
import com.vegemil.domain.QualificationDTO;
import com.vegemil.domain.ResumeDTO;


@Mapper
public interface ApplicationMapper {

	public int savePersonalInformation(ApplicationDTO params);

	public PersonalInfoDTO selectPersonalInfo(@Param("idx") Long idx, @Param("memNo") Long memNo);
	
	public AcademyInfoDTO selectAcademy(@Param("idx") Long idx ,@Param("memNo") Long memNo);
	
	public QualificationDTO selectQualification(@Param("idx") Long idx, @Param("memNo") Long memNo);
	
	public CareerDTO selectCareer(@Param("idx") Long idx, @Param("memNo") Long memNo);
	
	public IntroduceDTO selectIntroduce(@Param("idx") Long idx, @Param("memNo") Long memNo);
	
	public ApplicationDTO selectApplication(@Param("idx") Long idx);
	
	public int updateCareer(CareerDTO params);
	
	public int updateIntroduce(IntroduceDTO params);
	
	public int updateQualification(QualificationDTO params);

	public int updatePersonalInformation(PersonalInfoDTO params);
	
	public int updateAcademy(Map<String, Object> params);
	
	public int registAcademy(AcademyInfoDTO params);
	
	public ResumeDTO selectResume(Long idx);
	
	public List<ResumeDTO> selectResumeList(Long memNo);

	public List<ApplicationDTO> selectApplicationList(Long memNo);

	public int selectApplicationCount(Long memNo);
	
	public int selectApplicationCntBySth(@Param("memNo") Long memNo, @Param("sTh") String sTh);
	
	public int selectIdxBySth(@Param("memNo") Long memNo, @Param("sTh") String sTh);
	
	public int selectIdxCount(@Param("memNo") Long memNo, @Param("sTh") String sTh);
	
	public int deleteResume(Long idx);
	
	public String selectPortSaveName(@Param("memNo") Long memNo, @Param("idx") Long idx);
	

}
