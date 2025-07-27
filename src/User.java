/**
 * User class for Strategic Wealth Management System
 * Stores user info and investments
 */

import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private double monthlyIncome;
    private Map<String, Double> investments;

    public User(String username, double monthlyIncome) {
        this.username = username;
        this.monthlyIncome = monthlyIncome;
        this.investments = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public Map<String, Double> getInvestments() {
        return investments;
    }
}