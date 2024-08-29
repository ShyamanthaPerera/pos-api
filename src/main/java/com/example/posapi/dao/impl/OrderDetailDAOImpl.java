package com.example.posapi.dao.impl;

import com.example.posapi.dao.custom.OrderDetailDAO;
import com.example.posapi.entity.OrderDetailEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    static final String SAVE_ORDER_DETAIL = "INSERT INTO order_details (orderId, itemCode, orderQty, unitPrice) VALUES (?, ?, ?, ?)";
    @Override
    public boolean save(OrderDetailEntity orderDetailEntity, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_ORDER_DETAIL)) {
            preparedStatement.setString(1, orderDetailEntity.getOrderId());
            preparedStatement.setString(2, orderDetailEntity.getItemCode());
            preparedStatement.setInt(3, orderDetailEntity.getOrderQty());
            preparedStatement.setDouble(4, orderDetailEntity.getUnitPrice());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(String id, OrderDetailEntity orderDetailEntity, Connection connection) {
        return false;
    }

    @Override
    public List<OrderDetailEntity> get(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id, Connection connection) {
        return false;
    }
}
