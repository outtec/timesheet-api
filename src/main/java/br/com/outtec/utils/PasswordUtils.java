package br.com.outtec.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
	
	/**
	 * Gera hash com BCrypt
	 * @param password
	 * @return string
	 */
	public static String getBCrypt(String password) {
		if(password == null) {
			return password;
		}
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		return bCryptEncoder.encode(password);
	}
	
	public static boolean isValidPassword(String password, String encodedPassword){
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		return bCryptEncoder.matches(password, encodedPassword);

				
	}
}
