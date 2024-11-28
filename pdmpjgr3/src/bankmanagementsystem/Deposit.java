package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Random;

public class Deposit extends JFrame implements ActionListener{
    String pin;
    String cardno;
    TextField textField;

    JButton b1, b2;

    Random ran = new Random();
    long first4 =(ran.nextLong() % 9000L) +1000L;
    String first = " " + Math.abs(first4);
    Deposit(String pin, String cardno){
        this.pin = pin;
        this.cardno= cardno;
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/atm.png"));
        Image i2 = i1.getImage().getScaledInstance(1550,830,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0,0,1550,830);
        add(l3);
        
        JLabel label1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(460,180,400,35);
        l3.add(label1);

        textField = new TextField();
        textField.setBackground(new Color(65,125,128));
        textField.setForeground(Color.BLACK);
        textField.setBounds(460,230,320,25);
        textField.setFont(new Font("Raleway", Font.BOLD,22));
        l3.add(textField);

        b1 = new JButton("DEPOSIT");
        b1.setBounds(700,362,150,35);
        b1.setBackground(new Color(65,125,128));
        b1.setForeground(Color.BLACK);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(700,406,150,35);
        b2.setBackground(new Color(65,125,128));
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
        try{
        String amount = textField.getText();
        String transaction_id  = first;
        String type = "Deposit";
        String interest = "null";
        Date date = new Date();
        if(e.getSource() == b1){
            if(textField.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Please enter the amount you want to deposit");
            }else{
                Con c = new Con();
                String q = "insert into Bank values('"+transaction_id+"','"+cardno+"' ,'"+pin+"','"+date+"','"+type+"','"+amount+"','"+interest+"')";
                c.statement.executeUpdate(q);
                JOptionPane.showMessageDialog(null, "$." +amount+ " Deposited successfully");
                setVisible(false);
                new main_class(pin,cardno);
                }
            }else if(e.getSource() == b2){
                setVisible(false);
                new main_class(pin,cardno);
            }
        }catch(Exception E){
            E.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Deposit("","");
    }
}
