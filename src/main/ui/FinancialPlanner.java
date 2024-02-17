package ui;

import model.FinancialProjection;
import model.FinancialStatement;
import model.Loan;

import java.util.Scanner;

public class FinancialPlanner {

    private Scanner input;
    private FinancialProjection projection;
    private Loan selectedLoan;
    private FinancialStatement selectedStatement;

    public FinancialPlanner() {
        projection = new FinancialProjection();
        input = new Scanner(System.in);
        runPlanner();
    }

    //EFFECTS: runs start up screen and takes user input
    public void runPlanner() {
        while (true) {
            System.out.println("Financial Planner Home Page");
            options1();
            inputHandlerHome(takeInputString());
        }
    }

    //EFFECTS: displays user input options on homepage
    public void options1() {
        System.out.println("Press 'l' to edit/input loans.");
        System.out.println("Press 's' to edit/input financial statements.");
        System.out.println("Press 'r' to view analysis reports.");
        System.out.println("Press 'q' to quit.");
    }

    //MODIFIES: this
    //EFFECTS: takes user string input and returns it
    public String takeInputString() {
        return input.nextLine();
    }

    //MODIFIES: this
    //EFFECTS: takes user integer input and returns it
    public int takeInputInt() {
        int userInput = input.nextInt();
        input.nextLine();
        return userInput;
    }

    //MODIFIES: this
    //EFFECTS: takes user double input and returns it
    public double takeInputDouble() {
        double userInput = input.nextDouble();
        input.nextLine();
        return userInput;
    }

    //EFFECTS: handles user input from home screen
    public void inputHandlerHome(String input) {
        switch (input) {
            case "l":
                loanScreen();
                break;
            case "s":
                financialStatementScreen();
                break;
            case "r":
                reportsScreen();
                break;
            case "q":
                System.exit(0);
                break;
            default:
                System.out.println("Please type a valid command\n");
                break;
        }
    }

    //EFFECTS: initializes loan screen
    public void loanScreen() {
        options2();
        inputHandlerLoan(takeInputString());
    }

    //EFFECTS: displays user input options on loan screen
    public void options2() {
        System.out.println("Press 'a' to add new loan.");
        System.out.println("Press 'e' to edit existing loans.");
        System.out.println("Press 'm' to exit to menu.");
    }


