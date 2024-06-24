package view.management.room;

import business.ReservationManager;
import business.RoomManager;
import core.Helper;
import core.RoomType;
import core.mouselistener.MouseListener;
import core.tableloader.RoomTableLoader;
import entity.Room;
import entity.User;
import view.Layout;
import view.management.Personnel;
import view.management.reservation.ReservationPopup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.List;

public class RoomView extends Layout {
    private JButton button_goback;
    private JTable table_room;
    private JLabel label_greeting;
    private JScrollPane scroll_room;
    private JPanel container;
    private JTextField textField_hotel_name;
    private JTextField textField_hotel_city;
    private JTextField textField_finish_date;
    private JTextField textField_start_date;
    private JButton button_search;
    private JLabel label_hotel_name;
    private JLabel label_hotel_city;
    private JLabel label_start_date;
    private JLabel label_finish_date;
    private JTextField textField_bed_count;
    private JLabel label_bed_count;
    private JButton button_add;

    private Object[] rows;
    private DefaultTableModel table_model;
    private JPopupMenu jPopupMenu;

    private final User user;
    private final RoomManager roomManager;

    private final RoomTableLoader roomTableLoader;
    private final MouseListener mouseListener;

    public RoomView(User user) {
        initializeView(1500, 1000, "Room");
        add(container);

        createRoomList();
        createPopupMenu();

        this.user = user;
        this.roomManager = RoomManager.getInstance();

        this.roomTableLoader = new RoomTableLoader(table_room, rows, table_model);

        this.mouseListener = new MouseListener(table_room, jPopupMenu);
        mouseListener.listenToTable();

        label_greeting.setText("Welcome " + user.getName());

        goBack();

        roomTableLoader.loadRooms();


        searchRooms();
        initializeAddRoomListener();

    }

    private void searchRooms() {
        button_search.addActionListener(e -> {
            JTextField[] fields = {textField_hotel_name, textField_hotel_city, textField_start_date, textField_finish_date, textField_bed_count};

            if (Helper.isFieldListEmpty(fields)) {
                Helper.showMassageAll("fill");
                return;
            }

            List<Room> filteredRooms = roomManager.searchRoom(
                    textField_hotel_name.getText(),
                    textField_hotel_city.getText(),
                    Date.valueOf(textField_start_date.getText()),
                    Date.valueOf(textField_finish_date.getText()),
                    Integer.parseInt(textField_bed_count.getText())
            );

            roomTableLoader.loadRooms(filteredRooms);
        });
    }

    private void goBack() {
        button_goback.addActionListener(e -> {
            new Personnel(user);
            dispose();
        });
    }

    private void createRoomList() {
        table_model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6 || column == 7 || column == 8 || column == 9 || column == 10 || column == 11)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_user_list = {"ID", "Hotel ID", "Pension ID", "Type", "Stock", "Bed Count", "Size", "Has Tv", "Has Minibar", "Has Game Console", "Has Safe", "Has Projector"};
        table_model.setColumnIdentifiers(col_user_list);
        rows = new Object[col_user_list.length];

        table_room.setModel(table_model);
        table_room.getTableHeader().setReorderingAllowed(false);
    }

    private void createPopupMenu() {
        jPopupMenu = new JPopupMenu();

        JMenuItem updateRoom = new JMenuItem("Update Room");
        JMenuItem deleteRoom = new JMenuItem("Delete Room");
        JMenuItem reserveRoom = new JMenuItem("Reserve Room");

        jPopupMenu.add(updateRoom);
        jPopupMenu.add(deleteRoom);
        jPopupMenu.add(reserveRoom);

        initializeUpdateRoomListener(updateRoom);
        initializeDeleteRoomListener(deleteRoom);
        initializeReserveRoomListener(reserveRoom);

    }

    private void initializeAddRoomListener() {
        button_add.addActionListener(e -> {
            RoomPopup roomPopup = new RoomPopup();
            roomPopup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    roomTableLoader.loadRooms();
                }
            });
        });
    }

    private void initializeUpdateRoomListener(JMenuItem updateRoom) {
        updateRoom.addActionListener(e -> {
            Room room = getRoomFromRows();

            RoomPopup roomPopup = new RoomPopup(room);
            roomPopup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    roomTableLoader.loadRooms();
                }
            });
        });
    }

    private Room getRoomFromRows() {
        int selectedRow = table_room.getSelectedRow();

        int id = (int) table_room.getValueAt(selectedRow, 0);
        int hotelId = (int) table_room.getValueAt(selectedRow, 1);
        int pensionId = (int) table_room.getValueAt(selectedRow, 2);
        RoomType roomType = (RoomType) table_room.getValueAt(selectedRow, 3);
        int stock = (int) table_room.getValueAt(selectedRow, 4);
        int bedCount = (int) table_room.getValueAt(selectedRow, 5);
        int size = (int) table_room.getValueAt(selectedRow, 6);
        boolean hasTV = (boolean) table_room.getValueAt(selectedRow, 7);
        boolean hasMinibar = (boolean) table_room.getValueAt(selectedRow, 8);
        boolean hasGameConsole = (boolean) table_room.getValueAt(selectedRow, 9);
        boolean hasSafe = (boolean) table_room.getValueAt(selectedRow, 10);
        boolean hasProjector = (boolean) table_room.getValueAt(selectedRow, 11);

        return new Room(id, hotelId, pensionId, roomType, stock, bedCount, size, hasTV, hasMinibar, hasGameConsole, hasSafe, hasProjector);
    }

    private void initializeDeleteRoomListener(JMenuItem deleteRoom) {
        deleteRoom.addActionListener(e -> {
            if (!Helper.showMassageSure("sure")) {
                return;
            }

            int selectedId = Integer.parseInt(table_room.getValueAt(table_room.getSelectedRow(), 0).toString());

            if (roomManager.delete(selectedId)) {
                Helper.showMassageAll("done");
                roomTableLoader.loadRooms();
            }

            roomTableLoader.loadRooms();
        });
    }

    private void initializeReserveRoomListener(JMenuItem reserveRoom) {
        reserveRoom.addActionListener(e -> {

            Room roomToBeReserved = getRoomFromRows();

            if (roomToBeReserved.getStock() == 0) {
                Helper.showMassageAll("error");
                return;
            }

            ReservationPopup reservationPopup = new ReservationPopup(roomToBeReserved, textField_start_date.getText(), textField_finish_date.getText());
            reservationPopup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    roomTableLoader.loadRooms();
                }
            });
        });
    }

}
