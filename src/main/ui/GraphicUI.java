package ui;

import model.FinancialProjection;
import ui.buttons.LoanButton;
import ui.buttons.ReportsButton;
import ui.buttons.StatementButton;
import ui.menu.FileMenu;
import ui.menu.ProgramMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Source Credits: LongFormProblemStarters project from CPSC 210 repository
// Creates and runs the main GUI for this application
public class GraphicUI {

    private FinancialProjection projection;

    private final JFrame mainWindow;
    private static final String TITLE = "Financial Planner";
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    private FileMenu file;
    private ProgramMenu program;

    public GraphicUI() {
        this.projection = new FinancialProjection();
        mainWindow = new JFrame(TITLE);
        mainWindow.setSize(WIDTH, HEIGHT);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);

        loadMenu();
        loadStartScreen();
        loadButtons();

        mainWindow.setVisible(true);



    }

    //EFFECTS: creates buttons on home screen and adds them to the display
    public void loadButtons() {

        JPanel buttonPanel = new JPanel();

        LoanButton loanButton = new LoanButton(this.projection);
        buttonPanel.add(loanButton);

        ReportsButton reportsButton = new ReportsButton();
        buttonPanel.add(reportsButton);

        StatementButton statementButton = new StatementButton();
        buttonPanel.add(statementButton);

        buttonPanel.setBackground(Color.lightGray);
        mainWindow.add(buttonPanel, BorderLayout.SOUTH);

    }

    //EFFECTS: creates sidebar and adds to main display
    public void loadSidebar() {
        JTabbedPane sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);
        JPanel statementsTab = new JPanel();
        sidebar.add(statementsTab, 0);
        sidebar.setTitleAt(0, "Financial Statements");
        mainWindow.add(sidebar);
    }

    //EFFECTS: loads menu options
    public void loadMenu() {

        file = new FileMenu("File");
        program = new ProgramMenu("Program");

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(program);

        mainWindow.setJMenuBar(menuBar);
    }

    //EFFECTS: adds home screen graphic to be displayed on start up
    public void loadStartScreen() {
        ImageIcon homeImage =
                new ImageIcon(new ImageIcon("./data/homeScreenImage.jpg")
                        .getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));
        mainWindow.add(new JLabel(homeImage), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton loanButton = new JButton();
        buttonPanel.setBackground(Color.lightGray);
        buttonPanel.add(new JLabel("Welcome"));
        mainWindow.add(buttonPanel, BorderLayout.SOUTH);
    }


}
