package model;

// Represents financial statements having a fiscal year and financial data and results from that year
public class FinancialStatement {

    private int fiscalYear;
    private double assets;
    private double liabil;
    private double curAssets;
    private double curLiabilities;
    private double netInc;
    private double depEx;
    private double intEx;
    private double taxEx;
    private double debtRepaid;

    //REQUIRES: Year be a valid year in the format YYYY
    public FinancialStatement(int year) {
        this.fiscalYear = year;
    }

    //REQUIRES: debtRepaid + interestExpense != 0
    //EFFECTS: calculates debt service coverage ratio (EBITDA / interestExpense + debtRepaid)
    public double calcDSC() {
        return calcEbitda() / debtRepaid;
    }

    //EFFECTS: calculates net income left over after debt repayment
    public double calcFreeCashFlow() {
        return calcEbitda() - debtRepaid;
    }

    //REQUIRES: currentLiabilities != 0
    //EFFECTS: calculates ratio of current assets to current liabilities
    public double calcCurrentRatio() {
        return curAssets / curLiabilities;
    }

    //EFFECTS: calculates Earnings Before Interest Tax and Depreciation/Amortization
    public double calcEbitda() {
        return netInc + depEx + intEx + taxEx;
    }

    //REQUIRES: assets - liabilities != 0
    //EFFECTS: calculates debt to equity ratio (equity = assets - liabilities)
    public double calcLeverage() {
        return calcEquity() / liabil;
    }

    //EFFECTS: calculates equity
    public double calcEquity() {
        return assets - liabil;
    }

    //getters

    public int getFiscalYear() {
        return this.fiscalYear;
    }

    public double getAssets() {
        return this.assets;
    }

    public double getLiabil() {
        return this.liabil;
    }

    public double getCurAssets() {
        return this.curAssets;
    }

    public double getCurLiabilities() {
        return this.curLiabilities;
    }

    public double getNetInc() {
        return this.netInc;
    }

    public double getDepEx() {
        return this.depEx;
    }

    public double getIntEx() {
        return this.intEx;
    }

    public double getTaxEx() {
        return this.taxEx;
    }

    public double getDebtRepaid() {
        return this.debtRepaid;
    }

    //setters

    public void setFiscalYear(int year) {
        this.fiscalYear = year;
    }

    public void setAssets(int assets) {
        this.assets = assets;
    }

    public void setLiabil(int liabil) {
        this.liabil = liabil;
    }

    public void setCurAssets(int curAssets) {
        this.curAssets = curAssets;
    }

    public void setCurLiabilities(int curLiabilities) {
        this.curLiabilities = curLiabilities;
    }

    public void setNetInc(int netInc) {
        this.netInc = netInc;
    }

    public void setDepEx(int depEx) {
        this.depEx = depEx;
    }

    public void setIntEx(int intEx) {
        this.intEx = intEx;
    }

    public void setTaxEx(int taxEx) {
        this.taxEx = taxEx;
    }

    public void setDebtRepaid(int debtRepaid) {
        this.debtRepaid = debtRepaid;
    }
}
