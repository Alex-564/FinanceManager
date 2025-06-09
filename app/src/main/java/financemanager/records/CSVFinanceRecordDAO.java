package financemanager.records;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFinanceRecordDAO implements FinanceRecordDAO {

    // File storage
    private static final String FILE_NAME = "finance_records.csv";

    @Override
    public void saveRecord(FinanceRecord record) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            String line = String.format("%s,%s,%.2f,%s,%s\n",
                record.getType(),
                record.getCategory(),
                record.getAmount(),
                record.getDate(),
                record.getNotes().replace(",", " "));
            writer.write(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FinanceRecord> getAllRecords() {
        List<FinanceRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",", -1);
                if (tokens.length >= 5) {
                    records.add(new FinanceRecord(
                        tokens[0], tokens[1],
                        Double.parseDouble(tokens[2]),
                        tokens[3], tokens[4]
                    ));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public List<FinanceRecord> getRecordsByType(String type) {
        List<FinanceRecord> all = getAllRecords();
        all.removeIf(r -> !r.getType().equalsIgnoreCase(type));

        return all;
    }

    @Override
    public List<FinanceRecord> getRecordsByCategory(String category) {
        List<FinanceRecord> all = getAllRecords();
        all.removeIf(r -> !r.getCategory().equalsIgnoreCase(category));

        return all;
    }
}