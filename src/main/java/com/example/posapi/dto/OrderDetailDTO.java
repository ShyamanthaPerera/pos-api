package com.example.posapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailDTO implements Serializable {

    private String orderId;
    private String itemCode;
    private int orderQty;
    private double unitPrice;
}
