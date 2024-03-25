package ui.buttons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsButton extends JButton {

    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    public ReportsButton() {
        super("Reports");
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openReportsPage();
            }
        });

    }

    private void openReportsPage() {
        JFrame loanPage = new JFrame();
        loanPage.setTitle("Reports");
        loanPage.setSize(WIDTH, HEIGHT);
        loanPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loanPage.setLocationRelativeTo(null);

        loanPage.setVisible(true);
    }
}
