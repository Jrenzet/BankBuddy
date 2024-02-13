package model;

// Represents financial statements having a fiscal year and financial data and results from that year
public class FinancialStatement {

    private int fiscalYear;
    private int assets;
    private int liabilities;
    private int currentAssets;
    private int currentLiabilities;
    private int netIncome;
    private int depreciationExpense;
    private int interestExpense;
    private int incomeTaxExpense;
    private int debtRepaid;

    //REQUIRES: Year be a valid year in the format YYYY
    public FinancialStatement(int year) {
        this.fiscalYear = year;
    }

    //REQUIRES: debtRepaid + interestExpense != 0
    //EFFECTS: calculates debt service coverage ratio (EBITDA / interestExpense + debtRepaid)
    public double calculateDSC() {
        return 0.0; //stub
    }

    //EFFECTS: calculates net income left over after debt repayment
    public int calculateFreeCashFlow() {
        return 0; //stub
    }

    //REQUIRES: currentLiabilities != 0
    //EFFECTS: calculates ratio of current assets to current liabilities
    public double calculateCurrentRatio() {
        return 0.0; //stub
    }

    //EFFECTS: calculates Earnings Before Interest Tax and Depreciation/Amortization
    public int calculateEbitda() {
        return 0; //stub
    }

    //REQUIRES: assets - liabilities != 0
    //EFFECTS: calculates debt to equity ratio (equity = assets - liabilities)
    public double calculateLeverage() {
        return 0.0; //stub
    }

    //EFFECTS: calculates equity
    public int calculateEquity() {
        return 0; //stub
    }

    //getters

    public int getFiscalYear() {
        return this.fiscalYear;
    }

    //setters

    public void setFiscalYear(int year) {
        this.fiscalYear = year;
    }

    public void setAssets(int assets) {
        this.assets = assets;
    }

    public void setLiabilities(int liabilities) {
        this.liabilities = liabilities;
    }

    public void setCurrentAssets(int currentAssets) {
        this.currentAssets = currentAssets;
    }

    public void setCurrentLiabilities(int currentLiabilities) {
        this.currentLiabilities = currentLiabilities;
    }

    public void setNetIncome(int netIncome) {
        this.netIncome = netIncome;
    }

    public void setDepreciationExpense(int depreciationExpense) {
        this.depreciationExpense = depreciationExpense;
    }

    public void setInterestExpense(int interestExpense) {
        this.interestExpense = interestExpense;
    }

    public void setIncomeTaxExpense(int incomeTaxExpense) {
        this.incomeTaxExpense = incomeTaxExpense;
    }

    public void setDebtRepaid(int debtRepaid) {
        this.debtRepaid = debtRepaid;
    }
}
