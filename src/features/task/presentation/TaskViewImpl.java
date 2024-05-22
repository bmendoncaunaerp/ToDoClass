package features.task.presentation;

import features.task.datasource.TaskListener;
import features.task.datasource.TaskSubscriber;
import features.task.model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TaskViewImpl extends JFrame implements TaskView, TaskListener {
    private DefaultTableModel table;
    private final TaskController taskController;

    public TaskViewImpl(TaskSubscriber taskSubscriber, TaskController taskController) {
        setTitle("ToDo List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        taskSubscriber.subscribe(this);
        this.taskController = taskController;
        taskController.setView(this);

        initializeUI();

        // Load tasks from the database and display them
        loadTasks();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Table to display tasks
        table = new DefaultTableModel(new Object[]{"ID", "Description", "Is Done"}, 0);
        JTable taskTable = new JTable(table);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button to add a new task
        JButton addButton = new JButton("Add features.task.model.Task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        add(addButton, BorderLayout.NORTH);

        // Button to edit a task
        JButton editButton = new JButton("Edit features.task.model.Task");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    editTask(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(TaskViewImpl.this, "Please select a task to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(editButton, BorderLayout.SOUTH);

        // Button to mark task as done
        JButton doneButton = new JButton("Mark as Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    int taskId = (int) table.getValueAt(selectedRow, 0);
                    taskController.setDone(taskId);
                } else {
                    JOptionPane.showMessageDialog(TaskViewImpl.this, "Please select a task to mark as done.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(doneButton, BorderLayout.WEST);
    }

    private void loadTasks() {
        // Clear existing rows from the table
        table.setRowCount(0);

        // Populate the table with tasks from the database
        List<Task> tasks = taskController.getTasks();
        for (Task task : tasks) {
            table.addRow(new Object[]{task.getId(), task.getDescription(), task.isDone()});
        }
    }

    private void addTask() {
        String description = JOptionPane.showInputDialog(this, "Enter features.task.model.Task Description:");
        taskController.addTask(description);
    }

    private void editTask(int rowIndex) {
        int taskId = (int) table.getValueAt(rowIndex, 0);
        String currentDescription = (String) table.getValueAt(rowIndex, 1);
        boolean currentIsDone = (boolean) table.getValueAt(rowIndex, 2);

        String newDescription = JOptionPane.showInputDialog(this, "Enter New features.task.model.Task Description:", currentDescription);
        taskController.updateTask(taskId, newDescription, currentIsDone);
    }

    @Override
    public void open() {
        setVisible(true);
    }

    @Override
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(TaskViewImpl.this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void updateData() {
        loadTasks();
    }
}