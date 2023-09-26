package edu.goshop_ecommerce.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorStructure {
	private int status;
	private String message;
	private String rootCause;
}
