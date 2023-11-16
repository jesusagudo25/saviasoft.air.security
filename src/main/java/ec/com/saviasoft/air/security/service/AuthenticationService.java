package ec.com.saviasoft.air.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.saviasoft.air.security.data.PasswordResetTokenRepository;
import ec.com.saviasoft.air.security.data.UserRepository;
import ec.com.saviasoft.air.security.model.pojo.PasswordResetToken;
import ec.com.saviasoft.air.security.model.pojo.Role;
import ec.com.saviasoft.air.security.model.pojo.User;
import ec.com.saviasoft.air.security.model.request.LoginRequest;
import ec.com.saviasoft.air.security.model.request.RegisterRequest;
import ec.com.saviasoft.air.security.model.request.ResetPasswordRequest;
import ec.com.saviasoft.air.security.model.response.AuthenticationResponse;
import ec.com.saviasoft.air.security.util.EmailUtil;
import ec.com.saviasoft.air.security.util.TokenUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private EmailUtil emailUtil;

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            ));

        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String forgotPassword(String email) {
        userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

        passwordResetTokenRepository.findByEmail(email)
            .ifPresent(passwordResetTokenRepository::delete);

        String token = tokenUtil.generateToken();

        try {
            emailUtil.sendForgotPasswordEmail(email, token);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send email please try again");
        }

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setEmail(email);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(tokenUtil.getExpiryDate());
        passwordResetTokenRepository.save(passwordResetToken);

        return "Email sent... please reset password within 1 minute";
    }

    public AuthenticationResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(resetPasswordRequest.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (isBefore(passwordResetToken.getExpiryDate(), tokenUtil.getCurrentDate())) {
            throw new RuntimeException("Token expired");
        }

        User user = userRepository.findByEmail(passwordResetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        userRepository.save(user);

        passwordResetTokenRepository.delete(passwordResetToken);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private boolean isBefore(long expiryDate, long currentDate) {
    	return expiryDate < currentDate;
    }
}
