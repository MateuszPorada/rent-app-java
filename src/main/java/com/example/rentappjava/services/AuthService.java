package com.example.rentappjava.services;

import com.example.rentappjava.dtos.RegisterRequestDTO;
import com.example.rentappjava.exceptions.RentAppException;
import com.example.rentappjava.models.User;
import com.example.rentappjava.models.UserVerifyToken;
import com.example.rentappjava.repos.UserRepo;
import com.example.rentappjava.repos.UserVerifyTokenRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
    private final UserRepo userRepo;
    private final UserVerifyTokenRepo userVerifyTokenRepo;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signUser(RegisterRequestDTO registerRequestDTO) {
        Optional<User> optionalUser = userRepo.getUserByEmail(registerRequestDTO.getEmail());
        User user = modelMapper.map(registerRequestDTO, User.class);
        if (optionalUser.isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            emailService.sendEmail(user.getEmail(), "Registration in rent app",
                    "Thank you for signing up in rent app, please verify your email: http://localhost:6060/auth/verify/" + generateVerifcationToken(user));
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

        if (userVerifyToken.getValidUntil().isAfter(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("-02:00")))) {
            user.setEnabled(true);
            userRepo.save(user);
        } else {
            userVerifyTokenRepo.delete(userVerifyToken);
            userRepo.delete(user);
            throw new RentAppException("Token expired");
        }

    }
}
