public class TaskController {
    private final TaskView taskView;
    private final TaskDAO taskDAO;

    public TaskController(TaskView taskView, TaskDAO taskDAO) {
        this.taskView = taskView;
        this.taskDAO = taskDAO;
    }

    void addTask(String description) {
        if(description == null || description.isEmpty()) {
            taskView.showErrorMessage("Descrição é obrigatório");
        } else {
            taskDAO.insertTask(description);
        }
    }

    void updateTask(int taskId, String description, boolean isDone) {
        if(description == null || description.isEmpty()) {
            taskView.showErrorMessage("Descrição é obrigatório");
        } else  {
            taskDAO.updateTask(taskId, description, isDone);
        }
    }

    void setDone(int taskId) {
        taskDAO.markTaskAsDone(taskId);
    }
}
