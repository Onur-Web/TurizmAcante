package view.management.hotel;

import business.HotelManager;
import core.Helper;
import core.mouselistener.MouseListener;
import core.tableloader.HotelTableLoader;
import entity.Hotel;
import entity.User;
import view.Layout;
import view.management.Personnel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HotelView extends Layout {
    private JPanel container;
    private JButton button_goback;
    private JLabel label_greeting;
    private JTable table_hotel;
    private JScrollPane scroll_hotel;
    private JButton button_add;

    private Object[] rows;
    private DefaultTableModel table_model;
    private JPopupMenu jPopupMenu;

    private final User user;
    private final HotelManager hotelManager;

    private final HotelTableLoader hotelTableLoader;
    private final MouseListener mouseListener;

    public HotelView(User user) {
        initializeView(1500, 800, "Hotel");
        add(container);

        createHotelList();
        createPopupMenu();

        this.user = user;
        this.hotelManager = HotelManager.getInstance();

        this.hotelTableLoader = new HotelTableLoader(table_hotel, rows, table_model);

        this.mouseListener = new MouseListener(table_hotel, jPopupMenu);
        mouseListener.listenToTable();

        label_greeting.setText("Welcome " + user.getName());

        goBack();

        hotelTableLoader.loadHotels();

        initializeAddHotelListener();
    }

    private void goBack() {
        button_goback.addActionListener(e -> {
            new Personnel(user);
            dispose();
        });
    }

    private void createHotelList() {
        table_model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6 || column == 7)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_user_list = {"ID", "Name", "City", "Region", "Address", "Email", "Telephone", "Star"};
        table_model.setColumnIdentifiers(col_user_list);
        rows = new Object[col_user_list.length];

        table_hotel.setModel(table_model);
        table_hotel.getTableHeader().setReorderingAllowed(false);
    }

    private void createPopupMenu() {
        jPopupMenu = new JPopupMenu();

        JMenuItem updateHotel = new JMenuItem("Update Hotel");
        JMenuItem deleteHotel = new JMenuItem("Delete Hotel");

        jPopupMenu.add(updateHotel);
        jPopupMenu.add(deleteHotel);

        initializeUpdateHotelListener(updateHotel);
        initializeDeleteHotelListener(deleteHotel);


        // facility, pension, and season
        JMenuItem updateFacility = new JMenuItem("Update Facility");
        initializeUpdateFacilityListener(updateFacility);
        jPopupMenu.add(updateFacility);

        JMenuItem updatePension = new JMenuItem("Update Pension");
        initializeUpdatePensionListener(updatePension);
        jPopupMenu.add(updatePension);


        JMenuItem updateSeason = new JMenuItem("Update Season");
        initializeUpdateSeasonListener(updateSeason);
        jPopupMenu.add(updateSeason);
    }

    private void initializeAddHotelListener() {
        button_add.addActionListener(e -> {
            HotelPopup hotelPopup = new HotelPopup();
            hotelPopup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    hotelTableLoader.loadHotels();
                }
            });
        });
    }

    private void initializeUpdateHotelListener(JMenuItem updateHotel) {
        updateHotel.addActionListener(e -> {
            int selectedRow = table_hotel.getSelectedRow();

            if (selectedRow == -1) {
                return;
            }

            int id = (int) table_hotel.getValueAt(selectedRow, 0);
            String name = (String) table_hotel.getValueAt(selectedRow, 1);
            String city = (String) table_hotel.getValueAt(selectedRow, 2);
            String region = (String) table_hotel.getValueAt(selectedRow, 3);
            String address = (String) table_hotel.getValueAt(selectedRow, 4);
            String email = (String) table_hotel.getValueAt(selectedRow, 5);
            String telephone = (String) table_hotel.getValueAt(selectedRow, 6);
            String star = (String) table_hotel.getValueAt(selectedRow, 7);

            Hotel hotel = new Hotel(id, name, city, region, address, email, telephone, star);

            HotelPopup hotelPopup = new HotelPopup(hotel);
            hotelPopup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    hotelTableLoader.loadHotels();
                }
            });
        });
    }

    private void initializeDeleteHotelListener(JMenuItem deleteHotel) {
        deleteHotel.addActionListener(e -> {
            if (!Helper.showMassageSure("sure")) {
                return;
            }

            int selectedId = Integer.parseInt(table_hotel.getValueAt(table_hotel.getSelectedRow(), 0).toString());

            if (hotelManager.delete(selectedId)) {
                Helper.showMassageAll("done");
                hotelTableLoader.loadHotels();
            }

            hotelTableLoader.loadHotels();
        });
    }

    private void initializeUpdateFacilityListener(JMenuItem updateFacility) {
        updateFacility.addActionListener(e -> {
            int selectedId = Integer.parseInt(table_hotel.getValueAt(table_hotel.getSelectedRow(), 0).toString());
            new FacilityPopup(selectedId);
        });
    }

    private void initializeUpdatePensionListener(JMenuItem updatePension) {
        updatePension.addActionListener(e -> {
            int selectedId = Integer.parseInt(table_hotel.getValueAt(table_hotel.getSelectedRow(), 0).toString());
            new PensionPopup(selectedId);
        });
    }

    private void initializeUpdateSeasonListener(JMenuItem updateSeason) {
        updateSeason.addActionListener(e -> {
            int selectedId = Integer.parseInt(table_hotel.getValueAt(table_hotel.getSelectedRow(), 0).toString());
            new SeasonPopup(selectedId);
        });
    }
}
