package financemanager.ui.components;

import java.awt.Component;
import java.awt.Dimension;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import financemanager.core.DashboardManager;

public class Sidebar extends JPanel {
    public Sidebar(DashboardManager manager, Consumer<String> onModuleSelected) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (String moduleName : manager.getModuleNames()) {
            JButton btn = new JButton(moduleName, manager.getModules().get(moduleName).getIcon());
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.addActionListener(e -> onModuleSelected.accept(moduleName));
            add(Box.createVerticalStrut(10));
            add(btn);
        }
    }
}
