package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PayLoan extends JFrame implements ActionListener {
    JTextField payAmountField;
    JLabel outstandingLoanLabel, balanceLabel;
    JButton payButton, backButton;
    String pin, cardno;
    double totalLoan = 0;

    PayLoan(String pin, String cardno) {
        this.pin = pin;
        this.cardno = cardno;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/atm.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 1550, 830);
        add(background);

        JLabel titleLabel = new JLabel("PAY LOAN");
        titleLabel.setBounds(450, 150, 700, 35);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("System", Font.BOLD, 28));
        background.add(titleLabel);

        outstandingLoanLabel = new JLabel("Outstanding Loan: $0");
        outstandingLoanLabel.setBounds(450, 220, 400, 30);
        outstandingLoanLabel.setForeground(Color.WHITE);
        outstandingLoanLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(outstandingLoanLabel);

        balanceLabel = new JLabel("Current Balance: $0");
        balanceLabel.setBounds(450, 270, 400, 30);
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(balanceLabel);

        JLabel payAmountLabel = new JLabel("Payment Amount:");
        payAmountLabel.setBounds(450, 320, 200, 30);
        payAmountLabel.setForeground(Color.WHITE);
        payAmountLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(payAmountLabel);

        payAmountField = new JTextField();
        payAmountField.setBounds(650, 320, 200, 30);
        background.add(payAmountField);

        payButton = new JButton("Pay");
        payButton.setBounds(450, 400, 150, 35);
        payButton.addActionListener(this);
        background.add(payButton);

        backButton = new JButton("Back");
        backButton.setBounds(650, 400, 150, 35);
        backButton.addActionListener(this);
        background.add(backButton);

        fetchDetails();

        setSize(1550, 830);
        setLocation(0, 0);
        setLayout(null);
        setVisible(true);
    }

    private void fetchDetails() {
        try {
            Con c = new Con();

            // Fetch total loan amount
            String loanQuery = "SELECT SUM(amount) AS totalLoan FROM bank WHERE card_number = ? AND type = 'Loan'";
            var loanStmt = c.connection.prepareStatement(loanQuery);
            loanStmt.setString(1, cardno);
            ResultSet loanRs = loanStmt.executeQuery();

            if (loanRs.next()) {
                totalLoan = loanRs.getDouble("totalLoan");
            }

            // Fetch total paid loans
            String payLoanQuery = "SELECT SUM(amount) AS totalPaidLoan FROM bank WHERE card_number = ? AND type = 'Pay Loan'";
            var payLoanStmt = c.connection.prepareStatement(payLoanQuery);
            payLoanStmt.setString(1, cardno);
            ResultSet payLoanRs = payLoanStmt.executeQuery();

            double totalPaidLoan = 0;
            if (payLoanRs.next()) {
                totalPaidLoan = payLoanRs.getDouble("totalPaidLoan");
            }

            // Calculate outstanding loan
            totalLoan -= totalPaidLoan;

            // Fetch current balance
            double balance = 0;

            // Connect to the database
//            Con c = new Con();

            // Fetch the initial balance from the Account table
            String accountQuery = "SELECT balance FROM Account WHERE card_number = ?";
            PreparedStatement accountStmt = c.connection.prepareStatement(accountQuery);
            accountStmt.setString(1, cardno);
            ResultSet accountRs = accountStmt.executeQuery();

            if (accountRs.next()) {
                balance = accountRs.getDouble("balance");
            }
            String balanceQuery = "SELECT * FROM bank WHERE card_number = ?";
            var balanceStmt = c.connection.prepareStatement(balanceQuery);
            balanceStmt.setString(1, cardno);
            ResultSet balanceRs = balanceStmt.executeQuery();

            while (balanceRs.next()) {
                String type = balanceRs.getString("type");
                double amount = balanceRs.getDouble("amount");

                if (type.equals("Deposit") || type.equals("Loan")) {
                    balance += amount;
                } else if (type.equals("Withdraw") || type.equals("Pay Loan")) {
                    balance -= amount;
                }
            }

            outstandingLoanLabel.setText("Outstanding Loan: $" + totalLoan);
            balanceLabel.setText("Current Balance: $" + balance);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching details: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == payButton) {
            try {
                double payAmount = Double.parseDouble(payAmountField.getText());
                if (payAmount <= 0) {
                    JOptionPane.showMessageDialog(null, "Enter a valid amount.");
                    return;
                }
                if (payAmount > totalLoan) {
                    JOptionPane.showMessageDialog(null, "Payment exceeds outstanding loan.");
                    return;
                }

                Con c = new Con();
                String transactionId = String.valueOf(System.currentTimeMillis());
                String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
                String query = "INSERT INTO bank (transaction_id, card_number, pin, date, type, amount) VALUES (?, ?, ?, ?, 'Pay Loan', ?)";
                var stmt = c.connection.prepareStatement(query);
                stmt.setString(1, transactionId);
                stmt.setString(2, cardno);
                stmt.setString(3, pin);
                stmt.setString(4, date);
                stmt.setDouble(5, payAmount);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Loan payment successful.");
                fetchDetails();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else if (e.getSource() == backButton) {
            setVisible(false);
            new main_class(pin, cardno);
        }
    }

    public static void main(String[] args) {
        new PayLoan("", "");
    }
}
