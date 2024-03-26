package ui.buttons;

import model.FinancialProjection;
import model.Loan;

import javax.swing.*;

import exceptions.LoanDoesNotExistException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//generates all JFrame windows related to adding and editing loans
public class LoanButton extends JButton implements ActionListener {

    private final JButton addButton;
    private final JButton displayButton;
    private final JButton editButton;
    private JButton addSubmit;
    private JButton editSubmit;
    private JButton delete;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JPanel addPanel;
    private JPanel editPanel;
    private JScrollPane displayPanel;

    private DefaultListModel<String> listModel;

    private static final String ADDSTRING = "add";
    private static final String EDITSTRING = "edit";
    private static final String DISPLAYSTRING = "display";

    private final FinancialProjection projection;

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
                cardLayout.show(cardPanel, ADDSTRING);
            } else if (e.getSource() == editButton) {
                cardLayout.show(cardPanel, EDITSTRING);
            } else if (e.getSource() == displayButton) {
                cardLayout.show(cardPanel, DISPLAYSTRING);
            } else if (e.getSource() == addSubmit) {
                saveLoan();
            } else if (e.getSource() == editSubmit) {
                editLoan();
            } else if (e.getSource() == delete) {
                deleteLoan();
            }
        } catch (NumberFormatException n) {
            JOptionPane.showMessageDialog(null, "Please format submissions correctly");
        } catch (RuntimeException r) {
            JOptionPane.showMessageDialog(null, "Please complete all fields");
        } catch (LoanDoesNotExistException l) {
            JOptionPane.showMessageDialog(null, "This loan was not found");
        }
    }

    // MODIFIES: this
    // EFFECTS: generates first page user sees when they select loan button
    public void openLoanPage() {
        JFrame loanHomePage = new JFrame();
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
        cardLayout = (CardLayout) cardPanel.getLayout();

        JPanel menuPanel = new JPanel();
        menuPanel.add(addButton);
        menuPanel.add(editButton);
        menuPanel.add(displayButton);

        loanHomePage.add(menuPanel, BorderLayout.PAGE_START);
        loanHomePage.add(cardPanel, BorderLayout.CENTER);

        loanHomePage.pack();
        loanHomePage.setLocationRelativeTo(null);
        loanHomePage.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up all components of the add screen
    public void initializeAddPanel() {

        addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));

        initializeTextPanels();

        JPanel projPanel = new JPanel();
        String[] options = {"Current", "Projected"};
        projBox = new JComboBox<>(options);
        projPanel.add(new JLabel("Is this a current or projected loan?"));
        projPanel.add(projBox);

        JPanel buttonPanel = new JPanel();
        addSubmit = new JButton("Submit");
        addSubmit.addActionListener(this);
        buttonPanel.add(addSubmit);

        addPanel.add(projPanel);
        addPanel.add(buttonPanel);

    }

    // MODIFIES: this
    // EFFECTS: creates all text input boxes shown on the add loan screen
    public void initializeTextPanels() {
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

        addPanel.add(termPanel);
        addPanel.add(interestPanel);
        addPanel.add(balancePanel);
        addPanel.add(descriptionPanel);
    }


    // MODIFIES: this
    // EFFECTS: sets up all components of the edit screen
    public void initializeEditPanel() {
        editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));

        initializeLoanNumberPanels();

        JPanel fieldPanel = new JPanel();
        fieldPanel.add(new JLabel("Then select the field you wish to edit"));

        JPanel fieldSelectionPanel = new JPanel();
        String[] options = {"Description", "Loan Term", "Interest Rate", "Current Balance", "Current or Projection?"};
        editBox = new JComboBox<>(options);
        fieldSelectionPanel.add(editBox);

        JPanel newValuePanel = new JPanel();
        newValuePanel.add(new JLabel("Finally, input the updated value and hit the submit button"));

        JPanel newValueBoxPanel = new JPanel();
        newValueBox = new JTextField(20);
        newValueBoxPanel.add(newValueBox);

        editPanel.add(fieldPanel);
        editPanel.add(fieldSelectionPanel);
        editPanel.add(newValuePanel);
        editPanel.add(newValueBoxPanel);

        initializeEditButtons();
    }

    // MODIFIES: this
    // EFFECTS: creates buttons shown in edit loan window
    public void initializeEditButtons() {
        JPanel submitButtonPanel = new JPanel();
        editSubmit = new JButton("Submit");
        editSubmit.addActionListener(this);
        submitButtonPanel.add(editSubmit);
        delete = new JButton("Delete");
        delete.addActionListener(this);
        submitButtonPanel.add(delete);

        editPanel.add(submitButtonPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates panels related ot user input of the desired loan number in the edit window
    public void initializeLoanNumberPanels() {
        JPanel numberPanel = new JPanel();
        numberPanel.add(new JLabel("First type the number of the loan you wish to edit"));

        JPanel numberBoxPanel = new JPanel();
        loanNumberBox = new JTextField(20);
        numberBoxPanel.add(loanNumberBox);

        editPanel.add(numberPanel);
        editPanel.add(numberBoxPanel);
    }

    // MODIFIES: this
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
    }

    // MODIFIES: this
    // EFFECTS: saves a new loan to the projection as per the users inputs
    public void saveLoan() {
        if (descriptionBox.getText().isEmpty()
                || balanceBox.getText().isEmpty()
                || interestBox.getText().isEmpty()
                || termBox.getText().isEmpty()) {
            throw new RuntimeException();
        } else {
            Loan loan = new Loan(descriptionBox.getText());
            loan.setCurrentBalance(Double.parseDouble(balanceBox.getText()));
            loan.setInterestRate(Double.parseDouble(interestBox.getText()));
            loan.setRemainingTerm(Integer.parseInt(termBox.getText()));
            loan.setProjection(projBox.getSelectedItem() != "Current");
            projection.addLoan(loan);
            stringifyLoan(loan);
            resetAddTextBoxes();
        }
    }

    // EFFECTS: sets the text boxes to be empty after submitting a new loan
    public void resetAddTextBoxes() {
        descriptionBox.setText("");
        balanceBox.setText("");
        interestBox.setText("");
        termBox.setText("");
    }

    // EFFECTS: sets the text boxes to be empty after submitting an edit
    public void resetEditTextBoxes() {
        newValueBox.setText("");
        loanNumberBox.setText("");
    }

    // EFFECTS: searches for loan number user submitted, if it exists update it to
    // user input
    public void editLoan() throws LoanDoesNotExistException {
        Loan loanToEdit = searchLoan();
        if (newValueBox.getText().isEmpty()) {
            throw new RuntimeException();
        }
        updateLoan(loanToEdit);
        refreshLoanList();
        resetEditTextBoxes();
    }

    //EFFECTS: clears list of loan description strings and updates it with current list
    public void refreshLoanList() {
        listModel.removeAllElements();
        for (Loan loan : projection.getLoans()) {
            stringifyLoan(loan);
        }
    }

    // MODIFIES: loanToEdit
    // EFFECTS: updates loanToEdit according to user submitted field and value
    private void updateLoan(Loan loanToEdit) {
        switch (String.valueOf(editBox.getSelectedItem())) {
            case "Description":
                loanToEdit.setDescription(newValueBox.getText());
                break;
            case "Loan Term":
                loanToEdit.setRemainingTerm(Integer.parseInt(newValueBox.getText()));
                break;
            case "Interest Rate":
                loanToEdit.setInterestRate(Double.parseDouble(newValueBox.getText()));
                break;
            case "Current Balance":
                loanToEdit.setCurrentBalance(Integer.parseInt(newValueBox.getText()));
                break;
            case "Current or Projection?":
                if (newValueBox.getText().equalsIgnoreCase("Current")) {
                    loanToEdit.setProjection(false);
                } else if (newValueBox.getText().equalsIgnoreCase("Projection")) {
                    loanToEdit.setProjection(true);
                } else {
                    throw new NumberFormatException();
                }
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: deletes loan returned by searchLoan()
    public void deleteLoan() throws LoanDoesNotExistException {
        Loan loanToDelete = searchLoan();
        int loanNumber = projection.getLoans().indexOf(loanToDelete) + 1;
        projection.removeLoan(loanToDelete);
        refreshLoanList();
        resetEditTextBoxes();
        JOptionPane.showMessageDialog(null, "Loan number "
                + loanNumber + " deleted successfully");
    }

    //EFFECTS: returns loan matching the number inputted by the user in loanNumberBox, if one exists
    public Loan searchLoan() throws LoanDoesNotExistException {
        Loan foundLoan = null;
        if (loanNumberBox.getText().isEmpty()) {
            throw new LoanDoesNotExistException();
        } else {
            for (Loan l : projection.getLoans()) {
                if (projection.getLoans().indexOf(l) + 1 == Integer.parseInt(loanNumberBox.getText())) {
                    foundLoan = l;
                    break;
                }
            }
            if (foundLoan == null) {
                throw new LoanDoesNotExistException();
            }
        }
        return foundLoan;
    }

    // EFFECTS: converts a loan into strings and adds to display on the view loan
    // screen
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

}
