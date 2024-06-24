package business;

import dao.RoomDAO;
import entity.Room;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class RoomManager {
    private final RoomDAO roomDAO;
    private static RoomManager instance;

    private RoomManager() {
        this.roomDAO = RoomDAO.getInstance();
    }

    public static RoomManager getInstance() {
        if (instance == null) {
            instance = new RoomManager();
        }

        return instance;
    }

    public List<Room> getAll() {
        return roomDAO.findAll();
    }

    public List<Room> getRoomsByHotelIdAndPensionId(int hotelId, int pensionId) {
        return roomDAO.findRoomsByHotelIdAndPensionId(hotelId, pensionId);
    }

    public Optional<Room> getById(int id) {
        return roomDAO.findById(id);
    }

    public List<Room> searchRoom(String hotelName, String hotelCity, Date start, Date finish, int bedCount) {
        return roomDAO.searchRoom(hotelName, hotelCity, start, finish, bedCount);
    }

    public boolean create(Room room) {
        return roomDAO.create(room);
    }

    public boolean update(Room room) {
        return roomDAO.update(room);
    }

    public boolean delete(int id) {
        return roomDAO.delete(id);
    }
}
