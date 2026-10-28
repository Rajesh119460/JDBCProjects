
package movieticketbookingsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Movie {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter movie name: ");
        String movieName = sc.nextLine();

        System.out.print("Enter language: ");
        String language = sc.nextLine();

        System.out.print("Enter duration in minutes: ");
        int duration = sc.nextInt();

        String insertSql =
            "INSERT INTO movies (movie_name, language, duration) VALUES (?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            PreparedStatement ps = con.prepareStatement(insertSql);

            ps.setString(1, movieName);
            ps.setString(2, language);
            ps.setInt(3, duration);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Movie added successfully!");
            }

            System.out.println("\nMovie List:");

            String selectSql = "SELECT * FROM movies";

            PreparedStatement ps2 = con.prepareStatement(selectSql);
            ResultSet rs = ps2.executeQuery();

            while (rs.next()) {
                System.out.println(
                    rs.getInt("movie_id") + " | " +
                    rs.getString("movie_name") + " | " +
                    rs.getString("language") + " | " +
                    rs.getInt("duration") + " minutes"
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

