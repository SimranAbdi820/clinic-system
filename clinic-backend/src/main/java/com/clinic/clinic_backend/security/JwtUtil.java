package com.clinic.clinic_backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username, String role) {
        try {
            String payload = username + ":" + role + ":" + System.currentTimeMillis();
            String encodedPayload = Base64.getEncoder().encodeToString(payload.getBytes());
            String signature = sign(encodedPayload);
            return encodedPayload + "." + signature;
        } catch (Exception e) {
            throw new RuntimeException("Token generation failed");
        }
    }

    public String extractUsername(String token) {
        try {
            String[] parts = token.split("\\.");
            String decoded = new String(Base64.getDecoder().decode(parts[0]));
            return decoded.split(":")[0];
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            String expectedSignature = sign(parts[0]);
            return expectedSignature.equals(parts[1]);
        } catch (Exception e) {
            return false;
        }
    }

    private String sign(String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
        return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
    }
}