package allcodes;

import java.util.Scanner;
import java.util.TooManyListenersException;

public class LoginAttempt {
    private static final String USERNAME = "Aman";
    private static final String PASSWORD = "Aman@5285";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int attemptCount = 0;

        while (attemptCount < 3) {
            System.out.print("Enter username: ");
            String inputUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String inputPassword = scanner.nextLine();

            if (authenticate(inputUsername, inputPassword)) {
                System.out.println("Login successful!");
                return;
            } else {
                attemptCount++;
                System.out.println("Invalid credentials. Attempts left: " + (3 - attemptCount));
            }
        }

        try {
            throw new Exception("Too many failed login attempts. Access denied.");
        } catch (TooManyListenersException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static boolean authenticate(String username, String password) {
        return USERNAME.equals(username) && PASSWORD.equals(password);
    }
}
