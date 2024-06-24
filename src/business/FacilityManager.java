package business;

import core.FacilityType;
import dao.FacilityDAO;
import entity.Facility;

import java.util.List;
import java.util.Optional;

public class FacilityManager {
    private final FacilityDAO facilityDAO;
    private static FacilityManager instance;

    private FacilityManager() {
        this.facilityDAO = FacilityDAO.getInstance();
    }

    public static FacilityManager getInstance() {
        if (instance == null) {
            instance = new FacilityManager();
        }

        return instance;
    }

    public List<Facility> getAll() {
        return facilityDAO.findAll();
    }

    public List<Facility> getFacilitiesByHotelId(int hotelId) {
        return facilityDAO.findFacilitiesByHotelId(hotelId);
    }

    public Optional<Facility> getFacilityByHotelIdAndType(int hotelId, FacilityType type) {
        return facilityDAO.findFacilityByHotelIdAndType(hotelId, type);
    }

    public boolean create(Facility facility) {
        return facilityDAO.create(facility);
    }

    public boolean update(Facility facility) {
        return facilityDAO.update(facility);
    }

    public boolean delete(int id) {
        return facilityDAO.delete(id);
    }
}
