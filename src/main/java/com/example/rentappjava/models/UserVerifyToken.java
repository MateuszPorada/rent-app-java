package com.example.rentappjava.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@Entity
public class UserVerifyToken {
    OffsetDateTime validUntil = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("-02:00")).plusDays(1);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

}
