package edu.goshop_ecommerce.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import edu.goshop_ecommerce.entity.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtService {

	@Value("${access.token.secret.key}")
	private String accessTokenKey;

	// generating the token here...
	public String generateJwtToken(String email) {
		log.info("Generting JWT token...");
		Map<String, Object> claims = new HashMap<>();
		return createJwtToken(claims, email);
	}

	// creating token using subject and signature with issued and expiration time
	private String createJwtToken(Map<String, Object> claims, String email) {
		return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
				.signWith(getSignatureKey(), SignatureAlgorithm.HS512).compact();
	}

	// generating signature key to the access token.
	private Key getSignatureKey() {
		byte[] keyBytes = Decoders.BASE64.decode(accessTokenKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// *********** JWT Validation and extraction ************

	// extracting user-name form the token
	public String ExtractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// The method extract the claims from the token, takes a generic argument to
	// return a desired claim based on requirement.
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		log.info("Extractiong claims...");
		Claims claims = Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(token).getBody();
		return claimResolver.apply(claims);
	}

	// extracting Expiration-Time of the token
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
	}

	/**
	 * @return {@link Boolean}
	 *         <p>
	 *         The validates is the userName against the userName in database, and checks
	 *         whether the token is expired.
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		log.info("Validating the token...");
		return (ExtractUserName(token).equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public RefreshToken generateRefreshToken() {
		RefreshToken token = new RefreshToken();
		// correct expiration here
		token.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20));
		token.setRefreshToken(UUID.randomUUID().toString());
		return token;
	}
}
