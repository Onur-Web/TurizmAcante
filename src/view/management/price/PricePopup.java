package view.management.price;

import business.*;
import core.ComboBoxItem;
import core.Helper;
import entity.*;
import view.Layout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PricePopup extends Layout {
    private JPanel container;
    private JTextField textField_adult_price;
    private JTextField textField_child_price;
    private JComboBox comboBox_hotel;
    private JComboBox comboBox_pension;
    private JComboBox comboBox_season;
    private JButton button_action;
    private JComboBox comboBox_room;
    private JLabel label_adult_price;
    private JLabel label_child_price;
    private JLabel label_hotel;
    private JLabel label_pension;
    private JLabel label_season;
    private JLabel label_room;

    private Price price;
    private final PriceManager priceManager;
    private final HotelManager hotelManager;
    private final PensionManager pensionManager;
    private final SeasonManager seasonManager;
    private final RoomManager roomManager;

    public PricePopup() {
        initializeView(320, 400, "Add Price");
        add(container);

        this.priceManager = PriceManager.getInstance();
        this.hotelManager = HotelManager.getInstance();
        this.pensionManager = PensionManager.getInstance();
        this.seasonManager = SeasonManager.getInstance();
        this.roomManager = RoomManager.getInstance();

        button_action.setText("Add Price");

        fillComboBoxes();
        initializeComboBoxListeners();

        addPrice();
    }

    private void initializeComboBoxListeners() {
        comboBox_hotel.addActionListener(e -> {
            comboBox_pension.removeAllItems();

            ComboBoxItem comboBoxItem = (ComboBoxItem) comboBox_hotel.getSelectedItem();
            int hotelID = comboBoxItem.getKey();

            System.out.println(hotelID);

            for (Pension pension : pensionManager.getPensionsByHotelId(hotelID)) {
                comboBox_pension.addItem(new ComboBoxItem(pension.getId(), pension.getType().toString()));
            }
        });

        comboBox_hotel.addActionListener(e -> {
            comboBox_season.removeAllItems();

            ComboBoxItem comboBoxItem = (ComboBoxItem) comboBox_hotel.getSelectedItem();
            int hotelID = comboBoxItem.getKey();

            for (Season season : seasonManager.getSeasonsByHotelId(hotelID)) {
                comboBox_season.addItem(new ComboBoxItem(season.getId(), season.getType().toString()));
            }
        });

        comboBox_hotel.addActionListener(e -> {
            comboBox_room.removeAllItems();

            ComboBoxItem comboBoxItem = (ComboBoxItem) comboBox_hotel.getSelectedItem();
            int hotelID = comboBoxItem.getKey();

            ComboBoxItem comboBoxItem1 = (ComboBoxItem) comboBox_pension.getSelectedItem();

            if (comboBoxItem1 == null)
                return;

            int pensionID = comboBoxItem1.getKey();

            for (Room room : roomManager.getRoomsByHotelIdAndPensionId(hotelID, pensionID)) {
                comboBox_room.addItem(new ComboBoxItem(room.getId(), room.getType().toString()));
            }
        });

        comboBox_pension.addActionListener(e -> {
            comboBox_room.removeAllItems();

            ComboBoxItem comboBoxItem = (ComboBoxItem) comboBox_hotel.getSelectedItem();
            int hotelID = comboBoxItem.getKey();

            ComboBoxItem comboBoxItem1 = (ComboBoxItem) comboBox_pension.getSelectedItem();
            if (comboBoxItem1 == null)
                return;
            int pensionID = comboBoxItem1.getKey();

            for (Room room : roomManager.getRoomsByHotelIdAndPensionId(hotelID, pensionID)) {
                comboBox_room.addItem(new ComboBoxItem(room.getId(), room.getType().toString()));
            }
        });
    }

    private void fillComboBoxes() {
        fillHotelComboBoxes();
        fillPensionComboBoxes();
        fillSeasonComboBoxes();
        fillRoomComboBoxes();
    }

    private void fillHotelComboBoxes() {
        for (Hotel hotel : hotelManager.getAll()) {
            comboBox_hotel.addItem(new ComboBoxItem(hotel.getId(), hotel.getName()));
        }
    }

    private void fillPensionComboBoxes() {
        ComboBoxItem comboBoxItemHotel = (ComboBoxItem) comboBox_hotel.getSelectedItem();
        int hotelId = comboBoxItemHotel.getKey();

        for (Pension pension : pensionManager.getPensionsByHotelId(hotelId)) {
            comboBox_pension.addItem(new ComboBoxItem(pension.getId(), pension.getType().toString()));
        }

    }

    private void fillSeasonComboBoxes() {
        ComboBoxItem comboBoxItemHotel = (ComboBoxItem) comboBox_hotel.getSelectedItem();
        int hotelId = comboBoxItemHotel.getKey();

        for (Season season : seasonManager.getSeasonsByHotelId(hotelId)) {
            comboBox_season.addItem(new ComboBoxItem(season.getId(), season.getType().toString()));
        }
    }

    private void fillRoomComboBoxes() {
        ComboBoxItem comboBoxItemHotel = (ComboBoxItem) comboBox_hotel.getSelectedItem();
        int hotelId = comboBoxItemHotel.getKey();

        ComboBoxItem comboBoxItemPension = (ComboBoxItem) comboBox_pension.getSelectedItem();
        int pensionId = comboBoxItemPension.getKey();

        for (Room room : roomManager.getRoomsByHotelIdAndPensionId(hotelId, pensionId)) {
            comboBox_room.addItem(new ComboBoxItem(room.getId(), room.getType().toString()));
        }
    }

    private void addPrice() {
        button_action.addActionListener(e -> {
            if (areFieldsEmpty())
                return;

            if (areComboBoxesEmpty())
                return;

            if (priceManager.create(getPriceFromFields(null)))
                Helper.showMassageAll("done");
            else
                Helper.showMassageAll("error");

            dispose();
        });
    }

    private boolean areComboBoxesEmpty() {
        JComboBox[] comboBoxes = {comboBox_hotel, comboBox_pension, comboBox_room, comboBox_season};

        if (Helper.isComboBoxListEmpty(comboBoxes))
            return true;

        return false;
    }

    public Price getPriceFromFields(Price price) {
        int id = price == null ? 0 : price.getId();

        ComboBoxItem comboBoxItemHotel = (ComboBoxItem) comboBox_hotel.getSelectedItem();
        int hotelId = comboBoxItemHotel.getKey();

        ComboBoxItem comboBoxItemPension = (ComboBoxItem) comboBox_pension.getSelectedItem();
        int pensionId = comboBoxItemPension.getKey();

        ComboBoxItem comboBoxItemRoom = (ComboBoxItem) comboBox_room.getSelectedItem();
        int roomId = comboBoxItemRoom.getKey();

        ComboBoxItem comboBoxItemSeason = (ComboBoxItem) comboBox_season.getSelectedItem();
        int seasonId = comboBoxItemSeason.getKey();

        double adultPrice = Double.valueOf(textField_adult_price.getText());
        double childPrice = Double.valueOf(textField_child_price.getText());

        return new Price(id, hotelId, pensionId, roomId, seasonId, adultPrice, childPrice);
    }

    private boolean areFieldsEmpty() {
        JTextField[] fields = {textField_adult_price, textField_child_price};

        if (Helper.isFieldListEmpty(fields)) {
            return true;
        }

        return false;
    }

    public PricePopup(Price price) {
        initializeView(320, 400, "Update Price");
        add(container);

        this.price = price;
        this.priceManager = PriceManager.getInstance();
        this.hotelManager = HotelManager.getInstance();
        this.pensionManager = PensionManager.getInstance();
        this.seasonManager = SeasonManager.getInstance();
        this.roomManager = RoomManager.getInstance();

        button_action.setText("Update Price");
        fillPrices();
        fillComboBoxes();

        selectCorrectItemsInComboBox();
        initializeComboBoxListeners();


        updatePrice();
    }

    private void fillPrices() {
        textField_adult_price.setText(String.valueOf(price.getAdultPrice()));
        textField_child_price.setText(String.valueOf(price.getChildPrice()));
    }

    private void selectCorrectItemsInComboBox() {
        selectInHotelComboBox();
        selectInPensionComboBox();
        selectInRoomComboBox();
        selectInSeasonComboBox();
    }

    private void selectInHotelComboBox() {
        int hotelId = price.getHotelId();

        for (int i = 0; i < comboBox_hotel.getItemCount(); i++) {
            ComboBoxItem currentItem = (ComboBoxItem) comboBox_hotel.getItemAt(i);

            if (currentItem.getKey() == hotelId) {
                comboBox_hotel.setSelectedItem(currentItem);
                break;
            }

        }
    }

    private void selectInPensionComboBox() {
        int pensionId = price.getPensionId();

        for (int i = 0; i < comboBox_pension.getItemCount(); i++) {
            ComboBoxItem currentItem = (ComboBoxItem) comboBox_pension.getItemAt(i);

            if (currentItem.getKey() == pensionId) {
                comboBox_pension.setSelectedItem(currentItem);
                break;
            }
        }
    }

    private void selectInRoomComboBox() {
        int roomId = price.getRoomId();

        for (int i = 0; i < comboBox_room.getItemCount(); i++) {
            ComboBoxItem currentItem = (ComboBoxItem) comboBox_room.getItemAt(i);

            if (currentItem.getKey() == roomId) {
                comboBox_room.setSelectedItem(currentItem);
                break;
            }
        }
    }

    private void selectInSeasonComboBox() {
        int seasonId = price.getSeasonId();

        for (int i = 0; i < comboBox_season.getItemCount(); i++) {
            ComboBoxItem currentItem = (ComboBoxItem) comboBox_season.getItemAt(i);

            if (currentItem.getKey() == seasonId) {
                comboBox_season.setSelectedItem(currentItem);
                break;
            }
        }
    }

    private void updatePrice() {
        button_action.addActionListener(e -> {
            if (areFieldsEmpty())
                return;

            if (areComboBoxesEmpty())
                return;

            if (priceManager.update(getPriceFromFields(price)))
                Helper.showMassageAll("done");
            else
                Helper.showMassageAll("error");


            dispose();
        });


    }


}
