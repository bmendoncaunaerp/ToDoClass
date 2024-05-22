package features.task.datasource;

public interface TaskSubscriber {
    void subscribe(TaskListener taskObserver);
}
