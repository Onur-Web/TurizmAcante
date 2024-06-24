package dao;

import core.DB;
import entity.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {
    private final Connection connection;
    private static HotelDAO instance = null;

    private HotelDAO() {
        this.connection = DB.getInstance();
    }

    public static HotelDAO getInstance() {
        if (instance == null) {
            instance = new HotelDAO();
        }

        return instance;
    }

    //HOTELI LISTELE
    public List<Hotel> findAll() {
        List<Hotel> hotels = new ArrayList<>();

        String query = "SELECT * FROM public.hotel";

        try {

            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                hotels.add(match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return hotels;
    }

    //HOTELE VERI EKLE
    public boolean create(Hotel hotel) {
        String query = "INSERT INTO public.hotel (name, city, region, address, email, telephone, star) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, hotel);

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //HOTELDEKI VERILERI GUNCELLE
    public boolean update(Hotel hotel) {
        String query = "UPDATE public.hotel SET name = ?, city = ?, region = ?, address = ?, email = ?, telephone = ?, star = ? WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, hotel);
            ps.setInt(8, hotel.getId());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    //HOTELDEN VERI SIL
    public boolean delete(int id) {
        String query = "DELETE FROM public.hotel WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);

            return ps.executeUpdate() != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    //RESULTSET TEN HOTEL NESNESI OLUSTURMAK (NESNELERI SUTUNDA KARSILIK GELDIKLERI YERLERINE KOYAR)
    public Hotel match(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel();

        hotel.setId(rs.getInt("id"));
        hotel.setName(rs.getString("name"));
        hotel.setCity(rs.getString("city"));
        hotel.setRegion(rs.getString("region"));
        hotel.setAddress(rs.getString("address"));
        hotel.setEmail(rs.getString("email"));
        hotel.setTelephone(rs.getString("telephone"));
        hotel.setStar(rs.getString("star"));

        return hotel;
    }

    //SQL ILE ILETISIME GECER VE RESULTSET SONUCU OLUSMUS SONUCA VERILERI EKLER ((EKLE - GUNCELLE - SILME) ICIN DINAMIK METOD)
    public void match(PreparedStatement ps, Hotel hotel) throws SQLException {
        ps.setString(1, hotel.getName());
        ps.setString(2, hotel.getCity());
        ps.setString(3, hotel.getRegion());
        ps.setString(4, hotel.getAddress());
        ps.setString(5, hotel.getEmail());
        ps.setString(6, hotel.getTelephone());
        ps.setString(7, hotel.getStar());
    }
}
