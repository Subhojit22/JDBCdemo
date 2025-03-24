import java.sql.*;
import java.util.Scanner;

public class JDBCCrudExample {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root1234";

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database!");

            while (true) {
                System.out.println("\n--- MENU ---");
                System.out.println("1. Insert User");
                System.out.println("2. View Users");
                System.out.println("3. Update User Email");
                System.out.println("4. Delete User");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        insertUser(conn, name, email);
                        break;
                    case 2:
                        readUsers(conn);
                        break;
                    case 3:
                        System.out.print("Enter user ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new email: ");
                        String newEmail = scanner.nextLine();
                        updateUserEmail(conn, updateId, newEmail);
                        break;
                    case 4:
                        System.out.print("Enter user ID to delete: ");
                        int deleteId = scanner.nextInt();
                        deleteUser(conn, deleteId);
                        break;
                    case 5:
                        System.out.println("Exiting program...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert Operation
    public static void insertUser(Connection conn, String name, String email) throws SQLException {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("User inserted successfully!");
        }
    }

    // Read Operation
    public static void readUsers(Connection conn) throws SQLException {
        String sql = "SELECT * FROM users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                        ", Email: " + rs.getString("email"));
            }
        }
    }

    // Update Operation
    public static void updateUserEmail(Connection conn, int id, String newEmail) throws SQLException {
        String sql = "UPDATE users SET email = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("User email updated!");
        }
    }

    // Delete Operation
    public static void deleteUser(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("User deleted!");
        }
    }
}
