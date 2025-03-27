package User;

import java.util.ArrayList;

public class UserSystem {
    ArrayList<UserModel> users;

    public boolean RegisterUser(String name, String password) {
        for (UserModel user : users) {
            if(name.equals(user.getName())) {
                return false;
            }
        }
        users.add(new UserModel(name, password));
        return true;
    }

    public boolean LoginUser(String name, String password) {
        for (UserModel user : users) {
            if(name.equals(user.getName())) {
                return user.validatePassword(password);
            }
        }
        return false;
    }
}
