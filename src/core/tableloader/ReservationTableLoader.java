package core.tableloader;

import business.ReservationManager;
import entity.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReservationTableLoader {
    private JTable table_reservation;
    private Object[] rows;
    private DefaultTableModel table_model;
    private ReservationManager reservationManager;

    public ReservationTableLoader(JTable table_reservation, Object[] rows, DefaultTableModel table_model) {
        this.table_reservation = table_reservation;
        this.rows = rows;
        this.table_model = table_model;
        this.reservationManager = ReservationManager.getInstance();
    }

    public void loadReservations() {
        DefaultTableModel model = (DefaultTableModel) table_reservation.getModel();
        model.setRowCount(0);
        int i;
        for (Reservation reservation : reservationManager.getAll()) {
            i = 0;
            rows[i++] = reservation.getId();
            rows[i++] = reservation.getRoomId();
            rows[i++] = reservation.getCustomerName();
            rows[i++] = reservation.getCustomerTC();
            rows[i++] = reservation.getCustomerEmail();
            rows[i++] = reservation.getTotalPrice();
            rows[i++] = reservation.getStart();
            rows[i++] = reservation.getFinish();
            rows[i++] = reservation.getNumberOfAdults();
            rows[i++] = reservation.getNumberOfChildren();
            table_model.addRow(rows);
        }
    }

    public void loadReservations(List<Reservation> reservations) {
        DefaultTableModel model = (DefaultTableModel) table_reservation.getModel();
        model.setRowCount(0);
        int i;
        for (Reservation reservation : reservations) {
            i = 0;
            rows[i++] = reservation.getId();
            rows[i++] = reservation.getRoomId();
            rows[i++] = reservation.getCustomerName();
            rows[i++] = reservation.getCustomerTC();
            rows[i++] = reservation.getCustomerEmail();
            rows[i++] = reservation.getTotalPrice();
            rows[i++] = reservation.getStart();
            rows[i++] = reservation.getFinish();
            rows[i++] = reservation.getNumberOfAdults();
            rows[i++] = reservation.getNumberOfChildren();
            table_model.addRow(rows);
        }
    }
}
