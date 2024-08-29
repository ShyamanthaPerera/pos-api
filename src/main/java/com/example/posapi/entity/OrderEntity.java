package com.example.posapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderEntity {

    private String orderId;
    private LocalDate orderDate;
    private String customerId;
    private double totalAmount;
}
