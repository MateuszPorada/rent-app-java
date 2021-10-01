package com.example.rentappjava.models;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
public class UserVerifyToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    Instant validUntil = Instant.now().plusSeconds(86400);
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

}
