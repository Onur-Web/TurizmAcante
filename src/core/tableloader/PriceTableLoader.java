package core.tableloader;

import business.PriceManager;
import business.UserManager;
import entity.Price;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PriceTableLoader {
    private JTable table_price;
    private Object[] rows;
    private DefaultTableModel table_model;
    private PriceManager priceManager;

    public PriceTableLoader(JTable table_price, Object[] rows, DefaultTableModel table_model) {
        this.table_price = table_price;
        this.rows = rows;
        this.table_model = table_model;
        this.priceManager = PriceManager.getInstance();
    }

    public void loadPrices() {
        DefaultTableModel model = (DefaultTableModel) table_price.getModel();
        model.setRowCount(0);
        int i;
        for (Price price : priceManager.getAll()) {
            i = 0;
            rows[i++] = price.getId();
            rows[i++] = price.getHotelId();
            rows[i++] = price.getPensionId();
            rows[i++] = price.getRoomId();
            rows[i++] = price.getSeasonId();
            rows[i++] = price.getAdultPrice();
            rows[i++] = price.getChildPrice();
            table_model.addRow(rows);
        }
    }

    public void loadPrices(List<Price> prices) {
        DefaultTableModel model = (DefaultTableModel) table_price.getModel();
        model.setRowCount(0);
        int i;
        for (Price price : prices) {
            i = 0;
            rows[i++] = price.getId();
            rows[i++] = price.getHotelId();
            rows[i++] = price.getPensionId();
            rows[i++] = price.getRoomId();
            rows[i++] = price.getSeasonId();
            rows[i++] = price.getAdultPrice();
            rows[i++] = price.getChildPrice();
            table_model.addRow(rows);
        }
    }
}
