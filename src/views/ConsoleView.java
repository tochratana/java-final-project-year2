package views;

import java.util.Scanner;

/**
 * ConsoleView - Base class for all view components
 * Provides common UI utilities like menus, input handling, and formatting
 */
public abstract class ConsoleView {
    protected Scanner scanner;

    public ConsoleView(Scanner scanner) {
        this.scanner = scanner;
    }

    protected void clearScreen() {
        // Print multiple lines to simulate clear screen
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    protected void printHeader(String title) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  " + centerText(title, 54) + "  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    protected void printSeparator() {
        System.out.println("──────────────────────────────────────────────────────────");
    }

    protected String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        sb.append(text);
        while (sb.length() < width) {
            sb.append(" ");
        }
        return sb.toString();
    }

    protected void pause() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    protected String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    protected int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid number. Please try again.");
            }
        }
    }

    protected Long readLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid number. Please try again.");
            }
        }
    }

    protected boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            }
            System.out.println("✗ Please enter 'y' or 'n'");
        }
    }
}