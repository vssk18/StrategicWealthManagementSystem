/**
 * InvestmentManager class
 * Handles investment logic and risk analysis
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InvestmentManager {
    private Map<String, Double> investments;
    private final double fdInterestRate = 0.05;

    public InvestmentManager() {
        this.investments = new HashMap<>();
    }

    public void addInvestment(User user, String investmentType, double amount) {
        double remainingBudget = user.getMonthlyIncome() - getTotalInvestments();
        double riskPercentage = (amount / user.getMonthlyIncome()) * 100;

        System.out.println("\nRisk Assessment:");
        System.out.printf("Remaining Budget after other investments: $%.2f\n", remainingBudget);
        System.out.printf("Risk Percentage: %.2f%%\n", riskPercentage);

        if (riskPercentage > 30) {
            System.out.println("⚠️ Caution: This investment involves a significant portion of your income. It's risky!");
        } else {
            System.out.println("✅ This investment appears to be within a reasonable risk range.");
        }

        investments.put(investmentType, amount);
        System.out.println("Investment in " + investmentType + " added successfully!");
    }

    public void viewInvestments() {
        if (investments.isEmpty()) {
            System.out.println("No investments to display.");
        } else {
            System.out.println("\n--- Investments ---");
            investments.forEach((type, amount) -> System.out.printf("%s: $%.2f\n", type, amount));
        }
    }

    public void calculateFutureSavings(User user) {
        double totalInvestments = getTotalInvestments();
        double remainingBudget = user.getMonthlyIncome() - totalInvestments;

        System.out.println("\n--- Future Savings Estimate ---");

        if (remainingBudget < 0) {
            System.out.println("⚠️ Warning: Your monthly expenses exceed your income.");
        }

        System.out.printf("Monthly Income: $%.2f\n", user.getMonthlyIncome());
        System.out.printf("Total Investments: $%.2f\n", totalInvestments);
        System.out.printf("Remaining Budget: $%.2f\n", remainingBudget);

        predictFutureSavings(totalInvestments, remainingBudget, 5, user);
    }

    public void predictFutureSavings(double totalInvestments, double remainingBudget, int years, User user) {
        double predictedSavings = remainingBudget * 12 * years;
        System.out.printf("Predicted Savings in %d years: $%.2f\n", years, predictedSavings);

        calculateCompoundedAmount(totalInvestments, remainingBudget, 0.05, years, user);
        checkInvestmentPerformance(totalInvestments, user);
        assessRiskAndOpinion(totalInvestments, remainingBudget, user);
    }

    public void calculateCompoundedAmount(double principal, double additionalAmount, double rate, int years, User user) {
        double compoundAmount = principal + additionalAmount;
        for (int i = 0; i < years; i++) {
            compoundAmount *= (1 + rate);
        }
        System.out.printf("Compounded Amount in %d years: $%.2f\n", years, compoundAmount);
    }

    public void checkInvestmentPerformance(double principal, User user) {
        double currentFdInterestRate = fdInterestRate + generateRandom(-0.02, 0.02);
        double fdValue = principal * Math.pow((1 + currentFdInterestRate), 5);
        double cryptoValue = principal * generateRandom(0.8, 1.2);

        System.out.println("\n--- Investment Performance ---");

        printPerformanceResult("Fixed Deposit", fdValue, principal);
        printPerformanceResult("Crypto", cryptoValue, principal);
    }

    public void printPerformanceResult(String investmentType, double value, double principal) {
        if (value > principal) {
            System.out.printf("%s: Profit! Estimated Value: $%.2f\n", investmentType, value);
        } else if (value < principal) {
            System.out.printf("%s: Loss! Estimated Value: $%.2f\n", investmentType, value);
        } else {
            System.out.printf("%s: No change. Estimated Value: $%.2f\n", investmentType, value);
        }
    }

    public void assessRiskAndOpinion(double totalInvestments, double remainingBudget, User user) {
        double monthlyExpenses = user.getMonthlyIncome() - totalInvestments / 12;
        double totalExpenses = monthlyExpenses * 12;

        System.out.println("\n--- Risk Assessment and Opinion ---");

        if (totalInvestments > totalExpenses) {
            double profitAmount = totalInvestments - totalExpenses;
            System.out.printf("Profit: $%.2f\n", profitAmount);
            System.out.println("Opinion: Your financial strategy seems sound.");

            if (monthlyExpenses < user.getMonthlyIncome() * 0.3) {
                System.out.println("⚠️ Caution: Ensure your investments align with long-term financial goals.");
            }
        } else {
            double lossAmount = totalExpenses - totalInvestments;
            System.out.printf("Loss: $%.2f\n", lossAmount);
            System.out.println("Opinion: Consider adjusting your investment strategy for better risk management.");

            if (monthlyExpenses > user.getMonthlyIncome() * 0.5) {
                System.out.println("⚠️ Caution: Your monthly expenses are high relative to your income.");
            }
        }

        System.out.println("\n--- Risk-Free Investment Option ---");

        double riskFreeInvestment = user.getMonthlyIncome() * 0.8;
        double futureSavingsRiskFree = riskFreeInvestment * Math.pow((1 + fdInterestRate), 5);

        System.out.printf("Predicted Savings in 5 years with Risk-Free Investment: $%.2f\n", futureSavingsRiskFree);

        if (futureSavingsRiskFree > totalInvestments) {
            double profitAmount = futureSavingsRiskFree - totalInvestments;
            System.out.println("Opinion: Consider allocating a portion of your income to a risk-free investment.");
            System.out.printf("Potential Profit with Risk-Free Investment: $%.2f\n", profitAmount);
        } else {
            System.out.println("Opinion: Your current investment strategy appears more lucrative than a risk-free option.");
        }

        System.out.println("\n--- Monthly Expenses and Loan/EMI Comparison ---");

        double loanAndEmi = calculateLoanAndEmi(user);

        if (totalInvestments > loanAndEmi) {
            double monthlyProfit = totalInvestments / 12 - loanAndEmi / 12;
            double yearlyProfit = totalInvestments - loanAndEmi;

            System.out.printf("Potential Monthly Profit: $%.2f\n", monthlyProfit);
            System.out.printf("Potential Yearly Profit: $%.2f\n", yearlyProfit);
        } else {
            double monthlyLoss = loanAndEmi / 12 - totalInvestments / 12;
            double yearlyLoss = loanAndEmi - totalInvestments;

            System.out.printf("Potential Monthly Loss: $%.2f\n", monthlyLoss);
            System.out.printf("Potential Yearly Loss: $%.2f\n", yearlyLoss);
        }

        predictRiskManagement(totalInvestments, loanAndEmi);
    }

    public double calculateLoanAndEmi(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter monthly loan amount: $");
        double loanAmount = InputUtils.readDouble(scanner);

        System.out.print("Enter monthly EMI amount: $");
        double emiAmount = InputUtils.readDouble(scanner);

        return loanAmount + emiAmount;
    }

    public void predictRiskManagement(double totalInvestments, double loanAndEmi) {
        System.out.println("\n--- Risk Management Predictions ---");

        if (totalInvestments > loanAndEmi) {
            System.out.println("🎉 Congratulations! Your investments are expected to cover your monthly loan and EMI.");
            System.out.println("Opinion: Your financial strategy seems sound.");
        } else {
            System.out.println("⚠️ Warning: Your investments may not cover your monthly loan and EMI.");
            System.out.println("Opinion: Consider adjusting your investment strategy for better risk management.");
        }
    }

    public double generateRandom(double minValue, double maxValue) {
        return minValue + Math.random() * (maxValue - minValue);
    }

    public double getTotalInvestments() {
        return investments.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}