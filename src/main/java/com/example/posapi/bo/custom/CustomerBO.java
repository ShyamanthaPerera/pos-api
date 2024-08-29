package com.example.posapi.bo.custom;

import com.example.posapi.bo.SuperBO;
import com.example.posapi.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {

    boolean saveCustomer(CustomerDTO dto, Connection connection) throws SQLException;

    boolean updateCustomer(String customerId,CustomerDTO customerDTO,Connection connection);
    List<CustomerDTO> getCustomer(Connection connection) throws SQLException;
    boolean deleteCustomer(String customerId,Connection connection);
}
