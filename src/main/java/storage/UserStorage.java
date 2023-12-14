package storage;



import enums.UserType;
import model.ShopUser;
import util.FileUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserStorage  implements Serializable {


        private Map<String,ShopUser> userMap = new HashMap<>();

        public void add(ShopUser user) {
            userMap.put(user.getEmail(),user);

        }


        public void print() {
            userMap.get(userMap);
            System.out.println(userMap);

        }

        public ShopUser getByEmail(String email) {

            return userMap.get(email);
        }

        public void printByType(UserType userType) {
            for (ShopUser shopUser : userMap.values())
                if (shopUser.getType() == userType) {
                    System.out.println(shopUser);
                }
        }
    }
