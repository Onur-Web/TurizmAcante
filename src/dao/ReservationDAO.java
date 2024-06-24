package dao;

import core.DB;
import entity.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationDAO {
    private final Connection connection;
    private static ReservationDAO instance = null;

    private ReservationDAO() {
        this.connection = DB.getInstance();
    }

    //NULL ISE YENI NESNE OLUSTUR
    public static ReservationDAO getInstance() {
        if (instance == null) {
            instance = new ReservationDAO();
        }

        return instance;
    }

    //TUM RESERVATIONLARI DONER VE LISTEYE EKLEME YAPAR
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();

        String query = "SELECT * FROM public.reservation";

        try {

            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                reservations.add(match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return reservations;
    }

    public Optional<Reservation> findById(int id) {
        String query = "SELECT * FROM public.reservation WHERE id = ?";

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

    //RESERVATIONA VERI EKLE
    public boolean create(Reservation reservation){
        String query = "INSERT INTO public.reservation (room_id, customer_name, customer_tc, customer_email, total_price, start, finish, number_of_adults, number_of_children) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, reservation);

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //RESERVATIONDAKI VERILERI GUNCELLE
    public boolean update(Reservation reservation) {
        String query = "UPDATE public.reservation SET room_id = ?, customer_name = ?, customer_tc = ?, customer_email = ?, total_price = ?, start = ?, finish = ?, number_of_adults = ?, number_of_children = ? WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, reservation);
            ps.setInt(10, reservation.getId());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    //RESERVATION VERI SIL
    public boolean delete(int id) {
        String query = "DELETE FROM public.reservation WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);

            return ps.executeUpdate() != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }
    //RESULTSET TEN RESERVATION NESNESI OLUSTURMAK (NESNELERI SUTUNDA KARSILIK GELDIKLERI YERLERINE KOYAR)
    public Reservation match(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();

        reservation.setId(rs.getInt("id"));
        reservation.setRoomId(rs.getInt("room_id"));
        reservation.setCustomerName(rs.getString("customer_name"));
        reservation.setCustomerTC(rs.getString("customer_tc"));
        reservation.setCustomerEmail(rs.getString("customer_email"));
        reservation.setTotalPrice(rs.getDouble("total_price"));
        reservation.setStart(rs.getDate("start"));
        reservation.setFinish(rs.getDate("finish"));
        reservation.setNumberOfAdults(rs.getInt("number_of_adults"));
        reservation.setNumberOfChildren(rs.getInt("number_of_children"));

        return reservation;
    }
    //SQL ILE ILETISIME GECER VE RESULTSET SONUCU OLUSMUS SONUCA VERILERI EKLER ((EKLE - GUNCELLE - SILME) ICIN DINAMIK METOD)
    public void match(PreparedStatement ps, Reservation reservation) throws SQLException {
        ps.setInt(1, reservation.getRoomId());
        ps.setString(2, reservation.getCustomerName());
        ps.setString(3, reservation.getCustomerTC());
        ps.setString(4, reservation.getCustomerEmail());
        ps.setDouble(5, reservation.getTotalPrice());
        ps.setDate(6, reservation.getStart());
        ps.setDate(7, reservation.getFinish());
        ps.setInt(8, reservation.getNumberOfAdults());
        ps.setInt(9, reservation.getNumberOfChildren());
    }
}
