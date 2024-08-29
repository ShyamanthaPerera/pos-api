package com.example.posapi.bo.impl;

import com.example.posapi.bo.custom.ItemBO;
import com.example.posapi.dao.DAOFactory;
import com.example.posapi.dao.custom.ItemDAO;
import com.example.posapi.dto.ItemDTO;
import com.example.posapi.entity.ItemEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean saveItem(ItemDTO dto, Connection connection) throws SQLException {
        return itemDAO.save(new ItemEntity(dto.getItemCode(), dto.getItemName(), dto.getQtyOnHand(), dto.getUnitPrice()), connection);
    }

    @Override
    public boolean updateItem(String itemCode, ItemDTO itemDTO, Connection connection) {
        ItemEntity itemEntity = new ItemEntity(itemCode, itemDTO.getItemName(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice());
        return itemDAO.update(itemCode, itemEntity, connection);
    }

    @Override
    public List<ItemDTO> getItem(Connection connection) throws SQLException {
        List<ItemEntity> items = itemDAO.get(connection);
        List<ItemDTO> itemDTOS = new ArrayList<>();

        for (ItemEntity itemEntity : items) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setItemCode(itemEntity.getItemCode());
            itemDTO.setItemName(itemEntity.getItemName());
            itemDTO.setQtyOnHand(itemEntity.getQtyOnHand());
            itemDTO.setUnitPrice(itemEntity.getUnitPrice());
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }

    @Override
    public boolean deleteItem(String itemCode, Connection connection) {
        return itemDAO.delete(itemCode, connection);
    }

    @Override
    public boolean updateItemQuantity(String itemCode, int quantity, Connection connection) throws SQLException {
        ItemEntity itemEntity = itemDAO.getItemByCode(itemCode,connection);

        if(itemEntity ==null) {
            throw new SQLException("Item not found with code: " + itemCode);
        }

        int newQuantity = itemEntity.getQtyOnHand() - quantity ;

        if(newQuantity< 0) {
            throw new SQLException("Insufficient stock for item code: " + itemCode);
        }

        return itemDAO.updateQuantity(itemCode,newQuantity,connection);
    }
}
