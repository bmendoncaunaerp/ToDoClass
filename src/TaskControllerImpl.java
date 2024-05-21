import java.util.List;

public class TaskControllerImpl implements TaskController {
    private final TaskView taskView;
    private final TaskDatabase taskDatabase;

    public TaskControllerImpl(TaskView taskView, TaskDAO taskDatabase) {
        this.taskView = taskView;
        this.taskDatabase = taskDatabase;
    }

    @Override
    public void addTask(String description) {
        if(description == null || description.isEmpty()) {
            taskView.showErrorMessage("Descrição é obrigatório");
        } else {
            taskDatabase.insertTask(description);
        }
    }

    @Override
    public void updateTask(int taskId, String description, boolean isDone) {
        if(description == null || description.isEmpty()) {
            taskView.showErrorMessage("Descrição é obrigatório");
        } else  {
            taskDatabase.updateTask(taskId, description, isDone);
        }
    }

    @Override
    public void setDone(int taskId) {
        taskDatabase.markTaskAsDone(taskId);
    }

    @Override
    public List<Task> getTasks() {
        return taskDatabase.getTasks();
    }
}
