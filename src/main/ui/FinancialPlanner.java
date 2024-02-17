package ui;

import model.FinancialProjection;
import model.Loan;

import java.util.Scanner;

public class FinancialPlanner {

    private Scanner input;
    private FinancialProjection projection;
    private Loan selectedLoan;

    public FinancialPlanner() {
        projection = new FinancialProjection();
        runPlanner();
    }

    public void setup() {
        Loan testLoan = new Loan("car");
        testLoan.setCurrentBalance(12000);
        testLoan.setRemainingTerm(120);
        testLoan.setInterestRate(5.5);
        testLoan.setProjection(true);
        projection.addLoan(testLoan);
        testLoan = new Loan("house");
        testLoan.setCurrentBalance(250000);
        testLoan.setRemainingTerm(240);
        testLoan.setInterestRate(6);
        testLoan.setProjection(false);
        projection.addLoan(testLoan);
    }

    //EFFECTS: runs start up screen and takes user input
    private void runPlanner() {
        while (true) {
            setup();
            System.out.println("Financial Planner Home Page");
            input = new Scanner(System.in);
            options1();
            inputHandlerHome(takeInputMenu());
        }
    }

    //EFFECTS: displays user input options on homepage
    private void options1() {
        System.out.println("Press 'l' to edit/input loans.");
        System.out.println("Press 's' to edit/input financial statements.");
        System.out.println("Press 'r' to view analysis reports.");
        System.out.println("Press 'q' to quit.");
    }

    //MODIFIES: this
    //EFFECTS: takes user input and returns it, unless input is q then quit program
    private String takeInputMenu() {
        String userInput = input.nextLine();
        if (userInput.equals("q")) {
            quit();
        }
        return userInput;
    }

    //MODIFIES: this
    //EFFECTS: takes user string input and returns it
    private String takeInputString() {
        return input.nextLine();
    }

    //MODIFIES: this
    //EFFECTS: takes user integer input and returns it
    private int takeInputInt() {
        int userInput = input.nextInt();
        input.nextLine();
        return userInput;
    }

    //MODIFIES: this
    //EFFECTS: takes user double input and returns it
    private double takeInputDouble() {
        double userInput = input.nextDouble();
        input.nextLine();
        return userInput;
    }

    //EFFECTS: handles user input from home screen
    private void inputHandlerHome(String input) {
        switch (input) {
            case "l":
                loanScreen();
                break;
            case "s":
                financialStatementScreen();
                break;
            case "r":
                printLoans();
                break;
            default:
                System.out.println("Please type a valid command\n");
                break;
        }
    }

    //EFFECTS: displays options for loan screen
    private void loanScreen() {
        options2();
        inputHandlerLoan(takeInputMenu());
    }

    //EFFECTS: displays user input options on loan screen
    private void options2() {
        System.out.println("Press 'a' to add new loan.");
        System.out.println("Press 'e' to edit existing loans.");
        System.out.println("Press 'q' to quit.");
    }


    //EFFECTS: handles user input from home screen
    private void inputHandlerLoan(String input) {
        switch (input) {
            case "a":
                addLoanScreen();
                break;
            case "e":
                editLoanScreen();
                break;
            default:
                System.out.println("Please type a valid command\n");
                loanScreen();
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds user created loan to projection
    private void addLoanScreen() {
        System.out.println("What is this loan for?");
        selectedLoan = new Loan(takeInputString());
        handleLoan(selectedLoan);
        projection.addLoan(selectedLoan);
    }

    //MODIFIES: loan
    //EFFECTS: completes the steps for a user to change a loans fields
    private void handleLoan(Loan loan) {
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
    private void editLoanScreen() {
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


    private void financialStatementScreen() {
        System.out.println("FSTATE");
    }

    private void reportsScreen() {
        System.out.println("REPORTS");
    }

    //EFFECTS: ends program
    private void quit() {
        System.exit(0);
    }

    //EFFECTS: prints summary of all existing loans
    private void printLoans() {
        for (Loan l : projection.getLoans()) {
            printLoan(l);
        }
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
}


//print list will be printing a list of all debt, could print a list of projected and current debt
//possibly ad print financials

//non trivial: printLoan method, if remaining term is less than 12 months a special string is printed