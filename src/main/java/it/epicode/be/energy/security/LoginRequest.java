package it.epicode.be.energy.security;

import lombok.Data;

@Data
public class LoginRequest {
	
	private String userName;
	private String password;

}
