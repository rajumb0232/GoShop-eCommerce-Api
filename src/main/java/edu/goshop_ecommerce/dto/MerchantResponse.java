package edu.goshop_ecommerce.dto;

import java.time.LocalDateTime;

import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.enums.Verification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantResponse {
	private long userId;
	private String userFirstName;
	private String userSecondName;
	private long userPhoneNumber;
	private String UserEmail;
	private String userPassword;
	private LocalDateTime userCreatedDateTime;
	private UserRole userRole;
	private Verification verification;
}
