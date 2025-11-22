package cli;

import model.*;
import logic.*;
import data.*;
import utils.InputUtils;

import java.time.LocalDate;
import java.util.*;

/**
 * Main CLI application for Strategic Wealth Management System.
 */
public class StrategicWealthManagementSystem {
    
    private User currentUser;
    private Portfolio currentPortfolio;
    private boolean running;
    
    public static void main(String[] args) {
        StrategicWealthManagementSystem app = new StrategicWealthManagementSystem();
        app.run();
    }
    
    public void run() {
        running = true;
        
        InputUtils.clearScreen();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   STRATEGIC WEALTH MANAGEMENT SYSTEM                      ║");
        System.out.println("║   Professional Portfolio Management & Analysis            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        // Initialize demo user with sample data
        initializeDemoUser();
        
        while (running) {
            showMainMenu();
        }
        
        System.out.println("\nThank you for using Strategic Wealth Management System!");
    }
    
    private void initializeDemoUser() {
        // Create user
        currentUser = new User("user001", "demo", "demo@example.com", "Demo User");
        
        // Set risk profile
        RiskProfile riskProfile = new RiskProfile(
            RiskProfile.RiskTolerance.BALANCED, 35, 15, 75000.0, 10000.0
        );
        currentUser.setRiskProfile(riskProfile);
        
        // Create default portfolio with sample data
        currentPortfolio = new Portfolio("portfolio001", "My Investment Portfolio", currentUser.getUserId());
        currentUser.addPortfolio(currentPortfolio);
        
        // Add sample assets
        addSampleAssets();
        
        // Initial cash deposit
        currentPortfolio.setCashBalance(5000.0);
    }
    
    private void addSampleAssets() {
        // Tech stocks
        currentPortfolio.addAsset(new Asset("AAPL", "Apple Inc.", Asset.AssetType.STOCK, 
            50, 150.00, 175.50, LocalDate.now().minusMonths(6), "Technology"));
        currentPortfolio.addAsset(new Asset("MSFT", "Microsoft Corp.", Asset.AssetType.STOCK, 
            30, 300.00, 350.00, LocalDate.now().minusMonths(8), "Technology"));
        currentPortfolio.addAsset(new Asset("GOOGL", "Alphabet Inc.", Asset.AssetType.STOCK, 
            20, 120.00, 140.00, LocalDate.now().minusMonths(4), "Technology"));
        
        // ETFs
        currentPortfolio.addAsset(new Asset("SPY", "S&P 500 ETF", Asset.AssetType.ETF, 
            100, 400.00, 450.00, LocalDate.now().minusMonths(12), "Diversified"));
        currentPortfolio.addAsset(new Asset("VTI", "Total Stock Market ETF", Asset.AssetType.ETF, 
            75, 200.00, 220.00, LocalDate.now().minusMonths(10), "Diversified"));
        
        // Bonds
        currentPortfolio.addAsset(new Asset("AGG", "Bond Aggregate ETF", Asset.AssetType.BOND, 
            200, 100.00, 98.00, LocalDate.now().minusMonths(14), "Fixed Income"));
        
        // Record some sample transactions
        currentPortfolio.recordTransaction(new Transaction("T001", Transaction.TransactionType.DEPOSIT, 
            "CASH", 1, 50000.0, "Initial deposit"));
        currentPortfolio.recordTransaction(new Transaction("T002", Transaction.TransactionType.BUY, 
            "AAPL", 50, 150.00, "Purchase Apple stock"));
        currentPortfolio.recordTransaction(new Transaction("T003", Transaction.TransactionType.DIVIDEND, 
            "SPY", 100, 2.50, "Quarterly dividend"));
    }
    
    private void showMainMenu() {
        InputUtils.printHeader("MAIN MENU");
        System.out.println("User: " + currentUser.getFullName());
        System.out.println("Portfolio: " + currentPortfolio.getPortfolioName());
        System.out.println("\n1.  View Portfolio Summary");
        System.out.println("2.  View All Assets");
        System.out.println("3.  Add New Asset");
        System.out.println("4.  Update Asset Price");
        System.out.println("5.  Remove Asset");
        System.out.println("6.  Record Transaction");
        System.out.println("7.  View Transaction History");
        System.out.println("8.  Performance Analysis");
        System.out.println("9.  Risk Analysis");
        System.out.println("10. Rebalancing Recommendations");
        System.out.println("11. Asset Allocation");
        System.out.println("12. Top/Bottom Performers");
        System.out.println("13. Update Risk Profile");
        System.out.println("14. Import/Export Portfolio");
        System.out.println("15. Generate Reports");
        System.out.println("0.  Exit");
        
        int choice = InputUtils.readInt("\nEnter your choice: ", 0, 15);
        
        switch (choice) {
            case 1: viewPortfolioSummary(); break;
            case 2: viewAllAssets(); break;
            case 3: addNewAsset(); break;
            case 4: updateAssetPrice(); break;
            case 5: removeAsset(); break;
            case 6: recordTransaction(); break;
            case 7: viewTransactionHistory(); break;
            case 8: performanceAnalysis(); break;
            case 9: riskAnalysis(); break;
            case 10: rebalancingRecommendations(); break;
            case 11: assetAllocation(); break;
            case 12: viewTopBottomPerformers(); break;
            case 13: updateRiskProfile(); break;
            case 14: importExportMenu(); break;
            case 15: generateReports(); break;
            case 0: running = false; break;
        }
    }
    
    private void viewPortfolioSummary() {
        InputUtils.printHeader("PORTFOLIO SUMMARY");
        System.out.println(currentPortfolio.getSummary());
        InputUtils.waitForEnter();
    }
    
    private void viewAllAssets() {
        InputUtils.printHeader("ALL ASSETS");
        Map<String, Asset> assets = currentPortfolio.getAssets();
        
        if (assets.isEmpty()) {
            System.out.println("No assets in portfolio.");
        } else {
            int i = 1;
            for (Asset asset : assets.values()) {
                System.out.println(i++ + ". " + asset);
            }
        }
        InputUtils.waitForEnter();
    }
    
    private void addNewAsset() {
        InputUtils.printHeader("ADD NEW ASSET");
        
        String symbol = InputUtils.readString("Symbol: ").toUpperCase();
        if (currentPortfolio.hasAsset(symbol)) {
            System.out.println("Asset already exists in portfolio!");
            InputUtils.waitForEnter();
            return;
        }
        
        String name = InputUtils.readString("Name: ");
        
        System.out.println("\nAsset Types:");
        Asset.AssetType[] types = Asset.AssetType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }
        int typeChoice = InputUtils.readInt("Select type: ", 1, types.length);
        Asset.AssetType type = types[typeChoice - 1];
        
        double quantity = InputUtils.readDouble("Quantity: ", 0.0001, Double.MAX_VALUE);
        double purchasePrice = InputUtils.readDouble("Purchase Price: $", 0.01, Double.MAX_VALUE);
        String sector = InputUtils.readString("Sector (or press Enter for General): ");
        if (sector.isEmpty()) sector = "General";
        
        Asset asset = new Asset(symbol, name, type, quantity, purchasePrice, 
                               purchasePrice, LocalDate.now(), sector);
        currentPortfolio.addAsset(asset);
        
        // Record purchase transaction
        Transaction transaction = new Transaction(
            "T" + System.currentTimeMillis(),
            Transaction.TransactionType.BUY,
            symbol,
            quantity,
            purchasePrice,
            "Purchase of " + name
        );
        currentPortfolio.recordTransaction(transaction);
        
        System.out.println("\n✓ Asset added successfully!");
        InputUtils.waitForEnter();
    }
    
