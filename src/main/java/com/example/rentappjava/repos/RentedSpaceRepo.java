package com.example.rentappjava.repos;

import com.example.rentappjava.entities.RentedSpace;
import com.example.rentappjava.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentedSpaceRepo extends JpaRepository<RentedSpace, Long> {
    List<RentedSpace> findRentedSpaceByOwner(User user);
}
