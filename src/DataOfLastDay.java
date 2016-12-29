
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bichoymessiha
 */
public class DataOfLastDay implements Serializable{

    private final long MILLISINDAY = 86400000;
    private Connection connection;

    private HashMap<Long, Float> chartTimedB = new HashMap<>();
    private ArrayList<String[]> mapLocationdB = new ArrayList<>();
    private float minimumdB;
    private float maximumdB;
    
    public DataOfLastDay(Connection connection) throws SQLException {
        this.connection = connection;
        filterData(getLastDayData());
    }

    public ResultSet getLastDayData() throws SQLException {
        Statement statement = connection.createStatement();
        
        // Minimum dB of the day
        minimumdB = statement.executeQuery("SELECT min(dB) FROM elim.data;").getFloat("dB");
        // Maximum dB of the day
        maximumdB = statement.executeQuery("SELECT max(dB) FROM elim.data;").getFloat("dB");
        
        LocalDate localDate = LocalDate.now();
        ZoneId zoneId = ZoneId.systemDefault();
        long epochOfTodayMillis = localDate.atStartOfDay(zoneId).toEpochSecond() * 1000;

        return statement.executeQuery("SELECT * FROM elim.data where Time >" + epochOfTodayMillis
                + "and Time < " + epochOfTodayMillis + MILLISINDAY);
    }

    public void filterData(ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            chartTimedB.put(resultSet.getLong("Time"), resultSet.getFloat("dB"));
            String[] locationdB = {resultSet.getString("Longitude"),resultSet.getString("Latitude"),resultSet.getString("dB")};
            mapLocationdB.add(locationdB);
        }
    }

    
    public HashMap<Long, Float> getChartTimedB() {
        return chartTimedB;
    }
    
    
}
