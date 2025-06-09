package  financemanager.records;

import java.util.List;

public interface FinanceRecordDAO {
    void saveRecord(FinanceRecord record);
    List<FinanceRecord> getAllRecords();
    List<FinanceRecord> getRecordsByType(String type); // Income/Expense
    List<FinanceRecord> getRecordsByCategory(String category);
}