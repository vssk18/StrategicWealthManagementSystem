# System Architecture

## Overview

The Strategic Wealth Management System follows a layered architecture pattern with clear separation of concerns:

```
┌─────────────────────────────────────────────────┐
│           CLI Layer (Presentation)              │
│         StrategicWealthManagementSystem         │
└─────────────────────────────────────────────────┘
                      ↓ ↑
┌─────────────────────────────────────────────────┐
│           Logic Layer (Business)                │
│  RiskCalculator | PerformanceAnalyzer |         │
│  RebalanceEngine                                │
└─────────────────────────────────────────────────┘
                      ↓ ↑
┌─────────────────────────────────────────────────┐
│           Model Layer (Domain)                  │
│  User | Portfolio | Asset | Transaction |       │
│  RiskProfile                                    │
└─────────────────────────────────────────────────┘
                      ↓ ↑
┌─────────────────────────────────────────────────┐
│           Data Layer (Persistence)              │
│         CSVHandler | DataValidator              │
└─────────────────────────────────────────────────┘
```

---

## Layer Descriptions

### 1. CLI Layer (`cli/`)
**Purpose**: User interaction and presentation logic

**Components**:
- `StrategicWealthManagementSystem.java`: Main application entry point
  - Manages user sessions
  - Coordinates between user input and business logic
  - Renders results to console

**Key Features**:
- Menu-driven navigation
- Input validation via `InputUtils`
- Real-time portfolio updates
- Report generation

---

### 2. Logic Layer (`logic/`)
**Purpose**: Business rules and algorithms

**Components**:

#### `RiskCalculator.java`
Calculates risk metrics for portfolios:
- **Beta Calculation**: Systematic risk measure
- **Volatility Scoring**: 0-10 scale based on asset composition
- **Diversification Analysis**: Asset type and sector spread
- **Risk Alignment**: Compare portfolio vs. risk profile

**Key Methods**:
```java
public static double calculatePortfolioBeta(Portfolio portfolio)
public static int calculateVolatilityScore(Portfolio portfolio)
public static int calculateDiversificationScore(Portfolio portfolio)
public static String assessRiskAlignment(Portfolio portfolio, RiskProfile riskProfile)
```

#### `PerformanceAnalyzer.java`
Analyzes portfolio performance:
- **ROI Calculation**: Simple and annualized returns
- **Yield Analysis**: Dividend/income generation
- **Turnover Rate**: Trading activity metrics
- **Fee Tracking**: Total costs analysis

**Key Methods**:
```java
public static double calculateROI(Portfolio portfolio)
public static double calculateAnnualizedReturn(Portfolio portfolio)
public static double calculateYield(Portfolio portfolio)
public static String getPerformanceReport(Portfolio portfolio)
```

#### `RebalanceEngine.java`
Provides rebalancing recommendations:
- **Target Allocation**: Based on risk profile
- **Drift Detection**: Identifies allocation deviations
- **Trade Recommendations**: Buy/sell suggestions
- **Tax-Loss Harvesting**: Identifies tax optimization opportunities

**Key Methods**:
```java
public static List<RebalanceRecommendation> getRebalanceRecommendations(Portfolio, RiskProfile)
public static boolean needsRebalancing(Portfolio, RiskProfile)
public static List<Asset> getTaxLossHarvestingOpportunities(Portfolio)
```

---

### 3. Model Layer (`model/`)
**Purpose**: Domain entities and business objects

**Core Entities**:

#### `User.java`
Represents a system user
- User credentials and profile
- Multiple portfolio management
- Aggregate net worth calculation

#### `Portfolio.java`
Investment portfolio container
- Asset collection
- Transaction history
- Performance calculations
- Allocation analysis

**Key Methods**:
```java
public double getTotalValue()
public double getTotalGainLoss()
public Map<AssetType, Double> getAssetAllocation()
public List<Asset> getTopPerformers(int count)
```

#### `Asset.java`
Individual investment holding
- Symbol, name, type, quantity
- Purchase and current prices
- Gain/loss calculations
- Holding period tracking

**Supported Asset Types**:
- STOCK
- BOND
- MUTUAL_FUND
- ETF
- CASH
- REAL_ESTATE
- COMMODITY

#### `Transaction.java`
Financial transaction record
- Type (BUY, SELL, DIVIDEND, DEPOSIT, WITHDRAWAL, FEE)
- Timestamp and amount
- Audit trail for compliance

#### `RiskProfile.java`
User risk tolerance definition
- Age and investment horizon
- Risk tolerance level (Conservative to Aggressive)
- Recommended asset allocation
- Dynamic risk score calculation

