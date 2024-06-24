package view;

import business.UserManager;
import core.Helper;
import entity.User;
import view.management.Personnel;

import javax.swing.*;

public class Login extends Layout {
    // swing
    private JTextField textField_username;
    private JTextField textField_password;
    private JPanel container;
    private JButton button_login;

    // others
    private final UserManager userManager;

    public Login() {
        add(container);
        initializeView(320, 400, "Login");

        this.userManager = UserManager.getInstance();

        //GIRILEN BILGILERIN NE OLDUKLARININI KULLANICIYA BILDIRMEK
        button_login.addActionListener(e -> {
            JTextField[] list = {textField_username, textField_password};

            if (Helper.isFieldListEmpty(list)) {
                Helper.showMassageAll("fill");
                return;
            }

            User user = userManager.getUserByUsernameAndPassword(textField_username.getText(), textField_password.getText());

            if (user == null) {
                Helper.showMassageAll("notFound");
                return;
            }

            Helper.showMassageAll("logSuccessful");

            login(user);
        });
    }

    public void login(User user) {
        switch (user.getUserRole().toString()) {
            case "ADMIN" -> new Admin(user);
            case "PERSONNEL" -> new Personnel(user);
        }
        dispose();
    }

}