    private void updateAssetPrice() {
        InputUtils.printHeader("UPDATE ASSET PRICE");
        
        String symbol = InputUtils.readString("Enter asset symbol: ").toUpperCase();
        Asset asset = currentPortfolio.getAsset(symbol);
        
        if (asset == null) {
            System.out.println("Asset not found!");
        } else {
            System.out.println("\nCurrent asset: " + asset);
            System.out.println("Current price: $" + asset.getCurrentPrice());
            
            double newPrice = InputUtils.readDouble("New price: $", 0.01, Double.MAX_VALUE);
            asset.setCurrentPrice(newPrice);
            
            System.out.println("\n✓ Price updated successfully!");
        }
        InputUtils.waitForEnter();
    }
    
    private void removeAsset() {
        InputUtils.printHeader("REMOVE ASSET");
        
        String symbol = InputUtils.readString("Enter asset symbol to remove: ").toUpperCase();
        Asset asset = currentPortfolio.getAsset(symbol);
        
        if (asset == null) {
            System.out.println("Asset not found!");
        } else {
            System.out.println("\nAsset to remove: " + asset);
            boolean confirm = InputUtils.readYesNo("Are you sure you want to remove this asset?");
            
            if (confirm) {
                currentPortfolio.removeAsset(symbol);
                System.out.println("\n✓ Asset removed successfully!");
            } else {
                System.out.println("\nRemoval cancelled.");
            }
        }
        InputUtils.waitForEnter();
    }
    
