package com.example.posapi.bo;

import com.example.posapi.bo.impl.CustomerBOImpl;
import com.example.posapi.bo.impl.ItemBOImpl;
import com.example.posapi.bo.impl.OrderBOImpl;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBOFactory(){
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,ORDER
    }

    public SuperBO getBo(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new OrderBOImpl();
            default:
                return null;
        }
    }
}
