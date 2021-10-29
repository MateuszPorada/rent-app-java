package com.example.rentappjava.repos;

import com.example.rentappjava.entities.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<ImageModel,String> {
}
