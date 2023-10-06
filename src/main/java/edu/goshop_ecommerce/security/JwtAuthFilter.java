package edu.goshop_ecommerce.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.goshop_ecommerce.util.ErrorStructure;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.atTrace();
		log.info("Authenticating user Access Token...");
		String token = request.getHeader("Authorization");
		String userName = null;
		try {
			if (token != null && token.startsWith("Bearer ")) {
				token = token.substring(7);
				userName = jwtService.ExtractUserName(token);
				log.info("username extracted from token");
			}
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
				if (jwtService.validateToken(token, userDetails)) {
					log.info("username is valid");
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
					log.info("User authenticated successfully");
				}
			}
		} catch (ExpiredJwtException e) {
			handleJwtTokenExpiredException(response, e);
		} catch (JwtException e) {
			handleOtherJwtException(response, e);
		}
		filterChain.doFilter(request, response);
	}

	private void handleJwtTokenExpiredException(HttpServletResponse response, ExpiredJwtException e)
			throws StreamWriteException, DatabindException, IOException {
		log.error("Error Authenticating user : " + e.getMessage());
		response.setHeader("error", e.getMessage());
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType("Application/json");
		ErrorStructure error = ErrorStructure.builder().status(HttpStatus.FORBIDDEN.value())
				.message("Failed to authenticate the user").rootCause(e.getMessage()).build();
		new ObjectMapper().writeValue(response.getOutputStream(), error);
	}

	private void handleOtherJwtException(HttpServletResponse response, JwtException e)
			throws StreamWriteException, DatabindException, IOException {
		log.error("Error authenticating user : " + e.getMessage());
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setHeader("error", e.getMessage());
		response.setContentType("Applciaiton/json");
		ErrorStructure error = ErrorStructure.builder().status(HttpStatus.FORBIDDEN.value())
				.message("Failed to authenticate the user.").rootCause(e.getMessage()).build();
		new ObjectMapper().writeValue(response.getOutputStream(), error);
	}

}
