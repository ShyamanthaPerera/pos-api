package com.example.posapi.dao.impl;

import com.example.posapi.dao.custom.OrderDAO;
import com.example.posapi.entity.OrderEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    static final String SAVE_ORDER = "INSERT INTO orders (orderId, orderDate, customerId, totalAmount) VALUES (?, ?, ?, ?)";
    @Override
    public boolean save(OrderEntity orderEntity, Connection connection) throws SQLException {
        try {
            var preparedStatement = connection.prepareStatement(SAVE_ORDER);
            preparedStatement.setString(1,orderEntity.getOrderId());
            preparedStatement.setDate(2, java.sql.Date.valueOf(String.valueOf(orderEntity.getOrderDate())));
            preparedStatement.setString(3,orderEntity.getCustomerId());
            preparedStatement.setDouble(4,orderEntity.getTotalAmount());
            return preparedStatement.executeUpdate() != 0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(String id, OrderEntity orderEntity, Connection connection) {
        return false;
    }

    @Override
    public List<OrderEntity> get(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id, Connection connection) {
        return false;
    }
}
