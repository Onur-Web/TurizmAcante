package view.management.reservation;

import business.ReservationManager;
import business.RoomManager;
import core.ComboBoxItem;
import core.Helper;
import entity.Reservation;
import entity.Room;
import view.Layout;

import javax.swing.*;
import java.sql.Date;

public class ReservationPopup extends Layout {
    private JPanel container;
    private JTextField textField_customer_name;
    private JTextField textField_customer_tc;
    private JTextField textField_customer_email;
    private JTextField textField_start;
    private JTextField textField_finish;
    private JComboBox comboBox_room;
    private JButton button_action;
    private JLabel label_customer_name;
    private JLabel label_room;
    private JLabel label_start;
    private JLabel label_finish;
    private JLabel label_customer_email;
    private JLabel label_customer_tc;
    private JTextField textField_number_of_adults;
    private JTextField textField_number_of_children;
    private JLabel label_number_of_adults;
    private JLabel label_number_of_children;

    private Reservation reservation;
    private final ReservationManager reservationManager;
    private final RoomManager roomManager;

    private Room room;

    public ReservationPopup(Room room, String start, String finish) {
        initializeView(320, 400, "Add Reservation");
        add(container);

        button_action.setText("Add");
        textField_start.setText(start);
        textField_finish.setText(finish);

        this.room = room;
        this.reservationManager = ReservationManager.getInstance();
        this.roomManager = RoomManager.getInstance();

        comboBox_room.addItem(new ComboBoxItem(room.getId(), room.getType().toString()));

        addButton();

    }

    private void addButton() {
        button_action.addActionListener(e -> {
            if (areFieldsEmpty())
                return;

            if (areComboBoxesEmpty())
                return;

            if (reservationManager.create(getReservationFromFields(null)))
                Helper.showMassageAll("done");
            else
                Helper.showMassageAll("error");

            dispose();
        });
    }


    public ReservationPopup(Reservation reservation) {
        initializeView(320, 400, "Update Reservation");
        add(container);

        button_action.setText("Update");

        this.reservation = reservation;
        this.reservationManager = ReservationManager.getInstance();
        this.roomManager = RoomManager.getInstance();

        fillTextFields();
        fillComboBoxes();
        selectInRoomComboBox();

        updateButton();
    }

    private void updateButton() {
        button_action.addActionListener(e -> {
            if (areFieldsEmpty())
                return;

            if (areComboBoxesEmpty())
                return;

            if (reservationManager.update(getReservationFromFields(reservation)))
                Helper.showMassageAll("done");
            else
                Helper.showMassageAll("error");

            dispose();
        });
    }

    private Reservation getReservationFromFields(Reservation reservation) {

        ComboBoxItem comboBoxItemRoom = (ComboBoxItem) comboBox_room.getSelectedItem();

        int id = reservation == null ? 0 : reservation.getId();

        int roomId = comboBoxItemRoom.getKey();
        String customerName = textField_customer_name.getText();
        String customerTC = textField_customer_tc.getText();
        String customerEmail = textField_customer_email.getText();
        int totalPrice = 0;
        Date start = Date.valueOf(textField_start.getText());
        Date finish = Date.valueOf(textField_finish.getText());
        int numberOfAdults = Integer.parseInt(textField_number_of_adults.getText());
        int numberOfChildren = Integer.parseInt(textField_number_of_children.getText());

        return new Reservation(
                id,
                roomId,
                customerName,
                customerTC,
                customerEmail,
                totalPrice,
                start,
                finish,
                numberOfAdults,
                numberOfChildren
        );
    }

    private boolean areComboBoxesEmpty() {
        JComboBox[] comboBoxes = {comboBox_room};

        if (Helper.isComboBoxListEmpty(comboBoxes)) {
            Helper.showMassageAll("fill");
            return true;
        }

        return false;
    }

    private boolean areFieldsEmpty() {
        JTextField[] fields = {textField_customer_name, textField_customer_tc, textField_customer_email, textField_start, textField_finish, textField_number_of_adults, textField_number_of_children};

        if (Helper.isFieldListEmpty(fields)) {
            Helper.showMassageAll("fill");
            return true;
        }

        return false;
    }

    private void selectInRoomComboBox() {
        int roomId = reservation.getRoomId();

        for (int i = 0; i < comboBox_room.getItemCount(); i++) {
            ComboBoxItem currentItem = (ComboBoxItem) comboBox_room.getItemAt(i);

            if (currentItem.getKey() == roomId) {
                comboBox_room.setSelectedItem(currentItem);
                break;
            }
        }
    }

    private void fillTextFields() {
        textField_customer_name.setText(reservation.getCustomerName());
        textField_customer_tc.setText(reservation.getCustomerTC());
        textField_customer_email.setText(reservation.getCustomerEmail());
        textField_start.setText(reservation.getStart().toString());
        textField_finish.setText(reservation.getFinish().toString());
        textField_number_of_adults.setText(String.valueOf(reservation.getNumberOfAdults()));
        textField_number_of_children.setText(String.valueOf(reservation.getNumberOfChildren()));
    }

    private void fillComboBoxes() {
        fillRoom();
    }

    private void fillRoom() {
        for (Room room : roomManager.getAll()) {
            comboBox_room.addItem(new ComboBoxItem(room.getId(), room.getType().toString()));
        }
    }

}
