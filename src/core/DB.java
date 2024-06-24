package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static DB instance = null;
    private Connection dbConnection = null;

    //SQL CONNECTION BILGILERI VE SQL BAGLANTISI
    private static final String db_url = "jdbc:postgresql://localhost:5432/tourismAgency";
    private static final String db_username = "postgres";
    private static final String db_password = "onur123";

    private DB() {
        try {
            this.dbConnection = DriverManager.getConnection(db_url, db_username, db_password);
        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    private Connection getConnection() {
        return dbConnection;
    }

    //SINGLETONIN AMACI NULL VEYA BAGLANTI YOK ISE VAROLAN CONNECTION UZERINDEN YENI CONNECTION OLUSTUR
    public static Connection getInstance() {
        try {

            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DB();
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return instance.getConnection();
    }
}
