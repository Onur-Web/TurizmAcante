package view.management.price;

import business.PriceManager;
import core.Helper;
import core.mouselistener.MouseListener;
import core.tableloader.PriceTableLoader;
import entity.Price;
import entity.User;
import view.Layout;
import view.management.Personnel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PriceView extends Layout {
    private JPanel container;
    private JButton button_goback;
    private JTable table_price;
    private JLabel label_greeting;
    private JScrollPane scroll_price;
    private JButton button_add;

    private Object[] rows;
    private DefaultTableModel table_model;
    private JPopupMenu jPopupMenu;

    private final User user;
    private final PriceManager priceManager;

    private final PriceTableLoader priceTableLoader;
    private final MouseListener mouseListener;

    public PriceView(User user) {
        initializeView(1500, 800, "Price");
        add(container);

        createPriceList();
        createPopupMenu();

        this.user = user;
        this.priceManager = PriceManager.getInstance();

        this.priceTableLoader = new PriceTableLoader(table_price, rows, table_model);

        this.mouseListener = new MouseListener(table_price, jPopupMenu);
        mouseListener.listenToTable();

        label_greeting.setText("Welcome " + user.getName());

        goBack();

        priceTableLoader.loadPrices();

        initializeAddPriceListener();
    }

    private void goBack() {
        button_goback.addActionListener(e -> {
            new Personnel(user);
            dispose();
        });
    }

    private void createPriceList() {
        table_model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 )
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_user_list = {"ID", "Hotel ID", "Pension ID", "Room ID", "Season ID", "Adult Price", "Child Price"};
        table_model.setColumnIdentifiers(col_user_list);
        rows = new Object[col_user_list.length];

        table_price.setModel(table_model);
        table_price.getTableHeader().setReorderingAllowed(false);
    }

    private void createPopupMenu() {
        jPopupMenu = new JPopupMenu();

        JMenuItem updatePrice = new JMenuItem("Update Price");
        JMenuItem deletePrice = new JMenuItem("Delete Price");

        jPopupMenu.add(updatePrice);
        jPopupMenu.add(deletePrice);

        initializeUpdatePriceListener(updatePrice);
        initializeDeletePriceListener(deletePrice);

    }

    private void initializeAddPriceListener() {
        button_add.addActionListener(e -> {

            PricePopup pricePopup = new PricePopup();
            pricePopup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    priceTableLoader.loadPrices();
                }
            });
        });
    }

    private void initializeUpdatePriceListener(JMenuItem updatePrice) {
        updatePrice.addActionListener(e -> {
            int selectedRow = table_price.getSelectedRow();

            if (selectedRow == -1) {
                return;
            }

            int id = (int) table_price.getValueAt(selectedRow, 0);
            int hotelId = (int) table_price.getValueAt(selectedRow, 1);
            int pensionId = (int) table_price.getValueAt(selectedRow, 2);
            int roomId = (int) table_price.getValueAt(selectedRow, 3);
            int seasonId = (int) table_price.getValueAt(selectedRow, 4);
            double adultPrice = (double) table_price.getValueAt(selectedRow, 5);
            double childPrice = (double) table_price.getValueAt(selectedRow, 6);

            Price price = new Price(id, hotelId, pensionId, roomId, seasonId, adultPrice, childPrice);

            PricePopup pricePopup = new PricePopup(price);
            pricePopup.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    priceTableLoader.loadPrices();
                }
            });
        });
    }

    private void initializeDeletePriceListener(JMenuItem deletePrice) {
        deletePrice.addActionListener(e -> {
            if (!Helper.showMassageSure("sure")) {
                return;
            }

            int selectedId = Integer.parseInt(table_price.getValueAt(table_price.getSelectedRow(), 0).toString());

            if (priceManager.delete(selectedId)) {
                Helper.showMassageAll("done");
                priceTableLoader.loadPrices();
            }

            priceTableLoader.loadPrices();
        });
    }
}
