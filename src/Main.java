import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static Dao dao;
    
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Couldn't initialise database class.");
        }
        dao = new Dao();
        try {
            ServerSocket serverSocket = new ServerSocket(3000);
            while (true){
                Socket socket = serverSocket.accept();
                Thread thread = new ServerThread(socket, dao);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with the server/client sockets.");
        }
    }
}
