package edu.goshop_ecommerce.request_dto;

import lombok.Data;

@Data
public class AuthRequest {
	private String email;
	private String password;
}
