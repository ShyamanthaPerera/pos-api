package com.example.posapi.dao.custom;

import com.example.posapi.dao.CrudDAO;
import com.example.posapi.entity.ItemEntity;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<ItemEntity> {

    boolean updateQuantity(String itemCode, int quantity, Connection connection) throws SQLException;
    ItemEntity getItemByCode(String itemCode, Connection connection) throws SQLException;
}
