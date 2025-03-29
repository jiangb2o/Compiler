package User;

import java.util.ArrayList;

public class UserSystem {
    ArrayList<UserModel> users = new ArrayList<>();

    public boolean registerUser(String name, String password) {
        if(getUserByName(name) != null) {
            return false;
        }
        users.add(new UserModel(name, password));
        return true;
    }

    public boolean validateUser(String name, String password) {
        for (UserModel user : users) {
            if(name.equals(user.getName())) {
                return user.validatePassword(password);
            }
        }
        return false;
    }

    public UserModel getUserByName(String name) {
        for (UserModel user : users) {
            if(name.equals(user.getName())) {
                return user;
            }
        }
        return null;
    }
}
