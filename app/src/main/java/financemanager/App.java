package financemanager;

import javax.swing.SwingUtilities;

import financemanager.ui.MainFrame;

public class App {

    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();

            // Register available modules
            frame.registerModule(new financemanager.ui.modules.ReportsModule());

            frame.setVisible(true);
        });
    }
}
