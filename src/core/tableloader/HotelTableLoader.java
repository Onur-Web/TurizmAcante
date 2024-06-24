package core.tableloader;

import business.HotelManager;
import entity.Hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class HotelTableLoader {

    private JTable table_hotel;
    private Object[] rows;
    private DefaultTableModel table_model;
    private HotelManager hotelManager;

    public HotelTableLoader(JTable table_hotel, Object[] rows, DefaultTableModel table_model) {
        this.table_hotel = table_hotel;
        this.rows = rows;
        this.table_model = table_model;
        this.hotelManager = HotelManager.getInstance();
    }

    public void loadHotels() {
        DefaultTableModel model = (DefaultTableModel) table_hotel.getModel();
        model.setRowCount(0);
        int i;
        for (Hotel hotel : hotelManager.getAll()) {
            i = 0;
            rows[i++] = hotel.getId();
            rows[i++] = hotel.getName();
            rows[i++] = hotel.getCity();
            rows[i++] = hotel.getRegion();
            rows[i++] = hotel.getAddress();
            rows[i++] = hotel.getEmail();
            rows[i++] = hotel.getTelephone();
            rows[i++] = hotel.getStar();
            table_model.addRow(rows);
        }
    }

    public void loadHotels(List<Hotel> hotels) {
        DefaultTableModel model = (DefaultTableModel) table_hotel.getModel();
        model.setRowCount(0);
        int i;
        for (Hotel hotel : hotels) {
            i = 0;
            rows[i++] = hotel.getId();
            rows[i++] = hotel.getName();
            rows[i++] = hotel.getCity();
            rows[i++] = hotel.getRegion();
            rows[i++] = hotel.getAddress();
            rows[i++] = hotel.getEmail();
            rows[i++] = hotel.getTelephone();
            rows[i++] = hotel.getStar();
            table_model.addRow(rows);
        }
    }
}
