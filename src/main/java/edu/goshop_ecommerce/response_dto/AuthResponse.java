package edu.goshop_ecommerce.response_dto;

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
