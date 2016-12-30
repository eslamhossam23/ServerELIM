
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bichoymessiha
 */
public class Dao {

    public static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/ELIM";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";

    private final long MILLISINDAY = 86400000;
    Connection connection;
    Statement statement;
    long epochOfTodayMillis;

    public Dao() {
        try {
            this.connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            this.statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        LocalDate localDate = LocalDate.now();
        ZoneId zoneId = ZoneId.systemDefault();
        epochOfTodayMillis = localDate.atStartOfDay(zoneId).toEpochSecond() * 1000;
    }

    public ResultSet getLastDayData() throws SQLException {
        return statement.executeQuery("SELECT * FROM elim.data where Time >" + epochOfTodayMillis
                + " and Time <" + (epochOfTodayMillis + MILLISINDAY) + ";");
    }

    public float getminimumdB() throws SQLException {
        ResultSet result = statement.executeQuery("SELECT min(dB) as dB FROM elim.data where Time >" + epochOfTodayMillis
                + " and Time <" + (epochOfTodayMillis + MILLISINDAY) + ";");
        if (result.next()) {
            return result.getFloat("dB");
        }
        return -1;
    }

    public float getmaximumdB() throws SQLException {
        ResultSet result = statement.executeQuery("SELECT max(dB) as dB FROM elim.data where Time >" + epochOfTodayMillis
                + " and Time <" + (epochOfTodayMillis + MILLISINDAY) + ";");
        if (result.next()) {
            return result.getFloat("dB");
        }
        return -1;
    }

    public void insertData(long time, float longitude, float latitude, float dB) throws SQLException {
        statement.executeUpdate("INSERT INTO elim.data (Time, Longitude, Latitude, dB) VALUES ("
                + time + ", " + longitude + ", " + latitude + ", " + dB + ")");
    }
}
