
package onlineexaminationsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Exam {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Exam Name:");
        String examName = sc.nextLine();

        System.out.println("Enter Exam Duration in Minutes:");
        int duration = sc.nextInt();

        String query = "INSERT INTO exams (exam_name, duration) VALUES (?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            if (con != null) {
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, examName);
                ps.setInt(2, duration);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Exam added successfully!");
                }

                ps.close();
                con.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        sc.close();
    }
}

