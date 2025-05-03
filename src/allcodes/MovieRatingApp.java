package allcodes;

import java.sql.*;
import java.util.Scanner;

public class MovieRatingApp {
 
    private static final String DB_URL = "jdbc:mysql://localhost:3306/moviedb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; 

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n1. Add Movie");
                System.out.println("2. Rate Movie");
                System.out.println("3. Show Top 3 Rated Movies");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1 -> addMovie(conn, scanner);
                    case 2 -> rateMovie(conn, scanner);
                    case 3 -> showTopMovies(conn);
                    case 4 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }
    }

    private static void addMovie(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();

        String sql = "INSERT INTO movies (title) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.executeUpdate();
            System.out.println("Movie added successfully.");
        }
    }

    private static void rateMovie(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter movie ID to rate: ");
        int movieId = scanner.nextInt();
        System.out.print("Enter rating (1 to 10): ");
        int rating = scanner.nextInt();
        scanner.nextLine(); 

        if (rating < 1 || rating > 10) {
            System.out.println("Invalid rating. Must be between 1 and 10.");
            return;
        }

        String sql = "INSERT INTO ratings (movie_id, rating) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            stmt.setInt(2, rating);
            stmt.executeUpdate();
            System.out.println("Rating submitted.");
        }
    }

    private static void showTopMovies(Connection conn) throws SQLException {
        String sql = """
            SELECT m.id, m.title, AVG(r.rating) AS avg_rating
            FROM movies m
            JOIN ratings r ON m.id = r.movie_id
            GROUP BY m.id, m.title
            ORDER BY avg_rating DESC
            LIMIT 3
            """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nTop 3 Rated Movies:");
            while (rs.next()) {
                System.out.printf("ID: %d | Title: %s | Average Rating: %.2f\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDouble("avg_rating"));
            }
        }
    }
}
