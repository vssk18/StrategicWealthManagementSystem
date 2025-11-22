package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a financial transaction (buy, sell, dividend, deposit, withdrawal).
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum TransactionType {
        BUY, SELL, DIVIDEND, DEPOSIT, WITHDRAWAL, FEE
    }
    
    private String id;
    private TransactionType type;
    private String assetSymbol;
    private double quantity;
    private double pricePerUnit;
    private double totalAmount;
    private LocalDateTime timestamp;
    private String notes;
    
    public Transaction(String id, TransactionType type, String assetSymbol, 
                      double quantity, double pricePerUnit, String notes) {
        this.id = id;
        this.type = type;
        this.assetSymbol = assetSymbol;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalAmount = quantity * pricePerUnit;
        this.timestamp = LocalDateTime.now();
        this.notes = notes;
    }
    
    public Transaction(String id, TransactionType type, String assetSymbol, 
                      double quantity, double pricePerUnit, LocalDateTime timestamp, String notes) {
        this.id = id;
        this.type = type;
        this.assetSymbol = assetSymbol;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalAmount = quantity * pricePerUnit;
        this.timestamp = timestamp;
        this.notes = notes;
    }
    
    // Getters
    public String getId() { return id; }
    public TransactionType getType() { return type; }
    public String getAssetSymbol() { return assetSymbol; }
    public double getQuantity() { return quantity; }
    public double getPricePerUnit() { return pricePerUnit; }
    public double getTotalAmount() { return totalAmount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getNotes() { return notes; }
    
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s: %s %.2f units @ $%.2f = $%.2f | %s",
            getFormattedTimestamp(), type, assetSymbol, quantity, pricePerUnit, totalAmount, notes);
    }
}
