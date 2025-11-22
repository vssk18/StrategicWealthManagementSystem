package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents an investment portfolio with multiple assets.
 */
public class Portfolio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String portfolioId;
    private String portfolioName;
    private String ownerId;
    private LocalDate creationDate;
    private Map<String, Asset> assets; // symbol -> Asset
    private List<Transaction> transactionHistory;
    private double cashBalance;
    
    public Portfolio(String portfolioId, String portfolioName, String ownerId) {
        this.portfolioId = portfolioId;
        this.portfolioName = portfolioName;
        this.ownerId = ownerId;
        this.creationDate = LocalDate.now();
        this.assets = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
        this.cashBalance = 0.0;
    }
    
    // Getters
    public String getPortfolioId() { return portfolioId; }
    public String getPortfolioName() { return portfolioName; }
    public String getOwnerId() { return ownerId; }
    public LocalDate getCreationDate() { return creationDate; }
    public Map<String, Asset> getAssets() { return new HashMap<>(assets); }
    public List<Transaction> getTransactionHistory() { return new ArrayList<>(transactionHistory); }
    public double getCashBalance() { return cashBalance; }
    
    // Setters
    public void setPortfolioName(String name) { this.portfolioName = name; }
    public void setCashBalance(double balance) { this.cashBalance = balance; }
    
    // Asset operations
    public void addAsset(Asset asset) {
        assets.put(asset.getSymbol(), asset);
    }
    
    public void removeAsset(String symbol) {
        assets.remove(symbol);
    }
    
    public Asset getAsset(String symbol) {
        return assets.get(symbol);
    }
    
    public boolean hasAsset(String symbol) {
        return assets.containsKey(symbol);
    }
    
    // Transaction operations
    public void recordTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
        
        // Update cash balance based on transaction type
        switch (transaction.getType()) {
            case BUY:
                cashBalance -= transaction.getTotalAmount();
                break;
            case SELL:
            case DIVIDEND:
                cashBalance += transaction.getTotalAmount();
                break;
            case DEPOSIT:
                cashBalance += transaction.getTotalAmount();
                break;
            case WITHDRAWAL:
            case FEE:
                cashBalance -= transaction.getTotalAmount();
                break;
        }
    }
    
    // Portfolio calculations
    public double getTotalValue() {
        double total = cashBalance;
        for (Asset asset : assets.values()) {
            total += asset.getCurrentValue();
        }
        return total;
    }
    
    public double getTotalCostBasis() {
        double total = 0.0;
        for (Asset asset : assets.values()) {
            total += asset.getCostBasis();
        }
        return total;
    }
    
    public double getTotalGainLoss() {
        return getTotalValue() - (getTotalCostBasis() + getInitialCashDeposit());
    }
    
    public double getTotalGainLossPercentage() {
        double initialValue = getTotalCostBasis() + getInitialCashDeposit();
        if (initialValue == 0) return 0.0;
        return (getTotalGainLoss() / initialValue) * 100.0;
    }
    
    private double getInitialCashDeposit() {
        double total = 0.0;
        for (Transaction t : transactionHistory) {
            if (t.getType() == Transaction.TransactionType.DEPOSIT) {
                total += t.getTotalAmount();
            } else if (t.getType() == Transaction.TransactionType.WITHDRAWAL) {
                total -= t.getTotalAmount();
            }
        }
        return total;
    }
    
    // Asset allocation
    public Map<Asset.AssetType, Double> getAssetAllocation() {
        Map<Asset.AssetType, Double> allocation = new HashMap<>();
        double totalValue = getTotalValue();
        
        if (totalValue == 0) return allocation;
        
        for (Asset asset : assets.values()) {
            double currentAllocation = allocation.getOrDefault(asset.getType(), 0.0);
            allocation.put(asset.getType(), currentAllocation + asset.getCurrentValue());
        }
        
        // Add cash
        if (cashBalance > 0) {
            allocation.put(Asset.AssetType.CASH, cashBalance);
        }
        
        // Convert to percentages
        for (Asset.AssetType type : allocation.keySet()) {
            allocation.put(type, (allocation.get(type) / totalValue) * 100.0);
        }
        
        return allocation;
    }
    
    public Map<String, Double> getSectorAllocation() {
        Map<String, Double> allocation = new HashMap<>();
        double totalValue = getTotalValue();
        
        if (totalValue == 0) return allocation;
        
        for (Asset asset : assets.values()) {
            String sector = asset.getSector();
            double currentAllocation = allocation.getOrDefault(sector, 0.0);
            allocation.put(sector, currentAllocation + asset.getCurrentValue());
        }
        
        // Convert to percentages
        for (String sector : allocation.keySet()) {
            allocation.put(sector, (allocation.get(sector) / totalValue) * 100.0);
        }
        
        return allocation;
    }
    
    // Top performers
    public List<Asset> getTopPerformers(int count) {
        List<Asset> sorted = new ArrayList<>(assets.values());
        sorted.sort((a, b) -> Double.compare(b.getGainLossPercentage(), a.getGainLossPercentage()));
        return sorted.subList(0, Math.min(count, sorted.size()));
    }
    
    public List<Asset> getBottomPerformers(int count) {
        List<Asset> sorted = new ArrayList<>(assets.values());
        sorted.sort((a, b) -> Double.compare(a.getGainLossPercentage(), b.getGainLossPercentage()));
        return sorted.subList(0, Math.min(count, sorted.size()));
    }
    
    // Portfolio summary
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Portfolio Summary: ").append(portfolioName).append(" ===\n");
        sb.append(String.format("Total Assets: %d | Cash: $%.2f\n", assets.size(), cashBalance));
        sb.append(String.format("Total Value: $%.2f | Cost Basis: $%.2f\n", getTotalValue(), getTotalCostBasis()));
        sb.append(String.format("Gain/Loss: $%.2f (%.2f%%)\n", getTotalGainLoss(), getTotalGainLossPercentage()));
        sb.append("\nAsset Allocation:\n");
        getAssetAllocation().forEach((type, pct) -> 
            sb.append(String.format("  %s: %.2f%%\n", type, pct)));
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return String.format("Portfolio: %s | Assets: %d | Total Value: $%.2f | Gain/Loss: %.2f%%",
            portfolioName, assets.size(), getTotalValue(), getTotalGainLossPercentage());
    }
}
