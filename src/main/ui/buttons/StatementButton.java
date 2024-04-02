package ui.buttons;

import exceptions.DuplicateStatementException;
import model.FinancialProjection;
import model.FinancialStatement;

import javax.swing.*;

import exceptions.ItemDoesNotExistException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

//generates all JFrame windows related to adding and editing financial statements
public class StatementButton extends JButton implements ActionListener {

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

    private JTextField yearBox;
    private JTextField incomeBox;
    private JTextField depBox;
    private JTextField intBox;
    private JTextField taxBox;
    private JTextField prinBox;

    private JComboBox<String> editBox;
    private JTextField newValueBox;
    private JTextField statementYearBox;

    private static final DecimalFormat decFormat = new DecimalFormat("0.00");

    // EFFECTS: creates the button which initially takes the user to the statement
    // options screen
    public StatementButton(FinancialProjection fp) {
        super("Financial Statements");
        this.projection = fp;

        this.addButton = new JButton("Add Financial Statement");
        this.editButton = new JButton("Edit Financial Statements");
        this.displayButton = new JButton("View All Statements");

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        displayButton.addActionListener(this);

        this.addActionListener(this);
    }

    // EFFECTS: creates an action listener for all buttons found in windows opened
    // by the initial StatementButton
    public void actionPerformed(ActionEvent e) {
        try {
            actionHandlers(e);
        } catch (NumberFormatException n) {
            JOptionPane.showMessageDialog(null, "Please format submissions correctly");
        } catch (RuntimeException r) {
            JOptionPane.showMessageDialog(null, "Please complete all fields");
        } catch (ItemDoesNotExistException l) {
            JOptionPane.showMessageDialog(null, "This statement was not found");
        } catch (DuplicateStatementException d) {
            JOptionPane.showMessageDialog(null, "A statement with this fiscal year "
                    + "already exists");
        }
    }

    public void actionHandlers(ActionEvent e) throws ItemDoesNotExistException, DuplicateStatementException {
        if (e.getSource() == this) {
            openStatementPage();
        } else if (e.getSource() == addButton) {
            cardLayout.show(cardPanel, ADDSTRING);
        } else if (e.getSource() == editButton) {
            cardLayout.show(cardPanel, EDITSTRING);
        } else if (e.getSource() == displayButton) {
            cardLayout.show(cardPanel, DISPLAYSTRING);
        } else if (e.getSource() == addSubmit) {
            saveStatement();
        } else if (e.getSource() == editSubmit) {
            editStatement();
        } else if (e.getSource() == delete) {
            deleteStatement();
        }
    }

    // MODIFIES: this
    // EFFECTS: generates first page user sees when they select statement button
    public void openStatementPage() {
        JFrame statementHomePage = new JFrame();
        statementHomePage.setTitle("Financial Statement Editor");
        statementHomePage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        statementHomePage.setLocationRelativeTo(null);

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

        statementHomePage.add(menuPanel, BorderLayout.PAGE_START);
        statementHomePage.add(cardPanel, BorderLayout.CENTER);

        statementHomePage.pack();
        statementHomePage.setLocationRelativeTo(null);
        statementHomePage.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up all components of the add screen
    public void initializeAddPanel() {

        addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));

        JPanel yearPanel = new JPanel();
        yearBox = new JTextField(20);
        yearPanel.add(new JLabel("Fiscal Year"));
        yearPanel.add(yearBox);

        JPanel incomePanel = new JPanel();
        incomeBox = new JTextField(20);
        incomePanel.add(new JLabel("Net Income"));
        incomePanel.add(incomeBox);

        addPanel.add(yearPanel);
        addPanel.add(incomePanel);

        initializeTextPanels();

        JPanel buttonPanel = new JPanel();
        addSubmit = new JButton("Submit");
        addSubmit.addActionListener(this);
        buttonPanel.add(addSubmit);

        addPanel.add(buttonPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates all text input boxes shown on the add statement screen
    public void initializeTextPanels() {

        JPanel depPanel = new JPanel();
        depBox = new JTextField(20);
        depPanel.add(new JLabel("Depreciation Expense"));
        depPanel.add(depBox);

        JPanel intPanel = new JPanel();
        intBox = new JTextField(20);
        intPanel.add(new JLabel("Interest Expense"));
        intPanel.add(intBox);

        JPanel taxPanel = new JPanel();
        taxBox = new JTextField(20);
        taxPanel.add(new JLabel("Tax Expense"));
        taxPanel.add(taxBox);

        JPanel prinPanel = new JPanel();
        prinBox = new JTextField(20);
        prinPanel.add(new JLabel("Principal Repaid"));
        prinPanel.add(prinBox);

        addPanel.add(depPanel);
        addPanel.add(intPanel);
        addPanel.add(taxPanel);
        addPanel.add(prinPanel);
    }


    // MODIFIES: this
    // EFFECTS: sets up all components of the edit screen
    public void initializeEditPanel() {
        editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));

        initializeStatementNumberPanels();

        JPanel fieldPanel = new JPanel();
        fieldPanel.add(new JLabel("Then select the field you wish to edit"));

