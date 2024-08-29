package com.example.posapi.controller;

import com.example.posapi.bo.BOFactory;
import com.example.posapi.bo.custom.ItemBO;
import com.example.posapi.dto.ItemDTO;
import com.example.posapi.util.IDGenerater;
import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/items")
public class ItemController extends HttpServlet {

    ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.ITEM);
    static Logger logger = LoggerFactory.getLogger(ItemController.class);
    Connection connection;

    @Override
    public void init() throws ServletException {
        logger.info("Initializing Item Controller with call inti method");
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/posSystem");
            this.connection = pool.getConnection();
        } catch (NamingException | SQLException e) {
            logger.error("Init failed with", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO:Save Item
        if(!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(),ItemDTO.class);
            itemDTO.setItemCode(IDGenerater.generateItemId());
            if(itemBO.saveItem(itemDTO,connection)){
                writer.write("Item Save Successful");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                writer.write("Item Save Unsuccessful");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (JsonException | SQLException e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO:Get Item
        try(var writer = resp.getWriter()) {
            List<ItemDTO> item = itemBO.getItem(connection);
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(item,writer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO:Update Item
        if(!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        try(var writer = resp.getWriter()){
            var itemCode = req.getParameter("itemCode");
            Jsonb jsonb = JsonbBuilder.create();
            var updateItem = jsonb.fromJson(req.getReader(), ItemDTO.class);
            if(itemBO.updateItem(itemCode,updateItem,connection)){
                writer.write("Item update successful");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                writer.write("Item Update Failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (JsonException e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO:Delete Item
        var itemCode = req.getParameter("itemCode");
        try(var writer = resp.getWriter()){
            if(itemBO.deleteItem(itemCode,connection)){
                writer.write("Delete Successful");
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            }else{
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                writer.write("Delete failed");
            }
        }catch (Exception e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
