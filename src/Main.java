import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.createDatabase();

        SwingUtilities.invokeLater(() -> {
            ToDoListGUI toDoListGUI = new ToDoListGUI();
            toDoListGUI.setVisible(true);
        });
    }
}