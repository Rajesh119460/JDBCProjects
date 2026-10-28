package movieticketbookingsystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/movie_ticket_booking_db",
                "root",
                "root"
            );

            System.out.println("Database connected successfully!");
            return con;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}