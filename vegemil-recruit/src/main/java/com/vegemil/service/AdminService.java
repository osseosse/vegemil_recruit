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
    public Integer joinAdmin(MemberDTO memberDto) {
    	
        // 비밀번호 암호화
    	memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        
        int queryResult = 0;
        
        MemberDTO loginUser = adminMapper.findAdminById(memberDto.getEmailAddr());
        
        if(loginUser == null) {
        	queryResult = adminMapper.insertAdmin(memberDto);
    		if(queryResult == 1) {
    			return 1;
    		} else {
    			return 2;
    		}
        }else {
        	return 0;
        }
    }
    
    public Map<String, Object> validationLogin(MemberDTO memberDto) {
    	MemberDTO loginUser = adminMapper.findAdminById(memberDto.getEmailAddr());
    	Map<String, Object> params = new LinkedHashMap<>();

       if(loginUser==null) {
    	   params.put("result", 1);
    	   return params;
       }

       if(!passwordEncoder.matches(memberDto.getPassword(), loginUser.getPassword())) {
    	   params.put("result", 2);
    	   return params;
       }
       
       params.put("result", 0);
       params.put("admin", loginUser);
       return params;
    }
    
    public MemberDTO getAdmin(String emailAddr) {
    	MemberDTO loginUser = adminMapper.findAdminById(emailAddr);
    	
    	return loginUser;
    }
    
    public boolean initPwd(MemberDTO memberDto) {
    	int result = adminMapper.checkMember(memberDto);
    	if(result > 0) {
    		String password = "abcd1234";
    		memberDto.setPassword(passwordEncoder.encode(password));
    		result = adminMapper.updateAdminPwd(memberDto);
    		if(result > 0) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public boolean changePwd(MemberDTO memberDto) {
    	MemberDTO loginUser = adminMapper.findAdminById(memberDto.getEmailAddr());
    	int result = 0; 

       if(!passwordEncoder.matches(memberDto.getPassword(), loginUser.getPassword())) {
    	   memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
    	   result = adminMapper.updateAdminPwd(memberDto);
   		   if(result > 0) {
   			   return true;
   		   }
       }
       
       return false;
    }
    
    public boolean activeMember(MemberDTO memberDto) {
		
		int queryResult = 0;
		queryResult = adminMapper.selectAdminCount(memberDto);
		
		if (queryResult != 0) {
			queryResult = adminMapper.activeAdmin(memberDto);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public UserDetails loadUserByUsername(String emailAddr) throws UsernameNotFoundException {
		//여기서 받은 유저 패스워드와 비교하여 로그인 인증
		MemberDTO admin = adminMapper.findAdminById(emailAddr);
        if (admin == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
        return admin;
	}

}