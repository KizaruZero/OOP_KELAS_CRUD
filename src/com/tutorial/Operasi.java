package com.tutorial;

import java.util.Scanner;

public class Operasi {
    Scanner scanner = new Scanner(System.in);
    public int getIntegerInput(String message) {
        int userInput = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                System.out.print(message);
                userInput = scanner.nextInt();
                isValidInput = true;
            } catch (Exception e) {
                System.out.println("Input harus berupa angka, silakan coba lagi.");
                scanner.nextLine(); // Membersihkan input buffer
            }
        }

        return userInput;
    }

    public boolean getYesNo(String pesan) {
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\n"+pesan+" (y/n)? ");
        String pilihanUser = terminalInput.next();

        while(!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")) {
            System.err.println("Pilihan anda bukan y atau n");
            System.out.print("\n"+pesan+" (y/n)? ");
            pilihanUser = terminalInput.next();
        }

        return pilihanUser.equalsIgnoreCase("y");
    }
}
