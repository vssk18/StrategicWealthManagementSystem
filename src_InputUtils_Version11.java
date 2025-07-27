/**
 * InputUtils class
 * Handles validated user input
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtils {
    public static double readDouble(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // clear buffer
            }
        }
    }

    public static int readInt(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter an integer: ");
                scanner.nextLine();
            }
        }
    }
}