import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoListDatabase {
    private final List<Task> tasks = new ArrayList<>();
    private int lastId = 0;

    public void insertTask(String description) {
        try (Connection connection = DatabaseManager.openDatabaseConnection();
             Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO tasks (description) VALUES " + description;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        tasks.add(new Task(++lastId, description, false));
    }

    public void editTask(int taskId, String description, boolean isDone) {
        for (Task task : tasks) {
            if (task.id == taskId) {
                task.description = description;
                task.isDone = isDone;
            }
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void markTaskAsDone(int taskId) {
        for (Task task : tasks) {
            if (task.id == taskId) {
                task.isDone = true;
            }
        }
    }
}
