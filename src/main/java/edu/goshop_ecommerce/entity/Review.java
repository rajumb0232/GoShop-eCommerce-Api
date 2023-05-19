package edu.goshop_ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reviewId;
	private long userId;
	private String userName;
	private int rating;
	private String feedback;
}
