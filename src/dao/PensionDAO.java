package dao;

import core.DB;
import core.PensionType;
import core.PensionType;
import entity.Pension;
import entity.Pension;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PensionDAO {
    private final Connection connection;
    private static PensionDAO instance = null;

    private PensionDAO() {
        this.connection = DB.getInstance();
    }

    //NULL ISE YENI NESNE OLUSTUR
    public static PensionDAO getInstance() {
        if (instance == null) {
            instance = new PensionDAO();
        }

        return instance;
    }

    //TUM PENSIONLARI DONER VE LISTEYE EKLEME YAPAR
    public List<Pension> findAll() {
        List<Pension> pensions = new ArrayList<>();

        String query = "SELECT * FROM public.pension";

        try {

            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                pensions.add(match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return pensions;
    }

    public List<Pension> findPensionsByHotelId(int hotelId) {
        List<Pension> pensions = new ArrayList<>();

        String query = "SELECT * FROM public.pension WHERE hotel_id = ?";

        try {

            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pensions.add(match(rs));
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return pensions;
    }

    public Optional<Pension> findPensionByHotelIdAndType(int hotelId, PensionType type) {
        String query = "SELECT * FROM public.pension WHERE hotel_id = ? AND type = ?";

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

    //PENSIONLARA VERI EKLE
    public boolean create(Pension pension) {
        String query = "INSERT INTO public.pension (hotel_id, type) VALUES (?, ?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, pension);

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //PENSIONDAKI VERILERI GUNCELLE
    public boolean update(Pension pension) {
        String query = "UPDATE public.pension SET hotel_id = ?, type = ? WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, pension);
            ps.setInt(3, pension.getId());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    //PENSIONDAN VERI SIL
    public boolean delete(int id) {
        String query = "DELETE FROM public.pension WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);

            return ps.executeUpdate() != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }
    //RESULTSET TEN PENSION NESNESI OLUSTURMAK (NESNELERI SUTUNDA KARSILIK GELDIKLERI YERLERINE KOYAR)
    public Pension match(ResultSet rs) throws SQLException {
        Pension pension = new Pension();

        pension.setId(rs.getInt("id"));
        pension.setHotelId(rs.getInt("hotel_id"));
        pension.setType(PensionType.valueOf(rs.getString("type")));

        return pension;
    }
    //SQL ILE ILETISIME GECER VE RESULTSET SONUCU OLUSMUS SONUCA VERILERI EKLER ((EKLE - GUNCELLE - SILME) ICIN DINAMIK METOD)
    public void match(PreparedStatement ps, Pension pension) throws SQLException {
        ps.setInt(1, pension.getHotelId());
        ps.setString(2, pension.getType().toString());
    }
}
