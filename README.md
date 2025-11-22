# Strategic Wealth Management System

**Professional Portfolio Management & Financial Analysis Tool**

A comprehensive Java-based wealth management system featuring portfolio tracking, risk assessment, performance analysis, and intelligent rebalancing recommendations.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-11+-blue.svg)](https://www.oracle.com/java/)

---

## 🌟 Features

### Core Capabilities
- **Portfolio Management**: Track multiple assets (stocks, bonds, ETFs, mutual funds, real estate, commodities)
- **Performance Analysis**: ROI, annualized returns, yield calculations, Sharpe ratio
- **Risk Assessment**: Beta calculation, volatility scoring, diversification analysis
- **Smart Rebalancing**: AI-driven recommendations based on risk profile and target allocation
- **Transaction History**: Complete audit trail of all portfolio activities
- **Tax-Loss Harvesting**: Identify opportunities to offset capital gains

### Advanced Features
- **Asset Allocation Analysis**: By type and sector with visual breakdowns
- **Risk Profile Management**: Customize tolerance levels (Conservative to Aggressive)
- **Top/Bottom Performers**: Identify winners and losers in your portfolio
- **CSV Import/Export**: Seamless data portability for external analysis
- **Multi-Portfolio Support**: Manage multiple investment accounts

---

## 📊 System Architecture

```
StrategicWealthManagementSystem/
├── src/
│   ├── model/          # Data models (User, Portfolio, Asset, Transaction, RiskProfile)
│   ├── logic/          # Business logic (RiskCalculator, PerformanceAnalyzer, RebalanceEngine)
│   ├── data/           # Data handlers (CSVHandler for import/export)
│   ├── utils/          # Utilities (InputUtils for CLI)
│   └── cli/            # Command-line interface
├── data/
│   └── sample_portfolio.csv
├── docs/
│   ├── ARCHITECTURE.md
│   ├── USER_GUIDE.md
│   └── API_REFERENCE.md
├── out/                # Compiled classes
├── Makefile
└── README.md
```

---

## 🚀 Quick Start

### Prerequisites
- **Java 11** or higher
- **Make** (optional, for convenience)

### Installation

```bash
# Clone the repository
git clone https://github.com/vssk18/StrategicWealthManagementSystem.git
cd StrategicWealthManagementSystem

# Build the project
make build

# Run the application
make run
```

### Manual Build (without Make)

```bash
# Create output directory
mkdir -p out

# Compile all Java files
javac -d out src/model/*.java src/logic/*.java src/data/*.java src/utils/*.java src/cli/*.java

# Run the application
java -cp out cli.StrategicWealthManagementSystem
```

---

## 📖 Usage Examples

### 1. View Portfolio Summary
```
=== Portfolio Summary: My Investment Portfolio ===
Total Assets: 6 | Cash: $5000.00
Total Value: $99,825.00 | Cost Basis: $76,000.00
Gain/Loss: $23,825.00 (31.35%)
```

### 2. Performance Analysis
```
=== Performance Analysis Report ===
ROI: 31.35%
Annualized Return: 28.42%
Yield: 2.15%
Total Dividends: $250.00
```

### 3. Risk Assessment
```
=== Risk Analysis Report ===
Beta: 0.87
Volatility Score: 6/10
Diversification Score: 8/10
Overall Risk Score: 4/10

Risk Alignment: WELL ALIGNED - Portfolio risk matches your risk profile
```

### 4. Rebalancing Recommendations
```
=== Portfolio Rebalancing Report ===
Rebalancing Priority: MEDIUM - Multiple allocations need adjustment

Recommendations:
1. BUY BOND: +$5,234.00 (Current: 15.2% → Target: 20.0%) - Underweight
2. SELL STOCK: -$3,120.00 (Current: 68.5% → Target: 60.0%) - Overweight
```

---

## 💡 Key Features Explained

### Portfolio Optimization
The system uses **Modern Portfolio Theory (MPT)** principles to:
- Calculate optimal asset allocation based on risk tolerance
- Identify overweight/underweight positions
- Suggest rebalancing trades to align with target allocation

### Risk Management
Risk assessment includes:
- **Beta**: Measures systematic risk relative to market
- **Volatility Score**: 0-10 scale indicating portfolio volatility
- **Diversification Score**: 0-10 scale measuring spread across asset types and sectors
- **Overall Risk Score**: Composite metric factoring in all risk dimensions

### Performance Metrics
Comprehensive performance tracking:
- **ROI**: Simple return on investment
- **Annualized Return**: Time-adjusted returns for accurate comparison
- **Sharpe Ratio**: Risk-adjusted return metric
- **Yield**: Income generation from dividends
- **Turnover Rate**: Portfolio trading activity

### Tax Optimization
Smart tax-loss harvesting identifies:
- Assets with unrealized losses > 5%
- Held for > 30 days (wash sale rule compliance)
- Ranked by loss magnitude for maximum tax benefit

---

## 📁 Data Management

### Importing Portfolios
Create a CSV file with the following format:
```csv
Symbol,Name,Type,Quantity,Purchase Price,Current Price,Purchase Date,Sector
AAPL,Apple Inc.,STOCK,50,150.00,175.50,2024-05-22,Technology
SPY,S&P 500 ETF,ETF,100,400.00,450.00,2023-11-22,Diversified
```

### Exporting Data
The system can export:
- **Portfolio**: All assets with full details
- **Transactions**: Complete transaction history
- **Summary**: High-level portfolio statistics

---

## 🔧 Configuration

### Risk Profiles
Five risk tolerance levels available:
1. **CONSERVATIVE**: 20% stocks, 80% bonds
2. **MODERATE**: 40% stocks, 60% bonds
3. **BALANCED**: 60% stocks, 40% bonds (default)
4. **GROWTH**: 75% stocks, 25% bonds
5. **AGGRESSIVE**: 90% stocks, 10% bonds

### Customization
Users can customize:
- Age (affects risk capacity)
- Investment horizon (years until goal)
- Annual income
- Liquidity needs
- Asset allocation targets

---

## 📚 Documentation

- [**Architecture Guide**](docs/ARCHITECTURE.md) - System design and component overview
- [**User Guide**](docs/USER_GUIDE.md) - Detailed usage instructions
- [**API Reference**](docs/API_REFERENCE.md) - Class and method documentation

---

## 🤝 Contributing

Contributions are welcome! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

### Development Setup
```bash
# Fork and clone the repository
git clone https://github.com/YOUR_USERNAME/StrategicWealthManagementSystem.git

# Create a feature branch
git checkout -b feature/your-feature-name

# Make changes and commit
git commit -am "Add new feature"

# Push and create PR
git push origin feature/your-feature-name
```

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**Varanasi Sai Srinivasa Karthik**  
B.Tech CSE (Cybersecurity), GITAM University, Hyderabad

📧 [svaranas3@gitam.in](mailto:svaranas3@gitam.in) | [varanasikarthik44@gmail.com](mailto:varanasikarthik44@gmail.com)  
🔗 [GitHub Profile](https://github.com/vssk18)

---

## 🙏 Acknowledgments

- Modern Portfolio Theory by Harry Markowitz
- Java community for excellent development tools
- Financial industry best practices and standards

---

## 🔮 Future Enhancements

- [ ] Web-based dashboard with real-time market data
- [ ] Mobile app for iOS and Android
- [ ] Machine learning for price prediction
- [ ] Integration with brokerage APIs
- [ ] Multi-currency support
- [ ] Automated rebalancing execution
- [ ] Goal-based planning tools
- [ ] Monte Carlo simulation for retirement planning

---

**Built with ❤️ for smarter wealth management**
