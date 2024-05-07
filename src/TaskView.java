import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TaskView extends JFrame {

    private DefaultTableModel tableModel;
    private final TaskDAO taskDAO;

    public TaskView() {
        setTitle("ToDo List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        taskDAO = new TaskDAO();

        initializeUI();

        // Load tasks from the database and display them
        loadTasks();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Table to display tasks
        tableModel = new DefaultTableModel(new Object[]{"ID", "Description", "Is Done"}, 0);
        JTable taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button to add a new task
        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        add(addButton, BorderLayout.NORTH);

        // Button to edit a task
        JButton editButton = new JButton("Edit Task");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    editTask(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(TaskView.this, "Please select a task to edit.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    int taskId = (int) tableModel.getValueAt(selectedRow, 0);
                    taskDAO.markTaskAsDone(taskId);
                    loadTasks(); // Reload tasks after marking as done
                } else {
                    JOptionPane.showMessageDialog(TaskView.this, "Please select a task to mark as done.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(doneButton, BorderLayout.WEST);
    }

    private void loadTasks() {
        // Clear existing rows from the table
        tableModel.setRowCount(0);

        // Populate the table with tasks from the database
        List<Task> tasks = taskDAO.getTasks();
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{task.getId(), task.getDescription(), task.isDone()});
        }
    }

    private void addTask() {
        String description = JOptionPane.showInputDialog(this, "Enter Task Description:");
        if (description != null && !description.isEmpty()) {
            taskDAO.insertTask(description);
            loadTasks(); // Reload tasks after adding a new one
        }
    }

    private void editTask(int rowIndex) {
        int taskId = (int) tableModel.getValueAt(rowIndex, 0);
        String currentDescription = (String) tableModel.getValueAt(rowIndex, 1);
        boolean currentIsDone = (boolean) tableModel.getValueAt(rowIndex, 2);

        String newDescription = JOptionPane.showInputDialog(this, "Enter New Task Description:", currentDescription);
        if (newDescription != null && !newDescription.isEmpty()) {
            taskDAO.updateTask(taskId, newDescription, currentIsDone);
            loadTasks(); // Reload tasks after editing
        }
    }
}