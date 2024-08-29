package com.example.posapi.controller;

import com.example.posapi.bo.BOFactory;
import com.example.posapi.bo.custom.CustomerBO;
import com.example.posapi.dto.CustomerDTO;
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

@WebServlet(urlPatterns = "/customer")
public class CustomerController extends HttpServlet {

    CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.CUSTOMER);
    static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    Connection connection;
    @Override
    public void init() throws ServletException {
        logger.info("Initializing Customer Controller with call inti method");
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
        //Save Customer
        if(!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(),CustomerDTO.class);
            if(customerBO.saveCustomer(customerDTO,connection)){
                writer.write("Customer Save Successful");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                writer.write("Customer Save Unsuccessful");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (JsonException | SQLException e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Update Customer
        if(!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        try(var writer = resp.getWriter()){
            var customerId = req.getParameter("customerId");
            Jsonb jsonb = JsonbBuilder.create();
            var updateCustomer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            if(customerBO.updateCustomer(customerId,updateCustomer,connection)){
                writer.write("Customer update successful");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                writer.write("Customer Update Failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (JsonException e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Delete Customer
        var customerId = req.getParameter("customerId");
        try(var writer = resp.getWriter()){
            if(customerBO.deleteCustomer(customerId,connection)){
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get Customer
        try(var writer = resp.getWriter()) {
            List<CustomerDTO> customer = customerBO.getCustomer(connection);
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(customer,writer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
