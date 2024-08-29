package com.example.posapi.bo.custom;

import com.example.posapi.bo.SuperBO;
import com.example.posapi.dto.OrderDTO;
import com.example.posapi.dto.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {

    boolean placeOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetailDTOs, Connection connection) throws SQLException;
}
