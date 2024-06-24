package core.tableloader;

import business.RoomManager;

import entity.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class RoomTableLoader {
    private JTable table_rooms;
    private Object[] rows;
    private DefaultTableModel table_model;
    private RoomManager roomManager;

    public RoomTableLoader(JTable table_rooms, Object[] rows, DefaultTableModel table_model) {
        this.table_rooms = table_rooms;
        this.rows = rows;
        this.table_model = table_model;
        this.roomManager = RoomManager.getInstance();
    }

    public void loadRooms() {

        DefaultTableModel model = (DefaultTableModel) table_rooms.getModel();
        model.setRowCount(0);
        int i;
        for (Room room : roomManager.getAll()) {
            i = 0;
            rows[i++] = room.getId();
            rows[i++] = room.getHotelId();
            rows[i++] = room.getPensionId();
            rows[i++] = room.getType();
            rows[i++] = room.getStock();
            rows[i++] = room.getBedCount();
            rows[i++] = room.getSize();
            rows[i++] = room.hasTV();
            rows[i++] = room.hasMinibar();
            rows[i++] = room.hasGameConsole();
            rows[i++] = room.hasSafe();
            rows[i++] = room.hasProjector();

            table_model.addRow(rows);
        }
    }

    public void loadRooms(List<Room> rooms) {
        DefaultTableModel model = (DefaultTableModel) table_rooms.getModel();
        model.setRowCount(0);
        int i;
        for (Room room : rooms) {
            i = 0;
            rows[i++] = room.getId();
            rows[i++] = room.getHotelId();
            rows[i++] = room.getPensionId();
            rows[i++] = room.getType();
            rows[i++] = room.getStock();
            rows[i++] = room.getBedCount();
            rows[i++] = room.getSize();
            rows[i++] = room.hasTV();
            rows[i++] = room.hasMinibar();
            rows[i++] = room.hasGameConsole();
            rows[i++] = room.hasSafe();
            rows[i++] = room.hasProjector();

            table_model.addRow(rows);
        }
    }
}
