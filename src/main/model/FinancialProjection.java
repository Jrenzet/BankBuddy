package model;

import java.util.ArrayList;
import java.util.List;

// include a field that is a list of debt you can add to repeatedly, this is the x to the y
//non trivial as there will be a method to calcualte dsc and if it is within certian threshold assign color rating

public class FinancialProjection {

    private String name;
    private List<Loan> loans;
    private List<FinancialStatement> financials;


    public FinancialProjection(String name) {
        this.name = name;
        loans = new ArrayList<>();
        financials = new ArrayList<>();
    }





}
