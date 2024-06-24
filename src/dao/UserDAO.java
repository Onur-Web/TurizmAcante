package dao;

import core.DB;
import core.UserRole;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final Connection connection;
    private static UserDAO instance = null;

    private UserDAO() {
        this.connection = DB.getInstance();
    }

    //NULL ISE YENI NESNE OLUSTUR
    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }

        return instance;
    }

    //TUM KULLANICILARI DONER VE LISTEYE EKLEME YAPAR
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM public.user";

        try {

            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                users.add(match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return users;
    }

    //USERROLE ILE ESLESEN TUM KULLANICILARI DONER
    public List<User> findUsersByUserRole(UserRole userRole) {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM public.user WHERE user_role = ? ORDER BY id ASC";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, userRole.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(match(rs));
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    //GIRILEN USERNAME VE PASSWORD A GORE BIR KULLANICI VARSA MATCHDEN NESNEYE DONUSTURULUR
    public User findUserByUsernameAndPassword(String username, String password) {
        User user = null;
        String query = "SELECT * FROM public.user WHERE username = ? AND password = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = match(rs);
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    //USERA VERI EKLE
    public boolean create(User user) {
        String query = "INSERT INTO public.user (name, username, password, user_role) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUserRole().toString());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //USERDAKI VERILERI GUNCELLE
    public boolean update(User user) {
        String query = "UPDATE public.user SET name = ?, username = ?, password = ?, user_role = ? WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUserRole().toString());
            ps.setInt(5, user.getId());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    //USERDAN VERI SIL
    public boolean delete(int id) {
        String query = "DELETE FROM public.user WHERE id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);

            return ps.executeUpdate() != -1;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }
    //RESULTSET TEN USER NESNESI OLUSTURMAK (NESNELERI SUTUNDA KARSILIK GELDIKLERI YERLERINE KOYAR)
    public User match(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setUserRole(UserRole.valueOf(rs.getString("user_role")));

        return user;
    }
}