    //EFFECTS: handles user input from home screen
    public void inputHandlerLoan(String input) {
        switch (input) {
            case "a":
                addLoanScreen();
                break;
            case "e":
                editLoanScreen();
                break;
            case "m":
                runPlanner();
                break;
            default:
                System.out.println("Please type a valid command\n");
                loanScreen();
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds user created loan to projection
    public void addLoanScreen() {
        System.out.println("What is this loan for?");
        selectedLoan = new Loan(takeInputString());
        handleLoan(selectedLoan);
        projection.addLoan(selectedLoan);
    }

    //MODIFIES: loan
    //EFFECTS: completes the steps for a user to change a loans fields
    public void handleLoan(Loan loan) {
        while (true) {
            System.out.println("Is this an existing loan (e) or projected loan (p)?");
            String answer = takeInputString();
            switch (answer) {
                case "e":
                    loan.setProjection(false);
                    break;
                case "p":
                    loan.setProjection(true);
                    break;
                default:
                    System.out.println("Please type a valid command\n");
                    continue;
            }
            break;
        }
        System.out.println("Enter the loan term in months:");
        loan.setRemainingTerm(takeInputInt());
        System.out.println("Enter the annual interest rate:");
        loan.setInterestRate(takeInputDouble());
        System.out.println("Enter the loan balance:");
        loan.setCurrentBalance(takeInputDouble());
    }

    //MODIFIES: this
    //EFFECTS: allows user to select and edit an existing loan
    public void editLoanScreen() {
        System.out.println("Enter number of loan to be edited:");
        int loanNumberInput;
        while (true) {
            loanNumberInput = takeInputInt() - 1;
            try {
                selectedLoan = projection.getLoans().get(loanNumberInput);
            } catch (IndexOutOfBoundsException i) {
                System.out.println("Please enter valid loan number.");
                continue;
            }
            System.out.println("Now editing loan number " + (loanNumberInput + 1) + ".");
            break;
        }
        System.out.println("Enter New Description: ");
        selectedLoan.setDescription(takeInputString());
        handleLoan(selectedLoan);
        System.out.println("Loan Editing Successful.");
    }

    //EFFECTS: initializes financial statement screen
    public void financialStatementScreen() {
        options3();
        inputHandlerStatement(takeInputString());
    }

    //EFFECTS: displays user input options on financial statement screen
    public void options3() {
        System.out.println("Press 'a' to add new statement.");
        System.out.println("Press 'e' to edit existing statements.");
        System.out.println("Press 'm' to exit to menu.");
    }


    //EFFECTS: handles user input from home screen
    public void inputHandlerStatement(String input) {
        switch (input) {
            case "a":
                addStatementScreen();
                break;
            case "e":
                editStatementScreen();
                break;
            case "m":
                runPlanner();
                break;
            default:
                System.out.println("Please type a valid command\n");
                financialStatementScreen();
                break;
        }
    }

    //EFFECTS: execute statement editing
    public void editStatementScreen() {
        System.out.println("Enter number of statement to be edited:");
        while (true) {
            int statementNumberInput = takeInputInt() - 1;
            try {
                selectedStatement = projection.getStatements().get(statementNumberInput);
            } catch (IndexOutOfBoundsException i) {
                System.out.println("Please enter valid statement number.");
                continue;
            }
            System.out.println("Now editing statement number " + (statementNumberInput + 1) + ".");
            break;
        }
        while (true) {
            System.out.println("Which field do you want to edit?");
            System.out.println("Type one of: 'year', 'income', 'depreciation', 'interest', 'tax', or 'principle'");
            System.out.println("Press 'm' to exit back to menu.");
            String statementChoice = takeInputString();
            if (statementChoice.equals("m")) {
                break;
            } else {
                editStatementHandler(statementChoice);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: allows user to select which field they want to edit in the statement and edit it
    public void editStatementHandler(String statementField) {
        switch (statementField) {
            case "year":
                updateStatementYear();
                break;
            case "income":
                updateStatementIncome();
                break;
            case "depreciation":
                updateStatementDep();
                break;
            case "interest":
                updateStatementInt();
                break;
            case "tax":
                updateStatementTax();
                break;
            case "principle":
                updateStatementPrin();
                break;
            default:
                System.out.println("Please enter valid input.\n");
        }
    }

    public void updateStatementYear() {
        System.out.println("Input updated year:");
        selectedStatement.setFiscalYear(takeInputInt());
        System.out.println("Year updated to " + selectedStatement.getFiscalYear());
    }

    public void updateStatementIncome() {
        System.out.println("Input updated income:");
        selectedStatement.setNetInc(takeInputDouble());
        System.out.println("Income updated to $" + selectedStatement.getNetInc());
    }

    public void updateStatementDep() {
        System.out.println("Input updated depreciation:");
        selectedStatement.setDepExp(takeInputDouble());
        System.out.println("Depreciation expense updated to $" + selectedStatement.getDepExp());
    }

    public void updateStatementInt() {
        System.out.println("Input updated interest:");
        selectedStatement.setIntExp(takeInputDouble());
        System.out.println("Interest expense updated to $" + selectedStatement.getIntExp());
    }

    public void updateStatementTax() {
        System.out.println("Input updated tax:");
        selectedStatement.setTaxExp(takeInputDouble());
        System.out.println("Tax expense updated to $" + selectedStatement.getTaxExp());
    }

    public void updateStatementPrin() {
        System.out.println("Input updated principle:");
        selectedStatement.setTaxExp(takeInputDouble());
        System.out.println("Principal repaid updated to $" + selectedStatement.getPrincipleRepaid());
    }

    public void addStatementScreen() {
        System.out.println("Enter Statement Year: ");
        selectedStatement = new FinancialStatement(takeInputInt());
        System.out.println("Enter Net Income: ");
        selectedStatement.setNetInc(takeInputDouble());
        System.out.println("Enter Depreciation Expense: ");
        selectedStatement.setDepExp(takeInputDouble());
        System.out.println("Enter Interest Expense: ");
        selectedStatement.setIntExp(takeInputDouble());
        System.out.println("Enter Income Tax Expense: ");
        selectedStatement.setTaxExp(takeInputDouble());
        System.out.println("Enter Principal Repaid: ");
        selectedStatement.setPrincipleRepaid(takeInputDouble());
        projection.addStatement(selectedStatement);
        System.out.println("Statement added successfully.");
    }

    //EFFECTS: initializes report printing screen
    public void reportsScreen() {
        options4();
        inputHandlerReports(takeInputString());
    }


    public void options4() {
        System.out.println("Press 'p' to print projection report");
        System.out.println("Press 'l' to print loan report.");
        System.out.println("Press 'm' to exit to menu.");
    }

    public void inputHandlerReports(String input) {
        switch (input) {
            case "p":
                printProjection();
                break;
            case "l":
                printLoans();
                break;
            case "m":
                runPlanner();
                break;
            default:
                reportsScreen();
        }
    }

    //EFFECTS: prints summary of all existing loans
    public void printLoans() {
        for (Loan l : projection.getLoans()) {
            printLoan(l);
        }
        pauseForUser();
    }

    //EFFECTS: prints summary of a single loan
    public void printLoan(Loan printedLoan) {
        System.out.println("Loan Number: " + (projection.getLoans().indexOf(printedLoan) + 1));
        if (printedLoan.isProjection()) {
            System.out.println("**** Projected Loan ****");
        }
        System.out.println("Description: " + printedLoan.getDescription());
        System.out.println("Balance: $" + printedLoan.getCurrentBalance());
        System.out.println("Remaining Term: " + printedLoan.getRemainingTerm() + " Months");
        System.out.println("Interest Rate: " + printedLoan.getInterestRate() + "%\n");
    }

    //EFFECTS: prints summary of current projection
    public void printProjection() {
        System.out.println("****** PROJECTION SUMMARY ******\n");
        System.out.println("Projected Annual Income: $" + projection.averageEbitda());
        System.out.println("Projected Annual Payments: $" + projection.totalPayment());
        System.out.println("Projected DSC: " + projection.projectedDSC());
        String rating = projection.projectionRating();
        switch (rating) {
            case "Green":
                System.out.println(rating + " rating, HIGH likelihood of approval.\n");
                break;
            case "Yellow":
                System.out.println(rating + " rating, MEDIUM likelihood of approval.\n");
                break;
            case "Red":
                System.out.println(rating + " rating, LOW likelihood of approval.\n");
                break;
            default:
                break;
        }
        pauseForUser();
    }

    public void pauseForUser() {
        System.out.println("Press any key to continue");
        takeInputString();
    }
}

