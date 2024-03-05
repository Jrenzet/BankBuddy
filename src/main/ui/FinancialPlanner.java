package ui;

import model.FinancialProjection;
import model.FinancialStatement;
import model.Loan;

import java.sql.SQLOutput;
import java.util.Scanner;

// Displays console prompts for the user to input loans, financial statements, and run projections
// Source Credits: JsonSerializationDemo project from CPSC 210 repository
public class FinancialPlanner {

    private static final String SAVE_PATH = "./data/financialProjection.json";
    private final Scanner input;
    private final FinancialProjection projection;
    private Loan selectedLoan;
    private FinancialStatement selectedStatement;
    boolean running;

    //EFFECTS: constructs a financial planner and runs the ui
    public FinancialPlanner() {
        projection = new FinancialProjection();
        input = new Scanner(System.in);
        running = true;
        runPlanner();
    }

    //EFFECTS: runs home screen and takes user input
    public void runPlanner() {
        while (running) {
            System.out.println("Financial Planner Home Page");
            options1();
            String input = takeInputString();
            if (input.equals("q")) {
                running = false;
            } else {
                inputHandlerHome(input);
            }
        }
    }

    //EFFECTS: displays user input options on home screen
    public void options1() {
        System.out.println("Press 'l' to edit/input loans.");
        System.out.println("Press 'f' to edit/input financial statements.");
        System.out.println("Press 'r' to view analysis reports.");
        System.out.println("Press 's' to save current projection");
        System.out.println("Press 'lp' to load saved projection");
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
            case "f":
                financialStatementScreen();
                break;
            case "r":
                reportsScreen();
                break;
            case "s":
                saveProjection();
                break;
            case "lp":
                loadProjection();
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
        System.out.println("Press 'r' to remove a loan.");
        System.out.println("Press 'm' to exit to menu.");
    }

    //EFFECTS: handles user input from loan screen
    public void inputHandlerLoan(String input) {
        switch (input) {
            case "a":
                addLoanScreen();
                break;
            case "e":
                editLoanScreen();
                break;
            case "r":
                remove("loan");
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

    //REQUIRES: user inputs int for loan number
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
        System.out.println("Press 'r' to remove a statement.");
        System.out.println("Press 'm' to exit to menu.");
    }

    //EFFECTS: handles user input from financial statement screen
    public void inputHandlerStatement(String input) {
        switch (input) {
            case "a":
                addStatementScreen();
                break;
            case "e":
                editStatementScreen();
                break;
            case "r":
                remove("statement");
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

    //REQUIRES: user inputs int for statement number
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

    //REQUIRES: user inputs int
    //MODIFIES: this
    //EFFECTS: updates statement year in statements according to user input
    public void updateStatementYear() {
        System.out.println("Input updated year:");
        selectedStatement.setFiscalYear(takeInputInt());
        System.out.println("Year updated to " + selectedStatement.getFiscalYear());
    }

    //REQUIRES: user inputs double
    //MODIFIES: this
    //EFFECTS: updates statement net income in statements according to user input
    public void updateStatementIncome() {
        System.out.println("Input updated income:");
        selectedStatement.setNetInc(takeInputDouble());
        System.out.println("Income updated to $" + selectedStatement.getNetInc());
    }

    //REQUIRES: user inputs double
    //MODIFIES: this
    //EFFECTS: updates statement depreciation expense in statements according to user input
    public void updateStatementDep() {
        System.out.println("Input updated depreciation:");
        selectedStatement.setDepExp(takeInputDouble());
        System.out.println("Depreciation expense updated to $" + selectedStatement.getDepExp());
    }

    //REQUIRES: user inputs double
    //MODIFIES: this
    //EFFECTS: updates statement interest expense in statements according to user input
    public void updateStatementInt() {
        System.out.println("Input updated interest:");
        selectedStatement.setIntExp(takeInputDouble());
        System.out.println("Interest expense updated to $" + selectedStatement.getIntExp());
    }

    //REQUIRES: user inputs double
    //MODIFIES: this
    //EFFECTS: updates statement tax expense in statements according to user input
    public void updateStatementTax() {
        System.out.println("Input updated tax:");
        selectedStatement.setTaxExp(takeInputDouble());
        System.out.println("Tax expense updated to $" + selectedStatement.getTaxExp());
    }

    //REQUIRES: user inputs double
    //MODIFIES: this
    //EFFECTS: updates statement principle paid in statements according to user input
    public void updateStatementPrin() {
        System.out.println("Input updated principle:");
        selectedStatement.setTaxExp(takeInputDouble());
        System.out.println("Principal repaid updated to $" + selectedStatement.getPrincipleRepaid());
    }

    //REQUIRES: user inputs integer for year, doubles for all else
    //MODIFIES: this
    //EFFECTS: adds financial statement to statements with users inputted values
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

    //EFFECTS displays user input options on report printing menu
    public void options4() {
        System.out.println("Press 'p' to print projection report");
        System.out.println("Press 'l' to print loan report.");
        System.out.println("Press 'm' to exit to menu.");
    }

    //EFFECTS: handles user input on report printing menu
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

    //REQUIRES: projection.totalPayment != 0
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

    // prompts user to input any key, giving them a chance to read over what was last printed
    public void pauseForUser() {
        System.out.println("Press any key to continue");
        takeInputString();
    }

    //REQUIRES: user inputs int && (removeType == "loan" || removeType == "statement")
    //MODIFIES: this
    //EFFECTS: removes a loan or statement from projection
    public void remove(String removeType) {
        System.out.println("Enter the number of the " + removeType + " to be removed.");
        while (true) {
            int userInput = takeInputInt() - 1;
            if (removeType.equals("loan")) {
                try {
                    projection.removeLoan(projection.getLoans().get(userInput));
                } catch (IndexOutOfBoundsException i) {
                    System.out.println("Please enter valid loan number.");
                    continue;
                }
                break;
            }
            if (removeType.equals("statement")) {
                try {
                    projection.removeStatement(projection.getStatements().get(userInput));
                } catch (IndexOutOfBoundsException i) {
                    System.out.println("Please enter valid statement number.");
                    continue;
                }
                break;
            }
        }
        System.out.println(removeType + " removed.");
    }

    //EFFECTS: saves current projection to ./data/financialProjection.json
    private void saveProjection() {

    }

    //MODIFIES: this.projection
    //EFFECTS: loads projection from ./data/financialProjection.json
    private void loadProjection() {
    }
}

