package org.rmck;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        String[] menuOptions = {
                "0. Exit",
                "1. Encrypt a string",
                "2. Decrypt a string",
                "3. Encrypt a file",
                "4. Decrypt a file (with a known key)",
                "5. Decrypt a file (brute force)"
        };

        //TODO: this never catches the 0 to exit the program - locate and fix the bug!
        int menuChoice = -1;
        do {
            displayMenu(menuOptions, "Encryption Menu");
            try {
                menuChoice = getMenuChoice(menuOptions.length);
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
                        encryptTextFileContents(plainTextFilename, encryptionKey);
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


    public static void displayMenu(String[] menuOptions, String menuTitle) {
        System.out.println(menuTitle);
        System.out.println("Please choose from one of the following options:");
        for (String option: menuOptions) {
            System.out.println(option);
        }
    }



    public static int getMenuChoice(int numItems) {
        Scanner keyboard = new Scanner(System.in);
        int choice = keyboard.nextInt();
        while (choice < 1 || choice > numItems) {
            System.out.printf("Please enter a valid option (1 - %d)\n", numItems);
            choice = keyboard.nextInt();
        }
        return choice;
    }

    public static void encryptTextFileContents(String filename, int key){
        ArrayList<String> encryptedText = new ArrayList<>();
        File inputFile = new File(filename);
        try {
            Scanner fileScanner = new Scanner(inputFile);

            while (fileScanner.hasNext()) {
                encryptedText.add(EncryptionUtil.encryptShiftCipher(fileScanner.nextLine(), key));
            }
            System.out.println(encryptedText);  //TODO: write this to a new file, or back to the original file.
        }  catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
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