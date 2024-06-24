package view.management;

import entity.User;
import view.Layout;
import view.Login;
import view.management.hotel.HotelView;
import view.management.price.PriceView;
import view.management.reservation.ReservationView;
import view.management.room.RoomView;

import javax.swing.*;

public class Personnel extends Layout {
    private JPanel container;
    private JButton button_logout;
    private JLabel label_greeting;
    private JButton button_hotel;
    private JButton button_room;
    private JButton button_price;
    private JButton button_reservation;

    private final User user;

    public Personnel(User user) {
        initializeView(500, 500, "Personnel");
        add(container);

        this.user = user;

        label_greeting.setText("Welcome " + user.getName());

        logOut();
        goToHotel();
        goToRoom();
        goToPrice();
        goToReservation();
    }

    private void logOut() {
        button_logout.addActionListener(e -> {
            dispose();
            new Login();
        });
    }

    private void goToHotel() {
        button_hotel.addActionListener(e -> {
            new HotelView(user);
            dispose();
        });
    }

    private void goToRoom() {
        button_room.addActionListener(e -> {
            new RoomView(user);
            dispose();
        });
    }

    private void goToPrice() {
        button_price.addActionListener(e -> {
            new PriceView(user);
            dispose();
        });
    }

    private void goToReservation() {
        button_reservation.addActionListener(e -> {
            new ReservationView(user);
            dispose();
        });
    }
}
