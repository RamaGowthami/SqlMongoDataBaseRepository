package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/emp4"; // Replace with your DB name
    private static final String USER = "root";  // Replace with your DB username
    private static final String PASSWORD = "Rama@Mysql2024";  // Replace with your DB password

    // SQL query for inserting data into the Employee table
    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO Employee (empcode, empname, empage, esalary) VALUES (?, ?, ?, ?)";

    // SQL query for updating employee details
    private static final String UPDATE_EMPLOYEE_SQL = "UPDATE Employee SET empname = ?, empage = ?, esalary = ? WHERE empcode = ?";

    public static void main(String[] args) {
        // Insert new employees
        insertEmployees();

        // Update an existing employee
        updateEmployee(101, "Jenny", 25, 10000.00f);
    }

    public static void insertEmployees() {
        Object[][] employees = {
                {102, "Jacky", 30, 20000.00f},
                {103, "Joe", 20, 40000.00f},
                {104, "John", 40, 80000.00f},
                {105, "Shameer", 25, 90000.00f}
        };

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to the database successfully!");

            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL)) {
                for (Object[] emp : employees) {
                    preparedStatement.setInt(1, (int) emp[0]);  // empcode
                    preparedStatement.setString(2, (String) emp[1]); // empname
                    preparedStatement.setInt(3, (int) emp[2]); // empage
                    preparedStatement.setFloat(4, (float) emp[3]); // esalary

                    preparedStatement.executeUpdate(); // Execute insert operation
                }
                System.out.println("All employees inserted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error executing the insert query: " + e.getMessage());
        }
    }

    public static void updateEmployee(int empcode, String empname, int empage, float esalary) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to the database successfully!");

            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE_SQL)) {
                preparedStatement.setString(1, empname);
                preparedStatement.setInt(2, empage);
                preparedStatement.setFloat(3, esalary);
                preparedStatement.setInt(4, empcode);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Employee details updated successfully!");
                } else {
                    System.out.println("No employee found with empcode: " + empcode);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing the update query: " + e.getMessage());
        }
    }
}
