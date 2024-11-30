package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class mini extends JFrame {
    JLabel cardLabel, transactionsLabel, balanceLabel;
    String cardno, pin;

    mini(String pin, String cardno) {
        this.pin = pin;
        this.cardno = cardno;

        // Frame title and layout
        setTitle("Mini Statement");
        setSize(500, 600);
        setLocation(100, 100);
        setLayout(null);
        getContentPane().setBackground(new Color(248, 248, 248)); // Light blue background

        // Header Section
        JLabel header = new JLabel("Mini Statement");
        header.setBounds(150, 20, 300, 40);
        header.setFont(new Font("Serif", Font.BOLD, 28));
        header.setForeground(Color.black); // Dark blue
        add(header);

        // Card Number Section
        cardLabel = new JLabel("Card Number: " + maskCardNumber(cardno));
        cardLabel.setBounds(30, 80, 400, 30);
        cardLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        cardLabel.setForeground(Color.BLACK);
        add(cardLabel);

        // Transactions Section (Scrollable)
        transactionsLabel = new JLabel();
        transactionsLabel.setVerticalAlignment(SwingConstants.TOP); // Align top
        transactionsLabel.setFont(new Font("Courier New", Font.PLAIN, 14));
        transactionsLabel.setForeground(new Color(70, 70, 70)); // Gray

        JScrollPane scrollPane = new JScrollPane(transactionsLabel);
        scrollPane.setBounds(30, 130, 430, 300);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Add border
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        // Current Balance Section
        balanceLabel = new JLabel();
        balanceLabel.setBounds(30, 450, 430, 30);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setForeground(new Color(34, 139, 34)); // Green
        add(balanceLabel);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(200, 500, 100, 35);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            setVisible(false);
            new main_class(pin, cardno);
        });
        add(backButton);

        // Fetch and Display Transactions & Balance
        fetchTransactionsAndBalance();

        setVisible(true);
    }

    private void fetchTransactionsAndBalance() {
        try {
            Con c = new Con();
            String query = "SELECT * FROM bank WHERE card_number = ? ORDER BY date DESC";
            var stmt = c.connection.prepareStatement(query);
            stmt.setString(1, cardno);
            ResultSet rs = stmt.executeQuery();

            StringBuilder transactions = new StringBuilder("<html><table width='100%'>");
            transactions.append("<tr><th align='left'>Date</th><th align='left'>Type</th><th align='right'>Amount</th></tr>");

            double balance = 0;


            String accountQuery = "SELECT balance FROM Account WHERE card_number = ?";
            PreparedStatement accountStmt = c.connection.prepareStatement(accountQuery);
            accountStmt.setString(1, cardno);
            ResultSet accountRs = accountStmt.executeQuery();

            if (accountRs.next()) {
                balance = accountRs.getDouble("balance");
            }

            while (rs.next()) {
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                String date = rs.getString("date");

                // Update balance
                if (type.equals("Deposit") || type.equals("Loan")) {
                    balance += amount;
                } else if (type.equals("Withdraw") || type.equals("Pay Loan")) {
                    balance -= amount;
                }

                // Append transaction row
                transactions.append("<tr><td>").append(date).append("</td>")
                        .append("<td>").append(type).append("</td>")
                        .append("<td align='right'>$").append(amount).append("</td></tr>");
            }

            transactions.append("</table></html>");
            transactionsLabel.setText(transactions.toString());
            balanceLabel.setText("Current Balance: $" + String.format("%.2f", balance));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching transactions: " + e.getMessage());
        }
    }

    private String maskCardNumber(String cardNumber) {
        return "XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4);
    }

    public static void main(String[] args) {
        new mini("", "");
    }
}
