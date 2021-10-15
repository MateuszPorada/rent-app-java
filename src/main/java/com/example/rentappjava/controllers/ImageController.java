package com.example.rentappjava.controllers;

import com.example.rentappjava.models.ImageModel;
import com.example.rentappjava.services.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<Object> getFiles() {
        return ResponseEntity.ok().body(imageService.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        ImageModel imageModel = imageService.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(imageModel.getContentType()))
                .body(imageModel.getData());
    }

    @PostMapping
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile[] files) {
        try {
            List<String> stringList = imageService.save(files);
            return new ResponseEntity<>(stringList, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
