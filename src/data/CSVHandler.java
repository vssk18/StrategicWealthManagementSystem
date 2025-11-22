package data;

import model.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Handles CSV import/export operations for portfolios.
 */
public class CSVHandler {
    
    private static final String DELIMITER = ",";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Export portfolio to CSV file.
     */
    public static boolean exportPortfolio(Portfolio portfolio, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Header
            writer.println("Symbol,Name,Type,Quantity,Purchase Price,Current Price,Purchase Date,Sector");
            
            // Assets
            for (Asset asset : portfolio.getAssets().values()) {
                writer.println(String.format("%s,%s,%s,%.4f,%.2f,%.2f,%s,%s",
                    asset.getSymbol(),
                    asset.getName(),
                    asset.getType(),
                    asset.getQuantity(),
                    asset.getPurchasePrice(),
                    asset.getCurrentPrice(),
                    asset.getPurchaseDate().format(DATE_FORMATTER),
                    asset.getSector()
                ));
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error exporting portfolio: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Import portfolio from CSV file.
     */
    public static Portfolio importPortfolio(String filename, String portfolioId, 
                                           String portfolioName, String ownerId) {
        Portfolio portfolio = new Portfolio(portfolioId, portfolioName, ownerId);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isHeader = true;
            
            while ((line = reader.readLine()) != null) {
                // Skip header
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 7) {
                    String symbol = parts[0].trim();
                    String name = parts[1].trim();
                    Asset.AssetType type = Asset.AssetType.valueOf(parts[2].trim());
                    double quantity = Double.parseDouble(parts[3].trim());
                    double purchasePrice = Double.parseDouble(parts[4].trim());
                    double currentPrice = Double.parseDouble(parts[5].trim());
                    LocalDate purchaseDate = LocalDate.parse(parts[6].trim(), DATE_FORMATTER);
                    String sector = parts.length > 7 ? parts[7].trim() : "General";
                    
                    Asset asset = new Asset(symbol, name, type, quantity, purchasePrice, 
                                          currentPrice, purchaseDate, sector);
                    portfolio.addAsset(asset);
                }
            }
            
            return portfolio;
        } catch (IOException e) {
            System.err.println("Error importing portfolio: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error parsing portfolio data: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Export transaction history to CSV.
     */
    public static boolean exportTransactions(Portfolio portfolio, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Header
            writer.println("ID,Type,Asset Symbol,Quantity,Price Per Unit,Total Amount,Timestamp,Notes");
            
            // Transactions
            for (Transaction transaction : portfolio.getTransactionHistory()) {
                writer.println(String.format("%s,%s,%s,%.4f,%.2f,%.2f,%s,%s",
                    transaction.getId(),
                    transaction.getType(),
                    transaction.getAssetSymbol(),
                    transaction.getQuantity(),
                    transaction.getPricePerUnit(),
                    transaction.getTotalAmount(),
                    transaction.getTimestamp().format(DATETIME_FORMATTER),
                    transaction.getNotes()
                ));
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error exporting transactions: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Generate portfolio summary CSV.
     */
    public static boolean exportPortfolioSummary(Portfolio portfolio, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Portfolio Summary Report");
            writer.println("Generated: " + LocalDateTime.now().format(DATETIME_FORMATTER));
            writer.println();
            
            writer.println("Portfolio Name," + portfolio.getPortfolioName());
            writer.println("Portfolio ID," + portfolio.getPortfolioId());
            writer.println("Creation Date," + portfolio.getCreationDate().format(DATE_FORMATTER));
            writer.println();
            
            writer.println("Total Assets," + portfolio.getAssets().size());
            writer.println("Cash Balance,$" + String.format("%.2f", portfolio.getCashBalance()));
            writer.println("Total Value,$" + String.format("%.2f", portfolio.getTotalValue()));
            writer.println("Cost Basis,$" + String.format("%.2f", portfolio.getTotalCostBasis()));
            writer.println("Gain/Loss,$" + String.format("%.2f", portfolio.getTotalGainLoss()));
            writer.println("Gain/Loss %," + String.format("%.2f%%", portfolio.getTotalGainLossPercentage()));
            writer.println();
            
            writer.println("Asset Allocation");
            writer.println("Asset Type,Percentage");
            Map<Asset.AssetType, Double> allocation = portfolio.getAssetAllocation();
            for (Map.Entry<Asset.AssetType, Double> entry : allocation.entrySet()) {
                writer.println(entry.getKey() + "," + String.format("%.2f%%", entry.getValue()));
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error exporting summary: " + e.getMessage());
            return false;
        }
    }
}
