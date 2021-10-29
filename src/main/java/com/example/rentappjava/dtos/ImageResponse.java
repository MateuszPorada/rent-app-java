package com.example.rentappjava.dtos;

import com.example.rentappjava.entities.ImageModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImageResponse extends ImageModel {
    private String ImageUrl;
}
