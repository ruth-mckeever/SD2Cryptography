package org.rmck.rsa;

import org.rmck.MenuUtil;

import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RSALab {
    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        String[] menuOptions = {
                "0. Exit",
                "1. Generate RSA Keys"
        };
        int menuChoice = -1;
        do {
            MenuUtil.displayMenu(menuOptions, "Encryption Menu");
            try {
                menuChoice = MenuUtil.getMenuChoice(menuOptions.length);
                switch (menuChoice) {
                    case 1:

                        int p;
                        do {    // Check if p is prime number
                            System.out.println("Choose a prime number (p)");
                            p = keyboard.nextInt();
                        } while (!isPrime(p));

                        int q;
                        do {    // Check if q is prime number
                            System.out.println("Choose another prime number");
                            q = keyboard.nextInt();
                        } while(!isPrime(q));

                        BigInteger n = BigInteger.valueOf(p*q);             //Public key, used together with e for encryption
                        System.out.println("n (public key): " + n);

                        BigInteger fn = BigInteger.valueOf((p-1)*(q-1));    //Used together with e to calculate d (private key, needed to decrypt)
                        System.out.println("fn: " + fn);

                        System.out.println("Choose an exponent (e):");
                        //TODO: suggest possible values for 3
                        BigInteger e = keyboard.nextBigInteger();   //exponent, used to encrypt
                        //TODO: check that e fits the criteria - will crash if not a suitable value
                        //Calculate d
                        BigInteger d = e.modInverse(fn);        //Formula to calculate d - used to decrypt
                        // Enter a number to encrypt
                        System.out.println("Enter a number to encrypt:");
                        BigInteger m = keyboard.nextBigInteger();  //message to encrypt
                        //Do encryption
                        BigInteger cipher = m.modPow(e, n);  //encryptRSA(m, e, n);
                        BigInteger messageDecrypted = cipher.modPow(d, n); //decryptRSA(cipher, d, n);

                        //Print out values
                        System.out.println("p: " + p);
                        System.out.println("q: " + q);

                        System.out.println("e: " + e);
                        System.out.println("d: " + d);
                        System.out.println("message: " + m);
                        System.out.println("message encrypted: " + cipher);
                        System.out.println("message decrypted: " + messageDecrypted);
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
    }


    private static boolean isPrime(int number) {
        //This is the brute force method - not efficient but should be clear what is happening.
        //To implement a more efficient method read this: https://www.geeksforgeeks.org/java-prime-number-program/
        if (number <= 1)
            return false;

        for(int i = 2; i < number; i++) {
            if(number % i == 0)
                return false;
        }
        return true;
    }

    private static BigInteger encryptRSA(BigInteger m, BigInteger e, BigInteger n){
        BigInteger cipher = m.modPow(e, n);
        return cipher;
    }

    private static BigInteger decryptRSA(BigInteger c, BigInteger d, BigInteger n) {
        BigInteger decryptedMessage = c.modPow(d, n);
        return decryptedMessage;
    }
}
