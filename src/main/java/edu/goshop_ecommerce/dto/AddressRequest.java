package edu.goshop_ecommerce.dto;

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

	@Min(1000)
	@Max(9999)
	private int flatNo;
	@NotBlank(message = "area cannot be blank")
	@NotNull(message = "area cannot be null")
	private String area;
	@NotBlank(message = "city cannot be blank")
	@NotNull(message = "city cannot be null")
	@Pattern(regexp = "[A-Z]{1}[a-zA-Z\\s]*", message = "city name should Start with capital letter")
	private String city;
	@NotBlank(message = "landmark cannot be blank")
	@NotNull(message = "landmark cannot be null")
	@Pattern(regexp = "[A-Z]{1}[a-zA-Z\\s]*", message = "ladmark name should Start with capital letter")
	private String landmark;
	@NotBlank(message = "state cannot be blank")
	@NotNull(message = "state cannot be null")
	@Pattern(regexp = "[A-Z]{1}[a-zA-Z\\s]*", message = "state Name should Start with capital letter")
	private String state;
	@NotBlank(message = "country cannot be blank")
	@NotNull(message = "country cannot be null")
	@Pattern(regexp = "[A-Z]{1}[a-zA-Z\\s]*", message = "country Name should Start with capital letter")
	private String country;
	@Min(value = 110000, message = " pincode must be valid")
	@Max(value = 990000, message = " pincode must be valid")
	private int pincode;

}
