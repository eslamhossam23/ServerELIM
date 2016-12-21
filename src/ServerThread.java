import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Havoc on 0018-18-12-2016.
 */
public class ServerThread extends Thread {
    private Socket socket;
    private final static String SEPARATOR = "-";

    public ServerThread(Socket socket) {
        this.socket = socket;
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
                    String[] message = line.split(SEPARATOR);
                    System.out.println("Time " + message[0]);
                    System.out.println("Latitude " + message[1]);
                    System.out.println("Longitude " + message[2]);
                    System.out.println("Decibels " + message[3]);
                }else{
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
