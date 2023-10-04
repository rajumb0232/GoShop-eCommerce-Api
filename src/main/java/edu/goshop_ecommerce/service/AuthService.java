package edu.goshop_ecommerce.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.entity.RefreshToken;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.exception.ExpiredRefreshTokenException;
import edu.goshop_ecommerce.exception.NoUserAccociatedWithRefreshTokenException;
import edu.goshop_ecommerce.exception.RefreshTokenNotFoundException;
import edu.goshop_ecommerce.repo.RefreshTokenRepo;
import edu.goshop_ecommerce.repo.UserRepo;
import edu.goshop_ecommerce.request_dto.AuthRequest;
import edu.goshop_ecommerce.request_dto.RefreshTokenRequest;
import edu.goshop_ecommerce.response_dto.AuthResponse;
import edu.goshop_ecommerce.security.JwtService;
import edu.goshop_ecommerce.util.ResponseStructure;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RefreshTokenRepo tokenRepo;

	@Autowired
	private JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AuthResponse authResponse;

	public ResponseEntity<ResponseStructure<AuthResponse>> authenticate(AuthRequest authRequest) {
		log.info("Authenticating user request...");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return generateRefreshAndAccessToken(authRequest.getEmail());
		} else
			throw new UsernameNotFoundException("Failed to authenticate the user.");
	}

	private ResponseEntity<ResponseStructure<AuthResponse>> generateRefreshAndAccessToken(String subject) {
		String accessToken = jwtService.generateJwtToken(subject);
		RefreshToken refreshToken = jwtService.generateRefreshToken();

		authResponse.setAccessToken(accessToken);
		authResponse.setRefershToken(refreshToken.getRefreshToken());

		User user = userRepo.findByUserEmail(subject);
		verifyAndUpdateUserRefreshToken(user, refreshToken);
		ResponseStructure<AuthResponse> structure = new ResponseStructure<>();
		structure.setData(authResponse);
		structure.setMessage("Successfully Authenticated.");
		structure.setStatus(HttpStatus.OK.value());

		log.info("User Updated with new Refersh and Access Token");
		return new ResponseEntity<ResponseStructure<AuthResponse>>(structure, HttpStatus.OK);
	}

	private void verifyAndUpdateUserRefreshToken(User user, RefreshToken refreshToken) {
		RefreshToken exRefreshToken = user.getRefreshToken();
		if (exRefreshToken != null) {
			refreshToken.setTokenId(exRefreshToken.getTokenId());
			refreshToken = tokenRepo.save(refreshToken);
		} else {
			user.setRefreshToken(refreshToken);
			refreshToken = tokenRepo.save(refreshToken);
			userRepo.save(user);
		}
	}

	public ResponseEntity<ResponseStructure<AuthResponse>> refreshAccessToken(RefreshTokenRequest refreshTokenRequest) {
		log.info("Requested to refresh Access Token");
		RefreshToken refreshToken = tokenRepo.findByRefreshToken(refreshTokenRequest.getRefreshToken());
		if (refreshToken != null) {
			User user = userRepo.findByRefreshToken(refreshToken);
			if (user != null) {
				if (refreshToken.getExpiration().before(new Date())) {
					return generateRefreshAndAccessToken(user.getUserEmail());
				} else
					throw new ExpiredRefreshTokenException("Failed to refresh Access Token.");
			} else
				throw new NoUserAccociatedWithRefreshTokenException("Failed to refresh Access Token.");
		} else
			throw new RefreshTokenNotFoundException("Failed to refresh Access Token.");

	}

}
