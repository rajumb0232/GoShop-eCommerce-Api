package edu.goshop_ecommerce.util;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ErrorStructure {
	private int status;
	private String message;
	private String rootCause;
}
