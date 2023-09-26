package edu.goshop_ecommerce.dto;

import java.time.LocalDateTime;

import edu.goshop_ecommerce.enums.UserRole;
import edu.goshop_ecommerce.enums.Verification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
	private long userId;
	private String userFirstName;
	private String userSecondName;
	private long userPhoneNumber;
	private LocalDateTime userCreatedDateTime;
	private UserRole userRole;
	private Verification verification;
}
