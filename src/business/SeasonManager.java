package business;
import core.SeasonType;
import dao.SeasonDAO;
import entity.Season;

import java.util.List;
import java.util.Optional;

public class SeasonManager {
    private final SeasonDAO seasonDAO;
    private static SeasonManager instance;

    private SeasonManager() {
        this.seasonDAO = SeasonDAO.getInstance();
    }

    public static SeasonManager getInstance() {
        if (instance == null) {
            instance = new SeasonManager();
        }

        return instance;
    }

    public List<Season> getAll() {
        return seasonDAO.findAll();
    }

    public List<Season> getSeasonsByHotelId(int hotelId) {
        return seasonDAO.findSeasonsByHotelId(hotelId);
    }

    public Optional<Season> getSeasonByHotelIdAndType(int hotelId, SeasonType type) {
        return seasonDAO.findSeasonByHotelIdAndType(hotelId, type);
    }

    public boolean create(Season season) {
        return seasonDAO.create(season);
    }

    public boolean update(Season season) {
        return seasonDAO.update(season);
    }

    public boolean delete(int id) {
        return seasonDAO.delete(id);
    }
}
