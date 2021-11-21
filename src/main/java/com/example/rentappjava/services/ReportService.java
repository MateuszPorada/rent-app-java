package com.example.rentappjava.services;

import com.example.rentappjava.dtos.ReportDTO;
import com.example.rentappjava.entities.Report;
import com.example.rentappjava.repos.RentActionRepo;
import com.example.rentappjava.repos.ReportRepo;
import com.example.rentappjava.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class ReportService {
    private final ReportRepo reportRepo;
    private final UserRepo userRepo;
    private final RentActionRepo rentActionRepo;
    private final ModelMapper modelMapper;

    public ReportDTO createReport(ReportDTO reportDTO) {
        assert (userRepo.existsById(reportDTO.getTenantId())) : new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        assert (rentActionRepo.existsById(reportDTO.getRentActionId())) : new ResponseStatusException(HttpStatus.NOT_FOUND, "Rent action not found");
        assert (rentActionRepo.getById(reportDTO.getRentActionId()).getTenant().equals(userRepo.getById(reportDTO.getTenantId()))) : new ResponseStatusException(HttpStatus.NOT_FOUND, "User and action didn't match");
        Report report = modelMapper.map(reportDTO, Report.class);
        return modelMapper.map(reportRepo.save(report), ReportDTO.class);
    }
}
