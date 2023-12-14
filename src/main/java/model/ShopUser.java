package model;

import enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopUser implements Serializable {
    private String id;
    private String name;
    private String email;
    private String password;
    private UserType type;



    }

