package com.example.posapi.bo.impl;

import com.example.posapi.bo.custom.OrderBO;
import com.example.posapi.dao.DAOFactory;
import com.example.posapi.dao.custom.ItemDAO;
import com.example.posapi.dao.custom.OrderDAO;
import com.example.posapi.dao.custom.OrderDetailDAO;
import com.example.posapi.dto.OrderDTO;
import com.example.posapi.dto.OrderDetailDTO;
import com.example.posapi.entity.OrderDetailEntity;
import com.example.posapi.entity.OrderEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ORDER_DETAIL);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ITEM);
    @Override
    public boolean placeOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetailDTOs, Connection connection) throws SQLException {
        try {
            connection.setAutoCommit(false);

            // Save the order
            boolean orderSaved = orderDAO.save(
                    new OrderEntity(orderDTO.getOrderId(),orderDTO.getOrderDate(),orderDTO.getCustomerId(), orderDTO.getTotalAmount()),
                    connection);
            if (!orderSaved) {
                connection.rollback();
                return false;
            }

            // Save the order details
            for (OrderDetailDTO orderDetail : orderDetailDTOs) {
                boolean orderDetailSaved = orderDetailDAO.save(
                        new OrderDetailEntity(orderDetail.getOrderId(),orderDetail.getItemCode(),orderDetail.getOrderQty(),orderDetail.getUnitPrice()),
                        connection);
                if (!orderDetailSaved) {
                    connection.rollback();
                    return false;
                }

                //Update Item Quantity
                boolean itemQuantityUpdated = itemDAO.updateQuantity(orderDetail.getItemCode(),
                        orderDetail.getOrderQty(),connection);
                if(!itemQuantityUpdated){
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
