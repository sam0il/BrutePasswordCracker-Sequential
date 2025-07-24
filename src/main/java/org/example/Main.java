package org.example;

import java.util.Scanner;

public class Main {
    // Just change this path when trying to run the dictionary attack
    // to your path on the passwords file located in resources
    // get the full path!
    // And you have hashes stored in tests.txt which are inside the Passwords.txt
    private static final String dictionaryPath = "C:\\Users\\samoi\\IdeaProjects\\BrutePasswordCrackerSequential\\src\\main\\resources\\Passwords";
    public static String crackedPassword;
    public static String mask = "";
    private static String maskIndicesInput;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the target MD5 hash: ");
        String targetHash = scanner.nextLine().trim();

        if (targetHash.length() != 32 || !targetHash.matches("[a-fA-F0-9]+")) {
            System.out.println("Invalid MD5 hash. Please provide a valid 32-character hexadecimal MD5 hash.");
            return;
        }

        System.out.print("Choose attack mode: 1 for Brute Force 2 for Dictionary Attack: ");
        int mode = scanner.nextInt();
        scanner.nextLine();

        if (mode == 1) {
            System.out.print("Enter the maximum password length to try and get to: ");
            int maxLength = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter the character set to use (e.g., abcdef) or press Enter for default: ");
            String charSet = scanner.nextLine().trim();
            if (charSet.isEmpty()) {
                charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                System.out.println("Using default character set: " + charSet);
            }

            System.out.print("Enter mask indices (comma-separated, e.g. 0,2): ");
            maskIndicesInput = scanner.nextLine().trim();

            System.out.print("Enter a mask (optional, press Enter for no mask): ");
            mask = scanner.nextLine().trim();

            HashValidator validator = new HashValidator(targetHash);
            BruteForceEngine engine = new BruteForceEngine(validator, charSet, maxLength, mask, maskIndicesInput);

            System.out.println("Starting brute-force attack...");
            long startTime = System.currentTimeMillis();
            crackedPassword = engine.crackPassword();
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken: " + (endTime - startTime) + " ms");

        } else if (mode == 2) {
            HashValidator validator = new HashValidator(targetHash);
            DictionaryEngine engine = new DictionaryEngine(validator, dictionaryPath);

            System.out.println("Starting dictionary attack...");
            long startTime = System.currentTimeMillis();
            crackedPassword = engine.crackPassword();
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken: " + (endTime - startTime) + " ms");

        } else {
            System.out.println("Invalid mode selected.");
            return;
        }

        if (crackedPassword != null) {
            System.out.println("Password cracked: " + crackedPassword);
        } else {
            System.out.println("Failed to crack the password.");
        }
    }
}
