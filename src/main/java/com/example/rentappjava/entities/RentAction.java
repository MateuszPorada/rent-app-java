package com.example.rentappjava.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "rent_action")
public class RentAction {
    @Id
    @GeneratedValue
    @Column(name = "rent_action_id")
    private Long id;

    private double price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "living_space_id")
    @ToString.Exclude
    private RentedSpace rentedSpace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User tenant;

    @OneToMany(mappedBy = "rentAction", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Payment> paymentList;

    @OneToMany(mappedBy = "rentAction", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Report> reports;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RentAction that = (RentAction) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
