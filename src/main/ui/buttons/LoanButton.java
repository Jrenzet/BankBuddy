package ui.buttons;

import model.FinancialProjection;
import model.Loan;

import javax.management.RuntimeErrorException;
import javax.swing.*;

import exceptions.LoanDoesNotExistException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//generates all JFrame windows related to adding and editing loans
public class LoanButton extends JButton implements ActionListener {

    private JFrame loanHomePage;

    private JButton addButton;
    private JButton displayButton;
    private JButton editButton;
    private JButton addSubmit;
    private JButton editSubmit;

    private JPanel cardPanel;
    private JPanel menuPanel;

    private JPanel addPanel;
    private JPanel editPanel;
    private JScrollPane displayPanel;

    private DefaultListModel<String> listModel;

    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    private static final String ADDSTRING = "add";
    private static final String EDITSTRING = "edit";
    private static final String DISPLAYSTRING = "display";

    private FinancialProjection projection;

    private JTextField termBox;
    private JTextField interestBox;
    private JTextField balanceBox;
    private JTextField descriptionBox;
    private JComboBox<String> projBox;

    private JComboBox<String> editBox;
    private JTextField newValueBox;
    private JTextField loanNumberBox;

    // EFFECTS: creates the button which initially takes the user to the loan
    // options screen
    public LoanButton(FinancialProjection fp) {
        super("Loans");
        this.projection = fp;

        this.addButton = new JButton("Add Loan");
        this.editButton = new JButton("Edit Loans");
        this.displayButton = new JButton("View Loans");

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        displayButton.addActionListener(this);

        this.addActionListener(this);
    }

