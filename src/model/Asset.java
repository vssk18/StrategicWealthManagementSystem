package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a financial asset in a portfolio.
 * Supports stocks, bonds, mutual funds, ETFs, and cash.
 */
public class Asset implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum AssetType {
        STOCK, BOND, MUTUAL_FUND, ETF, CASH, REAL_ESTATE, COMMODITY
    }
    
    private String symbol;
    private String name;
    private AssetType type;
    private double quantity;
    private double purchasePrice;
    private double currentPrice;
    private LocalDate purchaseDate;
    private String sector;
    
    public Asset(String symbol, String name, AssetType type, double quantity, 
                 double purchasePrice, LocalDate purchaseDate) {
        this.symbol = symbol;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.currentPrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.sector = "General";
    }
    
    public Asset(String symbol, String name, AssetType type, double quantity, 
                 double purchasePrice, double currentPrice, LocalDate purchaseDate, String sector) {
        this.symbol = symbol;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.currentPrice = currentPrice;
        this.purchaseDate = purchaseDate;
        this.sector = sector;
    }
    
    // Getters
    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public AssetType getType() { return type; }
    public double getQuantity() { return quantity; }
    public double getPurchasePrice() { return purchasePrice; }
    public double getCurrentPrice() { return currentPrice; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public String getSector() { return sector; }
    
    // Setters
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }
    public void setSector(String sector) { this.sector = sector; }
    
    // Calculated values
    public double getCostBasis() {
        return quantity * purchasePrice;
    }
    
    public double getCurrentValue() {
        return quantity * currentPrice;
    }
    
    public double getGainLoss() {
        return getCurrentValue() - getCostBasis();
    }
    
    public double getGainLossPercentage() {
        if (getCostBasis() == 0) return 0.0;
        return (getGainLoss() / getCostBasis()) * 100.0;
    }
    
    public int getHoldingPeriodDays() {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(purchaseDate, LocalDate.now());
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s) - %s: %.2f units @ $%.2f | Current: $%.2f | Gain/Loss: $%.2f (%.2f%%)",
            symbol, name, type, quantity, purchasePrice, currentPrice, 
            getGainLoss(), getGainLossPercentage());
    }
}
