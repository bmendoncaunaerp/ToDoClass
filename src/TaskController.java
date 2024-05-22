import java.util.List;

public interface TaskController {
    void setView(TaskView view);
    void addTask(String description);
    void updateTask(int taskId, String description, boolean isDone);
    void setDone(int taskId);
    List<Task> getTasks();
}
