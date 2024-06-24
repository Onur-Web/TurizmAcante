package view;

import core.Helper;

import javax.swing.*;
//FRAME AYARLARI VIEW PAKETININ TEMEL METHODU
public class Layout extends JFrame {
    public void initializeView(int width, int height, String title) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Tourism Agency " + title);
        this.setSize(width, height);
        this.setLocation(Helper.getScreenCenter("x", getSize()), Helper.getScreenCenter("y", getSize()));
        this.setVisible(true);
    }
}
