package edu.goshop_ecommerce.request_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenRequest {
	@NotBlank(message = "refershToken field cannot be blank")
	@NotNull(message = "refershToken field cannot be null")
	@Schema(required = true)
	private String refreshToken;
}
