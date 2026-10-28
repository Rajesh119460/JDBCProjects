
package onlineexaminationsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Question {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Exam ID:");
        int examId = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter Question:");
        String questionText = sc.nextLine();

        System.out.println("Enter Option A:");
        String optionA = sc.nextLine();

        System.out.println("Enter Option B:");
        String optionB = sc.nextLine();

        System.out.println("Enter Option C:");
        String optionC = sc.nextLine();

        System.out.println("Enter Option D:");
        String optionD = sc.nextLine();

        System.out.println("Enter Correct Answer (A/B/C/D):");
        String correctAnswer = sc.nextLine().trim().toUpperCase();

        if (!correctAnswer.matches("[ABCD]")) {
            System.out.println("Invalid answer! Enter A, B, C, or D.");
            sc.close();
            return;
        }

        String query = "INSERT INTO questions "
                + "(exam_id, question_text, option_a, option_b, "
                + "option_c, option_d, correct_answer) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            if (con != null) {
                PreparedStatement ps = con.prepareStatement(query);

                ps.setInt(1, examId);
                ps.setString(2, questionText);
                ps.setString(3, optionA);
                ps.setString(4, optionB);
                ps.setString(5, optionC);
                ps.setString(6, optionD);
                ps.setString(7, correctAnswer);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Question added successfully!");
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