    private void recordTransaction() {
        InputUtils.printHeader("RECORD TRANSACTION");
        
        System.out.println("Transaction Types:");
        Transaction.TransactionType[] types = Transaction.TransactionType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }
        int typeChoice = InputUtils.readInt("Select type: ", 1, types.length);
        Transaction.TransactionType type = types[typeChoice - 1];
        
        String symbol = InputUtils.readString("Asset symbol (or CASH): ").toUpperCase();
        double quantity = InputUtils.readDouble("Quantity: ", 0.0, Double.MAX_VALUE);
        double price = InputUtils.readDouble("Price per unit: $", 0.0, Double.MAX_VALUE);
        String notes = InputUtils.readString("Notes: ");
        
        Transaction transaction = new Transaction(
            "T" + System.currentTimeMillis(),
            type,
            symbol,
            quantity,
            price,
            notes
        );
        
        currentPortfolio.recordTransaction(transaction);
        System.out.println("\n✓ Transaction recorded successfully!");
        InputUtils.waitForEnter();
    }
    
    private void viewTransactionHistory() {
        InputUtils.printHeader("TRANSACTION HISTORY");
        List<Transaction> transactions = currentPortfolio.getTransactionHistory();
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            for (int i = transactions.size() - 1; i >= Math.max(0, transactions.size() - 20); i--) {
                System.out.println(transactions.get(i));
            }
            if (transactions.size() > 20) {
                System.out.println("\n(Showing last 20 transactions)");
            }
        }
        InputUtils.waitForEnter();
    }
    
    private void performanceAnalysis() {
        InputUtils.printHeader("PERFORMANCE ANALYSIS");
        String report = PerformanceAnalyzer.getPerformanceReport(currentPortfolio);
        System.out.println(report);
        
        System.out.println("\n" + PerformanceAnalyzer.getAssetPerformanceComparison(currentPortfolio));
        InputUtils.waitForEnter();
    }
    
    private void riskAnalysis() {
        InputUtils.printHeader("RISK ANALYSIS");
        String report = RiskCalculator.getRiskReport(currentPortfolio, currentUser.getRiskProfile());
        System.out.println(report);
        InputUtils.waitForEnter();
    }
    
    private void rebalancingRecommendations() {
        InputUtils.printHeader("REBALANCING RECOMMENDATIONS");
        String report = RebalanceEngine.getRebalancingReport(currentPortfolio, currentUser.getRiskProfile());
        System.out.println(report);
        InputUtils.waitForEnter();
    }
    
    private void assetAllocation() {
        InputUtils.printHeader("ASSET ALLOCATION");
        
        System.out.println("Asset Type Allocation:");
        Map<Asset.AssetType, Double> typeAllocation = currentPortfolio.getAssetAllocation();
        for (Map.Entry<Asset.AssetType, Double> entry : typeAllocation.entrySet()) {
            System.out.println(String.format("  %-20s: %5.2f%%", entry.getKey(), entry.getValue()));
        }
        
        System.out.println("\nSector Allocation:");
        Map<String, Double> sectorAllocation = currentPortfolio.getSectorAllocation();
        for (Map.Entry<String, Double> entry : sectorAllocation.entrySet()) {
            System.out.println(String.format("  %-20s: %5.2f%%", entry.getKey(), entry.getValue()));
        }
        
        InputUtils.waitForEnter();
    }
    
    private void viewTopBottomPerformers() {
        InputUtils.printHeader("TOP & BOTTOM PERFORMERS");
        
        List<Asset> topPerformers = currentPortfolio.getTopPerformers(5);
        System.out.println("Top 5 Performers:");
        for (int i = 0; i < topPerformers.size(); i++) {
            Asset asset = topPerformers.get(i);
            System.out.println(String.format("%d. %s: %.2f%% ($%.2f gain)", 
                i + 1, asset.getSymbol(), asset.getGainLossPercentage(), asset.getGainLoss()));
        }
        
        System.out.println("\nBottom 5 Performers:");
        List<Asset> bottomPerformers = currentPortfolio.getBottomPerformers(5);
        for (int i = 0; i < bottomPerformers.size(); i++) {
            Asset asset = bottomPerformers.get(i);
            System.out.println(String.format("%d. %s: %.2f%% ($%.2f loss)", 
                i + 1, asset.getSymbol(), asset.getGainLossPercentage(), asset.getGainLoss()));
        }
        
        InputUtils.waitForEnter();
    }
    
    private void updateRiskProfile() {
        InputUtils.printHeader("UPDATE RISK PROFILE");
        
        System.out.println("Current: " + currentUser.getRiskProfile());
        System.out.println("\nRisk Tolerance Levels:");
        RiskProfile.RiskTolerance[] tolerances = RiskProfile.RiskTolerance.values();
        for (int i = 0; i < tolerances.length; i++) {
            System.out.println((i + 1) + ". " + tolerances[i]);
        }
        
        int choice = InputUtils.readInt("Select new tolerance: ", 1, tolerances.length);
        RiskProfile.RiskTolerance tolerance = tolerances[choice - 1];
        
        int age = InputUtils.readInt("Age: ", 18, 100);
        int horizon = InputUtils.readInt("Investment horizon (years): ", 1, 50);
        
        RiskProfile newProfile = new RiskProfile(tolerance, age, horizon);
        currentUser.setRiskProfile(newProfile);
        
        System.out.println("\n✓ Risk profile updated!");
        System.out.println("New profile: " + newProfile);
        InputUtils.waitForEnter();
    }
    
    private void importExportMenu() {
        InputUtils.printHeader("IMPORT / EXPORT");
        System.out.println("1. Export Portfolio to CSV");
        System.out.println("2. Export Transactions to CSV");
        System.out.println("3. Export Portfolio Summary");
        System.out.println("4. Import Portfolio from CSV");
        System.out.println("0. Back to Main Menu");
        
        int choice = InputUtils.readInt("\nEnter choice: ", 0, 4);
        
        switch (choice) {
            case 1:
                String filename1 = InputUtils.readString("Enter filename (e.g., portfolio.csv): ");
                if (CSVHandler.exportPortfolio(currentPortfolio, filename1)) {
                    System.out.println("✓ Portfolio exported to " + filename1);
                }
                break;
            case 2:
                String filename2 = InputUtils.readString("Enter filename (e.g., transactions.csv): ");
                if (CSVHandler.exportTransactions(currentPortfolio, filename2)) {
                    System.out.println("✓ Transactions exported to " + filename2);
                }
                break;
            case 3:
                String filename3 = InputUtils.readString("Enter filename (e.g., summary.csv): ");
                if (CSVHandler.exportPortfolioSummary(currentPortfolio, filename3)) {
                    System.out.println("✓ Summary exported to " + filename3);
                }
                break;
            case 4:
                String filename4 = InputUtils.readString("Enter filename to import: ");
                Portfolio imported = CSVHandler.importPortfolio(filename4, "imported", "Imported Portfolio", currentUser.getUserId());
                if (imported != null) {
                    currentPortfolio = imported;
                    System.out.println("✓ Portfolio imported successfully!");
                }
                break;
        }
        
        if (choice != 0) {
            InputUtils.waitForEnter();
        }
    }
    
    private void generateReports() {
        InputUtils.printHeader("GENERATE REPORTS");
        System.out.println("1. Full Portfolio Report");
        System.out.println("2. Performance Report");
        System.out.println("3. Risk Analysis Report");
        System.out.println("4. Rebalancing Report");
        System.out.println("0. Back");
        
        int choice = InputUtils.readInt("\nEnter choice: ", 0, 4);
        
        switch (choice) {
            case 1:
                System.out.println("\n" + currentPortfolio.getSummary());
                System.out.println(PerformanceAnalyzer.getPerformanceReport(currentPortfolio));
                System.out.println(RiskCalculator.getRiskReport(currentPortfolio, currentUser.getRiskProfile()));
                break;
            case 2:
                System.out.println("\n" + PerformanceAnalyzer.getPerformanceReport(currentPortfolio));
                break;
            case 3:
                System.out.println("\n" + RiskCalculator.getRiskReport(currentPortfolio, currentUser.getRiskProfile()));
                break;
            case 4:
                System.out.println("\n" + RebalanceEngine.getRebalancingReport(currentPortfolio, currentUser.getRiskProfile()));
                break;
        }
        
        if (choice != 0) {
            InputUtils.waitForEnter();
        }
    }
}
