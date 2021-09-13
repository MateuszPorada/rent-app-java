package com.example.rentappjava.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "images")
public class ImageModel {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "image_id")
    private String id;

    private String name;

    private String contentType;

    private Long size;

    private int width;

    private int height;

    @Lob
    @JsonIgnore
    private byte[] data;
}