**Risk Tolerance Levels**:
- CONSERVATIVE: 20% stocks, 80% bonds
- MODERATE: 40% stocks, 60% bonds
- BALANCED: 60% stocks, 40% bonds
- GROWTH: 75% stocks, 25% bonds
- AGGRESSIVE: 90% stocks, 10% bonds

---

### 4. Data Layer (`data/`)
**Purpose**: Data persistence and import/export

**Components**:

#### `CSVHandler.java`
CSV file operations:
- Portfolio export (assets with metadata)
- Transaction history export
- Portfolio import with validation
- Summary report generation

**File Formats**:
```csv
# Portfolio Format
Symbol,Name,Type,Quantity,Purchase Price,Current Price,Purchase Date,Sector

# Transaction Format
ID,Type,Asset Symbol,Quantity,Price Per Unit,Total Amount,Timestamp,Notes
```

---

## Design Patterns

### 1. Layered Architecture
Clean separation between presentation, business logic, and data access.

**Benefits**:
- Easy testing (mock layers)
- Clear dependencies (top-down)
- Maintainable codebase

### 2. Model-View-Controller (MVC)
- **Model**: `model/` package
- **View**: CLI output rendering
- **Controller**: `StrategicWealthManagementSystem` coordinates

### 3. Strategy Pattern
Risk profile determines allocation strategy:
```java
RiskProfile.getRecommendedStockAllocation()
```

Different risk tolerances = different allocation strategies.

### 4. Builder Pattern
Complex object construction:
```java
Asset asset = new Asset(symbol, name, type, quantity, 
                       purchasePrice, currentPrice, 
                       purchaseDate, sector);
```

---

## Data Flow

### Adding an Asset
```
User Input → CLI → Portfolio.addAsset(asset)
                 → Transaction.record(BUY)
                 → Portfolio updates
```

### Performance Analysis
```
User Request → CLI → PerformanceAnalyzer.getReport(portfolio)
                  → Calculates metrics
                  → Returns formatted report
```

### Rebalancing
```
User Request → CLI → RebalanceEngine.getRecommendations(portfolio, riskProfile)
                  → Compares current vs. target allocation
                  → Generates buy/sell recommendations
                  → Returns prioritized list
```

---

## Extensibility Points

### Adding New Asset Types
1. Add to `Asset.AssetType` enum
2. Update beta estimates in `RiskCalculator`
3. Update allocation logic if needed

### Adding New Risk Metrics
1. Create new method in `RiskCalculator`
2. Add to risk report generation
3. Update UI to display new metric

### Adding New Data Sources
1. Implement new handler in `data/` package
2. Follow `CSVHandler` pattern
3. Integrate with `Portfolio` import/export

### Adding Real-Time Pricing
1. Create `MarketDataProvider` interface
2. Implement API integration (e.g., Alpha Vantage, Yahoo Finance)
3. Add price update job scheduler
4. Update `Asset.currentPrice` automatically

---

## Performance Considerations

### Memory Management
- Use `HashMap` for O(1) asset lookups by symbol
- Transaction history stored as `ArrayList` for chronological access
- Lazy calculation of derived metrics (no caching currently)

### Future Optimizations
- Add caching layer for expensive calculations
- Implement database backend for large portfolios
- Add pagination for transaction history
- Optimize allocation calculations for large asset counts

---

## Security Considerations

### Current Implementation
- No authentication (single-user CLI)
- No encryption (data in memory only)
- CSV files stored in plain text

### Production Recommendations
- Add user authentication
- Encrypt sensitive data at rest
- Use HTTPS for API calls
- Implement access control lists
- Add audit logging
- Secure credential storage

---

## Testing Strategy

### Unit Tests
Test individual components:
- `Asset` gain/loss calculations
- `Portfolio` allocation logic
- `RiskCalculator` metrics
- `PerformanceAnalyzer` return calculations

### Integration Tests
Test component interactions:
- Transaction recording updates portfolio
- Import/export round-trip consistency
- Risk profile affects rebalancing recommendations

### End-to-End Tests
Test full workflows:
- Create portfolio → add assets → analyze performance
- Update risk profile → get rebalancing recommendations
- Export portfolio → import elsewhere → verify integrity

---

## Future Architecture Enhancements

### Web Application
```
React Frontend ←→ REST API ←→ Spring Boot Backend ←→ PostgreSQL
```

### Microservices
- Portfolio Service
- Market Data Service
- Analytics Service
- User Service
- Notification Service

### Cloud Deployment
- AWS Lambda for serverless analytics
- DynamoDB for portfolio storage
- S3 for document storage
- CloudWatch for monitoring

---

**Architecture Document Version**: 1.0  
**Last Updated**: November 2024  
**Author**: Varanasi Sai Srinivasa Karthik
