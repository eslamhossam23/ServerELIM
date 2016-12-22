import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/ELIM";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Couldn't initialise database class.");
        }
        try {
            ServerSocket serverSocket = new ServerSocket(3000);
            while (true){
                Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                Socket socket = serverSocket.accept();
                Thread thread = new ServerThread(socket, connection);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with the server/client sockets.");
        } catch (SQLException e) {
            System.out.println("Couldn't establish connection with database.");
        }

    }
}
