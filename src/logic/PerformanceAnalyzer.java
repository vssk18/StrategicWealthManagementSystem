package logic;

import model.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Analyzes portfolio performance metrics.
 */
public class PerformanceAnalyzer {
    
    /**
     * Calculate simple return on investment (ROI).
     */
    public static double calculateROI(Portfolio portfolio) {
        double currentValue = portfolio.getTotalValue();
        double costBasis = portfolio.getTotalCostBasis();
        
        if (costBasis == 0) return 0.0;
        return ((currentValue - costBasis) / costBasis) * 100.0;
    }
    
    /**
     * Calculate annualized return.
     */
    public static double calculateAnnualizedReturn(Portfolio portfolio) {
        LocalDate creationDate = portfolio.getCreationDate();
        LocalDate today = LocalDate.now();
        long daysSinceCreation = ChronoUnit.DAYS.between(creationDate, today);
        
        if (daysSinceCreation < 1) return 0.0;
        
        double totalReturn = portfolio.getTotalGainLossPercentage() / 100.0;
        double years = daysSinceCreation / 365.25;
        
        if (years < 0.01) return totalReturn * 100.0; // Less than a few days
        
        // Annualized return formula: (1 + total return)^(1/years) - 1
        double annualizedReturn = Math.pow(1 + totalReturn, 1.0 / years) - 1;
        return annualizedReturn * 100.0;
    }
    
    /**
     * Calculate portfolio turnover rate (transaction activity).
     */
    public static double calculateTurnoverRate(Portfolio portfolio) {
        List<Transaction> transactions = portfolio.getTransactionHistory();
        long days = ChronoUnit.DAYS.between(portfolio.getCreationDate(), LocalDate.now());
        
        if (days < 1) return 0.0;
        
        long buyTransactions = transactions.stream()
            .filter(t -> t.getType() == Transaction.TransactionType.BUY)
            .count();
        long sellTransactions = transactions.stream()
            .filter(t -> t.getType() == Transaction.TransactionType.SELL)
            .count();
        
        double avgValue = portfolio.getTotalValue();
        if (avgValue == 0) return 0.0;
        
        // Transactions per year
        double transactionsPerYear = ((buyTransactions + sellTransactions) / (days / 365.25));
        return transactionsPerYear;
    }
    
    /**
     * Calculate total fees paid.
     */
    public static double calculateTotalFees(Portfolio portfolio) {
        return portfolio.getTransactionHistory().stream()
            .filter(t -> t.getType() == Transaction.TransactionType.FEE)
            .mapToDouble(Transaction::getTotalAmount)
            .sum();
    }
    
    /**
     * Calculate total dividends received.
     */
    public static double calculateTotalDividends(Portfolio portfolio) {
        return portfolio.getTransactionHistory().stream()
            .filter(t -> t.getType() == Transaction.TransactionType.DIVIDEND)
            .mapToDouble(Transaction::getTotalAmount)
            .sum();
    }
    
    /**
     * Calculate yield (dividends / portfolio value).
     */
    public static double calculateYield(Portfolio portfolio) {
        double totalValue = portfolio.getTotalValue();
        if (totalValue == 0) return 0.0;
        
        double annualDividends = calculateTotalDividends(portfolio);
        // Annualize if portfolio is less than 1 year old
        long days = ChronoUnit.DAYS.between(portfolio.getCreationDate(), LocalDate.now());
        if (days < 365) {
            annualDividends = annualDividends * (365.25 / days);
        }
        
        return (annualDividends / totalValue) * 100.0;
    }
    
    /**
     * Get performance summary report.
     */
    public static String getPerformanceReport(Portfolio portfolio) {
        StringBuilder report = new StringBuilder();
        report.append("=== Performance Analysis Report ===\n");
        report.append(String.format("Portfolio: %s\n", portfolio.getPortfolioName()));
        report.append(String.format("Created: %s (", portfolio.getCreationDate()));
        
        long days = ChronoUnit.DAYS.between(portfolio.getCreationDate(), LocalDate.now());
        if (days < 30) {
            report.append(String.format("%d days ago)\n\n", days));
        } else if (days < 365) {
            report.append(String.format("%.1f months ago)\n\n", days / 30.0));
        } else {
            report.append(String.format("%.1f years ago)\n\n", days / 365.25));
        }
        
        report.append("== Value Metrics ==\n");
        report.append(String.format("Current Value: $%.2f\n", portfolio.getTotalValue()));
        report.append(String.format("Cost Basis: $%.2f\n", portfolio.getTotalCostBasis()));
        report.append(String.format("Cash Balance: $%.2f\n", portfolio.getCashBalance()));
        report.append(String.format("Total Gain/Loss: $%.2f (%.2f%%)\n\n", 
            portfolio.getTotalGainLoss(), portfolio.getTotalGainLossPercentage()));
        
        report.append("== Return Metrics ==\n");
        report.append(String.format("ROI: %.2f%%\n", calculateROI(portfolio)));
        report.append(String.format("Annualized Return: %.2f%%\n", calculateAnnualizedReturn(portfolio)));
        report.append(String.format("Yield: %.2f%%\n\n", calculateYield(portfolio)));
        
        report.append("== Income & Expenses ==\n");
        report.append(String.format("Total Dividends: $%.2f\n", calculateTotalDividends(portfolio)));
        report.append(String.format("Total Fees: $%.2f\n", calculateTotalFees(portfolio)));
        report.append(String.format("Net Income: $%.2f\n\n", 
            calculateTotalDividends(portfolio) - calculateTotalFees(portfolio)));
        
        report.append("== Activity ==\n");
        report.append(String.format("Total Transactions: %d\n", portfolio.getTransactionHistory().size()));
        report.append(String.format("Turnover Rate: %.2f trades/year\n", calculateTurnoverRate(portfolio)));
        
        return report.toString();
    }
    
    /**
     * Get asset performance comparison.
     */
    public static String getAssetPerformanceComparison(Portfolio portfolio) {
        StringBuilder report = new StringBuilder();
        report.append("=== Asset Performance Comparison ===\n\n");
        
        List<Asset> topPerformers = portfolio.getTopPerformers(5);
        report.append("Top 5 Performers:\n");
        for (int i = 0; i < topPerformers.size(); i++) {
            Asset asset = topPerformers.get(i);
            report.append(String.format("%d. %s: %.2f%%\n", i + 1, 
                asset.getSymbol(), asset.getGainLossPercentage()));
        }
        
        report.append("\n");
        
        List<Asset> bottomPerformers = portfolio.getBottomPerformers(5);
        report.append("Bottom 5 Performers:\n");
        for (int i = 0; i < bottomPerformers.size(); i++) {
            Asset asset = bottomPerformers.get(i);
            report.append(String.format("%d. %s: %.2f%%\n", i + 1, 
                asset.getSymbol(), asset.getGainLossPercentage()));
        }
        
        return report.toString();
    }
}
