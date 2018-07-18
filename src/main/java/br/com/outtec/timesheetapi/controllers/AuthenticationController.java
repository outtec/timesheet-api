package br.com.outtec.timesheetapi.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.outtec.timesheetapi.dtos.EmailDto;
import br.com.outtec.timesheetapi.security.JWTUtil;
import br.com.outtec.timesheetapi.security.JwtUser;
import br.com.outtec.timesheetapi.security.JwtUserFactory;
import br.com.outtec.timesheetapi.services.AuthService;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		JwtUser user = JwtUserFactory.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDto objDto) throws ObjectNotFoundException {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
}