    // EFFECTS: creates an action listener for all buttons found in windows opened
    // by the initial LoanButton
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == this) {
                openLoanPage();
            } else if (e.getSource() == addButton) {
                CardLayout c = (CardLayout) cardPanel.getLayout();
                c.show(cardPanel, ADDSTRING);
            } else if (e.getSource() == editButton) {
                CardLayout c = (CardLayout) cardPanel.getLayout();
                c.show(cardPanel, EDITSTRING);
            } else if (e.getSource() == displayButton) {
                CardLayout c = (CardLayout) cardPanel.getLayout();
                c.show(cardPanel, DISPLAYSTRING);
            } else if (e.getSource() == addSubmit) {
                saveLoan();
                resetAddTextBoxes();
            } else if (e.getSource() == editSubmit) {
                editLoan();
                // resetEditTextBoxes();
            }
        } catch (NumberFormatException n) {
            JOptionPane.showMessageDialog(null, "Please format submissions correctly");
        } catch (RuntimeException r) {
            JOptionPane.showMessageDialog(null, "Please complete all fields");
        } catch (LoanDoesNotExistException l) {
            JOptionPane.showMessageDialog(null, "This loan was not found");
        }
    }

    // MODIFIES: loanHomePage
    // EFFECTS: generates first page user sees when they select loan button
    public void openLoanPage() {
        loanHomePage = new JFrame();
        loanHomePage.setTitle("Loan Editor");
        loanHomePage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loanHomePage.setLocationRelativeTo(null);

        initializeAddPanel();
        initializeEditPanel();
        initializeDisplayPanel();

        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(addPanel, ADDSTRING);
        cardPanel.add(editPanel, EDITSTRING);
        cardPanel.add(displayPanel, DISPLAYSTRING);

        menuPanel = new JPanel();
        menuPanel.add(addButton);
        menuPanel.add(editButton);
        menuPanel.add(displayButton);

        loanHomePage.add(menuPanel, BorderLayout.PAGE_START);
        loanHomePage.add(cardPanel, BorderLayout.CENTER);

        loanHomePage.pack();
        loanHomePage.setVisible(true);
    }

    // EFFECTS: sets up all components of the add screen
    public void initializeAddPanel() {

        addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));

        JPanel termPanel = new JPanel();
        termBox = new JTextField(20);
        termPanel.add(new JLabel("Remaining Term in Months"));
        termPanel.add(termBox);

        JPanel interestPanel = new JPanel();
        interestBox = new JTextField(20);
        interestPanel.add(new JLabel("Interest Rate"));
        interestPanel.add(interestBox);

        JPanel balancePanel = new JPanel();
        balanceBox = new JTextField(20);
        balancePanel.add(new JLabel("Remaining Balance"));
        balancePanel.add(balanceBox);

        JPanel descriptionPanel = new JPanel();
        descriptionBox = new JTextField(20);
        descriptionPanel.add(new JLabel("What is this loan for?"));
        descriptionPanel.add(descriptionBox);

        JPanel projPanel = new JPanel();
        String options[] = { "Current", "Projected" };
        projBox = new JComboBox<>(options);
        projPanel.add(new JLabel("Is this a current or projected loan?"));
        projPanel.add(projBox);

        JPanel buttonPanel = new JPanel();
        addSubmit = new JButton("Submit");
        addSubmit.addActionListener(this);
        buttonPanel.add(addSubmit);

        addPanel.add(termPanel);
        addPanel.add(interestPanel);
        addPanel.add(balancePanel);
        addPanel.add(descriptionPanel);
        addPanel.add(projPanel);
        addPanel.add(buttonPanel);

    }

    // EFFECTS: sets up all components of the edit screen
    public void initializeEditPanel() {
        editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));

        JPanel numberPanel = new JPanel();
        numberPanel.add(new JLabel("First type the number of the loan you wish to edit."));

        JPanel numberBoxPanel = new JPanel();
        loanNumberBox = new JTextField(20);
        numberBoxPanel.add(loanNumberBox);

        JPanel fieldPanel = new JPanel();
        fieldPanel.add(new JLabel("Then select the field you wish to edit"));

        JPanel fieldSelectionPanel = new JPanel();
        String options[] = { "Description", "Loan Term", "Interest Rate", "Current Balance", "Current or Projection?" };
        editBox = new JComboBox<>(options);
        fieldSelectionPanel.add(editBox);

        JPanel newValuePanel = new JPanel();
        newValuePanel.add(new JLabel("Finally, input the updated value and hit the submit button"));

        JPanel newValueBoxPanel = new JPanel();
        newValueBox = new JTextField(20);
        newValueBoxPanel.add(newValueBox);

        JPanel submitButtonPanel = new JPanel();
        editSubmit = new JButton("Submit");
        editSubmit.addActionListener(this);
        submitButtonPanel.add(editSubmit);

        editPanel.add(numberPanel);
        editPanel.add(numberBoxPanel);
        editPanel.add(fieldPanel);
        editPanel.add(fieldSelectionPanel);
        editPanel.add(newValuePanel);
        editPanel.add(newValueBoxPanel);
        editPanel.add(submitButtonPanel);
    }

    // EFFECTS: sets up all components of the view loans screen
    public void initializeDisplayPanel() {
        JList<String> list = new JList<>();
        listModel = new DefaultListModel<>();
        for (Loan loan : projection.getLoans()) {
            stringifyLoan(loan);
        }
        list.setModel(listModel);
        list.setVisibleRowCount(5);
        displayPanel = new JScrollPane(list);
        System.out.println("initilaized");
    }

    // EFFECTS: saves a new loan to the projection as per the users inputs
    public void saveLoan() {
        if (descriptionBox.getText().isEmpty() ||
                balanceBox.getText().isEmpty() ||
                interestBox.getText().isEmpty() ||
                termBox.getText().isEmpty()) {
            throw new RuntimeException();
        } else {
            Loan loan = new Loan(descriptionBox.getText());
            loan.setCurrentBalance(Double.valueOf(balanceBox.getText()));
            loan.setInterestRate(Double.valueOf(interestBox.getText()));
            loan.setRemainingTerm(Integer.valueOf(termBox.getText()));
            if (projBox.getSelectedItem() == "Current") {
                loan.setProjection(false);
            } else {
                loan.setProjection(true);
            }
            projection.addLoan(loan);
            stringifyLoan(loan);
        }
    }

    // EFFECTS: sets the text boxes to be empty after submitting
    public void resetAddTextBoxes() {
        descriptionBox.setText("");
        balanceBox.setText("");
        interestBox.setText("");
        termBox.setText("");
    }

    private void resetEditTextBoxes() {
        newValueBox.setText("");
        loanNumberBox.setText("");
        
    }

    private void editLoan() throws LoanDoesNotExistException{
        Loan loanToEdit = null;
        if (newValueBox.getText().isEmpty() || loanNumberBox.getText().isEmpty()) {
            throw new RuntimeException();
        } else {
            for (Loan l: projection.getLoans()) {
                if (projection.getLoans().indexOf(l) + 1 == Integer.valueOf(loanNumberBox.getText())) {
                    loanToEdit = l;
                    break;
                }
            }
            if (loanToEdit == null) {
                throw new LoanDoesNotExistException();
            }
        }
        updateLoan(loanToEdit);
        listModel.removeAllElements();
        initializeDisplayPanel();
        printLoans();
    }

    private void updateLoan(Loan loanToEdit) {
        switch (String.valueOf(editBox.getSelectedItem())) {
            case "Description":
               loanToEdit.setDescription(newValueBox.getText());
                break;
            case "Loan Term":
                loanToEdit.setRemainingTerm(Integer.valueOf(newValueBox.getText()));
                break;
            case "Interest Rate":
                loanToEdit.setInterestRate(Double.valueOf(newValueBox.getText()));
                break;
            case "Current Balance":
                loanToEdit.setCurrentBalance(Integer.valueOf(newValueBox.getText()));
                break;
            case "Current or Projection?":
                if (newValueBox.getText().equalsIgnoreCase("Current")) {
                    loanToEdit.setProjection(false);
                } else if (newValueBox.getText().equalsIgnoreCase("Projection")) {
                    loanToEdit.setProjection(true);
                } else {
                    throw new RuntimeException();
                }
                break;
            
        }
    }

    // EFFECTS: covnerts a loan into strings for display on the view loan screen
    public void stringifyLoan(Loan loan) {
        listModel.addElement("**************************************************");
        listModel.addElement("Loan Number: " + (projection.getLoans().indexOf(loan) + 1));
        if (loan.isProjection()) {
            listModel.addElement("\n **** Projected Loan ****");
        }
        listModel.addElement("\n Description: " + loan.getDescription());
        listModel.addElement("\n Balance: $" + loan.getCurrentBalance());
        listModel.addElement("\n Remaining Term: " + loan.getRemainingTerm() + " Months");
        listModel.addElement("\n Interest Rate: " + loan.getInterestRate() + "%\n");
        listModel.addElement("**************************************************");
    }



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

    public void printLoans() {
        for (Loan l : projection.getLoans()) {
            printLoan(l);
        }
    }

}
