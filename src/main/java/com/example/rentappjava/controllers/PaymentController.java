package com.example.rentappjava.controllers;

import com.example.rentappjava.dtos.PaymentDTO;
import com.example.rentappjava.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    private ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        return new ResponseEntity<>(paymentService.createPayment(paymentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/by-action/{id}")
    private ResponseEntity<List<PaymentDTO>> getPaymentsByAction(@PathVariable Long id) {
        return new ResponseEntity<>(paymentService.getPaymentsByAction(id), HttpStatus.OK);
    }

    @GetMapping("/by-tenant/{id}")
    private ResponseEntity<List<PaymentDTO>> getPaymentsByTenant(@PathVariable Long id) {
        return new ResponseEntity<>(paymentService.getPaymentsByTenant(id), HttpStatus.OK);
    }

}
