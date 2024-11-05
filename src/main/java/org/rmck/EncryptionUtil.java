package org.rmck;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncryptionUtil {
    public static final int MODULUS = 26;
    public static final String SHA256 = "SHA-256";
    public static final String SHA512 = "SHA-512";

    public static String encryptShiftCipher(String plaintext, int key) {
        String encryptedText;
        // Convert to uppercase first, then a character array
        char[] letters = plaintext.toUpperCase().toCharArray();
        for (int i = 0; i < letters.length; i++) {
            // Only encrypt(alter) characters a-z
            if (letters[i] >= 'A' && letters[i] <= 'Z') {
                // (floor(character - 65 + key))+ 65
                letters[i] = (char)(Math.floorMod(letters[i] - 'A' + key, MODULUS) + 'A');
            }
        }
        encryptedText = new String(letters);
        return encryptedText;
    }

    public static String decryptShiftCipher(String ciphertext, int key) {
        return encryptShiftCipher(ciphertext, -key);
    }
    /**
     *
     * @param plaintext String that you want to encrypt
     * @return
     */
    public static String encryptCaeserCipher(String plaintext) {
        int caeserKey = 3;
        return encryptShiftCipher(plaintext, caeserKey);
    }
    /**
     *
     * @param ciphertext String that you want to decrypt
     * @return
     */
    public static String decryptCaeserCipher(String ciphertext) {
        int caeserKey = 3;
        return decryptShiftCipher(ciphertext, caeserKey);
    }

    public static ArrayList<String> encryptTextFileContents(String filename, int key){
        ArrayList<String> encryptedText = new ArrayList<>();
        File inputFile = new File(filename);
        try {
            Scanner fileScanner = new Scanner(inputFile);

            while (fileScanner.hasNext()) {
                encryptedText.add(EncryptionUtil.encryptShiftCipher(fileScanner.nextLine(), key));
            }
        }  catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        return encryptedText;
    }

    public static ArrayList<String> decryptTextFileContents(String filename, int key) {
        return encryptTextFileContents(filename, -key);
    }

    public static int decryptFileBruteForce(String filename, String knownWord){
        for(int i = 1; i < 26; i++){
            var possibleDecryption = decryptTextFileContents(filename, -i);
            for(String line: possibleDecryption){
                if(line.contains(knownWord)) {
                    System.out.println(possibleDecryption);
                    return i;
                }
            }
        }
        System.out.println("Couldn't find known word: " + knownWord);
        return 0;
    }

    public static String securePassword(String password){

        String salt = generateRandomSalt();
        System.out.println("Random salt: " + salt);

        try {
            byte[] inputBytes = (password + salt).getBytes();
            MessageDigest md = MessageDigest.getInstance(SHA256);
            byte[] outputBytes = md.digest(inputBytes);
            String hash = Base64.getEncoder().encodeToString(outputBytes);
            return hash;
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Exception - No such algorithm: " + ex);
            return "";
        }

    }

    public static String generateRandomSalt() {
        byte[] saltBytes = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        return salt;
    }

}
