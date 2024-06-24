package business;

import dao.HotelDAO;
import entity.Hotel;

import java.util.List;

public class HotelManager {
    private final HotelDAO hotelDAO;
    private static HotelManager instance;

    private HotelManager() {
        this.hotelDAO = HotelDAO.getInstance();
    }

    public static HotelManager getInstance() {
        if (instance == null) {
            instance = new HotelManager();
        }

        return instance;
    }

    public List<Hotel> getAll() {
        return hotelDAO.findAll();
    }

    public boolean create(Hotel hotel) {
        return hotelDAO.create(hotel);
    }

    public boolean update(Hotel hotel) {
        return hotelDAO.update(hotel);
    }

    public boolean delete(int id) {
        return hotelDAO.delete(id);
    }
}
