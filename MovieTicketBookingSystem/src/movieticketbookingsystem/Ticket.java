
package movieticketbookingsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Ticket {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Booking ID: ");
        int bookingId = sc.nextInt();

        String sql =
            "SELECT b.booking_id, b.customer_name, " +
            "b.seat_number, b.ticket_price, " +
            "m.movie_name, t.theater_name, s.show_time " +
            "FROM bookings b " +
            "JOIN shows s ON b.show_id = s.show_id " +
            "JOIN movies m ON s.movie_id = m.movie_id " +
            "JOIN theaters t ON s.theater_id = t.theater_id " +
            "WHERE b.booking_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    System.out.println("\n===== MOVIE TICKET =====");
                    System.out.println("Booking ID : " +
                            rs.getInt("booking_id"));
                    System.out.println("Customer   : " +
                            rs.getString("customer_name"));
                    System.out.println("Movie      : " +
                            rs.getString("movie_name"));
                    System.out.println("Theater    : " +
                            rs.getString("theater_name"));
                    System.out.println("Show Time  : " +
                            rs.getTimestamp("show_time"));
                    System.out.println("Seat       : " +
                            rs.getString("seat_number"));
                    System.out.println("Price      : Rs. " +
                            rs.getBigDecimal("ticket_price"));
                    System.out.println("========================");
                    System.out.println("Ticket generated successfully!");

                } else {
                    System.out.println("Booking not found!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
