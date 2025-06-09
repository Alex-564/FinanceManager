package financemanager.records;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVFinanceRecordDAOTest {

    private static final Path DEFAULT_PATH = Path.of("src","main","resources","encrypted","finance_records.enc");

    private CSVFinanceRecordDAO dao;
    private Path encryptedPath;

    @BeforeEach
    public void setup() throws IOException {
        dao = new CSVFinanceRecordDAO();
        encryptedPath = Path.of("src", "main", "resources", "encrypted", "finance_records.enc");
        Files.deleteIfExists(encryptedPath);
        Files.createDirectories(encryptedPath.getParent());
    }

    @AfterEach
    public void cleanup() throws IOException {
        Files.deleteIfExists(encryptedPath);
    }

    @Test
    public void testSaveAndRetrieve() {
        FinanceRecord rec = new FinanceRecord("Income", "Salary", 1000.0, "2024-01-01", "Check");
        dao.saveRecord(rec);

        List<FinanceRecord> records = dao.getAllRecords();
        assertEquals(1, records.size());
        assertEquals("Income", records.get(0).getType());
        assertEquals("Salary", records.get(0).getCategory());
        assertEquals(1000.0, records.get(0).getAmount());
        assertEquals("2024-01-01", records.get(0).getDate());
        assertEquals("Check", records.get(0).getNotes());
    }

    @Test
    public void testDataIsEncrypted() throws Exception {
        FinanceRecord rec = new FinanceRecord("Expense", "Food", 50.0, "2024-01-02", "Lunch");
        dao.saveRecord(rec);

        byte[] raw = Files.readAllBytes(encryptedPath);
        String content = new String(raw);
        assertFalse(content.contains("Food"));
        assertFalse(content.contains("Lunch"));
    }
    @Test
    public void testGetAllRecordsEmptyWhenNoFile() throws Exception {
        Files.deleteIfExists(encryptedPath);
        List<FinanceRecord> records = dao.getAllRecords();
        assertTrue(records.isEmpty());
    }
}

