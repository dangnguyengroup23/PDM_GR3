package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BalanceEnquriy extends JFrame implements ActionListener {
    JLabel balanceLabel;
    JButton backButton;
    String pin, cardno;

    BalanceEnquriy(String pin, String cardno) {
        this.pin = pin;
        this.cardno = cardno;

        // Set up background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/atm.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 1550, 830);
        add(background);

        // Title label
        JLabel titleLabel = new JLabel("Your Current Balance is");
        titleLabel.setBounds(450, 180, 700, 35);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("System", Font.BOLD, 28));
        background.add(titleLabel);

        // Balance label
        balanceLabel = new JLabel();
        balanceLabel.setBounds(450, 250, 700, 35);
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(balanceLabel);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(700, 400, 150, 35);
        backButton.setFont(new Font("System", Font.BOLD, 18));
        backButton.addActionListener(this);
        background.add(backButton);

        fetchBalance();

        // Frame settings
        setSize(1550, 830);
        setLocation(0, 0);
        setLayout(null);
        setVisible(true);
    }

    private void fetchBalance() {
        try {
            double balance = 0;

            // Connect to the database
            Con c = new Con();

            // Fetch the initial balance from the Account table
            String accountQuery = "SELECT balance FROM Account WHERE card_number = ?";
            PreparedStatement accountStmt = c.connection.prepareStatement(accountQuery);
            accountStmt.setString(1, cardno);
            ResultSet accountRs = accountStmt.executeQuery();

            if (accountRs.next()) {
                balance = accountRs.getDouble("balance");
            }

            // Adjust the balance based on transactions in the bank table
            String transactionQuery = "SELECT * FROM bank WHERE card_number = ?";
            PreparedStatement transactionStmt = c.connection.prepareStatement(transactionQuery);
            transactionStmt.setString(1, cardno);
            ResultSet transactionRs = transactionStmt.executeQuery();

            while (transactionRs.next()) {
                String type = transactionRs.getString("type");
                double amount = transactionRs.getDouble("amount");

                if (type.equals("Deposit") || type.equals("Loan")) {
                    balance += amount;
                } else if (type.equals("Withdraw") || type.equals("Pay Loan")) {
                    balance -= amount;
                }
            }

            // Update the balance label
            balanceLabel.setText("$" + String.format("%.2f", balance));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching balance: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Go back to the main menu
        setVisible(false);
        new main_class(pin, cardno);
    }

    public static void main(String[] args) {
        new BalanceEnquriy("", "");
    }
}
