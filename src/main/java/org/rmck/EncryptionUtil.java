package org.rmck;

public class EncryptionUtil {
    public static final int MODULUS = 26;
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


}
