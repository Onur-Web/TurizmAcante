package business;

import core.UserRole;
import dao.UserDAO;
import entity.User;

import java.util.List;

public class UserManager {
    private final UserDAO userDAO;
    private static UserManager instance;

    private UserManager() {
        this.userDAO = UserDAO.getInstance();
    }

    //instance boş ise yeni bir UserManager örneği oluşturulur ve instance değişkenine atanır.
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }

        return instance;
    }

    public List<User> getAll() {
        return userDAO.findAll();
    }

    public List<User> getUsersByRole(UserRole userRole) {
        return userDAO.findUsersByUserRole(userRole);
    }
    //Girilen username ile
    public User getUserByUsernameAndPassword(String username, String password) {
        return userDAO.findUserByUsernameAndPassword(username, password);
    }

    public boolean create(User user) {
        return userDAO.create(user);
    }

    public boolean update(User user) {
        return userDAO.update(user);
    }

    public boolean delete(int id) {
        return userDAO.delete(id);
    }
}
