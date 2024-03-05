package model;

import org.json.JSONObject;
import persistance.Writable;

import java.lang.Math;

// Represents financial statements having a fiscal year and financial data from that year
public class FinancialStatement implements Writable {

    private int fiscalYear;
    private double netInc;
    private double depExp;
    private double intExp;
    private double taxExp;
    private double principleRepaid;

    //REQUIRES: Year be a valid year in the format YYYY
    //EFFECTS: initializes FinancialStatement with year field set to given year
    public FinancialStatement(int year) {
        this.fiscalYear = year;
    }

    //REQUIRES: debtRepaid + interestExpense != 0
    //EFFECTS: calculates debt service coverage ratio (EBITDA / interestExpense + debtRepaid)
    public double calcDSC() {
        double result = calcEbitda() / (intExp + principleRepaid);
        return Math.round(result * 100.0) / 100.0;
    }

    //EFFECTS: calculates net income left over after debt repayment
    public double calcFreeCashFlow() {
        return Math.round(calcEbitda() - principleRepaid - intExp);
    }

    //EFFECTS: calculates Earnings Before Interest Tax and Depreciation/Amortization
    public double calcEbitda() {
        return Math.round(netInc + depExp + intExp + taxExp);
    }

    //getters & setters

    public int getFiscalYear() {
        return this.fiscalYear;
    }

    public double getNetInc() {
        return this.netInc;
    }

    public double getDepExp() {
        return this.depExp;
    }

    public double getIntExp() {
        return this.intExp;
    }

    public double getTaxExp() {
        return this.taxExp;
    }

    public double getPrincipleRepaid() {
        return this.principleRepaid;
    }

    public void setFiscalYear(int year) {
        this.fiscalYear = year;
    }

    public void setNetInc(double netInc) {
        this.netInc = netInc;
    }

    public void setDepExp(double depExp) {
        this.depExp = depExp;
    }

    public void setIntExp(double intExp) {
        this.intExp = intExp;
    }

    public void setTaxExp(double taxExp) {
        this.taxExp = taxExp;
    }

    public void setPrincipleRepaid(double principleRepaid) {
        this.principleRepaid = principleRepaid;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("fiscalYear", fiscalYear);
        json.put("netInc", netInc);
        json.put("depExp", depExp);
        json.put("intExp", intExp);
        json.put("taxExp", taxExp);
        json.put("principleRepaid", principleRepaid);
        return json;
    }
}
