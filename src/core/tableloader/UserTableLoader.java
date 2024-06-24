package core.tableloader;

import business.HotelManager;
import business.UserManager;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UserTableLoader {

    private JTable table_users;
    private Object[] rows;
    private DefaultTableModel table_model;
    private UserManager userManager;

    public UserTableLoader(JTable table_users, Object[] rows, DefaultTableModel table_model) {
        this.table_users = table_users;
        this.rows = rows;
        this.table_model = table_model;
        this.userManager = UserManager.getInstance();
    }

    public void loadUsers() {
        DefaultTableModel model = (DefaultTableModel) table_users.getModel();
        model.setRowCount(0);
        int i;
        for (User user : userManager.getAll()) {
            i = 0;
            rows[i++] = user.getId();
            rows[i++] = user.getName();
            rows[i++] = user.getUsername();
            rows[i++] = user.getPassword();
            rows[i++] = user.getUserRole();
            table_model.addRow(rows);
        }
    }

    public void loadUsers(List<User> users) {
        DefaultTableModel model = (DefaultTableModel) table_users.getModel();
        model.setRowCount(0);
        int i;
        for (User user : users) {
            i = 0;
            rows[i++] = user.getId();
            rows[i++] = user.getName();
            rows[i++] = user.getUsername();
            rows[i++] = user.getPassword();
            rows[i++] = user.getUserRole();
            table_model.addRow(rows);
        }
    }
}
