package allcodes;

import java.sql.*;
import java.util.Scanner;

public class BookInventory{

    private static final String DB_URL = "jdbc:mysql://localhost:3306/books";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; 

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private static void addBook(Scanner scanner) {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO bookdb (id,title, author, price) VALUES (?,?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            System.out.println("Enter id: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter title: ");
            String title = scanner.nextLine();

            System.out.print("Enter author: ");
            String author = scanner.nextLine();

            System.out.print("Enter price: ");
            double price = Double.parseDouble(scanner.nextLine());

            pstmt.setInt(1,id);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setDouble(4, price);

            pstmt.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteBook(Scanner scanner) {
        try (Connection conn = connect()) {
            String sql = "DELETE FROM bookdb WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            System.out.print("Enter book ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully!");
            } else {
                System.out.println("Book not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewBooks() {
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM bookdb";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Book List:");
            System.out.println("ID | Title | Author | Price");

            while (rs.next()) {
                System.out.printf("%d | %s | %s | %.2f%n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Book Inventory Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. View Books");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addBook(scanner);
                case 2 -> deleteBook(scanner);
                case 3 -> viewBooks();
                case 4 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option. Try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
