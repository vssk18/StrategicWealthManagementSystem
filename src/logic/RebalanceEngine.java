package logic;

import model.*;
import java.util.*;

/**
 * Provides portfolio rebalancing recommendations.
 */
public class RebalanceEngine {
    
    public static class RebalanceRecommendation {
        public String assetSymbol;
        public String action; // "BUY", "SELL", "HOLD"
        public double currentAllocation;
        public double targetAllocation;
        public double dollarAmount;
        public String reason;
        
        public RebalanceRecommendation(String assetSymbol, String action, 
                                      double currentAllocation, double targetAllocation,
                                      double dollarAmount, String reason) {
            this.assetSymbol = assetSymbol;
            this.action = action;
            this.currentAllocation = currentAllocation;
            this.targetAllocation = targetAllocation;
            this.dollarAmount = dollarAmount;
            this.reason = reason;
        }
        
        @Override
        public String toString() {
            return String.format("%s %s: %s $%.2f (Current: %.1f%% → Target: %.1f%%) - %s",
                action, assetSymbol, action.equals("BUY") ? "+" : "-", 
                Math.abs(dollarAmount), currentAllocation, targetAllocation, reason);
        }
    }
    
    /**
     * Generate rebalancing recommendations based on risk profile.
     */
    public static List<RebalanceRecommendation> getRebalanceRecommendations(
            Portfolio portfolio, RiskProfile riskProfile) {
        
        List<RebalanceRecommendation> recommendations = new ArrayList<>();
        
        // Get current allocation
        Map<Asset.AssetType, Double> currentAllocation = portfolio.getAssetAllocation();
        
        // Calculate target allocation based on risk profile
        Map<Asset.AssetType, Double> targetAllocation = new HashMap<>();
        double stockTarget = riskProfile.getRecommendedStockAllocation() * 100;
        double bondTarget = riskProfile.getRecommendedBondAllocation() * 100;
        
        targetAllocation.put(Asset.AssetType.STOCK, stockTarget * 0.6);
        targetAllocation.put(Asset.AssetType.ETF, stockTarget * 0.3);
        targetAllocation.put(Asset.AssetType.MUTUAL_FUND, stockTarget * 0.1);
        targetAllocation.put(Asset.AssetType.BOND, bondTarget);
        targetAllocation.put(Asset.AssetType.CASH, 5.0); // 5% cash buffer
        
        double portfolioValue = portfolio.getTotalValue();
        
        // Compare current vs target and generate recommendations
        for (Asset.AssetType type : targetAllocation.keySet()) {
            double target = targetAllocation.get(type);
            double current = currentAllocation.getOrDefault(type, 0.0);
            double difference = current - target;
            
            // Only recommend if difference > 5%
            if (Math.abs(difference) > 5.0) {
                double dollarDifference = (difference / 100.0) * portfolioValue;
                
                String action = difference > 0 ? "SELL" : "BUY";
                String reason = difference > 0 
                    ? "Overweight - reduce exposure"
                    : "Underweight - increase exposure";
                
                RebalanceRecommendation rec = new RebalanceRecommendation(
                    type.toString(), action, current, target, dollarDifference, reason
                );
                recommendations.add(rec);
            }
        }
        
        // Sort by dollar amount (largest first)
        recommendations.sort((a, b) -> Double.compare(
            Math.abs(b.dollarAmount), Math.abs(a.dollarAmount)));
        
        return recommendations;
    }
    
    /**
     * Check if portfolio needs rebalancing.
     */
    public static boolean needsRebalancing(Portfolio portfolio, RiskProfile riskProfile) {
        List<RebalanceRecommendation> recommendations = getRebalanceRecommendations(portfolio, riskProfile);
        return !recommendations.isEmpty();
    }
    
    /**
     * Get rebalancing priority level.
     */
    public static String getRebalancingPriority(Portfolio portfolio, RiskProfile riskProfile) {
        List<RebalanceRecommendation> recommendations = getRebalanceRecommendations(portfolio, riskProfile);
        
        if (recommendations.isEmpty()) {
            return "LOW - Portfolio is well balanced";
        }
        
        // Check if any allocation is >15% off target
        boolean highPriority = recommendations.stream()
            .anyMatch(r -> Math.abs(r.currentAllocation - r.targetAllocation) > 15.0);
        
        if (highPriority) {
            return "HIGH - Significant allocation drift detected";
        } else if (recommendations.size() >= 3) {
            return "MEDIUM - Multiple allocations need adjustment";
        } else {
            return "LOW - Minor rebalancing recommended";
        }
    }
    
    /**
     * Generate tax-loss harvesting opportunities.
     */
    public static List<Asset> getTaxLossHarvestingOpportunities(Portfolio portfolio) {
        List<Asset> opportunities = new ArrayList<>();
        
        for (Asset asset : portfolio.getAssets().values()) {
            // Look for assets with losses > 5% and held for > 30 days
            if (asset.getGainLossPercentage() < -5.0 && asset.getHoldingPeriodDays() > 30) {
                opportunities.add(asset);
            }
        }
        
        // Sort by loss amount (largest losses first)
        opportunities.sort((a, b) -> Double.compare(a.getGainLoss(), b.getGainLoss()));
        
        return opportunities;
    }
    
    /**
     * Get full rebalancing report.
     */
    public static String getRebalancingReport(Portfolio portfolio, RiskProfile riskProfile) {
        StringBuilder report = new StringBuilder();
        report.append("=== Portfolio Rebalancing Report ===\n");
        report.append(String.format("Portfolio: %s\n", portfolio.getPortfolioName()));
        report.append(String.format("Risk Profile: %s\n\n", riskProfile.getTolerance()));
        
        String priority = getRebalancingPriority(portfolio, riskProfile);
        report.append(String.format("Rebalancing Priority: %s\n\n", priority));
        
        List<RebalanceRecommendation> recommendations = getRebalanceRecommendations(portfolio, riskProfile);
        
        if (recommendations.isEmpty()) {
            report.append("✓ Portfolio is well balanced. No rebalancing needed.\n");
        } else {
            report.append("Recommendations:\n");
            for (int i = 0; i < recommendations.size(); i++) {
                report.append(String.format("%d. %s\n", i + 1, recommendations.get(i)));
            }
        }
        
        // Tax-loss harvesting opportunities
        List<Asset> taxLossOpportunities = getTaxLossHarvestingOpportunities(portfolio);
        if (!taxLossOpportunities.isEmpty()) {
            report.append("\n=== Tax-Loss Harvesting Opportunities ===\n");
            for (int i = 0; i < Math.min(5, taxLossOpportunities.size()); i++) {
                Asset asset = taxLossOpportunities.get(i);
                report.append(String.format("%d. %s: Loss of $%.2f (%.2f%%)\n", 
                    i + 1, asset.getSymbol(), asset.getGainLoss(), asset.getGainLossPercentage()));
            }
        }
        
        return report.toString();
    }
}
