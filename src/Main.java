import infrastructure.DatabaseManager;
import di.ServiceLocator;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServiceLocator.getInstance().getTaskView().open();
        });
    }
}
