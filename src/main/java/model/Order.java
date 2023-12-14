package model;

import enums.OrderStatus;
import enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor



public class Order implements Serializable {
    private String id;
    Date date;
    private double price;
    private OrderStatus orderstatus;
    private double qty;
    private PaymentMethod paymentmethod;
private ShopUser user;
private Product product;


    public Order(String id, ShopUser currentUser, Product product, Date date, double price, OrderStatus aNew, int qty, PaymentMethod paymentMethod) {
    }
}

