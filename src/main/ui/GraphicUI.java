package ui;

import model.EventLog;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import model.Event;

// Creates and runs the main GUI for this application
public class GraphicUI implements ActionListener, WindowListener {

    private final FinancialProjection projection;

    private final JFrame mainWindow;
    private static final String TITLE = "BankBuddy";

    private JMenuItem save;
    private JMenuItem load;

    private LoanButton loanButton;
    private StatementButton statementButton;

    private static final String JSON_PATH = "./data/financialProjection.json";

    public GraphicUI() {
        this.projection = new FinancialProjection();
        mainWindow = new JFrame(TITLE);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.addWindowListener(this);


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

        statementButton = new StatementButton(this.projection);
        buttonPanel.add(statementButton);

        ReportsButton reportsButton = new ReportsButton(this.projection);
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
            try {
                loanButton.refreshLoanList();
            } catch (NullPointerException n) {
                //do nothing, loan button not yet clicked and loan display list is null
            }
            try {
                statementButton.refreshStatementList();
            } catch (NullPointerException n) {
                //do nothing, statement button not yet clicked and loan display list is null
            }
            JOptionPane.showMessageDialog(null, "File loaded from " + JSON_PATH + " successfully.");
        } catch (IOException i) {
            System.out.println("Invalid file name.");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event:EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
