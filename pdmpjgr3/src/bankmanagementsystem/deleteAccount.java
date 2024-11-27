package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class deleteAccount extends JFrame implements ActionListener {
    String pin, cardno;
    JButton confirmButton, backButton;

    deleteAccount(String pin, String cardno) {
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
        JLabel titleLabel = new JLabel("DELETE ACCOUNT");
        titleLabel.setBounds(425, 180, 700, 35);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("System", Font.BOLD, 28));
        background.add(titleLabel);

        // Warning message
        JLabel warningLabel = new JLabel("Are you sure you want to delete your account?");
        warningLabel.setBounds(410, 250, 700, 25);
        warningLabel.setForeground(Color.WHITE);
        warningLabel.setFont(new Font("System", Font.PLAIN, 20));
        background.add(warningLabel);

        // Confirm button
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(420, 350, 150, 40);
        confirmButton.setFont(new Font("System", Font.BOLD, 18));
        confirmButton.setBackground(new Color(255, 69, 58)); // Red color for emphasis
        confirmButton.setForeground(Color.BLACK);
        confirmButton.addActionListener(this);
        background.add(confirmButton);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(700, 350, 150, 40);
        backButton.setFont(new Font("System", Font.BOLD, 18));
        backButton.setBackground(new Color(65, 125, 128));
        backButton.setForeground(Color.BLACK);
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
        if (e.getSource() == confirmButton) {
            try {
                Con c = new Con();
                String query = "DELETE FROM account WHERE card_number = '" + cardno + "' AND pin = '" + pin + "'";
                int rowsAffected = c.statement.executeUpdate(query);

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Account deleted successfully.");
                    setVisible(false);
                    new Login(); // Redirect to the login screen
                } else {
                    JOptionPane.showMessageDialog(this, "Account deletion failed. Please try again.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
            }
        } else if (e.getSource() == backButton) {
            setVisible(false);
            new main_class(pin, cardno); // Return to the main menu
        }
    }

    public static void main(String[] args) {
        new deleteAccount("", "");
    }
}
