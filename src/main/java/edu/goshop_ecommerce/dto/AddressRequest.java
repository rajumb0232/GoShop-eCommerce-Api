package edu.goshop_ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

	@Schema(required = true)
	private int flatNo;
	@Schema(required = true)
	private String area;
	@Schema(required = true)
	private String city;
	@Schema(required = true)
	private String landmark;
	@Schema(required = true)
	private String state;
	@Schema(required = true)
	private String country;
	@Schema(required = true)
	private int pincode;

}
