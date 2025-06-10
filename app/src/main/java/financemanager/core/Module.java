package financemanager.core;

import javax.swing.Icon;
import javax.swing.JPanel;

public interface Module {
    String getModuleName();
    JPanel getPanel(); // The content to be shown on the dashboard
    Icon getIcon();    
}
