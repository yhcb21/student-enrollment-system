package edu.ccrm.util;

import java.util.Scanner;

public class InputValidator {
    
    public static int getInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next(); // consume the non-integer input
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }
    
    public static String getString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}