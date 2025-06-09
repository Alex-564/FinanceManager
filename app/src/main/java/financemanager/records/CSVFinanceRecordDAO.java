package financemanager.records;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import financemanager.util.AESEncryptionUtil;

public class CSVFinanceRecordDAO implements FinanceRecordDAO {

    private static final Path ENCRYPTED_FILE = Path.of("src", "main", "resources", "encrypted", "finance_records.enc");
    private static final SecretKey KEY = AESEncryptionUtil.getAESKeyFromBytes("12345678901234567890123456789012".getBytes());

    @Override
    public void saveRecord(FinanceRecord newRecord) {
        try {
            // Updates finance record log
            List<FinanceRecord> records = getAllRecords();
            records.add(newRecord);

            // Encrypts and re-saves the file
            byte[] encryptedCSV = serializeAndEncrypt(records);
            writeEncryptedAtomically(encryptedCSV);

        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger
        }
    }

    @Override
    public List<FinanceRecord> getAllRecords() {
        /* Decrypts finance records and parses them back into finance record objects */


        List<FinanceRecord> records = new ArrayList<>();

        try {
            // Check if file exists
            if (!Files.exists(ENCRYPTED_FILE)) {
                return records;
            }

            // Collects and decrypts csv content from encrypted file
            byte[] decrypted = AESEncryptionUtil.loadAndDecrypt(ENCRYPTED_FILE, KEY);
            String csvContent = new String(decrypted, StandardCharsets.UTF_8);

            // Parses the csv content line by line, splits records into attributes
            try (BufferedReader br = new BufferedReader(new StringReader(csvContent))) {
                
                String line;
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(",", -1);
                    if (tokens.length >= 5) {
                        records.add(new FinanceRecord(
                            tokens[0],
                            tokens[1],
                            Double.parseDouble(tokens[2]),
                            tokens[3],
                            tokens[4]
                        ));
                    
                    // Invalid record format encountered
                    } else {
                        System.out.println("Error: Invalid record encountered");
                    }
                }
            }
        
        // TODO: add error catch
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging
        }

        return records;
    }

    //  ---  Filters  ---  //

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

    
    private byte[] serializeAndEncrypt(List<FinanceRecord> records) throws Exception {

        // Build a CSV in memory to analyze
        StringBuilder csvBuilder = new StringBuilder();
        for (FinanceRecord r : records) {
            csvBuilder.append(String.format("%s,%s,%.2f,%s,%s\n",
                r.getType(),
                r.getCategory(),
                r.getAmount(),
                r.getDate(),
                r.getNotes().replace(",", " ")
            ));
        }

        // Encode the array into bytes
        byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);

        // Re-encrypts CSV bytes and saves
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            AESEncryptionUtil.encryptAndSave(csvBytes, baos, KEY);
            return baos.toByteArray();
        }
    }

    private void writeEncryptedAtomically(byte[] encryptedData) throws Exception {

        // Create a temporary file + write encrypted data
        Path tempFile = Files.createTempFile("finance_temp", ".enc");
        try {
            // Move temp file to replace main encrypted file
            Files.write(tempFile, encryptedData, StandardOpenOption.TRUNCATE_EXISTING);
            Files.move(tempFile, ENCRYPTED_FILE,
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE);
        } finally {
            // Cleanup in case move fails and temp lingers
            Files.deleteIfExists(tempFile);
        }
    }
}
