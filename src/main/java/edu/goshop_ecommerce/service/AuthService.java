package edu.goshop_ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.goshop_ecommerce.dto.AuthRequest;
import edu.goshop_ecommerce.dto.AuthResponse;
import edu.goshop_ecommerce.entity.RefreshToken;
import edu.goshop_ecommerce.entity.User;
import edu.goshop_ecommerce.repo.RefreshTokenRepo;
import edu.goshop_ecommerce.repo.UserRepo;
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
			String accessToken = jwtService.generateJwtToken(authRequest.getEmail());
			RefreshToken refreshToken = jwtService.generateRefreshToken();

			authResponse.setAccessToken(accessToken);
			authResponse.setRefershToken(refreshToken.getRefreshToken());

			User user = userRepo.findByUserEmail(authRequest.getEmail());
			verifyAndUpdateUserRefreshToken(user, refreshToken);
			ResponseStructure<AuthResponse> structure = new ResponseStructure<>();
			structure.setData(authResponse);
			structure.setMessage("Successfully Authenticated.");
			structure.setStatus(HttpStatus.OK.value());

			log.info("User Updated with new Refersh and Access Token");
			return new ResponseEntity<ResponseStructure<AuthResponse>>(structure, HttpStatus.OK);
		} else
			throw new UsernameNotFoundException("Failed to authenticate the user.");
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

}
