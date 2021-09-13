package com.example.rentappjava.services;

import com.example.rentappjava.models.ImageModel;
import com.example.rentappjava.repos.ImageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepo imageRepo;

    public String save(MultipartFile image) throws IOException {
        return MapImageModel(image);
    }

    public List<String> save(MultipartFile[] images) throws IOException {
        List<String> stringList = new ArrayList<>();
        for (MultipartFile image : images) {
            stringList.add(MapImageModel(image));
        }
        return stringList;
    }

    private String MapImageModel(MultipartFile image) throws IOException {
        ImageModel imageModel = new ImageModel();
        InputStream in = new ByteArrayInputStream(image.getBytes());
        BufferedImage originalImage = ImageIO.read(in);
        imageModel.setName(StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
        imageModel.setContentType(image.getContentType());
        imageModel.setData(image.getBytes());
        imageModel.setWidth(originalImage.getWidth());
        imageModel.setHeight(originalImage.getHeight());
        System.out.println(originalImage.getType());
        System.out.println(originalImage.getTransparency());
        imageModel.setSize(image.getSize());
        imageRepo.save(imageModel);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(imageModel.getId())
                .toUriString();
    }

    public Optional<ImageModel> getImage(String id) {
        return imageRepo.findById(id);
    }

    public void deleteImage(String id) {
        imageRepo.deleteById(id);
    }

}
