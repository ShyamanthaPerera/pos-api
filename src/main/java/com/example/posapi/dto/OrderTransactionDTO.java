package com.example.posapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderTransactionDTO implements Serializable {

    private OrderDTO orderDTO;
    private List<OrderDetailDTO> orderDetailDTOS;
}
