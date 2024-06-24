package business;

import dao.PriceDAO;
import entity.Price;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class PriceManager {
    private final PriceDAO priceDAO;
    private static PriceManager instance;

    private PriceManager() {
        this.priceDAO = PriceDAO.getInstance();
    }

    public static PriceManager getInstance() {
        if (instance == null) {
            instance = new PriceManager();
        }

        return instance;
    }

    public List<Price> getAll() {
        return priceDAO.findAll();
    }

    public Optional<Price> getByRoomIdAndPensionIdAndDates(int roomId, int pensionId, Date start, Date finish) {
        return priceDAO.findByRoomIdAndPensionIdAndDates(roomId, pensionId, start, finish);
    }

    public boolean create(Price price) {
        return priceDAO.create(price);
    }

    public boolean update(Price price) {
        return priceDAO.update(price);
    }

    public boolean delete(int id) {
        return priceDAO.delete(id);
    }
}
