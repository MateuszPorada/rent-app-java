package com.example.rentappjava.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @NotBlank(message = "You must provide an email ")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "You must provide an password ")
    private String password;

    private String imageUrl;

    private String role = "USER";

    private Instant createdAt = Instant.now();

    private boolean enabled = false;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RentAction> rentActions;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Payment> payments;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Report> reports;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
