import infrastructure.DatabaseManager;
import di.ServiceLocator;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.createSessionFactory();

        SwingUtilities.invokeLater(() -> {
            ServiceLocator.getInstance().getTaskView().open();
        });
    }
}
