import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Havoc on 0018-18-12-2016.
 */
public class ServerThread extends Thread {
    private Socket socket;
    private Connection connection;
    private final static String SEPARATOR = "-";

    public ServerThread(Socket socket, Connection connection) {
        this.socket = socket;
        this.connection = connection;
    }

    @Override
    public void run() {
        super.run();

        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine())!= null){
                if(line.contains("-")){
                    Statement statement = connection.createStatement();
                    String[] message = line.split(SEPARATOR);
                    System.out.println("Time " + message[0]);
                    long time = Long.parseLong(message[0]);
                    float db = Float.parseFloat(message[3]);
                    if(!message[1].equals("UNKNOWN")){
                        float latitude = Float.parseFloat(message[1]);
                        float longitude = Float.parseFloat(message[2]);
                        statement.executeUpdate("INSERT INTO elim.data (Time, Longitude, Latitude, dB) VALUES (" + time + ", " + longitude + ", " + latitude + ", " + db + ")");
                        System.out.println("Latitude " + message[1]);
                        System.out.println("Longitude " + message[2]);
                    }else{
                        statement.executeUpdate("INSERT INTO elim.data (Time, Longitude, Latitude, dB) VALUES (" + time + ", 0, 0, " + db + ")");
                    }
                    System.out.println("Decibels " + message[3]);
                }else{
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
//            System.out.println("Something went wrong while updating the database.");
        }
    }
}
