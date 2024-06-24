package view.management.room;

import business.HotelManager;
import business.PensionManager;
import business.RoomManager;
import core.ComboBoxItem;
import core.Helper;
import core.RoomType;
import entity.Hotel;
import entity.Pension;
import entity.Room;
import view.Layout;

import javax.swing.*;
import java.util.List;

public class RoomPopup extends Layout {
    private JPanel container;
    private JTextField textField_stock;
    private JTextField textField_bed_count;
    private JTextField textField_size;
    private JComboBox comboBox_room_type;
    private JComboBox comboBox_pension;
    private JCheckBox checkBox_tv;
    private JCheckBox checkBox_minibar;
    private JCheckBox checkBox_safe;
    private JCheckBox checkBox_game_console;
    private JButton button_action;
    private JCheckBox checkBox_projector;
    private JComboBox comboBox_hotel;
    private JLabel lable_stock;
    private JLabel label_bed_count;
    private JLabel label_size;
    private JLabel label_room_type;
    private JLabel label_pension;
    private JLabel label_hotel;


    private final RoomManager roomManager;
    private final HotelManager hotelManager;
    private final PensionManager pensionManager;
    private Room room;

    public RoomPopup() {
        initializeView(800, 600, "Add Room");
        add(container);

        this.roomManager = RoomManager.getInstance();
        this.hotelManager = HotelManager.getInstance();
        this.pensionManager = PensionManager.getInstance();

        button_action.setText("Add Room");

        fillComboBoxes();

        initializeAddButtonListener();
    }

    private void initializeAddButtonListener() {
        button_action.addActionListener(e -> {
            if (areFieldsEmpty())
                return;

            if (roomManager.create(getRoomFromFields(null))) {
                Helper.showMassageAll("done");
            } else {
                Helper.showMassageAll("error");
            }

            dispose();

        });
    }

    private Room getRoomFromFields(Room room) {
        ComboBoxItem comboBoxItemHotel = (ComboBoxItem) comboBox_hotel.getSelectedItem();
        int hotelId = comboBoxItemHotel.getKey();

        ComboBoxItem comboBoxItemPension = (ComboBoxItem) comboBox_pension.getSelectedItem();
        int pensionId = comboBoxItemPension.getKey();

        RoomType roomType = RoomType.valueOf(comboBox_room_type.getSelectedItem().toString());

        String str = textField_stock.getText();
        Integer integer = Integer.valueOf(str);
        int stock = integer.intValue();

        str = textField_bed_count.getText();
        integer = Integer.valueOf(str);
        int bedCount = integer.intValue();

        str = textField_size.getText();
        integer = Integer.valueOf(str);
        int size = integer.intValue();

        boolean hasTv = checkBox_tv.isSelected();
        boolean hasMinibar = checkBox_minibar.isSelected();
        boolean hasGameConsole = checkBox_game_console.isSelected();
        boolean hasSafe = checkBox_safe.isSelected();
        boolean hasProjector = checkBox_projector.isSelected();

        int id;
        if (room == null) {
            id = 0;
        } else {
            id = room.getId();
        }

        return new Room(
                id,
                hotelId,
                pensionId,
                roomType,
                stock,
                bedCount,
                size,
                hasTv,
                hasMinibar,
                hasGameConsole,
                hasSafe,
                hasProjector
        );
    }

    private boolean areFieldsEmpty() {
        JTextField[] fields = {textField_stock, textField_bed_count, textField_size};

        if (Helper.isFieldListEmpty(fields)) {
            Helper.showMassageAll("fill");
            return true;
        }

        return false;
    }

    private void fillComboBoxes() {
        fillHotelComboBox();
        fillPensionComboBox();
        fillRoomTypeComboBox();
    }

    private void fillHotelComboBox() {
        for (Hotel hotel : hotelManager.getAll()) {
            comboBox_hotel.addItem(new ComboBoxItem(hotel.getId(), hotel.getName()));
        }
    }

    private void fillPensionComboBox() {
        ComboBoxItem comboBoxItem = (ComboBoxItem) comboBox_hotel.getSelectedItem();

        List<Pension> pensions = pensionManager.getPensionsByHotelId(comboBoxItem.getKey());

        for (Pension pension : pensions) {
            comboBox_pension.addItem(new ComboBoxItem(pension.getId(), pension.getType().toString()));
        }

        listenToHotelComboBox();
    }

    private void listenToHotelComboBox() {
        comboBox_hotel.addActionListener(e -> {
            comboBox_pension.removeAllItems();

            ComboBoxItem comboBoxItem = (ComboBoxItem) comboBox_hotel.getSelectedItem();

            List<Pension> pensions = pensionManager.getPensionsByHotelId(comboBoxItem.getKey());

            for (Pension pension : pensions) {
                comboBox_pension.addItem(new ComboBoxItem(pension.getId(), pension.getType().toString()));
            }
        });
    }

    private void fillRoomTypeComboBox() {
        for (RoomType roomType : RoomType.values()) {
            comboBox_room_type.addItem(roomType);
        }
    }

    public RoomPopup(Room room) {
        initializeView(800, 600, "Update Room");
        add(container);

        this.roomManager = RoomManager.getInstance();
        this.hotelManager = HotelManager.getInstance();
        this.pensionManager = PensionManager.getInstance();
        this.room = room;

        button_action.setText("Update Room");

        fillFields();

        fillComboBoxes();
        selectCorrectItemsInComboBox();

        fillCheckBoxes();

        initializeUpdateButtonListener();
    }

    private void selectCorrectItemsInComboBox() {
        selectInHotelComboBox();
        selectInPensionComboBox();
    }

    private void selectInHotelComboBox() {
        int hotelId = room.getHotelId();

        for (int i = 0; i < comboBox_hotel.getItemCount(); i++) {
            ComboBoxItem currentItem = (ComboBoxItem) comboBox_hotel.getItemAt(i);

            if (currentItem.getKey() == hotelId) {
                comboBox_hotel.setSelectedItem(currentItem);
                break;
            }

        }
    }

    private void selectInPensionComboBox() {
        int pensionId = room.getPensionId();

        for (int i = 0; i < comboBox_pension.getItemCount(); i++) {
            ComboBoxItem currentItem = (ComboBoxItem) comboBox_pension.getItemAt(i);

            if (currentItem.getKey() == pensionId) {
                comboBox_pension.setSelectedItem(currentItem);
                break;
            }
        }
    }

    private void fillFields() {
        textField_stock.setText(String.valueOf(room.getStock()));
        textField_bed_count.setText(String.valueOf(room.getBedCount()));
        textField_size.setText(String.valueOf(room.getSize()));
    }

    private void fillCheckBoxes() {
        checkBox_tv.setSelected(room.hasTV());
        checkBox_minibar.setSelected(room.hasMinibar());
        checkBox_game_console.setSelected(room.hasGameConsole());
        checkBox_safe.setSelected(room.hasSafe());
        checkBox_projector.setSelected(room.hasProjector());
    }

    private void initializeUpdateButtonListener() {
        button_action.addActionListener(e -> {
            if (areFieldsEmpty())
                return;

            if (roomManager.update(getRoomFromFields(room))) {
                Helper.showMassageAll("done");
            } else {
                Helper.showMassageAll("error");
            }

            dispose();
        });
    }

}
