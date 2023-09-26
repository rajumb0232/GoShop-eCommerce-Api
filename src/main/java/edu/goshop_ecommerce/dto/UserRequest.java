package edu.goshop_ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

	@NotNull(message = "user first name cannot be bull")
	@NotBlank(message = "user first name cannot be blank")
	@Schema(required = true)
	private String userFirstName;
	@NotNull(message = "user second name cannot be bull")
	@NotBlank(message = "user second name cannot be blank")
	@Schema(required = true)
	private String userSecondName;
	@Min(value = 6000000000l, message = " phone number must be valid")
	@Max(value = 9999999999l, message = " phone number must be valid")
	@Schema(required = true)
	private long userPhoneNumber;
	@NotBlank
	@NotNull
	@Column(unique = true)
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	@Schema(required = true)
	private String userEmail;
	@NotBlank(message = "Password is required")
	@NotNull
	@Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "must contain at least one letter, one number, one special character")
	@Schema(required = true)
	private String userPassword;

}
