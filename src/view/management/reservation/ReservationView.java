package view.management.reservation;

import business.ReservationManager;
import core.Helper;
import core.mouselistener.MouseListener;
import core.tableloader.ReservationTableLoader;
import entity.Reservation;
import entity.User;
import view.Layout;
import view.management.Personnel;
import view.management.reservation.ReservationPopup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;

public class ReservationView extends Layout {
    private JPanel container;
    private JButton button_goback;
    private JTable table_reservation;
    private JLabel label_greeting;
    private JScrollPane scroll_reservation;

    private Object[] rows;
    private DefaultTableModel table_model;
    private JPopupMenu jPopupMenu;

    private final User user;
    private final ReservationManager reservationManager;

    private final ReservationTableLoader reservationTableLoader;
    private final MouseListener mouseListener;

    public ReservationView(User user) {
        initializeView(1500, 800, "Reservation");
        add(container);

        createReservationList();
        createPopupMenu();

        this.user = user;
        this.reservationManager = ReservationManager.getInstance();

        this.reservationTableLoader = new ReservationTableLoader(table_reservation, rows, table_model);

        this.mouseListener = new MouseListener(table_reservation, jPopupMenu);
        mouseListener.listenToTable();

        label_greeting.setText("Welcome " + user.getName());

        goBack();

        reservationTableLoader.loadReservations();
    }

    private void goBack() {
        button_goback.addActionListener(e -> {
            new Personnel(user);
            dispose();
        });
    }

    private void createReservationList() {
        table_model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 )
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_user_list = {"ID", "Room ID", "Customer Name", "Customer TC", "Customer Email", "Total Price", "Start", "Finish", "Number of Adults", "Number of Children"};
        table_model.setColumnIdentifiers(col_user_list);
        rows = new Object[col_user_list.length];

        table_reservation.setModel(table_model);
        table_reservation.getTableHeader().setReorderingAllowed(false);
    }

    private void createPopupMenu() {
        jPopupMenu = new JPopupMenu();

        JMenuItem updateReservation = new JMenuItem("Update Reservation");
        JMenuItem deleteReservation = new JMenuItem("Delete Reservation");

        jPopupMenu.add(updateReservation);
        jPopupMenu.add(deleteReservation);

        initializeUpdateReservationListener(updateReservation);
        initializeDeleteReservationListener(deleteReservation);
    }

    private void initializeUpdateReservationListener(JMenuItem updateReservation) {
        updateReservation.addActionListener(e -> {
            int selectedRow = table_reservation.getSelectedRow();

            if (selectedRow == -1) {
                return;
            }

            int id = (int) table_reservation.getValueAt(selectedRow, 0);
            int roomId = (int) table_reservation.getValueAt(selectedRow, 1);
            String customerName = (String) table_reservation.getValueAt(selectedRow, 2);
            String customerTC = (String) table_reservation.getValueAt(selectedRow, 3);
            String customerEmail = (String) table_reservation.getValueAt(selectedRow, 4);
            double totalPrice = (double) table_reservation.getValueAt(selectedRow, 5);
            Date start = (Date) table_reservation.getValueAt(selectedRow, 6);
            Date finish = (Date) table_reservation.getValueAt(selectedRow, 7);
            int numberOfAdults = (int) table_reservation.getValueAt(selectedRow, 8);
            int numberOfChildren = (int) table_reservation.getValueAt(selectedRow, 9);

            Reservation reservation = new Reservation(id, roomId, customerName, customerTC, customerEmail, totalPrice, start, finish, numberOfAdults, numberOfChildren);

            ReservationPopup reservationPopup = new ReservationPopup(reservation);
            reservationPopup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    reservationTableLoader.loadReservations();
                }
            });
        });
    }

    private void initializeDeleteReservationListener(JMenuItem deleteReservation) {
        deleteReservation.addActionListener(e -> {
            if (!Helper.showMassageSure("sure")) {
                return;
            }

            int selectedId = Integer.parseInt(table_reservation.getValueAt(table_reservation.getSelectedRow(), 0).toString());

            if (reservationManager.delete(selectedId)) {
                Helper.showMassageAll("done");
                reservationTableLoader.loadReservations();
            }

            reservationTableLoader.loadReservations();
        });
    }
}
