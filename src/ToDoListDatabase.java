import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoListDatabase {
    public void insertTask(String description) {
        String sql = "INSERT INTO tasks (description) VALUES (?);";

        try (Connection conn = DatabaseManager.openDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.executeUpdate();
            System.out.println("Task inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting task: " + e.getMessage());
        }
    }

    public void editTask(int taskId, String description, boolean isDone) {
        String sql = "UPDATE tasks SET description = ?, is_done = ? WHERE id = ?;";

        try (Connection conn = DatabaseManager.openDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setInt(2, isDone ? 1 : 0);
            pstmt.setInt(3, taskId);
            pstmt.executeUpdate();
            System.out.println("Task edited successfully.");
        } catch (SQLException e) {
            System.out.println("Error editing task: " + e.getMessage());
        }
    }

    public List<Task> getTasks() {
        String sql = "SELECT * FROM tasks WHERE is_done = 0;";
        List<Task> result = new ArrayList<>();

        try (Connection conn = DatabaseManager.openDatabaseConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            // Clear existing rows from the table

            // Populate the table with tasks from the database
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                boolean isDone = resultSet.getBoolean("is_done");
                result.add(new Task(id, description, isDone));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void markTaskAsDone(int taskId) {
        String sql = "UPDATE tasks SET is_done = ? WHERE id = ?;";

        try (Connection conn = DatabaseManager.openDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            pstmt.setInt(2, taskId);
            pstmt.executeUpdate();
            System.out.println("Task edited successfully.");
        } catch (SQLException e) {
            System.out.println("Error editing task: " + e.getMessage());
        }
    }
}
