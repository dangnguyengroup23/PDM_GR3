package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class PayLoan extends JFrame implements ActionListener {
    String pin;
    String cardno;
    JButton payButton, backButton;
    JTextField payAmountField;
    JLabel outstandingLoanLabel, balanceLabel;
    double totalLoan = 0;
    double balance = 0;

    PayLoan(String pin, String cardno) {
        this.pin = pin;
        this.cardno = cardno;

        // Background image setup
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/atm.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 1550, 830);
        add(background);

        // Title
        JLabel titleLabel = new JLabel("PAY LOAN");
        titleLabel.setBounds(425, 150, 700, 35);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("System", Font.BOLD, 28));
        background.add(titleLabel);

        // Outstanding loan
        JLabel outstandingLoanTextLabel = new JLabel("Outstanding Loan Amount:");
        outstandingLoanTextLabel.setBounds(410, 220, 300, 25);
        outstandingLoanTextLabel.setForeground(Color.WHITE);
        outstandingLoanTextLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(outstandingLoanTextLabel);

        outstandingLoanLabel = new JLabel("$0");
        outstandingLoanLabel.setBounds(710, 220, 200, 25);
        outstandingLoanLabel.setForeground(Color.WHITE);
        outstandingLoanLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(outstandingLoanLabel);

        // Balance
        JLabel balanceTextLabel = new JLabel("Current Balance:");
        balanceTextLabel.setBounds(410, 270, 200, 25);
        balanceTextLabel.setForeground(Color.WHITE);
        balanceTextLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(balanceTextLabel);

        balanceLabel = new JLabel("$0");
        balanceLabel.setBounds(710, 270, 200, 25);
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(balanceLabel);

        // Payment amount
        JLabel payAmountLabel = new JLabel("Payment Amount:");
        payAmountLabel.setBounds(410, 320, 200, 25);
        payAmountLabel.setForeground(Color.WHITE);
        payAmountLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(payAmountLabel);

        payAmountField = new JTextField();
        payAmountField.setBounds(660, 320, 200, 30);
        payAmountField.setFont(new Font("System", Font.PLAIN, 18));
        background.add(payAmountField);

        // Pay button
        payButton = new JButton("Pay");
        payButton.setBounds(420, 400, 150, 40);
        payButton.setFont(new Font("System", Font.BOLD, 18));
        payButton.addActionListener(this);
        background.add(payButton);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(700, 400, 150, 40);
        backButton.setFont(new Font("System", Font.BOLD, 18));
        backButton.addActionListener(this);
        background.add(backButton);

        // Fetch balances
        fetchBalances();

        // Frame settings
        setSize(1550, 830);
        setLocation(0, 0);
        setLayout(null);
        setVisible(true);
    }

    private void fetchBalances() {
        try {
            Con c = new Con();

            // Calculate current balance (reusing logic from BalanceEnquiry)
            ResultSet rs = c.statement.executeQuery("SELECT * FROM bank WHERE card_number = '" + cardno + "'");
            while (rs.next()) {
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");

                // Balance calculation
                if (type.equals("Deposit") || type.equals("Loan")) {
                    balance += amount;
                } else if (type.equals("Withdraw") || type.equals("Pay Loan")) {
                    balance -= amount;
                }

                // Loan calculation
                if (type.equals("Loan")) {
                    totalLoan += amount;
                } else if (type.equals("Pay Loan")) {
                    totalLoan -= amount;
                }
            }

            // Update labels
            outstandingLoanLabel.setText("$" + totalLoan);
            balanceLabel.setText("$" + balance);

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
                    JOptionPane.showMessageDialog(null, "Invalid payment amount. Please enter a positive value.");
                    return;
                }

                if (payAmount > balance) {
                    JOptionPane.showMessageDialog(null, "Insufficient balance to pay this amount.");
                    return;
                }

                if (payAmount > totalLoan) {
                    JOptionPane.showMessageDialog(null, "Payment amount exceeds the outstanding loan.");
                    return;
                }

                // Insert transaction
                Con c = new Con();
                String transactionType = "Pay Loan";
                String transactionId = String.valueOf(System.currentTimeMillis());
                String currentDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

                String query = "INSERT INTO bank (transaction_id, card_number, pin, date, type, amount) " +
                        "VALUES ('" + transactionId + "', '" + cardno + "', '" + pin + "', '" + currentDate + "', '" + transactionType + "', '" + payAmount + "')";
                c.statement.executeUpdate(query);

                // Update loan and balance
                totalLoan -= payAmount;
                balance -= payAmount;

                outstandingLoanLabel.setText("$" + totalLoan);
                balanceLabel.setText("$" + balance);

                JOptionPane.showMessageDialog(null, "Loan payment successful. Remaining loan: $" + totalLoan);

                // Check if loan is fully paid
                if (totalLoan == 0) {
                    JOptionPane.showMessageDialog(null, "Congratulations! You have fully paid off your loan.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid payment amount. Please enter a valid number.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error processing payment: " + ex.getMessage());
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
