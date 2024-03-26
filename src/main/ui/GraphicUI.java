package ui;

import model.FinancialProjection;
import model.FinancialStatement;
import model.Loan;
import persistance.JsonReader;
import persistance.JsonWriter;
import ui.buttons.LoanButton;
import ui.buttons.ReportsButton;
import ui.buttons.StatementButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Source Credits: LongFormProblemStarters project from CPSC 210 repository
// Creates and runs the main GUI for this application
public class GraphicUI implements ActionListener {

    private final FinancialProjection projection;

    private final JFrame mainWindow;
    private static final String TITLE = "BankBuddy";

    private JMenuItem save;
    private JMenuItem load;

    LoanButton loanButton;

    private static final String JSON_PATH = "./data/financialProjection.json";

    public GraphicUI() {
        this.projection = new FinancialProjection();
        mainWindow = new JFrame(TITLE);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        loadMenu();
        loadStartScreen();
        loadButtons();

        mainWindow.pack();
        mainWindow.setLocationRelativeTo(null);

        mainWindow.setVisible(true);

    }

    //EFFECTS: creates buttons on home screen and adds them to the display
    public void loadButtons() {

        JPanel buttonPanel = new JPanel();

        loanButton = new LoanButton(this.projection);
        buttonPanel.add(loanButton);

        StatementButton statementButton = new StatementButton();
        buttonPanel.add(statementButton);

        ReportsButton reportsButton = new ReportsButton();
        buttonPanel.add(reportsButton);

        buttonPanel.setBackground(Color.lightGray);
        mainWindow.add(buttonPanel, BorderLayout.SOUTH);

    }

    //EFFECTS: loads menu options
    public void loadMenu() {

        JMenu file = new JMenu("File");
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");

        save.addActionListener(this);
        load.addActionListener(this);

        file.add(save);
        file.add(load);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);

        mainWindow.setJMenuBar(menuBar);
    }

    //EFFECTS: adds home screen graphic to be displayed on start up
    public void loadStartScreen() {
        ImageIcon homeImage =
                new ImageIcon(new ImageIcon("./data/BankBuddy.png")
                        .getImage());
        mainWindow.add(new JLabel(homeImage), BorderLayout.CENTER);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            saveProjection();
        } else if (e.getSource() == load) {
            loadProjection();
        }
    }

    //EFFECTS: saves current projection to ./data/financialProjection.json
    public void saveProjection() {
        JsonWriter writer = new JsonWriter(JSON_PATH);
        try {
            writer.open();
            writer.write(projection);
            writer.close();
            JOptionPane.showMessageDialog(null, "File saved to " + JSON_PATH + " successfully.");
        } catch (IOException i) {
            System.out.println("Invalid file name.");
        }
    }

    //MODIFIES: this.projection
    //EFFECTS: loads projection from ./data/financialProjection.json
    public void loadProjection() {
        try {
            JsonReader reader = new JsonReader(JSON_PATH);
            FinancialProjection loadedProjection = reader.read();
            for (Loan l : loadedProjection.getLoans()) {
                this.projection.addLoan(l);
            }
            for (FinancialStatement s : loadedProjection.getStatements()) {
                this.projection.addStatement(s);
            }
            loanButton.refreshLoanList();
            JOptionPane.showMessageDialog(null, "File loaded from " + JSON_PATH + " successfully.");
        } catch (IOException i) {
            System.out.println("Invalid file name.");
        }
    }

}
