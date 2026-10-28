
package movieticketbookingsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Show {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter movie ID: ");
        int movieId = sc.nextInt();

        System.out.print("Enter theater ID: ");
        int theaterId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter show time (YYYY-MM-DD HH:MM:SS): ");
        String showTime = sc.nextLine();

        System.out.print("Enter total seats: ");
        int totalSeats = sc.nextInt();

        String sql = "INSERT INTO shows " +
                "(movie_id, theater_id, show_time, total_seats, available_seats) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, movieId);
            ps.setInt(2, theaterId);
            ps.setString(3, showTime);
            ps.setInt(4, totalSeats);
            ps.setInt(5, totalSeats);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Show added successfully!");
            }

            System.out.println("\nShow List:");

            String selectSql =
                    "SELECT s.show_id, m.movie_name, t.theater_name, " +
                    "s.show_time, s.total_seats, s.available_seats " +
                    "FROM shows s " +
                    "JOIN movies m ON s.movie_id = m.movie_id " +
                    "JOIN theaters t ON s.theater_id = t.theater_id";

            PreparedStatement selectPs = con.prepareStatement(selectSql);
            ResultSet rs = selectPs.executeQuery();

            while (rs.next()) {
                System.out.println(
                    rs.getInt("show_id") + " | " +
                    rs.getString("movie_name") + " | " +
                    rs.getString("theater_name") + " | " +
                    rs.getString("show_time") + " | Available Seats: " +
                    rs.getInt("available_seats")
                );
            }

            rs.close();
            selectPs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}

