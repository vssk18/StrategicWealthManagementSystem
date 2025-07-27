/**
 * Strategic Wealth Management System
 * Main Application Entry Point
 * Author: vssk18
 * License: MIT
 */

import java.util.Scanner;

public class StrategicWealthManagementSystem {
    public static void main(String[] args) {
        System.out.println("=== Strategic Wealth Management System ===\n");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your monthly income: $");
        double monthlyIncome = InputUtils.readDouble(scanner);

        User user = new User(username, monthlyIncome);
        InvestmentManager investmentManager = new InvestmentManager();

        boolean exitProgram = false;

        while (!exitProgram) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add Investment");
            System.out.println("2. View Investments");
            System.out.println("3. Calculate Future Savings");
            System.out.println("4. Monthly Expenses and Loan/EMI Comparison");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int choice = InputUtils.readInt(scanner);

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter investment type (Stocks, Crypto, FD, Mutual Fund): ");
                    String investmentType = scanner.nextLine();

                    System.out.print("Enter investment amount: $");
                    double amount = InputUtils.readDouble(scanner);

                    investmentManager.addInvestment(user, investmentType, amount);
                    break;
                case 2:
                    investmentManager.viewInvestments();
                    break;
                case 3:
                    investmentManager.calculateFutureSavings(user);
                    break;
                case 4:
                    investmentManager.assessRiskAndOpinion(
                        investmentManager.getTotalInvestments(),
                        user.getMonthlyIncome() - investmentManager.getTotalInvestments(),
                        user
                    );
                    break;
                case 5:
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("\nThank you for using Strategic Wealth Management System.");
        scanner.close();
    }
}