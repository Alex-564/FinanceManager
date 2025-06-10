package financemanager;

import javax.swing.SwingUtilities;

import financemanager.ui.MainFrame;

public class App {

    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();

            // // Register all modules
            // frame.registerModule(new TransactionsPanel());
            // frame.registerModule(new BudgetsPanel());
            // frame.registerModule(new ReportsPanel());

            frame.setVisible(true);
        });
    }
}
