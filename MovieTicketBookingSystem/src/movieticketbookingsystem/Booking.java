
package movieticketbookingsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Booking {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter show ID: ");
        int showId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter customer name: ");
        String customerName = sc.nextLine();

        System.out.print("Enter seat number: ");
        String seatNumber = sc.nextLine();

        System.out.print("Enter ticket price: ");
        double ticketPrice = sc.nextDouble();

        Connection con = null;

        try {
            con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            con.setAutoCommit(false);

            String seatSql =
                "SELECT available_seats FROM shows " +
                "WHERE show_id = ? FOR UPDATE";

            PreparedStatement seatPs = con.prepareStatement(seatSql);
            seatPs.setInt(1, showId);

            ResultSet seatRs = seatPs.executeQuery();

            if (!seatRs.next()) {
                System.out.println("Show not found!");
                con.rollback();
                return;
            }

            int availableSeats = seatRs.getInt("available_seats");

            if (availableSeats <= 0) {
                System.out.println("No seats available!");
                con.rollback();
                return;
            }

            String checkSql =
                "SELECT booking_id FROM bookings " +
                "WHERE show_id = ? AND seat_number = ?";

            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, showId);
            checkPs.setString(2, seatNumber);

            ResultSet checkRs = checkPs.executeQuery();

            if (checkRs.next()) {
                System.out.println("Seat " + seatNumber +
                                   " is already booked!");
                con.rollback();
                return;
            }

            String bookingSql =
                "INSERT INTO bookings " +
                "(show_id, customer_name, seat_number, ticket_price) " +
                "VALUES (?, ?, ?, ?)";

            PreparedStatement bookingPs =
                con.prepareStatement(bookingSql);

            bookingPs.setInt(1, showId);
            bookingPs.setString(2, customerName);
            bookingPs.setString(3, seatNumber);
            bookingPs.setDouble(4, ticketPrice);

            bookingPs.executeUpdate();

            String updateSql =
                "UPDATE shows SET available_seats = available_seats - 1 " +
                "WHERE show_id = ?";

            PreparedStatement updatePs = con.prepareStatement(updateSql);
            updatePs.setInt(1, showId);
            updatePs.executeUpdate();

            con.commit();

            System.out.println("Booking successful!");
            System.out.println("Customer: " + customerName);
            System.out.println("Seat: " + seatNumber);
            System.out.println("Ticket Price: ₹" + ticketPrice);

            checkRs.close();
            checkPs.close();
            seatRs.close();
            seatPs.close();
            bookingPs.close();
            updatePs.close();
            con.close();

        } catch (Exception e) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }

            e.printStackTrace();

        } finally {
            sc.close();
        }
    }
}
