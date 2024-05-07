import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.createSessionFactory();

        SwingUtilities.invokeLater(() -> {
            TaskView taskView = new TaskView();
            taskView.setVisible(true);
        });
    }
}

/**
 * Hibernate
 * 1. Adicionar dependências
 *  - hibernate.orm.core
 *  - hibernate.orm.community.dialects
 * 2. Ajustar classe Task (construtores, getters e setters)
 * 3. Criar hibernate.properties
 * 4. Ajustar DatabaseManager
 * 5. Ajustar ToDoListDatabase -> TaskDAO
 */