package User;

import java.util.ArrayList;

/**
 * user system
 */
public class UserSystem {
    private static UserSystem instance;
    private UserSystem() {}

    /**
     * get user system singleton instance
     * @return instance
     */
    public static UserSystem getInstance() {
        if (instance == null) {
            instance = new UserSystem();
        }
        return instance;
    }

    ArrayList<UserModel> users = new ArrayList<>();

    /**
     * try to register a user
     * @param name user name
     * @param password user password
     * @return true if register successfully
     */
    public boolean registerUser(String name, String password) {
        if(getUserByName(name) != null) {
            return false;
        }
        users.add(new UserModel(name, password));
        return true;
    }

    /**
     * validate user name and password
     * @param name user name
     * @param password user password
     * @return true if validate successfully
     */
    public boolean validateUser(String name, String password) {
        for (UserModel user : users) {
            if(name.equals(user.getName())) {
                return user.validatePassword(password);
            }
        }
        return false;
    }

    /**
     * get user model by name
     * @param name user name
     * @return user model of the name, if name not exist return null
     */
    public UserModel getUserByName(String name) {
        for (UserModel user : users) {
            if(name.equals(user.getName())) {
                return user;
            }
        }
        return null;
    }

    /**
     * get user model if username and password validate successfully
     * @param username user name
     * @param password user password
     * @return user model if validate successfully else null
     */
    public UserModel getValidateUser(String username, String password) {
            if(validateUser(username, password)){
                return getUserByName(username);
            } else {
                return null;
            }
    }
}
