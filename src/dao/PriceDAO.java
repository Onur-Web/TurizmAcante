package dao;

import core.DB;
import entity.Price;
import entity.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PriceDAO {
    private final Connection connection;
    private static PriceDAO instance = null;

    private PriceDAO() {
        this.connection = DB.getInstance();
    }

    //NULL ISE YENI NESNE OLUSTUR
    public static PriceDAO getInstance() {
        if (instance == null) {
            instance = new PriceDAO();
        }

        return instance;
    }

    //TUM PRICELARI DONER VE LISTEYE EKLEME YAPAR
    public List<Price> findAll() {
        List<Price> prices = new ArrayList<>();

        String query = "SELECT * FROM public.price";

        try {

            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                prices.add(match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return prices;
    }

    public Optional<Price> findByRoomIdAndPensionIdAndDates(int roomId, int pensionId, Date start, Date finish) {
        String query = "SELECT price.* FROM public.price INNER JOIN public.season ON price.season_id = season.id WHERE room_id = ? AND pension_id = ? AND season.start < ? AND ? < season.finish";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, roomId);
            ps.setInt(2, pensionId);
            ps.setDate(3, start);
            ps.setDate(4, finish);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(match(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    //PRICEA VERI EKLE
    public boolean create(Price price) {
        String query = "INSERT INTO public.price (hotel_id, pension_id, room_id, season_id, adult_price, child_price) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, price);

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //PRICEDAKI VERILERI GUNCELLE
    public boolean update(Price price) {
        String query = "UPDATE public.price SET hotel_id = ?, pension_id = ?, room_id = ?, season_id = ?, adult_price = ?, child_price = ? WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, price);
            ps.setInt(7, price.getId());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    //PRICEDAN VERI SIL
    public boolean delete(int id) {
        String query = "DELETE FROM public.price WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);

            return ps.executeUpdate() != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }
    //RESULTSET TEN PRICE NESNESI OLUSTURMAK (NESNELERI SUTUNDA KARSILIK GELDIKLERI YERLERINE KOYAR)
    public Price match(ResultSet rs) throws SQLException {
        Price price = new Price();

        price.setId(rs.getInt("id"));
        price.setHotelId(rs.getInt("hotel_id"));
        price.setPensionId(rs.getInt("pension_id"));
        price.setRoomId(rs.getInt("room_id"));
        price.setSeasonId(rs.getInt("season_id"));
        price.setAdultPrice(rs.getDouble("adult_price"));
        price.setChildPrice(rs.getDouble("child_price"));

        return price;
    }
    //SQL ILE ILETISIME GECER VE RESULTSET SONUCU OLUSMUS SONUCA VERILERI EKLER ((EKLE - GUNCELLE - SILME) ICIN DINAMIK METOD)
    public void match(PreparedStatement ps, Price price) throws SQLException {
        ps.setInt(1, price.getHotelId());
        ps.setInt(2, price.getPensionId());
        ps.setInt(3, price.getRoomId());
        ps.setInt(4, price.getSeasonId());
        ps.setDouble(5, price.getAdultPrice());
        ps.setDouble(6, price.getChildPrice());
    }
}
