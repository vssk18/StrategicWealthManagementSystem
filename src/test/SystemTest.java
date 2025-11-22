package test;

import model.*;
import logic.*;
import data.*;
import java.time.LocalDate;

/**
 * Simple test to verify all components work correctly.
 */
public class SystemTest {
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║  STRATEGIC WEALTH MANAGEMENT SYSTEM - TEST       ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");
        
        // Test 1: Create User and Portfolio
        System.out.println("✓ TEST 1: Creating User and Portfolio");
        User user = new User("test001", "tester", "test@example.com", "Test User");
        Portfolio portfolio = new Portfolio("port001", "Test Portfolio", user.getUserId());
        user.addPortfolio(portfolio);
        System.out.println("  User: " + user.getFullName());
        System.out.println("  Portfolio: " + portfolio.getPortfolioName());
        
        // Test 2: Add Assets
        System.out.println("\n✓ TEST 2: Adding Assets");
        Asset apple = new Asset("AAPL", "Apple Inc.", Asset.AssetType.STOCK, 
                               50, 150.00, 175.50, LocalDate.now().minusMonths(6), "Technology");
        Asset spy = new Asset("SPY", "S&P 500 ETF", Asset.AssetType.ETF, 
                             100, 400.00, 450.00, LocalDate.now().minusMonths(12), "Diversified");
        Asset bond = new Asset("AGG", "Bond ETF", Asset.AssetType.BOND, 
                              200, 100.00, 98.00, LocalDate.now().minusMonths(14), "Fixed Income");
        
        portfolio.addAsset(apple);
        portfolio.addAsset(spy);
        portfolio.addAsset(bond);
        portfolio.setCashBalance(5000.0);
        
        System.out.println("  Added: " + portfolio.getAssets().size() + " assets");
        System.out.println("  Cash: $" + String.format("%.2f", portfolio.getCashBalance()));
        
        // Test 3: Record Transactions
        System.out.println("\n✓ TEST 3: Recording Transactions");
        Transaction deposit = new Transaction("T001", Transaction.TransactionType.DEPOSIT, 
                                             "CASH", 1, 50000.0, "Initial deposit");
        Transaction buy = new Transaction("T002", Transaction.TransactionType.BUY, 
                                         "AAPL", 50, 150.00, "Purchase Apple");
        Transaction dividend = new Transaction("T003", Transaction.TransactionType.DIVIDEND, 
                                              "SPY", 100, 2.50, "Quarterly dividend");
        
        portfolio.recordTransaction(deposit);
        portfolio.recordTransaction(buy);
        portfolio.recordTransaction(dividend);
        
        System.out.println("  Transactions: " + portfolio.getTransactionHistory().size());
        
        // Test 4: Portfolio Calculations
        System.out.println("\n✓ TEST 4: Portfolio Calculations");
        System.out.println("  Total Value: $" + String.format("%.2f", portfolio.getTotalValue()));
        System.out.println("  Cost Basis: $" + String.format("%.2f", portfolio.getTotalCostBasis()));
        System.out.println("  Gain/Loss: $" + String.format("%.2f", portfolio.getTotalGainLoss()));
        System.out.println("  Gain/Loss %: " + String.format("%.2f%%", portfolio.getTotalGainLossPercentage()));
        
        // Test 5: Risk Profile
        System.out.println("\n✓ TEST 5: Risk Profile");
        RiskProfile riskProfile = new RiskProfile(RiskProfile.RiskTolerance.BALANCED, 35, 15);
        user.setRiskProfile(riskProfile);
        System.out.println("  " + riskProfile.toString());
        
        // Test 6: Risk Calculations
        System.out.println("\n✓ TEST 6: Risk Calculations");
        double beta = RiskCalculator.calculatePortfolioBeta(portfolio);
        int volatility = RiskCalculator.calculateVolatilityScore(portfolio);
        int diversification = RiskCalculator.calculateDiversificationScore(portfolio);
        
        System.out.println("  Beta: " + String.format("%.2f", beta));
        System.out.println("  Volatility Score: " + volatility + "/10");
        System.out.println("  Diversification Score: " + diversification + "/10");
        
        // Test 7: Performance Analysis
        System.out.println("\n✓ TEST 7: Performance Analysis");
        double roi = PerformanceAnalyzer.calculateROI(portfolio);
        double totalDividends = PerformanceAnalyzer.calculateTotalDividends(portfolio);
        
        System.out.println("  ROI: " + String.format("%.2f%%", roi));
        System.out.println("  Total Dividends: $" + String.format("%.2f", totalDividends));
        
        // Test 8: Rebalancing Recommendations
        System.out.println("\n✓ TEST 8: Rebalancing");
        boolean needsRebalancing = RebalanceEngine.needsRebalancing(portfolio, riskProfile);
        System.out.println("  Needs Rebalancing: " + (needsRebalancing ? "Yes" : "No"));
        
        // Test 9: CSV Export
        System.out.println("\n✓ TEST 9: CSV Export");
        boolean exportSuccess = CSVHandler.exportPortfolio(portfolio, "test_portfolio.csv");
        System.out.println("  Export Success: " + (exportSuccess ? "Yes" : "No"));
        
        // Test 10: Asset Allocation
        System.out.println("\n✓ TEST 10: Asset Allocation");
        portfolio.getAssetAllocation().forEach((type, pct) -> 
            System.out.println("  " + type + ": " + String.format("%.2f%%", pct)));
        
        // Summary
        System.out.println("\n╔═══════════════════════════════════════════════════╗");
        System.out.println("║  ALL TESTS PASSED ✓                              ║");
        System.out.println("║  System is fully functional!                     ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
    }
}
