import java.util.List;

public interface TaskDatabase {
    void insertTask(String description);
    void updateTask(int taskId, String description, boolean isDone);
    List<Task> getTasks();
    void markTaskAsDone(int taskId);
}
