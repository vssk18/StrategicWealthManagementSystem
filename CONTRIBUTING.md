# Contributing to Strategic Wealth Management System

Thank you for your interest in contributing! This document provides guidelines for contributing to the project.

## Code of Conduct

This project adheres to a Code of Conduct. By participating, you are expected to uphold this code. Please report unacceptable behavior to [svaranas3@gitam.in](mailto:svaranas3@gitam.in).

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check existing issues. When creating a bug report, include:

- **Clear title and description**
- **Steps to reproduce** the problem
- **Expected behavior**
- **Actual behavior**
- **System information** (OS, Java version)
- **Screenshots** if applicable

**Example**:
```markdown
**Bug**: Portfolio value calculation incorrect after dividend

**Steps to Reproduce**:
1. Create portfolio with 100 shares
2. Record dividend of $2.50/share
3. View portfolio summary

**Expected**: Total value increases by $250
**Actual**: Total value unchanged

**Environment**: macOS 14, Java 11.0.2
```

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When suggesting an enhancement:

- **Use a clear title**
- **Provide detailed description** of the suggested enhancement
- **Explain why** this enhancement would be useful
- **Provide examples** of how it would work

**Example**:
```markdown
**Enhancement**: Add real-time price updates via API

**Description**: Integrate with Yahoo Finance API to automatically update asset prices.

**Benefits**:
- Eliminates manual price updates
- Ensures portfolio values are current
- Enables real-time performance tracking

**Implementation Ideas**:
- Create MarketDataProvider interface
- Implement YahooFinanceProvider
- Add background update scheduler
```

### Pull Requests

1. **Fork** the repository
2. **Create a branch** from `main`:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Make your changes**
4. **Test thoroughly**
5. **Commit** with clear messages:
   ```bash
   git commit -m "Add feature: Real-time price updates"
   ```
6. **Push** to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```
7. **Open a Pull Request**

## Development Guidelines

### Code Style

Follow standard Java conventions:

```java
// Class names: PascalCase
public class PortfolioManager {
    
    // Method names: camelCase
    public double calculateReturn() {
        // Implementation
    }
    
    // Constants: UPPER_SNAKE_CASE
    private static final double DEFAULT_RISK_FREE_RATE = 0.02;
    
    // Variables: camelCase
    private double portfolioValue;
}
```

### Documentation

- Add JavaDoc comments for public classes and methods:
  ```java
  /**
   * Calculates the Sharpe ratio for a portfolio.
   * 
   * @param portfolioReturn The portfolio's return rate
   * @param beta The portfolio's beta value
   * @return The Sharpe ratio
   */
  public static double calculateSharpeRatio(double portfolioReturn, double beta) {
      // Implementation
  }
  ```

- Update README.md if adding new features
- Add examples to USER_GUIDE.md for user-facing features

### Testing

- Write unit tests for new functionality
- Ensure existing tests pass: `make test`
- Test edge cases and error conditions
- Verify backwards compatibility

### Commit Messages

Use clear, descriptive commit messages:

**Good**:
```
Add tax-loss harvesting feature
Fix portfolio value calculation bug
Update risk assessment algorithm
```

**Bad**:
```
Update
Fixed stuff
Changes
```

### Branch Naming

- `feature/description` - New features
- `fix/description` - Bug fixes
- `docs/description` - Documentation updates
- `refactor/description` - Code refactoring

## Project Structure

When adding new files, follow the existing structure:

```
src/
├── model/          # Add new domain entities here
├── logic/          # Add new business logic here
├── data/           # Add new data handlers here
├── utils/          # Add new utility classes here
└── cli/            # Add new CLI components here
```

## Feature Requests

We welcome feature requests! Priority areas:

### High Priority
- Real-time market data integration
- Web-based dashboard
- Database backend (PostgreSQL/MySQL)
- Multi-currency support
- Goal-based planning tools

### Medium Priority
- Mobile app (iOS/Android)
- Automated rebalancing execution
- Advanced tax optimization
- Monte Carlo simulation
- Retirement planning calculator

### Low Priority
- Social features (sharing portfolios)
- Gamification
- AI-powered predictions
- Cryptocurrency support

## Recognition

Contributors will be recognized in:
- README.md acknowledgments section
- CHANGELOG.md for releases
- GitHub contributor list

Significant contributions may earn maintainer status.

## Questions?

- **Technical questions**: Open a GitHub Discussion
- **Bug reports**: Create an Issue
- **Security vulnerabilities**: Email [svaranas3@gitam.in](mailto:svaranas3@gitam.in)
- **General inquiries**: Email [varanasikarthik44@gmail.com](mailto:varanasikarthik44@gmail.com)

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

---

**Thank you for contributing to Strategic Wealth Management System!** 🎉
