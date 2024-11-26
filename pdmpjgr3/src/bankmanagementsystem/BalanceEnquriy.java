package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class BalanceEnquriy extends JFrame implements ActionListener{
    JLabel label2;
    JButton b1;
    
    String pin;
    String cardno;
    BalanceEnquriy(String pin,String cardno){
        this.pin = pin;
        this.cardno = cardno;
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/atm.png"));
        Image i2 = i1.getImage().getScaledInstance(1550,830,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0,0,1550,830);
        add(l3);

        JLabel label1 = new JLabel("Your Current Balance is ");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(460,180,700,35);
        l3.add(label1);

        label2 = new JLabel();
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System", Font.BOLD, 16));
        label2.setBounds(460,220,400,35);
        l3.add(label2);
        
        b1 = new JButton("Back");
        b1.setBounds(700,362,150,35);
        b1.setBackground(new Color(65,125,128));
        b1.setForeground(Color.BLACK);
        b1.addActionListener(this);
        l3.add(b1);
        
        double balance = 0;
        try{
            Con c = new Con();
            ResultSet resultSet = c.statement.executeQuery("Select * from bank where card_number = '"+cardno+"'");
            while(resultSet.next()){
                if (resultSet.getString("type").equals("Deposit") || resultSet.getString("type").equals("Loan")) {
                            balance += Double.parseDouble(resultSet.getString("amount"));
                        } else {
                            balance -= Double.parseDouble(resultSet.getString("amount"));
                        }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage());
        }
           
        label2.setText(""+balance);
        
        setLayout(null);
        setSize(1550,1080);
        setLocation(0,0);
        setVisible(true);}
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        new main_class(pin, cardno);
    } 
    public static void main(String[] args) {
        new BalanceEnquriy("","");
    }
    
}
