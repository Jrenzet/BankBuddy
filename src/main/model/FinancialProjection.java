package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a financial projection scenario that uses average EBITDA from a group of financial statements, along
// with projected loan payments based off of total payments of a list of loans, to calculate projected DSC
public class FinancialProjection implements Writable {

    private List<Loan> loans;
    private List<FinancialStatement> statements;

    //EFFECTS: constructs a financial projection
    public FinancialProjection() {
        loans = new ArrayList<>();
        statements = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds Loan instance to loans
    public void addLoan(Loan newLoan) {
        loans.add(newLoan);
        EventLog.getInstance().logEvent(new Event("Loan for " + newLoan.getDescription()
                + " added to projection"));
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
        EventLog.getInstance().logEvent(new Event("Loan for " + oldLoan.getDescription()
                + " removed from projection"));
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

    public List<Loan> getLoans() {
        return loans;
    }

    public List<FinancialStatement> getStatements() {
        return statements;
    }


    @Override
    //EFFECTS: returns this as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("loans", loansToJson());
        json.put("statements", statementsToJson());
        return json;
    }

    // EFFECTS: returns loans in this financial projection as a JSON array
    private JSONArray loansToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Loan l : loans) {
            jsonArray.put(l.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns financial statements in this financial projection as a JSON array
    private JSONArray statementsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FinancialStatement s : statements) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}
