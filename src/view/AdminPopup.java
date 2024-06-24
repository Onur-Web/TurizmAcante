package view;

import business.UserManager;
import core.Helper;
import core.UserRole;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPopup extends Layout{
    private JPanel container;
    private JTextField textField_name;
    private JTextField textField_username;
    private JTextField textField_password;
    private JComboBox comboBox_role;
    private JButton button_save;

    private final UserManager userManager;
    public AdminPopup() {
        initializeView(300, 500, "Add User");
        this.userManager = UserManager.getInstance();

        add(container);
        populateRoleComboBox();
        button_save.addActionListener(e -> {
            JTextField[] fields = {textField_name, textField_username, textField_password};

            if (Helper.isFieldListEmpty(fields)) {
                Helper.showMassageAll("fill");
                return;
            }

            boolean result = userManager.create(
                    new User(
                            0,
                            textField_name.getText(),
                            textField_username.getText(),
                            textField_password.getText(),
                            UserRole.valueOf(comboBox_role.getSelectedItem().toString())));

            if (result) {
                Helper.showMassageAll("done");
            } else {
                Helper.showMassageAll("error");
            }

            dispose();
        });
    }

    public AdminPopup(User user) {
        initializeView(300, 500, "Update User");
        this.userManager = UserManager.getInstance();

        add(container);

        populateRoleComboBox();

        textField_name.setText(user.getName());
        textField_password.setText(user.getPassword());
        textField_username.setText(user.getUsername());
        comboBox_role.setSelectedItem(user.getUserRole());

        button_save.addActionListener(e -> {
            JTextField[] fields = {textField_name, textField_username, textField_password};

            if (Helper.isFieldListEmpty(fields)) {
                Helper.showMassageAll("fill");
                return;
            }

            boolean result = userManager.update(
                    new User(
                            user.getId(),
                            textField_name.getText(),
                            textField_username.getText(),
                            textField_password.getText(),
                            UserRole.valueOf(comboBox_role.getSelectedItem().toString())));

            if (result) {
                Helper.showMassageAll("done");
            } else {
                Helper.showMassageAll("error");
            }

            dispose();

        });
    }

    private void populateRoleComboBox() {
        comboBox_role.addItem(UserRole.ADMIN);
        comboBox_role.addItem(UserRole.PERSONNEL);
    }
}
