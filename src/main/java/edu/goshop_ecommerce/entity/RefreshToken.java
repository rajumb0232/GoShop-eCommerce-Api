package edu.goshop_ecommerce.entity;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import edu.goshop_ecommerce.util.CustomIdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class RefreshToken {
	@Id
	@GeneratedValue(generator = "custom_id")
	@GenericGenerator(name = "custom_id", type = CustomIdGenerator.class)
	private String tokenId;
	private String refreshToken;
	private Date expiration;
}
