package edu.goshop_ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

	@Schema(required = true)
	private String userFirstName;
	@Schema(required = true)
	private String userSecondName;
	@Schema(required = true)
	private long userPhoneNumber;
	@Schema(required = true)
	private String userEmail;
	@Schema(required = true)
	private String userPassword;
}
