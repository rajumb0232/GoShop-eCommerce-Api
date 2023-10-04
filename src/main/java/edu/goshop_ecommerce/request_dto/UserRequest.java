package edu.goshop_ecommerce.request_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

	@NotNull(message = "Invalid Input")
	@NotBlank(message = "Invalid Input")
	@Schema(required = true)
	private String userFirstName;
	@NotNull(message = "Invalid Input")
	@NotBlank(message = "Invalid Input")
	@Schema(required = true)
	private String userSecondName;
	@Min(value = 6000000000l, message = " phone number must be valid")
	@Max(value = 9999999999l, message = " phone number must be valid")
	@Schema(required = true)
	private long userPhoneNumber;
	@NotBlank(message = "Invalid Email")
	@NotNull(message = "Invalid Email")
	@Column(unique = true)
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	@Schema(required = true)
	private String userEmail;

}
