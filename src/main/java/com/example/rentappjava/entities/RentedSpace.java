package com.example.rentappjava.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "living_spaces")
public class RentedSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "living_space_id")
    private Long id;

    private String address;

    @Enumerated(EnumType.STRING)
    private spaceType spaceType;

    @ElementCollection
    private List<String> imgUrls;

    @OneToMany(mappedBy = "rentedSpace", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RentAction> rentAction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RentedSpace that = (RentedSpace) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public enum spaceType {
        Flat, Room
    }
}
