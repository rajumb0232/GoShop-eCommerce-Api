package edu.goshop_ecommerce.request_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
	@Schema(required = true)
	@Min(100)
	@Max(9999)
	private int flatNo;
	@Schema(required = true)
	@NotBlank(message = "area cannot be blank")
	@NotNull(message = "area cannot be null")
	private String area;
	@Schema(required = true)
	@NotBlank(message = "city cannot be blank")
	@NotNull(message = "city cannot be null")
	@Pattern(regexp = "[A-Z]{1}[a-zA-Z\\s]*", message = "city name should Start with capital letter")
	private String city;
	@NotBlank(message = "landmark cannot be blank")
	@NotNull(message = "landmark cannot be null")
	@Pattern(regexp = "[A-Z]{1}[a-zA-Z\\s]*", message = "ladmark name should Start with capital letter")
	private String landmark;
	@Schema(required = true)
	@NotBlank(message = "state cannot be blank")
	@NotNull(message = "state cannot be null")
	@Pattern(regexp = "[A-Z]{1}[a-zA-Z\\s]*", message = "state Name should Start with capital letter")
	private String state;
	@Schema(required = true)
	@NotBlank(message = "country cannot be blank")
	@NotNull(message = "country cannot be null")
	@Pattern(regexp = "[A-Z]{1}[a-zA-Z\\s]*", message = "country Name should Start with capital letter")
	private String country;
	@Schema(required = true)
	@Min(value = 110000, message = " pincode must be valid")
	@Max(value = 990000, message = " pincode must be valid")
	private int pincode;

}
