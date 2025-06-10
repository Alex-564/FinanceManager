package financemanager.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

public class DashboardManager {
    private final Map<String, Module> modules = new LinkedHashMap<>();

    public void registerModule(Module module) {
        modules.put(module.getModuleName(), module);
    }

    public Set<String> getModuleNames() {
        return modules.keySet();
    }

    public JPanel getModulePanel(String name) {
        return modules.get(name).getPanel();
    }

    public Map<String, Module> getModules() {
        return modules;
    }
}

