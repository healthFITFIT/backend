package com.fitfit.server.api.user.controller;

import com.fitfit.server.api.user.service.IdTokenVerfiy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OAuthController {


    private final IdTokenVerfiy idTokenVerfiy;

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestBody String idToken) {
        try {
            Map<String, Object> userDetails = idTokenVerfiy.authenticateUser(idToken);
            return ResponseEntity.ok(userDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error validating the ID token: " + e.getMessage());
        }
    }
}