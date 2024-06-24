package view;

import business.UserManager;
import core.Helper;
import core.mouselistener.MouseListener;
import core.tableloader.UserTableLoader;
import core.UserRole;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Admin extends Layout {
    //SWING CREATE
    private JPanel container;
    private JTable table_users;
    private JScrollPane scroll_users;
    private JButton button_admins;
    private JButton button_logout;
    private JButton button_personnel;
    private JLabel label_greeting;
    private JButton button_reset;
    private JButton button_add;
    //SWING LATER
    private DefaultTableModel table_model;
    private Object[] rows;
    private JPopupMenu jPopupMenu;

    private UserTableLoader userTableLoader;
    private MouseListener mouseListener;


    //OTHERS
    private final UserManager userManager;
    private final User user;

    public Admin(User user) {
        initializeView(1500, 800, "Admin");
        add(container);

        createUserList();
        createPopupMenu();

        this.userManager = UserManager.getInstance();
        this.user = user;

        this.userTableLoader = new UserTableLoader(table_users, rows, table_model);

        this.mouseListener = new MouseListener(table_users, jPopupMenu);
        mouseListener.listenToTable();

        label_greeting.setText("Welcome " + user.getName());

        initializeListAdminsButton();
        initializeListPersonnelButton();
        initializeAdduser();

        initializeLogoutButton();

        resetList();

        userTableLoader.loadUsers();
    }

    private void resetList() {
        button_reset.addActionListener(e -> {
            userTableLoader.loadUsers();
        });
    }

    private void createUserList() {
        table_model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 3 || column == 4)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID", "Name", "Username", "Password", "User Role"};
        table_model.setColumnIdentifiers(col_user_list);
        rows = new Object[col_user_list.length];

        table_users.setModel(table_model);
        table_users.getTableHeader().setReorderingAllowed(false);
    }

    private void initializeListAdminsButton() {
        button_admins.addActionListener(e -> {
            userTableLoader.loadUsers(userManager.getUsersByRole(UserRole.ADMIN));
        });
    }

    private void initializeListPersonnelButton() {
        button_personnel.addActionListener(e -> {
            userTableLoader.loadUsers(userManager.getUsersByRole(UserRole.PERSONNEL));
        });
    }

    private void initializeLogoutButton() {
        button_logout.addActionListener(e -> {
            dispose();
            new Login();
        });
    }

    private void createPopupMenu() {
        jPopupMenu = new JPopupMenu();

        JMenuItem updateUser = new JMenuItem("Update User");
        JMenuItem deleteUser = new JMenuItem("Delete User");

        jPopupMenu.add(updateUser);
        jPopupMenu.add(deleteUser);

        initializeUpdateUserListener(updateUser);
        initializeDeleteUserListener(deleteUser);
    }

    private void initializeAdduser() {
        button_add.addActionListener(e -> {
            new AdminPopup();
        });
    }

    private void initializeUpdateUserListener(JMenuItem updateUser) {
        updateUser.addActionListener(e -> {
            int selectedRow = table_users.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }
            int id = (int) table_users.getValueAt(selectedRow, 0);
            String name = (String) table_users.getValueAt(selectedRow, 1);
            String username = (String) table_users.getValueAt(selectedRow, 2);
            String password = (String) table_users.getValueAt(selectedRow, 3);
            UserRole userRole = (UserRole) table_users.getValueAt(selectedRow, 4);

            User selectedUser = new User(id, name, username, password, userRole);
            new AdminPopup(selectedUser);
        });
    }

    private void initializeDeleteUserListener(JMenuItem deleteUser) {
        deleteUser.addActionListener(e -> {
            if (!Helper.showMassageSure("sure")) {
                return;
            }

            int selectedId = Integer.parseInt(table_users.getValueAt(table_users.getSelectedRow(), 0).toString());

            if (userManager.delete(selectedId)) {
                Helper.showMassageAll("done");
                userTableLoader.loadUsers();
            }
        });
    }
}
