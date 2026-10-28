
package onlineexaminationsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class TakeExam {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Student ID:");
        int studentId = sc.nextInt();

        System.out.println("Enter Exam ID:");
        int examId = sc.nextInt();

        int score = 0;
        int totalQuestions = 0;

        String query = "SELECT * FROM questions WHERE exam_id = ?";

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database connection failed!");
                sc.close();
                return;
            }

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, examId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                totalQuestions++;

                System.out.println("\nQuestion: "
                        + rs.getString("question_text"));

                System.out.println("A. " + rs.getString("option_a"));
                System.out.println("B. " + rs.getString("option_b"));
                System.out.println("C. " + rs.getString("option_c"));
                System.out.println("D. " + rs.getString("option_d"));

                System.out.println("Enter your answer (A/B/C/D):");
                String answer = sc.next().trim().toUpperCase();

                String correctAnswer =
                        rs.getString("correct_answer").trim().toUpperCase();

                if (answer.equals(correctAnswer)) {
                    score++;
                }
            }

            if (totalQuestions == 0) {
                System.out.println("No questions found for this exam!");
            } else {
                System.out.println("\nExam completed!");
                System.out.println("Your Score: " + score
                        + "/" + totalQuestions);

                String insertQuery =
                        "INSERT INTO results (student_id, exam_id, score) "
                        + "VALUES (?, ?, ?)";

                PreparedStatement insertPs =
                        con.prepareStatement(insertQuery);

                insertPs.setInt(1, studentId);
                insertPs.setInt(2, examId);
                insertPs.setInt(3, score);

                insertPs.executeUpdate();

                System.out.println("Result saved successfully!");

                insertPs.close();
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

