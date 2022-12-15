package com.vegemil.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vegemil.domain.AdminDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.mapper.AdminMapper;

@Service
public class AdminService implements UserDetailsService {
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	/**
	 * result : 0(아이디 중복)
	 * result : 1(저장 성공)
	 * result : 2(저장 실패)
	 */
    public Integer joinAdmin(AdminDTO adminDto) {
    	
        // 비밀번호 암호화
        adminDto.setAPwd(passwordEncoder.encode(adminDto.getAPwd()));
        
        int queryResult = 0;
        
        AdminDTO loginUser = adminMapper.findAdminById(adminDto.getAId());
        
        if(loginUser == null) {
        	queryResult = adminMapper.insertAdmin(adminDto);
    		if(queryResult == 1) {
    			return 1;
    		} else {
    			return 2;
    		}
        }else {
        	return 0;
        }
    }
    
    public Map<String, Object> validationLogin(AdminDTO adminDto) {
    	AdminDTO loginUser = adminMapper.findAdminById(adminDto.getAId());
    	Map<String, Object> params = new LinkedHashMap<>();

       if(loginUser==null) {
    	   params.put("result", 1);
    	   return params;
       }

       if(!passwordEncoder.matches(adminDto.getAPwd(), loginUser.getAPwd())) {
    	   params.put("result", 2);
    	   return params;
       }
       
       params.put("result", 0);
       params.put("admin", loginUser);
       return params;
    }
    
    public AdminDTO getAdmin(String aId) {
    	AdminDTO loginUser = adminMapper.findAdminById(aId);
    	
    	return loginUser;
    }
    
    public boolean initPwd(AdminDTO adminDto) {
    	int result = adminMapper.checkMember(adminDto);
    	if(result > 0) {
    		String password = "abcd1234";
    		adminDto.setAPwd(passwordEncoder.encode(password));
    		result = adminMapper.updateAdminPwd(adminDto);
    		if(result > 0) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public boolean changePwd(AdminDTO adminDto) {
    	AdminDTO loginUser = adminMapper.findAdminById(adminDto.getAId());
    	int result = 0; 

       if(!passwordEncoder.matches(adminDto.getAPwd(), loginUser.getAPwd())) {
    	   adminDto.setAPwd(passwordEncoder.encode(adminDto.getAPwd()));
    	   result = adminMapper.updateAdminPwd(adminDto);
   		   if(result > 0) {
   			   return true;
   		   }
       }
       
       return false;
    }
    
    public boolean activeMember(AdminDTO adminDto) {
		
		int queryResult = 0;
		queryResult = adminMapper.selectAdminCount(adminDto);
		
		if (queryResult != 0) {
			queryResult = adminMapper.activeAdmin(adminDto);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public UserDetails loadUserByUsername(String aId) throws UsernameNotFoundException {
		//여기서 받은 유저 패스워드와 비교하여 로그인 인증
		AdminDTO admin = adminMapper.findAdminById(aId);
        if (admin == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
        return admin;
	}

}