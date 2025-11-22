package logic;

import model.*;
import java.util.*;

/**
 * Calculates risk metrics for portfolios and assets.
 */
public class RiskCalculator {
    
    /**
     * Calculate portfolio beta (systematic risk).
     * Simplified calculation based on asset type weights.
     */
    public static double calculatePortfolioBeta(Portfolio portfolio) {
        Map<Asset.AssetType, Double> allocation = portfolio.getAssetAllocation();
        double beta = 0.0;
        
        // Simplified beta estimates by asset type
        Map<Asset.AssetType, Double> typeBetas = new HashMap<>();
        typeBetas.put(Asset.AssetType.STOCK, 1.0);
        typeBetas.put(Asset.AssetType.ETF, 0.9);
        typeBetas.put(Asset.AssetType.MUTUAL_FUND, 0.85);
        typeBetas.put(Asset.AssetType.BOND, 0.3);
        typeBetas.put(Asset.AssetType.REAL_ESTATE, 0.7);
        typeBetas.put(Asset.AssetType.COMMODITY, 1.2);
        typeBetas.put(Asset.AssetType.CASH, 0.0);
        
        for (Map.Entry<Asset.AssetType, Double> entry : allocation.entrySet()) {
            double typeBeta = typeBetas.getOrDefault(entry.getKey(), 0.8);
            beta += (entry.getValue() / 100.0) * typeBeta;
        }
        
        return beta;
    }
    
    /**
     * Calculate portfolio volatility score (0-10 scale).
     */
    public static int calculateVolatilityScore(Portfolio portfolio) {
        double beta = calculatePortfolioBeta(portfolio);
        
        // Convert beta to 0-10 scale
        // Beta 0.0 -> score 0, Beta 1.5+ -> score 10
        int score = (int) Math.round((beta / 1.5) * 10.0);
        return Math.max(0, Math.min(10, score));
    }
    
    /**
     * Calculate diversification score (0-10 scale).
     * Higher score = better diversification.
     */
    public static int calculateDiversificationScore(Portfolio portfolio) {
        Map<Asset.AssetType, Double> allocation = portfolio.getAssetAllocation();
        Map<String, Double> sectorAllocation = portfolio.getSectorAllocation();
        
        int score = 5; // Start neutral
        
        // Asset type diversity
        int assetTypes = allocation.size();
        if (assetTypes >= 4) score += 2;
        else if (assetTypes >= 3) score += 1;
        else if (assetTypes <= 1) score -= 2;
        
        // Sector diversity
        int sectors = sectorAllocation.size();
        if (sectors >= 5) score += 2;
        else if (sectors >= 3) score += 1;
        else if (sectors <= 1) score -= 2;
        
        // Check for concentration risk
        double maxAllocation = allocation.values().stream()
            .max(Double::compare).orElse(0.0);
        if (maxAllocation > 70.0) score -= 2;
        else if (maxAllocation < 40.0) score += 1;
        
        return Math.max(0, Math.min(10, score));
    }
    
    /**
     * Calculate overall portfolio risk score (0-10 scale).
     * 0 = very low risk, 10 = very high risk.
     */
    public static int calculateOverallRiskScore(Portfolio portfolio) {
        int volatilityScore = calculateVolatilityScore(portfolio);
        int diversificationScore = calculateDiversificationScore(portfolio);
        
        // Higher diversification reduces risk
        int riskScore = volatilityScore - (diversificationScore / 3);
        
        return Math.max(0, Math.min(10, riskScore));
    }
    
    /**
     * Assess risk alignment between portfolio and user's risk profile.
     */
    public static String assessRiskAlignment(Portfolio portfolio, RiskProfile riskProfile) {
        int portfolioRisk = calculateOverallRiskScore(portfolio);
        int targetRisk = riskProfile.getRiskScore();
        
        int difference = Math.abs(portfolioRisk - targetRisk);
        
        if (difference <= 1) {
            return "WELL ALIGNED - Portfolio risk matches your risk profile";
        } else if (difference <= 2) {
            return "MODERATE ALIGNMENT - Portfolio risk is close to your target";
        } else if (portfolioRisk > targetRisk) {
            return "TOO RISKY - Portfolio has more risk than recommended for your profile";
        } else {
            return "TOO CONSERVATIVE - Portfolio is less risky than your profile allows";
        }
    }
    
    /**
     * Calculate Sharpe Ratio approximation.
     * (Return - Risk-Free Rate) / Volatility
     */
    public static double calculateSharpeRatio(double portfolioReturn, double beta) {
        double riskFreeRate = 0.02; // Assume 2% risk-free rate
        double volatility = beta * 0.15; // Approximate volatility from beta
        
        if (volatility == 0) return 0.0;
        return (portfolioReturn - riskFreeRate) / volatility;
    }
    
    /**
     * Get risk report for portfolio.
     */
    public static String getRiskReport(Portfolio portfolio, RiskProfile riskProfile) {
        StringBuilder report = new StringBuilder();
        report.append("=== Risk Analysis Report ===\n");
        report.append(String.format("Portfolio: %s\n\n", portfolio.getPortfolioName()));
        
        double beta = calculatePortfolioBeta(portfolio);
        int volatility = calculateVolatilityScore(portfolio);
        int diversification = calculateDiversificationScore(portfolio);
        int overallRisk = calculateOverallRiskScore(portfolio);
        
        report.append(String.format("Beta: %.2f\n", beta));
        report.append(String.format("Volatility Score: %d/10\n", volatility));
        report.append(String.format("Diversification Score: %d/10\n", diversification));
        report.append(String.format("Overall Risk Score: %d/10\n\n", overallRisk));
        
        String alignment = assessRiskAlignment(portfolio, riskProfile);
        report.append(String.format("Risk Alignment: %s\n", alignment));
        report.append(String.format("Target Risk (from profile): %d/10\n", riskProfile.getRiskScore()));
        
        return report.toString();
    }
}
