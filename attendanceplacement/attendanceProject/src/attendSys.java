import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class attendSys {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/attendance_management_database";
        String username = "root";
        String password = "12345";
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Student ID:");
        int studentId = sc.nextInt();
        // System.out.println();
        System.out.print("Enter the Student name:");
        String name = sc.next();
        // Date date = new Date();
        // System.out.println();

        System.out.print("Enter the Status(Present/Absent):");
        String attendanceStatus = sc.next();
        // System.out.println();
        String date = "2023-08-13";
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = DriverManager.getConnection(url, username, password);

            String sql = "INSERT INTO attendance (studentid,studentname, Atdate, statusem) VALUES (?, ?, ?,?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, date);
            preparedStatement.setString(4, attendanceStatus);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Attendance recorded successfully.");
            } else {
                System.out.println("Failed to record attendance.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
