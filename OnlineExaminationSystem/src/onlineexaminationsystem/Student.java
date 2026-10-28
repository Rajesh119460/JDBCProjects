
package onlineexaminationsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Student {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Student Name:");
        String name = sc.nextLine();

        System.out.println("Enter Student Email:");
        String email = sc.nextLine();

        System.out.println("Enter Student Password:");
        String password = sc.nextLine();

        String query = "INSERT INTO students (name, email, password) VALUES (?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            if (con != null) {
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Student added successfully!");
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

