package model;

import java.lang.Math;

// Represents financial statements having a fiscal year and financial data from that year
public class FinancialStatement {

    private int fiscalYear;
    private double netInc;
    private double depExp;
    private double intExp;
    private double taxExp;
    private double debtRepaid;

    //REQUIRES: Year be a valid year in the format YYYY
    public FinancialStatement(int year) {
        this.fiscalYear = year;
    }

    //REQUIRES: debtRepaid + interestExpense != 0
    //EFFECTS: calculates debt service coverage ratio (EBITDA / interestExpense + debtRepaid)
    public double calcDSC() {
        double result = calcEbitda() / (intExp + debtRepaid);
        double rounded = Math.round(result * 100.0) / 100.0;
        return rounded;
    }

    //EFFECTS: calculates net income left over after debt repayment
    public double calcFreeCashFlow() {
        return Math.round(calcEbitda() - debtRepaid - intExp);
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

    public double getDebtRepaid() {
        return this.debtRepaid;
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

    public void setDebtRepaid(double debtRepaid) {
        this.debtRepaid = debtRepaid;
    }
}
