package di;

import features.task.datasource.TaskDAO;
import features.task.datasource.TaskDatabase;
import features.task.datasource.TaskSubscriber;
import features.task.presentation.TaskController;
import features.task.presentation.TaskControllerImpl;
import features.task.presentation.TaskView;
import features.task.presentation.TaskViewImpl;

public class ServiceLocator {

    // Instancia para o singleton
    private static ServiceLocator instance;

    public static ServiceLocator getInstance() {
        if(instance == null) {
            instance = new ServiceLocator();
        }

        return instance;
    }

    private TaskDAO taskDAO;

    private TaskDAO getTaskDao() {
        if(taskDAO == null) {
            taskDAO = new TaskDAO();
        }

        return taskDAO;
    }

    public TaskDatabase getTaskDatabase() {
        return getTaskDao();
    }

    public TaskSubscriber getTaskSubscriber() {
        return getTaskDao();
    }

    public TaskController getTaskController() {
        return new TaskControllerImpl(getTaskDatabase());
    }

    public TaskView getTaskView() {
        return new TaskViewImpl(getTaskSubscriber(), getTaskController());
    }
}
