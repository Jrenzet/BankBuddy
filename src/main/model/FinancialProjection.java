package model;

import java.util.ArrayList;
import java.util.List;

// Represents a financial projection scenario that uses average EBITDA from a group of financial statements, along
// with projected loan payments based off of total payments of a list of loans, to calculate projected DSC
public class FinancialProjection {

    private String name;
    private List<Loan> loans;
    private List<FinancialStatement> statements;


    public FinancialProjection(String name) {
        this.name = name;
        loans = new ArrayList<>();
        statements = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds Loan instance to loans
    public void addLoan(Loan newLoan) {
        loans.add(newLoan);
    }

    //MODIFIES: this
    //EFFECTS: adds FinancialStatement instance to statements
    public void addStatement(FinancialStatement newStatement) {
        statements.add(newStatement);
    }

    //REQUIRES: loans.contains(oldLoan) == true
    //MODIFIES: this
    //EFFECTS: removes Loan instance from loans
    public void removeLoan(Loan oldLoan) {
        loans.remove(oldLoan);
    }

    //REQUIRES: statements.contains(oldStatement) == true
    //MODIFIES: this
    //EFFECTS: removes FinancialStatement instance from statements
    public void removeStatement(FinancialStatement oldStatement) {
        statements.remove(oldStatement);
    }

    //REQUIRES: loans.size > 0
    //EFFECTS: returns total annual payment of all loans for projection purposes
    public double totalPayment() {
        double total = 0.0;
        for (Loan l: loans) {
            total += l.calculateAnnualPayment();
        }
        return total;
    }

    //REQUIRES: statements.size > 0
    //EFFECTS: returns average EBITDA among all FinancialStatements in statements
    public double averageEbitda() {
        double total = 0.0;
        for (FinancialStatement f: statements) {
            total += f.calcEbitda();
        }
        return Math.round(total / statements.size());
    }

    //REQUIRES: totalPayment != 0
    //EFFECTS: calculates projected DSC based on statements and loans
    public double projectedDSC() {
        double result = averageEbitda() / totalPayment();
        return Math.round(result * 100.0) / 100.0;
    }

    //EFFECTS: Produces a "likelihood of approval" rating, DSC > 1.15 = green, approval is likely,
    // 1.15 >= DSC >= 1.05 = yellow, approval less likely, DSC < 1.05 = red, approval unlikely
    public String projectionRating() {
        if (projectedDSC() > 1.15) {
            return "Green";
        } else if (projectedDSC() >= 1.05) {
            return "Yellow";
        } else {
            return "Red";
        }
    }

    //getters & setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public List<FinancialStatement> getStatements() {
        return statements;
    }

}
