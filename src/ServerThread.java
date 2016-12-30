
import com.eslamhossam23bichoymessiha.projetelim.DataOfLastDay;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Havoc on 0018-18-12-2016.
 */
public class ServerThread extends Thread {

    private final Socket socket;
    private final Dao dao;
    private final static String SEPARATOR = "-";

    public ServerThread(Socket socket, Dao dao) {
        this.socket = socket;
        this.dao = dao;
    }

    @Override
    public void run() {
        super.run();
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("-")) {
                    String[] message = line.split(SEPARATOR);
                    System.out.println("Time " + message[0]);
                    long time = Long.parseLong(message[0]);
                    float db = Float.parseFloat(message[3]);
                    if (!message[1].equals("UNKNOWN")) {
                        float latitude = Float.parseFloat(message[1]);
                        float longitude = Float.parseFloat(message[2]);
                        dao.insertData(time, longitude, latitude, db);
                        System.out.println("Latitude " + message[1]);
                        System.out.println("Longitude " + message[2]);
                    } else {
                        dao.insertData(time, 0, 0, db);
                    }
                    System.out.println("Decibels " + message[3]);
                } else if (line.equals("send me data please")) {
                    DataOfLastDay dataOfLastDay = new DataOfLastDay();
                    filterData(dataOfLastDay, dao.getLastDayData());
                    dataOfLastDay.setMaximumdB(dao.getmaximumdB());
                    dataOfLastDay.setMinimumdB(dao.getminimumdB());

                    sendObjectToClient(dataOfLastDay);
                    System.out.println(dataOfLastDay);
//                    System.out.println("data sent to client");
                } else {
                    System.out.println(line);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
//            System.out.println("Something went wrong while updating the database.");
        
    }

    public void filterData(DataOfLastDay dataOfLastDay,ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            dataOfLastDay.getChartTimedB().put(resultSet.getLong("Time"), resultSet.getFloat("dB"));
            String[] locationdB = {resultSet.getString("Longitude"), resultSet.getString("Latitude"), resultSet.getString("dB")};
            dataOfLastDay.getMapLocationdB().add(locationdB);
        }
    }
    
    public void sendObjectToClient(Object object) throws IOException {
        ObjectOutputStream serverOutputStream = new ObjectOutputStream(socket.getOutputStream());
        serverOutputStream.writeObject((DataOfLastDay) object);
    }
    
}
