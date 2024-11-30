package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Random;

public class Withdrawl extends JFrame implements ActionListener {
    Random ran = new Random();
    long first4 =(ran.nextLong() % 9000L) +1000L;
    String first = " " + Math.abs(first4);
    String pin;
    String cardno;
    TextField textField;
    JButton b1, b2;

    Withdrawl(String pin,String cardno){
        this.pin = pin;
        this.cardno = cardno;
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/atm.png"));
        Image i2 = i1.getImage().getScaledInstance(1550,830,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0,0,1550,830);
        add(l3);

        JLabel label1 = new JLabel("MAXIMUM WITHDRAWAL IS $.5,000,000");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(460,180,700,35);
        l3.add(label1);

        JLabel label2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System", Font.BOLD, 16));
        label2.setBounds(460,220,400,35);
        l3.add(label2);


        textField = new TextField();
        textField.setForeground(Color.WHITE);
        textField.setBounds(460,260,320,25);
        textField.setFont(new Font("Raleway", Font.BOLD,22));
        l3.add(textField);

        b1 = new JButton("WITHDRAW");
        b1.setBounds(700,362,150,35);
        b1.setForeground(Color.BLACK);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(700,406,150,35);
        b2.setForeground(Color.BLACK);
        b2.addActionListener(this);
        l3.add(b2);

        setLayout(null);
        setSize(1550,1080);
        setLocation(0,0);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == b1){
        try{
            String amount = textField.getText();
            Date date = new Date();
            String interest = "null";
            String transaction_id  = first;
            String type = "Withdraw";
            if (textField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter the Amount you want to withdraw");
            }else{
                Con c = new Con();
                ResultSet resultSet = c.statement.executeQuery("select * from bank where card_number = '" + cardno + "'");
                    double balance = 0;
                    while (resultSet.next()) {
                        if (resultSet.getString("type").equals("Deposit") || resultSet.getString("type").equals("Loan")) {
                            balance += Double.parseDouble(resultSet.getString("amount"));
                        } else if (resultSet.getString("type").equals("Withdraw")|| resultSet.getString("type").equals("Pay Loan") ){
                            balance -= Double.parseDouble(resultSet.getString("amount"));
                        }
                    }
                    if (balance < Double.parseDouble(amount)) {
                        JOptionPane.showMessageDialog(null, "Insuffient Balance");
                        return;
                    }

                String q = "insert into Bank values('"+transaction_id+"','"+cardno+"' ,'"+pin+"','"+date+"','"+type+"','"+amount+"','"+interest+"')";
                c.statement.executeUpdate(q);
                    JOptionPane.showMessageDialog(null, "$. " + amount + " Debited Successfully");
                    new main_class(pin,cardno);
                    setVisible(false);

            }
        }catch(Exception E){
            JOptionPane.showMessageDialog(null, "An error occurred: " + E.getMessage());
            
            }
        }else if(e.getSource() == b2){
            setVisible(false);
            new FastCash(pin,cardno);
        }
    }
        
    
    public static void main(String[] args) {
        
    }
}
