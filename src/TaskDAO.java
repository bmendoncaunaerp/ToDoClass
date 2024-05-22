import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements TaskDatabase, TaskSubscriber {
    private final List<TaskListener> listeners;

    public TaskDAO() {
        this(new ArrayList<>());
    }

    public TaskDAO(List<TaskListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public void subscribe(TaskListener taskListener) {
        listeners.add(taskListener);
    }

    private void notifyDataChanged() {
        for(TaskListener listener : listeners) {
            listener.updateData();
        }
    }

    @Override
    public void insertTask(String description) {
        try {
            DatabaseManager.getDatabaseSessionFactory().inTransaction(session -> {
                var task = new Task(description, false);
                session.persist(task);
            });
            System.out.println("Task inserted successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            System.out.println("Error inserting task: " + e.getMessage());
        }
    }

    @Override
    public void updateTask(int taskId, String description, boolean isDone) {
        try {
            DatabaseManager.getDatabaseSessionFactory().inTransaction(session -> {
                var task = session.get(Task.class, taskId);
                task.setDescription(description);
                task.setDone(isDone);
                session.persist(task);
            });
            System.out.println("Task edited successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            System.out.println("Error editing task: " + e.getMessage());
        }
    }

    @Override
    public List<Task> getTasks() {
        List<Task> result = new ArrayList<>();
        try {
            result = DatabaseManager.getDatabaseSessionFactory().fromTransaction(session -> {
                return session.createSelectionQuery("from Task where isDone = false", Task.class)
                        .getResultList();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void markTaskAsDone(int taskId) {
        try {
            DatabaseManager.getDatabaseSessionFactory().inTransaction(session -> {
                var task = session.get(Task.class, taskId);
                task.setDone(true);
                session.persist(task);
            });
            System.out.println("Task edited successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            System.out.println("Error editing task: " + e.getMessage());
        }
    }
}

