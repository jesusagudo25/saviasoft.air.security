package ec.com.saviasoft.air.security.controller;

import ec.com.saviasoft.air.security.model.pojo.User;
import ec.com.saviasoft.air.security.model.request.LoginRequest;
import ec.com.saviasoft.air.security.model.request.RegisterRequest;
import ec.com.saviasoft.air.security.model.request.ResetPasswordRequest;
import ec.com.saviasoft.air.security.model.response.AuthenticationResponse;
import ec.com.saviasoft.air.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest loginRequest
    ){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody Map<String, Object> payload
    ) {
        return ResponseEntity.ok(authenticationService.forgotPassword(payload.get("email").toString()));
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<Boolean> resetPassword(
            @RequestBody ResetPasswordRequest resetPasswordRequest
    ) {
        return ResponseEntity.ok(authenticationService.resetPassword(resetPasswordRequest));
    }

}
