package com.example.rentappjava.services;

import com.example.rentappjava.dtos.ImageResponse;
import com.example.rentappjava.entities.ImageModel;
import com.example.rentappjava.repos.ImageRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepo imageRepo;
    private final ModelMapper modelMapper;

    public List<String> save(MultipartFile[] images) throws IOException {
        List<String> stringList = new ArrayList<>();
        for (MultipartFile image : images) {
            stringList.add(MapImageModel(image));
        }
        return stringList;
    }

    private String MapImageModel(MultipartFile image) throws IOException {
        ImageModel imageModel = new ImageModel();
        InputStream inputStream = new ByteArrayInputStream(image.getBytes());
        BufferedImage originalImage = ImageIO.read(inputStream);

        imageModel.setName(StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
        imageModel.setContentType(image.getContentType());
        imageModel.setData(image.getBytes());
        imageModel.setWidth(originalImage.getWidth());
        imageModel.setHeight(originalImage.getHeight());
        imageModel.setSize(image.getSize());
        imageRepo.save(imageModel);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(imageModel.getId())
                .toUriString();
    }

    public ImageModel getImage(String id) {
        return imageRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
    }

    public void deleteImage(String id) {
        ImageModel imageModel = imageRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
        imageRepo.delete(imageModel);
    }

    public List<ImageResponse> getList() {
        List<ImageModel> imageModels = imageRepo.findAll();
        List<ImageResponse> imageResponses = new ArrayList<>();
        for (ImageModel imageModel : imageModels) {
            ImageResponse imageResponse = modelMapper.map(imageModel, ImageResponse.class);
            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/image/")
                    .path(imageModel.getId())
                    .toUriString();
            imageResponse.setImageUrl(url);
            imageResponses.add(imageResponse);
        }
        return imageResponses;
    }
}
