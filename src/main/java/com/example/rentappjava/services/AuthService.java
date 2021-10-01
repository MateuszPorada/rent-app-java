package com.example.rentappjava.services;

import com.example.rentappjava.dtos.LoginRequest;
import com.example.rentappjava.dtos.LoginResponse;
import com.example.rentappjava.dtos.RefreshTokenRequest;
import com.example.rentappjava.dtos.RegisterRequestDTO;
import com.example.rentappjava.exceptions.RentAppException;
import com.example.rentappjava.models.User;
import com.example.rentappjava.models.UserVerifyToken;
import com.example.rentappjava.repos.UserRepo;
import com.example.rentappjava.repos.UserVerifyTokenRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
    private final UserRepo userRepo;
    private final UserVerifyTokenRepo userVerifyTokenRepo;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;
    private final EmailService emailService;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signUser(RegisterRequestDTO registerRequestDTO) {
        Optional<User> optionalUser = userRepo.findByEmail(registerRequestDTO.getEmail());
        User user = modelMapper.map(registerRequestDTO, User.class);
        if (optionalUser.isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            String token = generateVerifcationToken(user);
            System.out.println(token);
            emailService.sendEmail(user.getEmail(), "Registration in rent app",
                    "Thank you for signing up in rent app, please verify your email: http://localhost:6060/auth/verify/" + token);
        } else
            throw new RentAppException("User with this email already exist");
    }

    private String generateVerifcationToken(User user) {
        UserVerifyToken userVerifyToken = new UserVerifyToken();
        String token = UUID.randomUUID().toString();
        userVerifyToken.setUser(user);
        userVerifyToken.setToken(token);
        userVerifyTokenRepo.save(userVerifyToken);
        return token;
    }

    public void verify(String token) {
        UserVerifyToken userVerifyToken = userVerifyTokenRepo.findUserVerifyTokenByToken(token).orElseThrow(() -> new RentAppException("Token doesn't exist"));
        User user = userRepo.findById(userVerifyToken.getUser().getUser_id()).orElseThrow(() -> new RentAppException("User doesn't exist"));
        if (userVerifyToken.getValidUntil().isAfter(Instant.now())) {
            user.setEnabled(true);
            userRepo.save(user);
        } else {
            userVerifyTokenRepo.delete(userVerifyToken);
            userRepo.delete(user);
            throw new RentAppException("Token expired");
        }
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return LoginResponse.builder()
                .authToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusSeconds(jwtProvider.getTokenExpirationTime()))
                .username(loginRequest.getEmail())
                .build();
    }

    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        Optional<User> optionalUser = userRepo.findByEmail(refreshTokenRequest.getEmail());
        if (optionalUser.isPresent()) {
            String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getEmail(), optionalUser.get().getRole());
            return LoginResponse.builder()
                    .authToken(token)
                    .refreshToken(refreshTokenRequest.getRefreshToken())
                    .expiresAt(Instant.now().plusSeconds(jwtProvider.getTokenExpirationTime()))
                    .username(refreshTokenRequest.getEmail())
                    .build();
        } else {
            throw new RentAppException("User not found");
        }
    }
}
