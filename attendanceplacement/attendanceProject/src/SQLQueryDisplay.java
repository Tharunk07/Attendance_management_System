import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class SQLQueryDisplay extends JFrame {
    private JTextField studentNameField;
    private JTextField studentIdField;
    private JTable resultTable;

    public SQLQueryDisplay() {
        setTitle("SQL Query Display");

        JLabel nameLabel = new JLabel("Student Name:");
        studentNameField = new JTextField(20);
        JLabel idLabel = new JLabel("Student ID:");
        studentIdField = new JTextField(20);
        JButton fetchButton = new JButton("Fetch Attendance");
        resultTable = new JTable();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(nameLabel);
        inputPanel.add(studentNameField);
        inputPanel.add(idLabel);
        inputPanel.add(studentIdField);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(fetchButton, BorderLayout.CENTER);
        panel.add(new JScrollPane(resultTable), BorderLayout.SOUTH);

        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String studentName = studentNameField.getText();
                String studentId = studentIdField.getText();

                fetchAttendanceDetails(studentName, studentId);
            }
        });

        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchAttendanceDetails(String studentName, String studentId) {

        String url = "jdbc:mysql://localhost:3306/attendance_management_database";
        String username = "root";
        String password = "12345";

        try {

            Connection connection = DriverManager.getConnection(url, username, password);

            String sqlQuery = "SELECT * FROM attendance WHERE studentname = ? AND studentid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, studentName);
            preparedStatement.setString(2, studentId);

            ResultSet resultSet = preparedStatement.executeQuery();

            java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnNames);

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                model.addRow(rowData);
            }

            resultTable.setModel(model);

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {

            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SQLQueryDisplay();
            }
        });
    }
}
