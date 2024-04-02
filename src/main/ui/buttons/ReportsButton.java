package ui.buttons;

import model.FinancialProjection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class ReportsButton extends JButton implements ActionListener {

    private final FinancialProjection projection;

    private JPanel reportsPanel;
    private double freeCashFlow;

    private static final DecimalFormat decFormat = new DecimalFormat("0.00");

    private static final Font FONT = new Font("Font", Font.BOLD, 100);

    public static final Color DARK_GREEN = new Color(0, 150, 0);
    public static final Color DARK_YELLOW = new Color(255, 204, 0);
    public static final Color DARK_RED = new Color(204, 0, 0);

    public ReportsButton(FinancialProjection p) {
        super("Reports");
        this.projection = p;
        this.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame reportsPage = new JFrame();
        reportsPanel = new JPanel();
        reportsPanel.setLayout(new BoxLayout(reportsPanel, BoxLayout.Y_AXIS));
        reportsPage.add(reportsPanel);
        reportsPage.setTitle("Reports");
        reportsPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        freeCashFlow = projection.averageEbitda() - projection.totalPayment();

        switch (projection.projectionRating()) {
            case "Green":
                greenDisplay();
                break;
            case "Yellow":
                yellowDisplay();
                break;
            case "Red":
                redDisplay();
                break;
        }

        reportsPage.pack();
        reportsPage.setLocationRelativeTo(null);
        reportsPage.setVisible(true);
    }

    private void greenDisplay() {
        JLabel greenLabel = new JLabel("Green");
        greenLabel.setFont(FONT);
        greenLabel.setForeground(DARK_GREEN);

        JLabel greenDescription = new JLabel(makeString("High"));

        reportsPanel.add(greenLabel);
        reportsPanel.add(greenDescription);
    }

    private void yellowDisplay() {
        JLabel yellowLabel = new JLabel("Yellow");
        yellowLabel.setFont(FONT);
        yellowLabel.setForeground(DARK_YELLOW);

        JLabel yellowDescription = new JLabel(makeString("Moderate"));

        reportsPanel.add(yellowLabel);
        reportsPanel.add(yellowDescription);
    }

    private void redDisplay() {
        JLabel redLabel = new JLabel("Red");
        redLabel.setFont(FONT);
        redLabel.setForeground(DARK_RED);

        JLabel redDescription = new JLabel(makeString("Low"));

        reportsPanel.add(redLabel);
        reportsPanel.add(redDescription);
    }

    public String makeString(String approvalOdds) {
        return "You have a DSC of " + projection.projectedDSC()
                + "x and projected free cash flow of $"
                + decFormat.format(freeCashFlow)
                + ". You have a "
                + approvalOdds
                + " likelihood of approval.";

    }
}
