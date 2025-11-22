# User Guide

## Table of Contents
1. [Getting Started](#getting-started)
2. [Main Features](#main-features)
3. [Portfolio Management](#portfolio-management)
4. [Performance Analysis](#performance-analysis)
5. [Risk Management](#risk-management)
6. [Rebalancing](#rebalancing)
7. [Import/Export](#importexport)
8. [Tips & Best Practices](#tips--best-practices)

---

## Getting Started

### First Launch

When you first run the application, you'll see:

```
╔════════════════════════════════════════════════════════════╗
║   STRATEGIC WEALTH MANAGEMENT SYSTEM                      ║
║   Professional Portfolio Management & Analysis            ║
╚════════════════════════════════════════════════════════════╝
```

The system automatically creates a demo user with a sample portfolio for exploration.

### Main Menu Overview

```
=== MAIN MENU ===
User: Demo User
Portfolio: My Investment Portfolio

1.  View Portfolio Summary
2.  View All Assets
3.  Add New Asset
...
0.  Exit
```

---

## Main Features

### 1. View Portfolio Summary

**Menu Option**: 1

Displays high-level portfolio statistics:
- Total number of assets
- Cash balance
- Total portfolio value
- Cost basis (total amount invested)
- Gain/loss (dollar amount and percentage)
- Asset allocation by type

**Example Output**:
```
=== Portfolio Summary: My Investment Portfolio ===
Total Assets: 6 | Cash: $5000.00
Total Value: $99,825.00 | Cost Basis: $76,000.00
Gain/Loss: $23,825.00 (31.35%)

Asset Allocation:
  STOCK: 45.20%
  ETF: 30.15%
  BOND: 19.65%
  CASH: 5.00%
```

---

### 2. View All Assets

**Menu Option**: 2

Lists every asset in your portfolio with details:
- Symbol and name
- Asset type
- Quantity held
- Purchase price vs. current price
- Total gain/loss

**Example Output**:
```
1. AAPL (Apple Inc.) - STOCK: 50.00 units @ $150.00 | Current: $175.50 | Gain/Loss: $1275.00 (17.00%)
2. SPY (S&P 500 ETF) - ETF: 100.00 units @ $400.00 | Current: $450.00 | Gain/Loss: $5000.00 (12.50%)
```

---

## Portfolio Management

### Adding New Assets

**Menu Option**: 3

**Step-by-step**:
1. Enter asset symbol (e.g., "AAPL")
2. Enter asset name (e.g., "Apple Inc.")
3. Select asset type:
   ```
   1. STOCK
   2. BOND
   3. MUTUAL_FUND
   4. ETF
   5. CASH
   6. REAL_ESTATE
   7. COMMODITY
   ```
4. Enter quantity (e.g., 50)
5. Enter purchase price per unit (e.g., 150.00)
6. Enter sector (e.g., "Technology" or press Enter for "General")

The system automatically:
- Adds the asset to your portfolio
- Records a BUY transaction
- Updates portfolio value

**Tips**:
- Use standard ticker symbols (AAPL, MSFT, SPY, etc.)
- Enter accurate purchase prices for correct gain/loss tracking
- Categorize by sector for better allocation analysis

---

### Updating Asset Prices

**Menu Option**: 4

Keep your portfolio current by updating market prices.

**Process**:
1. Enter asset symbol (e.g., "AAPL")
2. System shows current price
3. Enter new price

**When to Update**:
- End of each trading day
- Before major investment decisions
- When running performance reports

**Tip**: For real-world use, integrate with a market data API for automatic updates.

---

### Removing Assets

**Menu Option**: 5

**Process**:
1. Enter symbol of asset to remove
2. System displays asset details
3. Confirm removal (y/n)

**Caution**: This permanently removes the asset from your portfolio. Consider recording a SELL transaction first for accurate history.

---

### Recording Transactions

**Menu Option**: 6

Track all portfolio activities:

**Transaction Types**:
1. **BUY**: Purchase of new or additional shares
2. **SELL**: Sale of holdings
3. **DIVIDEND**: Dividend payments received
4. **DEPOSIT**: Cash added to portfolio
5. **WITHDRAWAL**: Cash removed from portfolio
6. **FEE**: Trading fees, management fees, etc.

**Example - Recording a Dividend**:
```
Transaction Type: 3 (DIVIDEND)
Asset symbol: SPY
Quantity: 100
Price per unit: $2.50
Notes: Quarterly dividend payment

✓ Transaction recorded successfully!
```

**Benefits**:
- Complete audit trail
- Accurate cash balance tracking
- Performance attribution
- Tax reporting preparation

---

## Performance Analysis

### Full Performance Report

**Menu Option**: 8

Comprehensive analysis including:

**Value Metrics**:
- Current value, cost basis, cash balance
- Total gain/loss (dollar and percentage)

**Return Metrics**:
- ROI (Return on Investment)
- Annualized return (time-adjusted)
- Yield (dividend income)

**Income & Expenses**:
- Total dividends received
- Total fees paid
- Net income

**Activity Metrics**:
- Total transactions
- Turnover rate (trades per year)

**Example Output**:
```
=== Performance Analysis Report ===
Portfolio: My Investment Portfolio
Created: 2023-11-22 (1.0 years ago)

== Value Metrics ==
Current Value: $99,825.00
Cost Basis: $76,000.00
Cash Balance: $5,000.00
Total Gain/Loss: $23,825.00 (31.35%)

== Return Metrics ==
ROI: 31.35%
Annualized Return: 28.42%
Yield: 2.15%

== Income & Expenses ==
Total Dividends: $500.00
Total Fees: $125.00
Net Income: $375.00
```

---

### Top & Bottom Performers

**Menu Option**: 12

Identifies winners and losers in your portfolio.

**Output**:
```
=== Top & Bottom Performers ===

Top 5 Performers:
1. MSFT: 16.67% ($1,500.00 gain)
2. AAPL: 17.00% ($1,275.00 gain)
3. SPY: 12.50% ($5,000.00 gain)

Bottom 5 Performers:
1. AGG: -2.00% ($-400.00 loss)
2. BND: -2.00% ($-225.00 loss)
```

**Usage**:
- Identify tax-loss harvesting opportunities (losers)
- Evaluate portfolio rebalancing (trim winners)
- Assess investment decisions

---

## Risk Management

### Risk Analysis Report

**Menu Option**: 9

Evaluates portfolio risk across multiple dimensions:

**Metrics**:
- **Beta**: Systematic risk (market sensitivity)
  - β < 1: Less volatile than market
  - β = 1: Moves with market
  - β > 1: More volatile than market

- **Volatility Score**: 0-10 scale
  - 0-3: Low volatility
  - 4-6: Moderate volatility
  - 7-10: High volatility

- **Diversification Score**: 0-10 scale
  - 0-3: Poorly diversified
  - 4-6: Moderately diversified
  - 7-10: Well diversified

- **Overall Risk Score**: Combined metric (0-10)

**Example Output**:
```
=== Risk Analysis Report ===
Portfolio: My Investment Portfolio

Beta: 0.87
Volatility Score: 6/10
Diversification Score: 8/10
Overall Risk Score: 4/10

Risk Alignment: WELL ALIGNED - Portfolio risk matches your risk profile
Target Risk (from profile): 5/10
```

---

### Updating Risk Profile

**Menu Option**: 13

Customize your risk tolerance:

**Factors**:
1. **Risk Tolerance Level**:
   - Conservative
   - Moderate
   - Balanced
   - Growth
   - Aggressive

2. **Age**: Affects risk capacity
3. **Investment Horizon**: Years until goal

**Example**:
```
Current: Risk Profile: BALANCED (Score: 5/10) | Age: 35 | Horizon: 15 years

Select new tolerance:
1. CONSERVATIVE
2. MODERATE
3. BALANCED
4. GROWTH
5. AGGRESSIVE

Age: 40
Investment horizon (years): 20

✓ Risk profile updated!
New profile: Risk Profile: GROWTH (Score: 6/10) | Age: 40 | Horizon: 20 years
```

---

## Rebalancing

### Getting Recommendations

**Menu Option**: 10

Receive intelligent buy/sell recommendations to align your portfolio with your risk profile.

**How It Works**:
1. System calculates target allocation based on risk profile
2. Compares current allocation to target
3. Identifies significant deviations (>5%)
4. Generates prioritized recommendations

**Example Output**:
```
=== Portfolio Rebalancing Report ===
Rebalancing Priority: MEDIUM - Multiple allocations need adjustment

Recommendations:
1. BUY BOND: +$5,234.00 (Current: 15.2% → Target: 20.0%) - Underweight
2. SELL STOCK: -$3,120.00 (Current: 68.5% → Target: 60.0%) - Overweight

=== Tax-Loss Harvesting Opportunities ===
1. AGG: Loss of $400.00 (-2.00%)
```

**Rebalancing Priorities**:
- **HIGH**: Allocation drift >15%
- **MEDIUM**: Multiple allocations need adjustment
- **LOW**: Minor fine-tuning or well balanced

**Best Practices**:
- Rebalance quarterly or semi-annually
- Use new contributions to rebalance (avoid selling)
- Harvest tax losses before year-end
- Consider transaction costs

---

## Import/Export

### Exporting Data

**Menu Option**: 14

Export your portfolio for backup or external analysis:

**Options**:
1. **Export Portfolio to CSV**: All assets with complete details
2. **Export Transactions to CSV**: Full transaction history
3. **Export Portfolio Summary**: High-level statistics

**Files Generated**:
```
portfolio.csv         - All assets
transactions.csv      - Transaction history
summary.csv          - Portfolio summary report
```

---

### Importing Portfolio

**Menu Option**: 14 → 4

Import assets from a CSV file.

**CSV Format Required**:
```csv
Symbol,Name,Type,Quantity,Purchase Price,Current Price,Purchase Date,Sector
AAPL,Apple Inc.,STOCK,50,150.00,175.50,2024-05-22,Technology
```

**Process**:
1. Prepare CSV file in correct format
2. Select import option
3. Enter filename
4. System validates and imports assets

**Validation**:
- Checks required columns
- Validates data types
- Skips invalid rows with warning

---

## Tips & Best Practices

### Portfolio Management

✅ **DO**:
- Update prices regularly
- Record all transactions promptly
- Use meaningful asset names
- Categorize assets by sector
- Keep cash buffer (5-10%)

❌ **DON'T**:
- Forget to record fees and dividends
- Over-concentrate in single sector
- Ignore rebalancing recommendations
- Make emotional decisions

---

### Performance Tracking

**Monitor These Metrics**:
- Annualized return (time-adjusted)
- Yield (income generation)
- Sharpe ratio (risk-adjusted return)
- Portfolio turnover

**Compare Against**:
- Benchmark indices (S&P 500, total market)
- Your target return
- Inflation rate + 5-7%

---

### Risk Management

**Diversification Rules**:
- At least 3-4 asset types
- 5+ different sectors
- No single holding >20% of portfolio
- Geographic diversification (US/International)

**Risk Alignment**:
- Review risk profile annually
- Adjust as you age (become more conservative)
- Rebalance when risk score drifts >2 points

---

### Tax Optimization

**Strategies**:
- Harvest losses before Dec 31
- Wait 31 days to avoid wash sale rule
- Hold winners >1 year for long-term gains
- Offset gains with losses

**Example Scenario**:
```
Loss on AGG: -$400
Gain on MSFT: +$1,500

Action: Sell AGG to realize loss
Benefit: Reduce taxable gains from MSFT
```

---

### Frequency Guidelines

**Daily**: Update prices (if actively managing)
**Weekly**: Review portfolio summary
**Monthly**: Check performance metrics
**Quarterly**: Review rebalancing recommendations
**Annually**: 
- Update risk profile
- Harvest tax losses
- Review investment strategy

---

## Common Questions

**Q: How often should I rebalance?**
A: Quarterly or when allocation drifts >5% from target.

**Q: What's a good diversification score?**
A: Aim for 7+. Scores <5 indicate concentration risk.

**Q: Should I always follow rebalancing recommendations?**
A: Use them as guidance. Consider tax implications and transaction costs.

**Q: What's a healthy portfolio turnover rate?**
A: 5-10 trades/year for long-term investors. >20 may indicate overtrading.

**Q: How do I calculate my target return?**
A: Inflation (3%) + Risk Premium (4-8% depending on risk tolerance).

---

## Keyboard Shortcuts & Navigation

- **Enter**: Confirm selection or continue
- **Numbers**: Menu navigation
- **0**: Return to previous menu or exit
- **y/n**: Confirmation prompts

---

## Getting Help

**In-App**:
- Each menu option is self-descriptive
- Prompts guide you through each step
- Error messages explain issues

**Documentation**:
- `README.md`: Quick start and features
- `ARCHITECTURE.md`: System design
- `USER_GUIDE.md`: This document

**Support**:
- GitHub Issues: Report bugs or request features
- Email: [svaranas3@gitam.in](mailto:svaranas3@gitam.in)

---

**Happy Investing! 📈**

*User Guide Version 1.0 | Last Updated: November 2024*
