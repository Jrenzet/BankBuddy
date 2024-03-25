package ui.buttons;

import model.FinancialProjection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//generates all JFrame windows related to adding and editing loans
public class LoanButton extends JButton {

    private JFrame loanHomePage;
    private ActionListener listener;

    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    private FinancialProjection projection;

    //EFFECTS: creates the button which initially takes the user to the loan options screen
    public LoanButton(FinancialProjection fp) {
        super("Loans");
        this.projection = fp;
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoanPage();
            }
        });
    }



    //MODIFIES: loanHomePage
    //EFFECTS: generates first page user sees when they select loan button
    private void openLoanPage() {
        loanHomePage = new JFrame();
        loanHomePage.setLayout(new FlowLayout());
        loanHomePage.setTitle("Loan Editor");
        loanHomePage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loanHomePage.setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        panel.add(addLoanButton(loanHomePage));
        panel.add(editLoanButton(loanHomePage));
        panel.add(displayLoanButton(loanHomePage));

        loanHomePage.add(panel);

        loanHomePage.pack();
        loanHomePage.setVisible(true);
    }

    //EFFECTS: creates the button that takes the user to the display loans page
    private JButton displayLoanButton(JFrame homePage) {
        JButton displayButton = new JButton("View Loans");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePage.dispose();
                openDisplayLoanPage();
            }
        });
        return displayButton;
    }

    //EFFECTS: opens the window for displaying existing loans
    private void openDisplayLoanPage() {
        JFrame displayScreen = new JFrame("Display Loans");
        displayScreen.setSize(800, 600);
        displayScreen.setVisible(true);
        displayScreen.setLocationRelativeTo(null);
    }

    //EFFECTS: creates the button that takes the user to the add loans page
    private JButton addLoanButton(JFrame homePage) {
        JButton addButton = new JButton("Add Loan");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePage.dispose();
                openAddLoanPage();
            }
        });
        return addButton;
    }

    //EFFECTS: opens the window for adding loans
    private void openAddLoanPage() {
        JFrame addScreen = new JFrame("Add Loan");
        addScreen.setSize(800, 600);
        addScreen.setVisible(true);
        addScreen.setLocationRelativeTo(null);

    }

    //EFFECTS: creates the button that takes the user to the edit loans page
    private JButton editLoanButton(JFrame homePage) {
        JButton editButton = new JButton("Edit Loan");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePage.dispose();
                openEditLoanPage();
            }
        });
        return editButton;
    }

    //EFFECTS: opens the window for editing loans
    private void openEditLoanPage() {

    }
}
