package com.example.posapi.dao.impl;

import com.example.posapi.dao.custom.ItemDAO;
import com.example.posapi.entity.ItemEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    static String SAVE_ITEM = "INSERT INTO item (itemCode,itemName,qtyOnHand,unitPrice) VALUES (?,?,?,?)";
    static String UPDATE_ITEM = "UPDATE item SET itemName=?,qtyOnHand=?,unitPrice=? WHERE itemCode=?";
    static String GET_ITEM = "SELECT * FROM item";
    static String DELETE_ITEM = "DELETE FROM item WHERE itemCode=?";
    static String UPDATE_ITEM_QUANTITY = "UPDATE item SET qtyOnHand = qtyOnHand - ? WHERE itemCode = ?";
    static String GET_ITEM_ID = "SELECT * FROM item WHERE itemCode = ?";
    @Override
    public boolean save(ItemEntity itemEntity, Connection connection) throws SQLException {
        try {
            var preparedStatement = connection.prepareStatement(SAVE_ITEM);
            preparedStatement.setString(1,itemEntity.getItemCode());
            preparedStatement.setString(2,itemEntity.getItemName());
            preparedStatement.setInt(3,itemEntity.getQtyOnHand());
            preparedStatement.setDouble(4,itemEntity.getUnitPrice());
            return preparedStatement.executeUpdate() != 0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(String id, ItemEntity itemEntity, Connection connection) {
        try {
            var preparedStatement = connection.prepareStatement(UPDATE_ITEM);
            preparedStatement.setString(1,itemEntity.getItemName());
            preparedStatement.setInt(2, itemEntity.getQtyOnHand());
            preparedStatement.setDouble(3, itemEntity.getUnitPrice());
            preparedStatement.setString(4,id);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemEntity> get(Connection connection) throws SQLException {
        List<ItemEntity> items = new ArrayList<>();

        try {
            var preparedStatement = connection.prepareStatement(GET_ITEM);
            var resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                var itemEntity = new ItemEntity();
                itemEntity.setItemCode(resultSet.getString("itemCode"));
                itemEntity.setItemName(resultSet.getString("itemName"));
                itemEntity.setQtyOnHand(resultSet.getInt("qtyOnHand"));
                itemEntity.setUnitPrice(resultSet.getDouble("unitPrice"));
                items.add(itemEntity);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public boolean delete(String id, Connection connection) {
        try {
            var preparedStatement = connection.prepareStatement(DELETE_ITEM);
            preparedStatement.setString(1,id);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateQuantity(String itemCode, int quantity, Connection connection) throws SQLException {
        try {
            var preparedStatement = connection.prepareStatement(UPDATE_ITEM_QUANTITY);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2, itemCode);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ItemEntity getItemByCode(String itemCode, Connection connection) throws SQLException {
        var itemEntity = new ItemEntity();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ITEM_ID)) {
            preparedStatement.setString(1, itemCode);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    itemEntity.setItemCode(resultSet.getString("itemCode"));
                    itemEntity.setItemName(resultSet.getString("itemName"));
                    itemEntity.setQtyOnHand(resultSet.getInt("qtyOnHand"));
                    itemEntity.setUnitPrice(resultSet.getDouble("unitPrice"));
                }
            }
        }
        return itemEntity;
    }
}
