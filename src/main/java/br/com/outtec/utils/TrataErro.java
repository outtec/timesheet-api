package br.com.outtec.utils;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;

public class TrataErro{
	
	public TrataErro() {}
	
	public ResponseEntity<Response<RequestBody>> retornaErrotratado(Object result, Object RequestBody)throws NoSuchAlgorithmException{
		
		Response<RequestBody> response = new Response<RequestBody>();
		if(((Errors) result).hasErrors()) {
			((Errors) result).getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		return null;
	}
}
