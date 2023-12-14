package model;

import enums.ProductType;
import lombok.*;


import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product implements Serializable {

    private String id;
    private String name;
    private String description;
    private double price;
    private int stockQty;
    private ProductType product;
    private boolean isRemoved = false;


}
