package com.example.rentappjava.dtos;

import com.example.rentappjava.models.ImageModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImageResponseModelDTO extends ImageModel {
    private String ImageUrl;
}
