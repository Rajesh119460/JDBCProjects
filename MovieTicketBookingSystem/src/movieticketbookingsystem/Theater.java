
package movieticketbookingsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Theater {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter theater name: ");
        String theaterName = sc.nextLine();

        System.out.print("Enter location: ");
        String location = sc.nextLine();

        String insertSql =
            "INSERT INTO theaters (theater_name, location) VALUES (?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            PreparedStatement ps = con.prepareStatement(insertSql);

            ps.setString(1, theaterName);
            ps.setString(2, location);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Theater added successfully!");
            }

            System.out.println("\nTheater List:");

            String selectSql = "SELECT * FROM theaters";

            PreparedStatement ps2 = con.prepareStatement(selectSql);
            ResultSet rs = ps2.executeQuery();

            while (rs.next()) {
                System.out.println(
                    rs.getInt("theater_id") + " | " +
                    rs.getString("theater_name") + " | " +
                    rs.getString("location")
                );
            }

            rs.close();
            ps2.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}

