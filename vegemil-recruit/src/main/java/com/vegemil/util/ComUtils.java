package com.vegemil.util;

import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class ComUtils {
	public String getTest() {
		return "test";
	}
	
	public String getTest(String test) {
		String text = test + "TEST";
		return text;
	}
	
	public String getTest(String test, String test2) {
		String text = test + "TEST" + test2;
		return text;
	}
	
	public String getDateFormat(String date) {
		StringBuffer sb = new StringBuffer();
		sb.append(date.substring(0, 4));
		sb.append("년 ");
		sb.append(date.substring(5, 7));
		sb.append("월 ");
		sb.append(date.substring(8, 10));
		sb.append("일");
		
		return sb.toString();
	}
	
	public int getAge(String birth) {
		Calendar current = Calendar.getInstance();
		String[] birthDayArr = birth.split("-");
		int birthYear = Integer.parseInt(birthDayArr[0]);
		int birthMonth = Integer.parseInt(birthDayArr[1]);
		int birthDay = Integer.parseInt(birthDayArr[2]);
				
        
        int currentYear  = current.get(Calendar.YEAR);
        int currentMonth = current.get(Calendar.MONTH) + 1;
        int currentDay   = current.get(Calendar.DAY_OF_MONTH);
        
        System.out.println("현재 년 : "+currentYear);
        System.out.println("현재 월 : "+currentMonth);
        System.out.println("현재 일 : "+currentDay);
        
        // 만 나이 구하기 2022-1995=27 (현재년-태어난년)
        int age = currentYear - birthYear;
        // 만약 생일이 지나지 않았으면 -1
        if (birthMonth * 100 + birthDay > currentMonth * 100 + currentDay) 
            age--;
        // 5월 26일 생은 526
        // 현재날짜 5월 25일은 525
        // 두 수를 비교 했을 때 생일이 더 클 경우 생일이 지나지 않은 것이다.
        return age;
	}
}
