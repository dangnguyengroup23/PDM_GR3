package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class mini extends JFrame implements ActionListener {
    String pin;
    String cardno;
    JButton button;

    mini(String pin,String cardno) {
        this.pin = pin;
        this.cardno = cardno;

        getContentPane().setBackground(new Color(224, 224, 224));
        setSize(460, 600);
        setLocation(20, 20);
        setLayout(null);

        JLabel label2 = new JLabel("Group 3");
        label2.setFont(new Font("System", Font.BOLD, 15));
        label2.setBounds(200, 20, 200, 20);
        add(label2);

        JLabel label3 = new JLabel();
        label3.setBounds(20, 50, 300, 20);
        add(label3);

        try {
            Con c = new Con();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM login WHERE pin = '" + pin + "'");
            while (resultSet.next()) {
                label3.setText("Card Number: " + resultSet.getString("card_number").substring(0, 4)
                        + "XXXXXXXX" + resultSet.getString("card_number").substring(12));
            }
        } catch (Exception E) {
            E.printStackTrace();
        }

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 100, 410, 400);
        add(scrollPane);


        try {
            double balance = 0;
            Con c = new Con();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "'");
            while (resultSet.next()) {
                textArea.append(resultSet.getString("date") + "    " + resultSet.getString("type") + "    $"
                        + resultSet.getString("amount") + "\n");

                if (resultSet.getString("type").equals("Deposit") || resultSet.getString("type").equals("Loan") ) {
                    balance += Double.parseDouble(resultSet.getString("amount"));
                } else {
                    balance -= Double.parseDouble(resultSet.getString("amount"));
                }
            }
            textArea.append("Your Total Balance is $. " + balance + "\n\n"); // Cập nhật tổng số dư
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Con c = new Con();
            ResultSet loanResultSet = c.statement.executeQuery("SELECT * FROM loan WHERE pin = '" + pin + "'");

            textArea.append("--- Loan Details ---\n");

            if (!loanResultSet.isBeforeFirst()) { // Không có dữ liệu trong ResultSet
                textArea.append("No active loans.\n");
            } else {
                while (loanResultSet.next()) {

                    textArea.setText(textArea.getText().replace("No active loans.\n", ""));

                    textArea.append(loanResultSet.getString("date") + "    Loan Amount: $"
                            + loanResultSet.getString("amount") + "    Interest: "
                            + loanResultSet.getString("interest") + "%\n");
                }
            }

        } catch (Exception E) {
            E.printStackTrace();
        }

        try {
            Con c = new Con();
            ResultSet payLoanResultSet = c.statement.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "' AND type = 'Pay Loan'");
            textArea.append("\n--- Loan Payments ---\n");

            if (!payLoanResultSet.isBeforeFirst()) { // No data in ResultSet
                textArea.append("No loan payments found.\n");
            } else {
                while (payLoanResultSet.next()) {
                    textArea.append(payLoanResultSet.getString("date") + "    Paid: $"
                            + payLoanResultSet.getString("amount") + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        button = new JButton("Exit");
        button.setBounds(20, 520, 100, 25);
        button.addActionListener(this);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        add(button);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent E) {
        setVisible(false);
        new main_class(pin,cardno);
    }

    public static void main(String[] args) {
        new mini("","");
    }
}
