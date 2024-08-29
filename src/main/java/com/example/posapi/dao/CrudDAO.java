package com.example.posapi.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {

    boolean save(T entity, Connection connection) throws SQLException;
    boolean update(String id,T entity,Connection connection);
    List<T> get(Connection connection) throws SQLException;
    boolean delete(String id,Connection connection);
}
