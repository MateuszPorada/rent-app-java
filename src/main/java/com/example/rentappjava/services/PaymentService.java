package com.example.rentappjava.services;

import com.example.rentappjava.dtos.PaymentDTO;
import com.example.rentappjava.entities.Payment;
import com.example.rentappjava.entities.RentAction;
import com.example.rentappjava.entities.User;
import com.example.rentappjava.repos.PaymentRepo;
import com.example.rentappjava.repos.RentActionRepo;
import com.example.rentappjava.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepo paymentRepo;
    private final RentActionRepo rentActionRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Transactional
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        payment.setRentAction(rentActionRepo.findById(paymentDTO.getRentActionID()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rent action not found")));
        payment.setTenant(userRepo.findById(paymentDTO.getTenantID()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found")));
        payment.setTimestamp(Instant.now());
        return modelMapper.map(paymentRepo.save(payment), PaymentDTO.class);
    }

    public List<PaymentDTO> getPaymentsByAction(Long id) {
        RentAction rentAction = rentActionRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Action not found"));
        return rentAction.getPaymentList().stream().map(payment -> modelMapper.map(payment, PaymentDTO.class)).collect(Collectors.toList());
    }

    public List<PaymentDTO> getPaymentsByTenant(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user.getPayments().stream().map(payment -> modelMapper.map(payment, PaymentDTO.class)).collect(Collectors.toList());
    }
}
