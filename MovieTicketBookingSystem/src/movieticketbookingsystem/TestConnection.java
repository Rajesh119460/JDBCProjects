package movieticketbookingsystem;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("Connection test successful!");
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection failed!");
        }
    }
}