package ui.buttons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatementButton extends JButton {

    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    public StatementButton() {
        super("Financial Statements");
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openStatementPage();
            }
        });

    }

    private void openStatementPage() {
        JFrame loanPage = new JFrame();
        loanPage.setTitle("Financial Statement Editor");
        loanPage.setSize(WIDTH, HEIGHT);
        loanPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loanPage.setLocationRelativeTo(null);

        loanPage.setVisible(true);
    }
}
