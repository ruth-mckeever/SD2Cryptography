package org.rmck;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        String[] menuOptions = {
                "0. Exit",
                "1. Encrypt a string",
                "2. Decrypt a string",
                "3. Encrypt a file",
                "4. Decrypt a file (with a known key)",
                "5. Decrypt a file (brute force)",
                "6. Salt & Hash a password",
                "7. Generate Diffie-Hellman Shared Private Key"
        };

        int menuChoice = -1;
        do {
            MenuUtil.displayMenu(menuOptions, "Encryption Menu");
            try {
                menuChoice = MenuUtil.getMenuChoice(menuOptions.length);
                switch (menuChoice) {
                    case 1:
                        System.out.println("Enter String to encrypt:");
                        String plainText = keyboard.next();
                        System.out.println("Enter Key to encrypt:");
                        int encryptionKey = keyboard.nextInt();
                        String cipherText = EncryptionUtil.encryptShiftCipher(plainText, encryptionKey);
                        System.out.println(cipherText);
                        break;
                    case 2:
                        System.out.println("Enter String to decrypt:");
                        cipherText = keyboard.next();
                        System.out.println("Enter Key to decrypt:");
                        encryptionKey = keyboard.nextInt();
                        plainText = EncryptionUtil.decryptShiftCipher(cipherText, encryptionKey);
                        System.out.println(plainText);
                        break;
                    case 3:
                        System.out.println("Enter the filename:");
                        String plainTextFilename = keyboard.next();
                        plainTextFilename = ValidationUtil.validateFileName(plainTextFilename);
                        System.out.println("Enter Key to encrypt:");
                        encryptionKey = keyboard.nextInt();
                        var encryptedText = EncryptionUtil.encryptTextFileContents(plainTextFilename, encryptionKey);
                        System.out.println(encryptedText);  //TODO: write this to a new file, or back to the original file.
                        break;
                    case 4:
                        System.out.println("Enter the filename:");
                        String textFilename = keyboard.next();
                        textFilename = ValidationUtil.validateFileName(textFilename);
                        System.out.println("Enter Key to decrypt:");
                        encryptionKey = keyboard.nextInt();
                        var decryptedText = EncryptionUtil.decryptTextFileContents(textFilename, encryptionKey);
                        System.out.println(decryptedText);
                        break;
                    case 5:
                        System.out.println("Enter the filename:");
                        String decryptFilename = keyboard.next();
                        decryptFilename = ValidationUtil.validateFileName(decryptFilename);
                        System.out.println("Enter known word:");
                        String knownWord = keyboard.next();
                        int decryptionKey = EncryptionUtil.decryptFileBruteForce(decryptFilename, knownWord);
                        System.out.println("Decryption key found: " + decryptionKey);
                        break;
                    case 6:
                        System.out.println("Enter your password");
                        String password = keyboard.nextLine();
                        String hashedPassword = EncryptionUtil.securePassword(password);
                        System.out.println("Hashed password: " + hashedPassword);
                        break;
                    case 7:
                        System.out.println("Please enter public key generator (g):");
                        int g = keyboard.nextInt();
                        System.out.println("Please enter public key modulus (p):");
                        int p = keyboard.nextInt();
                        System.out.println("Please enter private value (a):");
                        int a = keyboard.nextInt();
                        System.out.println("Please enter private value (b):");
                        int b = keyboard.nextInt();
                        BigInteger privateKey = EncryptionUtil.generateDHPrivateValuesBig(g, p, a, b);
                        System.out.println("Diffie-Hellman private key: " + privateKey);
                        break;
                    default:
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid - Please enter a valid option");
            }
        }
        while(menuChoice != 0);
        //testBasicEncryption();
    }

    public static void testBasicEncryption() {
        String[] testPlaintextStrings = {
                "very secret sentence",
                "abcdefghijklmnop",
                "How?Do.we,handle#other~characters!",
                ""
        };

        String[] testEncryptedStrings = new String[testPlaintextStrings.length];
        String[] testDecryptedStrings = new String[testPlaintextStrings.length];

        for (int i = 0; i < testPlaintextStrings.length; i++) {
            testEncryptedStrings[i] = EncryptionUtil.encryptCaeserCipher(testPlaintextStrings[i]);
            testDecryptedStrings[i] = EncryptionUtil.decryptCaeserCipher(testEncryptedStrings[i]);

            System.out.printf("%s : %s : %s\n", testPlaintextStrings[i], testEncryptedStrings[i], testDecryptedStrings[i]);
        }
    }
}