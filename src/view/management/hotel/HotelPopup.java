package view.management.hotel;

import business.HotelManager;
import core.Helper;

import entity.Hotel;
import view.Layout;

import javax.swing.*;

public class HotelPopup extends Layout {
    private JLabel label_action;
    private JTextField textField_name;
    private JTextField textField_city;
    private JTextField textField_region;
    private JTextField textField_address;
    private JTextField textField_email;
    private JTextField textField_telephone;
    private JTextField textField_star;
    private JButton button_action;
    private JPanel container;

    private final HotelManager hotelManager;
    private Hotel hotel;

    public HotelPopup() {
        initializeView(640,800, "Add Hotel");
        add(container);

        this.hotelManager = HotelManager.getInstance();

        label_action.setText("Add Hotel");
        button_action.setText("Add");

        addHotel();

    }

    private boolean areFieldsEmpty() {
        JTextField[] fields = {
                textField_name,
                textField_city,
                textField_region,
                textField_address,
                textField_email,
                textField_telephone,
                textField_star
        };

        if (Helper.isFieldListEmpty(fields)) {
            Helper.showMassageAll("fill");
            return true;
        }

        return false;
    }

    private Hotel createHotelFromTextFields() {

        int id = hotel != null ? hotel.getId() : 0;
        String name = textField_name.getText();
        String city = textField_city.getText();
        String region = textField_region.getText();
        String address = textField_address.getText();
        String email = textField_email.getText();
        String telephone = textField_telephone.getText();
        String star = textField_star.getText();

        return new Hotel(id, name, city, region, address, email, telephone, star);
    }

    private void addHotel() {
        button_action.addActionListener(e -> {
            if (areFieldsEmpty())
                return;

            if (hotelManager.create(createHotelFromTextFields())) {
                Helper.showMassageAll("done");
            } else {
                Helper.showMassageAll("error");
            }

            dispose();
        });
    }


    public HotelPopup(Hotel hotel) {
        initializeView(640, 800, "Update Hotel");
        add(container);

        this.hotelManager = HotelManager.getInstance();
        this.hotel = hotel;

        label_action.setText("Update Hotel");
        button_action.setText("Update");
        setTextFromHotel();

        updateHotel();
    }

    private void setTextFromHotel() {
        textField_name.setText(hotel.getName());
        textField_city.setText(hotel.getCity());
        textField_region.setText(hotel.getRegion());
        textField_address.setText(hotel.getAddress());
        textField_email.setText(hotel.getEmail());
        textField_telephone.setText(hotel.getTelephone());
        textField_star.setText(hotel.getStar());
    }

    private void updateHotel() {
        button_action.addActionListener(e -> {
            if (areFieldsEmpty())
                return;

            if (hotelManager.update(createHotelFromTextFields())) {
                Helper.showMassageAll("done");
            } else {
                Helper.showMassageAll("error");
            }

            dispose();
        });
    }
}
