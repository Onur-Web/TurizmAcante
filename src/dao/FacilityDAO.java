package dao;

import core.DB;
import core.FacilityType;
import entity.Facility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FacilityDAO {
    private final Connection connection;
    private static FacilityDAO instance = null;

    private FacilityDAO() {
        this.connection = DB.getInstance();
    }

    //NULL ISE YENI NESNE OLUSTUR
    public static FacilityDAO getInstance() {
        if (instance == null) {
            instance = new FacilityDAO();
        }

        return instance;
    }

    //TUM FACILITYLARI DONER VE LISTEYE EKLEME YAPAR
    public List<Facility> findAll() {
        List<Facility> facilities = new ArrayList<>();

        String query = "SELECT * FROM public.facility";

        try {

            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                facilities.add(match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return facilities;
    }

    public List<Facility> findFacilitiesByHotelId(int hotelId) {
        List<Facility> facilities = new ArrayList<>();

        String query = "SELECT * FROM public.facility WHERE hotel_id = ?";

        try {

            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                facilities.add(match(rs));
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return facilities;
    }

    public Optional<Facility> findFacilityByHotelIdAndType(int hotelId, FacilityType type) {
        String query = "SELECT * FROM public.facility WHERE hotel_id = ? AND type = ?";

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

    //FACILITYLERE VERI EKLE
    public boolean create(Facility facility) {
        String query = "INSERT INTO public.facility (hotel_id, type) VALUES (?, ?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, facility);

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //FACILITYDEKI VERILERI GUNCELLE
    public boolean update(Facility facility) {
        String query = "UPDATE public.facility SET hotel_id = ?, type = ? WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            match(ps, facility);
            ps.setInt(3, facility.getId());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    //FACILITYDEN VERI SIL
    public boolean delete(int id) {
        String query = "DELETE FROM public.facility WHERE id = ?";

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
    public Facility match(ResultSet rs) throws SQLException {
        Facility facility = new Facility();

        facility.setId(rs.getInt("id"));
        facility.setHotelId(rs.getInt("hotel_id"));
        facility.setType(FacilityType.valueOf(rs.getString("type")));

        return facility;
    }

    public void match(PreparedStatement ps, Facility facility) throws SQLException {
        ps.setInt(1, facility.getHotelId());
        ps.setString(2, facility.getType().toString());
    }
}
