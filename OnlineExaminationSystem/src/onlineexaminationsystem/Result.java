
package onlineexaminationsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Result {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Student ID:");
        int studentId = sc.nextInt();

        String query =
                "SELECT s.name, e.exam_name, r.score "
                + "FROM results r "
                + "JOIN students s ON r.student_id = s.student_id "
                + "JOIN exams e ON r.exam_id = e.exam_id "
                + "WHERE r.student_id = ?";

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed!");
                sc.close();
                return;
            }

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("\n----- Exam Result -----");
                System.out.println("Student Name: "
                        + rs.getString("name"));
                System.out.println("Exam Name: "
                        + rs.getString("exam_name"));
                System.out.println("Score: "
                        + rs.getInt("score"));
            }

            if (!found) {
                System.out.println("No results found for this student.");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        sc.close();
    }
}

