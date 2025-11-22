package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents a user of the wealth management system.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String username;
    private String email;
    private String fullName;
    private RiskProfile riskProfile;
    private Map<String, Portfolio> portfolios; // portfolioId -> Portfolio
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    
    public User(String userId, String username, String email, String fullName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.portfolios = new HashMap<>();
        this.createdAt = LocalDateTime.now();
        this.lastLoginAt = LocalDateTime.now();
        // Default risk profile
        this.riskProfile = new RiskProfile(RiskProfile.RiskTolerance.BALANCED, 30, 10);
    }
    
    // Getters
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public RiskProfile getRiskProfile() { return riskProfile; }
    public Map<String, Portfolio> getPortfolios() { return new HashMap<>(portfolios); }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    
    // Setters
    public void setEmail(String email) { this.email = email; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setRiskProfile(RiskProfile riskProfile) { this.riskProfile = riskProfile; }
    public void updateLastLogin() { this.lastLoginAt = LocalDateTime.now(); }
    
    // Portfolio operations
    public void addPortfolio(Portfolio portfolio) {
        portfolios.put(portfolio.getPortfolioId(), portfolio);
    }
    
    public void removePortfolio(String portfolioId) {
        portfolios.remove(portfolioId);
    }
    
    public Portfolio getPortfolio(String portfolioId) {
        return portfolios.get(portfolioId);
    }
    
    public boolean hasPortfolio(String portfolioId) {
        return portfolios.containsKey(portfolioId);
    }
    
    public Portfolio getDefaultPortfolio() {
        if (portfolios.isEmpty()) {
            Portfolio defaultPortfolio = new Portfolio("default", "Default Portfolio", userId);
            addPortfolio(defaultPortfolio);
            return defaultPortfolio;
        }
        return portfolios.values().iterator().next();
    }
    
    // Aggregate calculations across all portfolios
    public double getTotalNetWorth() {
        double total = 0.0;
        for (Portfolio p : portfolios.values()) {
            total += p.getTotalValue();
        }
        return total;
    }
    
    public double getTotalGainLoss() {
        double total = 0.0;
        for (Portfolio p : portfolios.values()) {
            total += p.getTotalGainLoss();
        }
        return total;
    }
    
    public double getTotalGainLossPercentage() {
        double totalValue = getTotalNetWorth();
        double totalCostBasis = 0.0;
        for (Portfolio p : portfolios.values()) {
            totalCostBasis += p.getTotalCostBasis();
        }
        
        if (totalCostBasis == 0) return 0.0;
        return ((totalValue - totalCostBasis) / totalCostBasis) * 100.0;
    }
    
    @Override
    public String toString() {
        return String.format("User: %s (%s) | Portfolios: %d | Net Worth: $%.2f | Risk: %s",
            fullName, username, portfolios.size(), getTotalNetWorth(), 
            riskProfile.getTolerance());
    }
}
