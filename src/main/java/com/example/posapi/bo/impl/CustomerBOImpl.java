package com.example.posapi.bo.impl;

import com.example.posapi.bo.custom.CustomerBO;
import com.example.posapi.dao.DAOFactory;
import com.example.posapi.dao.custom.CustomerDAO;
import com.example.posapi.dto.CustomerDTO;
import com.example.posapi.entity.CustomerEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDTO dto, Connection connection) throws SQLException {
        return customerDAO.save(new CustomerEntity(dto.getCustomerId(), dto.getCustomerName(), dto.getCustomerAddress(), dto.getCustomerTel()),connection);
    }

    @Override
    public boolean updateCustomer(String customerId, CustomerDTO customerDTO, Connection connection) {
        CustomerEntity customerEntity = new CustomerEntity(customerId, customerDTO.getCustomerName(), customerDTO.getCustomerAddress(), customerDTO.getCustomerTel());
        return customerDAO.update(customerId, customerEntity, connection);
    }

    @Override
    public List<CustomerDTO> getCustomer(Connection connection) throws SQLException {
        List<CustomerEntity> customers = customerDAO.get(connection);
        List<CustomerDTO> customerDTOS = new ArrayList<>();

        for(CustomerEntity customerEntity : customers){
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerId(customerEntity.getCustomerId());
            customerDTO.setCustomerName(customerEntity.getCustomerName());
            customerDTO.setCustomerAddress(customerEntity.getCustomerAddress());
            customerDTO.setCustomerTel(customerEntity.getCustomerTel());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) {
        return customerDAO.delete(customerId,connection);
    }
}
