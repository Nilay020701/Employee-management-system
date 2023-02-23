package EMS.service;

import EMS.DTO.Request.AuthenticationRequest;
import EMS.DTO.Request.RegisterRequest;
import EMS.DTO.Response.AuthenticationResponse;
import EMS.auth.JWTService;
import EMS.auth.Role;
import EMS.model.User;
import EMS.repositry.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(new HashMap<>(), user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        log.info("================="+ authenticationRequest.getEmail()+' '+authenticationRequest.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        var user = repository.findById(authenticationRequest.getEmail())
                .orElseThrow();
        log.info("User is {}", user);
        var jwtToken = jwtService.generateToken(new HashMap<>(), user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
