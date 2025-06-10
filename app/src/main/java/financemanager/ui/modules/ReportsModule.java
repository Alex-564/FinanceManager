package financemanager.ui.modules;

import java.awt.BorderLayout;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import financemanager.core.Module;
import financemanager.records.CSVFinanceRecordDAO;
import financemanager.records.FinanceRecord;

/**
 * Module that displays a simple bar chart of all finance records using
 * JFreeChart. Income and Expense records are plotted separately.
 */
public class ReportsModule implements Module {

    private final JPanel panel;
    private final Icon icon;

    public ReportsModule() {
        this.panel = new JPanel(new BorderLayout());
        this.icon = UIManager.getIcon("FileView.directoryIcon");
        initChart();
    }

    private void initChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        CSVFinanceRecordDAO dao = new CSVFinanceRecordDAO();
        for (FinanceRecord r : dao.getAllRecords()) {
            dataset.addValue(r.getAmount(), r.getType(), r.getDate());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Finance Records", "Date", "Amount", dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel, BorderLayout.CENTER);
    }

    @Override
    public String getModuleName() {
        return "Reports";
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }
}
