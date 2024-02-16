package model;

import java.lang.Math;

//Represents a loan, with interest rate, term, and balance
public class Loan {

    private int remainingTerm;
    private double interestRate;
    private double currentBalance;
    private boolean isProjection;
    private String description;

    //EFFECTS: initializes loan with description of purpose, and a boolean true if the loan represents a
    // projection, false if it represents a loan that currently exists.
    public Loan(String description, Boolean isProjection) {
        this.description = description;
        this.isProjection = isProjection;
    }

    //REQUIRES: remainingTerm > 0 && interestRate > 0
    //EFFECTS: calculates the current monthly payment on this loan, rounded to the nearest dollar
    public double calculateMonthlyPayment() {
        double result = (this.currentBalance * (this.interestRate / 1200))
                / (1 - Math.pow((1 + (this.interestRate / 1200)), -this.remainingTerm));
        return Math.round(result);
    }

    //REQUIRES: remainingTerm > 0 && interestRate > 0
    //EFFECTS: calculate 12 months of payments if remainingTerm  > 12, remainingTerm # of payments if remainingTerm < 12
    public double calculateAnnualPayment() {
        if (this.remainingTerm > 12) {
            return calculateMonthlyPayment() * 12;
        } else {
            return calculateMonthlyPayment() * this.remainingTerm;
        }
    }

    //getters & setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRemainingTerm() {
        return remainingTerm;
    }

    public void setRemainingTerm(int remainingTerm) {
        this.remainingTerm = remainingTerm;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public boolean isProjection() {
        return isProjection;
    }

    public void setProjection(boolean projection) {
        isProjection = projection;
    }
}