        JPanel fieldSelectionPanel = new JPanel();
        String[] options = {"Fiscal Year", "Net Income", "Depreciation Expense", "Interest Expense",
                "Tax Expense", "Principal Repaid"};
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
    // EFFECTS: creates buttons shown in edit statement window
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
    // EFFECTS: creates panels related to user input of the desired statement year in the edit window
    public void initializeStatementNumberPanels() {
        JPanel numberPanel = new JPanel();
        numberPanel.add(new JLabel("First type the year of the statement you wish to edit"));

        JPanel numberBoxPanel = new JPanel();
        statementYearBox = new JTextField(20);
        numberBoxPanel.add(statementYearBox);

        editPanel.add(numberPanel);
        editPanel.add(numberBoxPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up all components of the view statements screen
    public void initializeDisplayPanel() {
        JList<String> list = new JList<>();
        listModel = new DefaultListModel<>();
        for (FinancialStatement s : projection.getStatements()) {
            stringifyStatement(s);
        }
        list.setModel(listModel);
        list.setVisibleRowCount(5);
        displayPanel = new JScrollPane(list);
    }

    // MODIFIES: this
    // EFFECTS: saves a new statement to the projection as per the users inputs
    public void saveStatement() throws DuplicateStatementException {
        if (intBox.getText().isEmpty()
                || depBox.getText().isEmpty()
                || incomeBox.getText().isEmpty()
                || yearBox.getText().isEmpty()
                || taxBox.getText().isEmpty()
                || prinBox.getText().isEmpty()) {
            throw new RuntimeException();
        }
        for (FinancialStatement fs : projection.getStatements()) {
            if (Integer.parseInt(yearBox.getText()) == fs.getFiscalYear()) {
                throw new DuplicateStatementException();
            }
        }
        FinancialStatement statement = new FinancialStatement(Integer.parseInt(yearBox.getText()));
        statement.setNetInc(Double.parseDouble(incomeBox.getText()));
        statement.setDepExp(Double.parseDouble(depBox.getText()));
        statement.setIntExp(Double.parseDouble(intBox.getText()));
        statement.setTaxExp(Double.parseDouble(taxBox.getText()));
        statement.setPrincipleRepaid(Double.parseDouble(prinBox.getText()));
        projection.addStatement(statement);
        stringifyStatement(statement);
        resetAddTextBoxes();
    }

    // EFFECTS: sets the text boxes to be empty after submitting a new statement
    public void resetAddTextBoxes() {
        intBox.setText("");
        depBox.setText("");
        incomeBox.setText("");
        yearBox.setText("");
        taxBox.setText("");
        prinBox.setText("");
    }

    // EFFECTS: sets the text boxes to be empty after submitting an edit
    public void resetEditTextBoxes() {
        newValueBox.setText("");
        statementYearBox.setText("");
    }

    // EFFECTS: searches for statement year user submitted, if it exists update it to
    // user input
    public void editStatement() throws ItemDoesNotExistException {
        FinancialStatement statementToEdit = searchStatement();
        if (newValueBox.getText().isEmpty()) {
            throw new RuntimeException();
        }
        updateStatement(statementToEdit);
        refreshStatementList();
        resetEditTextBoxes();
    }

    //EFFECTS: clears list of statement description strings and updates it with current list
    public void refreshStatementList() {
        listModel.removeAllElements();
        for (FinancialStatement statement : projection.getStatements()) {
            stringifyStatement(statement);
        }
    }

    // MODIFIES: statementToEdit
    // EFFECTS: updates statementToEdit according to user submitted field and value
    private void updateStatement(FinancialStatement statementToEdit) {
        switch (String.valueOf(editBox.getSelectedItem())) {
            case "Fiscal Year":
                statementToEdit.setFiscalYear(Integer.parseInt(newValueBox.getText()));
                break;
            case "Net Income":
                statementToEdit.setNetInc(Double.parseDouble(newValueBox.getText()));
                break;
            case "Depreciation Expense":
                statementToEdit.setDepExp(Double.parseDouble(newValueBox.getText()));
                break;
            case "Interest Expense":
                statementToEdit.setIntExp(Double.parseDouble(newValueBox.getText()));
                break;
            case "Tax Expense":
                statementToEdit.setTaxExp(Double.parseDouble(newValueBox.getText()));
                break;
            case "Principal Repaid":
                statementToEdit.setPrincipleRepaid(Double.parseDouble(newValueBox.getText()));
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: deletes statement returned by searchStatement()
    public void deleteStatement() throws ItemDoesNotExistException {
        FinancialStatement statementToDelete = searchStatement();
        int fiscalYear = statementToDelete.getFiscalYear();
        projection.removeStatement(statementToDelete);
        refreshStatementList();
        resetEditTextBoxes();
        JOptionPane.showMessageDialog(null, "Financial statement for fiscal year "
                + fiscalYear + " deleted successfully");
    }

    //EFFECTS: returns statement matching the number inputted by the user in statementNumberBox, if one exists
    public FinancialStatement searchStatement() throws ItemDoesNotExistException {
        FinancialStatement foundStatement = null;
        if (statementYearBox.getText().isEmpty()) {
            throw new ItemDoesNotExistException();
        } else {
            for (FinancialStatement fs : projection.getStatements()) {
                if (fs.getFiscalYear() == Integer.parseInt(statementYearBox.getText())) {
                    foundStatement = fs;
                    break;
                }
            }
            if (foundStatement == null) {
                throw new ItemDoesNotExistException();
            }
        }
        return foundStatement;
    }

    // EFFECTS: converts a statement into strings and adds to display on the view statement screen
    public void stringifyStatement(FinancialStatement fs) {
        listModel.addElement("**************************************************");
        listModel.addElement("Fiscal Year: " + (fs.getFiscalYear()));
        listModel.addElement("\n Net Income: $" + decFormat.format(fs.getNetInc()));
        listModel.addElement("\n Depreciation: $" + decFormat.format(fs.getDepExp()));
        listModel.addElement("\n Interest Expense: $" + decFormat.format(fs.getIntExp()));
        listModel.addElement("\n Tax Expense: $" + decFormat.format(fs.getTaxExp()));
        listModel.addElement("\n Principal Repaid: $" + decFormat.format(fs.getPrincipleRepaid()));
        listModel.addElement("\n EBITDA: $" + decFormat.format(fs.calcEbitda()));
        listModel.addElement("**************************************************");
    }
}
