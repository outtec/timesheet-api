package br.com.outtec.timesheetapi.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class JwtUserFactory {

	
	public static JwtUser authenticated() {
		try {
			return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		}catch(Exception e){
			return null;
		}
		
	}

}
