package com.example.rentappjava.services;

import com.example.rentappjava.dtos.ImageResponseModelDTO;
import com.example.rentappjava.models.ImageModel;
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
        InputStream inputStream = new ByteArrayInputStream(image.getBytes());
        BufferedImage originalImage = ImageIO.read(inputStream);

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


    public ImageModel getImage(String id) {
        return imageRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
    }

    public void deleteImage(String id) {
        ImageModel imageModel = imageRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
        imageRepo.delete(imageModel);
    }

    public List<ImageModel> getAllFiles() {
        return imageRepo.findAll();
    }

    public List<ImageResponseModelDTO> getList() {
        List<ImageModel> imageModels = imageRepo.findAll();
        List<ImageResponseModelDTO> imageResponseModelDTOS = new ArrayList<>();
        for (ImageModel imageModel : imageModels) {
            ImageResponseModelDTO imageResponseModelDTO = modelMapper.map(imageModel, ImageResponseModelDTO.class);
            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/image/")
                    .path(imageModel.getId())
                    .toUriString();
            imageResponseModelDTO.setImageUrl(url);
            imageResponseModelDTOS.add(imageResponseModelDTO);
        }
        return imageResponseModelDTOS;
    }
}
