package com.example.posapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerEntity {

    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerTel;
}
