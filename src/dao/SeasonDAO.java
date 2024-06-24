package dao;

import core.DB;
import core.SeasonType;
import entity.Season;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeasonDAO {
    private final Connection connection;
    private static SeasonDAO instance = null;

    private SeasonDAO() {
        this.connection = DB.getInstance();
    }

    //NULL ISE YENI NESNE OLUSTUR
    public static SeasonDAO getInstance() {
        if (instance == null) {
            instance = new SeasonDAO();
        }

        return instance;
    }

    //TUM SEZONLARI DONER VE LISTEYE EKLEME YAPAR
    public List<Season> findAll() {
        List<Season> seasons = new ArrayList<>();

        String query = "SELECT * FROM public.season";

        try {

            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                seasons.add(match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return seasons;
    }

    public List<Season> findSeasonsByHotelId(int hotelId) {
        List<Season> seasons = new ArrayList<>();

        String query = "SELECT * FROM public.season WHERE hotel_id = ?";

        try {

            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                seasons.add(match(rs));
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return seasons;
    }

    public Optional<Season> findSeasonByHotelIdAndType(int hotelId, SeasonType type) {
        String query = "SELECT * FROM public.season WHERE hotel_id = ? AND type = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelId);
            ps.setString(2, type.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(match(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    //SEZONA VERI EKLE
    public boolean create(Season season) {
        String query = "INSERT INTO public.season (hotel_id, type, start, finish) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, season);

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //SEZONDAKİ VERILERI GUNCELLE
    public boolean update(Season season) {
        String query = "UPDATE public.season SET hotel_id = ?, type = ?, start = ?, finish = ? WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, season);
            ps.setInt(5, season.getId());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    //SEZONDAN VERI SIL
    public boolean delete(int id) {
        String query = "DELETE FROM public.season WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);

            return ps.executeUpdate() != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }
    //RESULTSET TEN SEZON NESNESI OLUSTURMAK (NESNELERI SUTUNDA KARSILIK GELDIKLERI YERLERINE KOYAR)
    public Season match(ResultSet rs) throws SQLException {
        Season season = new Season();

        season.setId(rs.getInt("id"));
        season.setType(SeasonType.valueOf(rs.getString("type")));
        season.setHotelId(rs.getInt("hotel_id"));
        season.setStart(rs.getDate("start"));
        season.setFinish(rs.getDate("finish"));

        return season;
    }
    //SQL ILE ILETISIME GECER VE RESULTSET SONUCU OLUSMUS SONUCA VERILERI EKLER ((EKLE - GUNCELLE - SILME) ICIN DINAMIK METOD)
    public void match(PreparedStatement ps, Season season) throws SQLException {
        ps.setInt(1, season.getHotelId());
        ps.setString(2, season.getType().toString());
        ps.setDate(3, season.getStart());
        ps.setDate(4, season.getFinish());
    }
}
