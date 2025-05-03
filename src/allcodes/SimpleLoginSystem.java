package allcodes;

import java.sql.*;
import java.util.Scanner;

public class SimpleLoginSystem {

    
    static final String DB_URL = "jdbc:mysql://localhost:3306/userdb";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = ""; 


    static boolean isLoggedIn = false;
    static String currentUser = null;

    public static void main(String[] args) {
        try (
            Scanner scanner = new Scanner(System.in);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        ) {
            while (true) {
                System.out.println("\n====== MENU ======");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Logout");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1 -> register(scanner, conn);
                    case 2 -> login(scanner, conn);
                    case 3 -> logout();
                    case 4 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    static void register(Scanner scanner, Connection conn) throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // For demo purposes, no hashing
            stmt.executeUpdate();
            System.out.println(" Registration successful!");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(" Username already exists.");
        }
    }

    static void login(Scanner scanner, Connection conn) throws SQLException {
        if (isLoggedIn) {
            System.out.println(" Already logged in as: " + currentUser);
            return;
        }

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isLoggedIn = true;
                currentUser = username;
                System.out.println(" Login successful. Welcome, " + username + "!");
            } else {
                System.out.println(" Invalid credentials.");
            }
        }
    }

    static void logout() {
        if (!isLoggedIn) {
            System.out.println(" You are not logged in.");
        } else {
            System.out.println("Logged out: " + currentUser);
            isLoggedIn = false;
            currentUser = null;
        }
    }
}
