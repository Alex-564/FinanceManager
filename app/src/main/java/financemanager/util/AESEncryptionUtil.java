package financemanager.util;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptionUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_SIZE = 16;

    public static void encryptAndSave(byte[] data, OutputStream out, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encrypted = cipher.doFinal(data);

        out.write(iv);
        out.write(encrypted);
    }   

    public static byte[] loadAndDecrypt(Path inputPath, SecretKey key) throws Exception {
        byte[] fileContent = Files.readAllBytes(inputPath);

        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(fileContent, 0, iv, 0, IV_SIZE);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        byte[] encryptedData = new byte[fileContent.length - IV_SIZE];
        System.arraycopy(fileContent, IV_SIZE, encryptedData, 0, encryptedData.length);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        return cipher.doFinal(encryptedData);
    }

    public static SecretKey getAESKeyFromBytes(byte[] keyBytes) {
        return new SecretKeySpec(keyBytes, "AES");
    }
}
