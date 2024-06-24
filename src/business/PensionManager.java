package business;

import core.PensionType;
import dao.PensionDAO;
import entity.Pension;
import entity.Pension;

import java.util.List;
import java.util.Optional;

public class PensionManager {
    private final PensionDAO pensionDAO;
    private static PensionManager instance;

    private PensionManager() {
        this.pensionDAO = PensionDAO.getInstance();
    }

    public static PensionManager getInstance() {
        if (instance == null) {
            instance = new PensionManager();
        }

        return instance;
    }

    public List<Pension> getAll() {
        return pensionDAO.findAll();
    }

    public List<Pension> getPensionsByHotelId(int hotelId) {
        return pensionDAO.findPensionsByHotelId(hotelId);
    }

    public Optional<Pension> getPensionByHotelIdAndType(int hotelId, PensionType type) {
        return pensionDAO.findPensionByHotelIdAndType(hotelId, type);
    }

    public boolean create(Pension pension) {
        return pensionDAO.create(pension);
    }

    public boolean update(Pension pension) {
        return pensionDAO.update(pension);
    }

    public boolean delete(int id) {
        return pensionDAO.delete(id);
    }
}
