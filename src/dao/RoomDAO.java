package dao;

import core.DB;
import core.RoomType;
import entity.Room;
import entity.Room;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDAO {
    private final Connection connection;
    private static RoomDAO instance = null;

    private RoomDAO() {
        this.connection = DB.getInstance();
    }

    //NULL ISE YENI NESNE OLUSTUR
    public static RoomDAO getInstance() {
        if (instance == null) {
            instance = new RoomDAO();
        }

        return instance;
    }

    //TUM ROOMLARIDONER VE LISTEYE EKLEME YAPAR
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();

        String query = "SELECT * FROM public.room";

        try {

            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                rooms.add(match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return rooms;
    }
    
    public List<Room> findRoomsByHotelIdAndPensionId(int hotelId, int pensionId) {
        List<Room> rooms = new ArrayList<>();

        String query = "SELECT * FROM public.room WHERE hotel_id = ? AND pension_id = ?";

        try {

            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelId);
            ps.setInt(2, pensionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rooms.add(match(rs));
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return rooms;
    }

    public Optional<Room> findById(int id) {
        String query = "SELECT * FROM public.room WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(match(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Room> searchRoom(String hotelName, String hotelCity, Date start, Date finish, int bedCount) {
        List<Room> rooms = new ArrayList<>();

        String query = "SELECT room.*\n" +
                "FROM public.room\n" +
                "INNER JOIN public.hotel ON room.hotel_id = hotel.id\n" +
                "INNER JOIN public.season ON room.hotel_id = season.hotel_id\n" +
                "WHERE \n" +
                "    (hotel.name ILIKE ? OR hotel.city ILIKE ? OR \n" +
                "    (? BETWEEN season.start AND season.finish AND \n" +
                "     ? BETWEEN season.start AND season.finish))\n" +
                "GROUP BY room.id\n" +
                "HAVING(room.stock > 0 AND room.bed_count = ?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, hotelName);
            ps.setString(2, hotelCity);
            ps.setDate(3, start);
            ps.setDate(4, finish);
            ps.setInt(5, bedCount);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rooms.add(match(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    //ROOMLARA VERI EKLE
    public boolean create(Room room) {
        String query = "INSERT INTO public.room (hotel_id, pension_id, type, stock, bed_count, size, has_tv, has_minibar, has_game_console, has_safe, has_projector) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, room);

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //ROOMLARA VERILERI GUNCELLE
    public boolean update(Room room) {
        String query = "UPDATE public.room SET hotel_id = ?, pension_id = ?, type = ?, stock = ?, bed_count = ?, size = ?, has_tv = ?, has_minibar = ?, has_game_console = ?, has_safe = ?, has_projector = ? WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, room);
            ps.setInt(12, room.getId());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    //ROOMDAN VERI SIL
    public boolean delete(int id) {
        String query = "DELETE FROM public.room WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);

            return ps.executeUpdate() != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }
    //RESULTSET TEN FACILITY NESNESI OLUSTURMAK (NESNELERI SUTUNDA KARSILIK GELDIKLERI YERLERINE KOYAR)
    public Room match(ResultSet rs) throws SQLException {
        Room room = new Room();

        room.setId(rs.getInt("id"));
        room.setHotelId(rs.getInt("hotel_id"));
        room.setPensionId(rs.getInt("pension_id"));
        room.setType(RoomType.valueOf(rs.getString("type")));
        room.setStock(rs.getInt("stock"));
        room.setBedCount(rs.getInt("bed_count"));
        room.setSize(rs.getInt("size"));
        room.setHasTV(rs.getBoolean("has_tv"));
        room.setHasMinibar(rs.getBoolean("has_minibar"));
        room.setHasGameConsole(rs.getBoolean("has_game_console"));
        room.setHasSafe(rs.getBoolean("has_safe"));
        room.setHasProjector(rs.getBoolean("has_projector"));

        return room;
    }
    //SQL ILE ILETISIME GECER VE RESULTSET SONUCU OLUSMUS SONUCA VERILERI EKLER ((EKLE - GUNCELLE - SILME) ICIN DINAMIK METOD)
    public void match(PreparedStatement ps, Room room) throws SQLException {
        ps.setInt(1, room.getHotelId());
        ps.setInt(2, room.getPensionId());
        ps.setString(3, room.getType().toString());
        ps.setInt(4, room.getStock());
        ps.setInt(5, room.getBedCount());
        ps.setInt(6, room.getSize());
        ps.setBoolean(7, room.hasTV());
        ps.setBoolean(8, room.hasMinibar());
        ps.setBoolean(9, room.hasGameConsole());
        ps.setBoolean(10, room.hasSafe());
        ps.setBoolean(11, room.hasProjector());
    }
}
