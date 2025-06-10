package financemanager.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import financemanager.core.DashboardManager;
import financemanager.ui.components.Sidebar;

public class MainFrame extends JFrame {
    private final DashboardManager dashboardManager = new DashboardManager();
    private final JPanel contentPanel = new JPanel(new CardLayout());

    public MainFrame() {
        setTitle("Finance Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Sidebar sidebar = new Sidebar(dashboardManager, this::switchToModule);
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }

    public void registerModule(financemanager.core.Module module) {
        dashboardManager.registerModule(module);
        contentPanel.add(module.getPanel(), module.getModuleName());
    }

    private void switchToModule(String moduleName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, moduleName);
    }
}
