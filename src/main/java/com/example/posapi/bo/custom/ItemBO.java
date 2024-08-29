package com.example.posapi.bo.custom;

import com.example.posapi.bo.SuperBO;
import com.example.posapi.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {

    boolean saveItem(ItemDTO dto, Connection connection) throws SQLException;

    boolean updateItem(String itemCode,ItemDTO itemDTO,Connection connection);
    List<ItemDTO> getItem(Connection connection) throws SQLException;
    boolean deleteItem(String itemCode,Connection connection);
    boolean updateItemQuantity(String itemCode, int quantity, Connection connection) throws SQLException;
}
