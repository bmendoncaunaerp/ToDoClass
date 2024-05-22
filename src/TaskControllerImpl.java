import java.util.List;

public class TaskControllerImpl implements TaskController {
    private TaskView taskView;
    private final TaskDatabase taskDatabase;

    public TaskControllerImpl(TaskDatabase taskDatabase) {
        this.taskDatabase = taskDatabase;
    }

    @Override
    public void setView(TaskView view) {
        this.taskView = view;
    }

    @Override
    public void addTask(String description) {
        if(description == null || description.isEmpty()) {
            showErrorMessage("Descrição é obrigatório");
        } else {
            taskDatabase.insertTask(description);
        }
    }

    @Override
    public void updateTask(int taskId, String description, boolean isDone) {
        if(description == null || description.isEmpty()) {
            showErrorMessage("Descrição é obrigatório");
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

    private void showErrorMessage(String msg) {
        if(taskView != null) {
            taskView.showErrorMessage(msg);
        }
    }
}
