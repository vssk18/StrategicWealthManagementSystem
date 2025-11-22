package model;

import java.io.Serializable;

/**
 * Represents a user's investment risk profile.
 */
public class RiskProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum RiskTolerance {
        CONSERVATIVE, MODERATE, BALANCED, GROWTH, AGGRESSIVE
    }
    
    private RiskTolerance tolerance;
    private int age;
    private int investmentHorizonYears;
    private double annualIncome;
    private double liquidityNeeds;
    private int riskScore; // 1-10 scale
    
    public RiskProfile(RiskTolerance tolerance, int age, int investmentHorizonYears) {
        this.tolerance = tolerance;
        this.age = age;
        this.investmentHorizonYears = investmentHorizonYears;
        this.annualIncome = 0.0;
        this.liquidityNeeds = 0.0;
        this.riskScore = calculateRiskScore();
    }
    
    public RiskProfile(RiskTolerance tolerance, int age, int investmentHorizonYears,
                      double annualIncome, double liquidityNeeds) {
        this.tolerance = tolerance;
        this.age = age;
        this.investmentHorizonYears = investmentHorizonYears;
        this.annualIncome = annualIncome;
        this.liquidityNeeds = liquidityNeeds;
        this.riskScore = calculateRiskScore();
    }
    
    private int calculateRiskScore() {
        int score = 5; // Start neutral
        
        // Tolerance adjustment
        switch (tolerance) {
            case CONSERVATIVE: score -= 2; break;
            case MODERATE: score -= 1; break;
            case BALANCED: break;
            case GROWTH: score += 1; break;
            case AGGRESSIVE: score += 2; break;
        }
        
        // Age adjustment (younger = more risk capacity)
        if (age < 30) score += 1;
        else if (age > 60) score -= 2;
        else if (age > 50) score -= 1;
        
        // Investment horizon adjustment
        if (investmentHorizonYears > 15) score += 1;
        else if (investmentHorizonYears < 5) score -= 1;
        
        // Clamp to 1-10
        return Math.max(1, Math.min(10, score));
    }
    
    // Getters
    public RiskTolerance getTolerance() { return tolerance; }
    public int getAge() { return age; }
    public int getInvestmentHorizonYears() { return investmentHorizonYears; }
    public double getAnnualIncome() { return annualIncome; }
    public double getLiquidityNeeds() { return liquidityNeeds; }
    public int getRiskScore() { return riskScore; }
    
    // Setters
    public void setTolerance(RiskTolerance tolerance) { 
        this.tolerance = tolerance;
        this.riskScore = calculateRiskScore();
    }
    public void setAge(int age) { 
        this.age = age;
        this.riskScore = calculateRiskScore();
    }
    public void setInvestmentHorizonYears(int years) { 
        this.investmentHorizonYears = years;
        this.riskScore = calculateRiskScore();
    }
    public void setAnnualIncome(double income) { this.annualIncome = income; }
    public void setLiquidityNeeds(double needs) { this.liquidityNeeds = needs; }
    
    // Recommended asset allocation based on risk profile
    public double getRecommendedStockAllocation() {
        switch (tolerance) {
            case CONSERVATIVE: return 0.20;
            case MODERATE: return 0.40;
            case BALANCED: return 0.60;
            case GROWTH: return 0.75;
            case AGGRESSIVE: return 0.90;
            default: return 0.60;
        }
    }
    
    public double getRecommendedBondAllocation() {
        return 1.0 - getRecommendedStockAllocation();
    }
    
    @Override
    public String toString() {
        return String.format("Risk Profile: %s (Score: %d/10) | Age: %d | Horizon: %d years | " +
                           "Recommended Allocation: %.0f%% Stocks, %.0f%% Bonds",
            tolerance, riskScore, age, investmentHorizonYears,
            getRecommendedStockAllocation() * 100, getRecommendedBondAllocation() * 100);
    }
}
