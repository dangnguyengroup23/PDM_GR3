package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Loan extends JFrame implements ActionListener {
    String pin;
    String cardno;
    JButton confirmButton, backButton;
    JTextField loanAmountField, startDateField;
    JLabel interestRateLabel;
    double interestRate = 0;

    Random ran = new Random();
    long first4 =(ran.nextLong() % 9000L) +1000L;
    String first = " " + Math.abs(first4);

    Loan(String pin, String cardno) {
        this.pin = pin;
        this.cardno = cardno;// Lưu giá trị pin

        // Background image setup
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/atm.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 1550, 830);
        add(background);

        // Title
        JLabel label = new JLabel("SELECT LOAN DETAILS");
        label.setBounds(425, 180, 700, 35);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("System", Font.BOLD, 28));
        background.add(label);

        // Loan amount
        JLabel loanAmountLabel = new JLabel("Loan Amount:");
        loanAmountLabel.setBounds(410, 250, 200, 25);
        loanAmountLabel.setForeground(Color.WHITE);
        loanAmountLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(loanAmountLabel);

        loanAmountField = new JTextField();
        loanAmountField.setBounds(660, 250, 200, 30);
        loanAmountField.setFont(new Font("System", Font.PLAIN, 18));
        background.add(loanAmountField);

        // Interest rate (auto-calculated)
        JLabel interestRateTextLabel = new JLabel("Interest Rate (%):");
        interestRateTextLabel.setBounds(410, 300, 200, 25);
        interestRateTextLabel.setForeground(Color.WHITE);
        interestRateTextLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(interestRateTextLabel);

        interestRateLabel = new JLabel("0"); // Hiển thị lãi suất
        interestRateLabel.setBounds(660, 300, 200, 25);
        interestRateLabel.setForeground(Color.WHITE);
        interestRateLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(interestRateLabel);

        // Start date
        JLabel startDateLabel = new JLabel("Start Date (dd-MM-yyyy):");
        startDateLabel.setBounds(410, 350, 250, 25);
        startDateLabel.setForeground(Color.WHITE);
        startDateLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(startDateLabel);

        startDateField = new JTextField(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        startDateField.setBounds(660, 350, 200, 30);
        startDateField.setFont(new Font("System", Font.PLAIN, 18));
        background.add(startDateField);

        // Confirm button
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(420, 400, 150, 40);
        confirmButton.setFont(new Font("System", Font.BOLD, 18));
        confirmButton.addActionListener(this);
        background.add(confirmButton);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(700, 400, 150, 40);
        backButton.setFont(new Font("System", Font.BOLD, 18));
        backButton.addActionListener(this);
        background.add(backButton);

        // Frame settings
        setSize(1550, 830);
        setLocation(0, 0);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String transaction_id  = first;
        String type = "Loan";
        if (e.getSource() == confirmButton) {
            try {
                String loanAmountText = loanAmountField.getText();
                double loanAmount = Double.parseDouble(loanAmountText);

                // Xác định lãi suất
                if (loanAmount < 1000) {
                    interestRate = 0;
                } else if (loanAmount <= 5000) {
                    interestRate = 1;
                } else {
                    interestRate = 2; // Ví dụ: >5000 thì lãi suất 2%
                }

                // Hiển thị lãi suất
                interestRateLabel.setText(String.valueOf(interestRate));

                // Lấy ngày vay
                String startDate = startDateField.getText();

                Con c = new Con();



                String q2 = "insert into Bank values('"+transaction_id+"','"+cardno+"' ,'"+pin+"','"+startDate+"','"+type+"','"+loanAmount+"','"+interestRate+"')";
                c.statement.executeUpdate(q2);




                // Hiển thị thông báo xác nhận
                JOptionPane.showMessageDialog(null, "Loan Details:\n" +
                        "Amount: $" + loanAmount + "\n" +
                        "Interest Rate: " + interestRate + "%\n" +
                        "Start Date: " + startDate + "\n" +
                        "PIN: " + pin + "\n"+
                        "You own: $" + loanAmount);

                // Lưu thông tin vào cơ sở dữ liệu (thực hiện sau nếu cần)
                setVisible(false);
                new main_class(pin,cardno);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid loan amount. Please enter a valid number.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
            }
        } else if (e.getSource() == backButton) {
            setVisible(false);
            new main_class(pin,cardno); // Quay lại màn hình chính
        }
    }
}
