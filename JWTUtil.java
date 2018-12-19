package net.openobject.tmmm.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @작성자 : 박형진
 * @작성일 : 2018. 10. 11.
 * @개요 : JWT 관련 공통 기능을 담은 util 클래스
 */

public class JWTUtil {
	public String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
						.setHeaderParam("typ", "JWT")
						.setHeaderParam("regDate", System.currentTimeMillis())
						.setSubject(subject)
						.addClaims(claims)
						.signWith(SignatureAlgorithm.HS256, generateKey())
						.compact();
	}
	
	public String getClaim(String jwt, String key) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
					  .setSigningKey(generateKey())
					  .parseClaimsJws(jwt);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			e.printStackTrace();
		}
		return claims.getBody().get(key).toString();
	}
	
	public boolean isRight(String jwt) {			// 정확한 형식인가?
        try{
            Jws<Claims> claims = Jwts.parser()
                      				.setSigningKey(generateKey())
                      				.parseClaimsJws(jwt);
            if(claims == null) {
            		return false;
            }
        } catch (Exception e) {
        		return false;
        }
        return true;
    }
	
	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = Constants.SALT_KEY.getBytes(StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return key;
	}
}
