package edu.goshop_ecommerce.dto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@Scope(value = "prototype")
public class AuthResponse {
	private String accessToken;
	private String refershToken;
}
