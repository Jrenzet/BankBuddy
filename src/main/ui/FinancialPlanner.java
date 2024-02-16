package ui;

import model.FinancialProjection;

import java.util.Scanner;

public class FinancialPlanner {

    private Scanner input;
    private FinancialProjection projection;

    public FinancialPlanner() {
        runPlanner();
    }

    //EFFECTS: runs start up screen and takes user input
    private void runPlanner() {

        while (true) {
            System.out.println("Financial Planner Home Page");
            input = new Scanner(System.in);
            options1();
            String userInput = input.nextLine();
            if (userInput.equals("q")) {
                quit();
            } else {
                inputHandlerHome(userInput);
            }
        }
    }

    //EFFECTS: displays user input options on homepage
    private void options1() {
        System.out.println("Press 'l' to edit/input loans.");
        System.out.println("Press 's' to edit/input financial statements.");
        System.out.println("Press 'r' to view analysis reports.");
        System.out.println("Press 'q' to quit.");
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
                reportsScreen();
                break;
            default:
                System.out.println("Please type a valid command\n");
                break;
        }
    }

    private void loanScreen() {
        System.out.println("LOAN");
    }

    private void financialStatementScreen() {
        System.out.println("FSTATE");
    }

    private void reportsScreen() {
        System.out.println("REPORTS");
    }

    private void quit() {
        System.exit(0);
    }


}


//print list will be printing a list of all debt, could print a list of projected and current debt
//possibly ad print financials

//non trivial: printLoan method, if remaining term is less than 12 months a special string is printed