import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "12345";

        String databaseName = "attendance_management_database";

        Connection connection = null;
        Statement statement = null;

        try {

            connection = DriverManager.getConnection(url, username, password);

            statement = connection.createStatement();

            String createDatabaseSQL = "CREATE DATABASE " + databaseName;

            statement.executeUpdate(createDatabaseSQL);

            System.out.println("Database created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the Statement and Connection
                if (statement != null) {
                    statement.close();
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
