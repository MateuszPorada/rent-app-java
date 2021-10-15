package com.example.rentappjava.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "flats")
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @ElementCollection
    private List<String> imgUrls;

    @OneToMany(mappedBy = "flat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

}
