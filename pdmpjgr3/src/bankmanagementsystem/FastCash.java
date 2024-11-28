package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Random;

public class FastCash extends JFrame implements ActionListener{
    JButton b1,b2,b3,b4,b5,b6,b8,b7;
    String pin;
    String cardno;
    Random ran = new Random();
    long first4 =(ran.nextLong() % 9000L) +1000L;
    String first = " " + Math.abs(first4);
    FastCash(String pin, String cardno){
        this.pin = pin;
        this.cardno = cardno;
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/atm.png"));
        Image i2 = i1.getImage().getScaledInstance(1550,830,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0,0,1550,830);
        add(l3);

        JLabel label = new JLabel("SELECT WITHDRAWL AMOUNT");
        label.setBounds(425 ,180,700,35);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("System",Font.BOLD,28));
        l3.add(label);

        b1 = new JButton("$. 100");
        b1.setForeground(Color.BLACK);
        b1.setBackground(new Color(65,125,128));
        b1.setBounds(410,274,150,35);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("$. 500");
        b2.setForeground(Color.BLACK);
        b2.setBackground(new Color(65,125,128));
        b2.setBounds(700,274,150,35);
        b2.addActionListener(this);
        l3.add(b2);

        b3 = new JButton("$. 1000");
        b3.setForeground(Color.BLACK);
        b3.setBackground(new Color(65,125,128));
        b3.setBounds(410,318,150,35);
        b3.addActionListener(this);
        l3.add(b3);

        b4 = new JButton("$. 2000");
        b4.setForeground(Color.BLACK);
        b4.setBackground(new Color(65,125,128));
        b4.setBounds(700,318,150,35);
        b4.addActionListener(this);
        l3.add(b4);

        b5 = new JButton("$. 5000");
        b5.setForeground(Color.BLACK);
        b5.setBackground(new Color(65,125,128));
        b5.setBounds(410,362,150,35);
        b5.addActionListener(this);
        l3.add(b5);

        b6 = new JButton("$. 10000");
        b6.setForeground(Color.BLACK);
        b6.setBackground(new Color(65,125,128));
        b6.setBounds(700,362,150,35);
        b6.addActionListener(this);
        l3.add(b6);

        b7 = new JButton("BACK");
        b7.setForeground(Color.BLACK);
        b7.setBackground(new Color(65,125,128));
        b7.setBounds(700,406,150,35);
        b7.addActionListener(this);
        l3.add(b7);

        b8 = new JButton("ENTER MONEY");
        b8.setForeground(Color.BLACK);
        b8.setBackground(new Color(65,125,128));
        b8.setBounds(410,406,150,35);
        b8.addActionListener(this);
        l3.add(b8);
        
        setLayout(null);        
        setSize(1550,1080);
        setLocation(0,0);
        setVisible(true);

    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        String transaction_id  = first;
        String type = "Withdraw";
        String interest = "null";

        if(e.getSource()== b8){
            setVisible(false);
            new Withdrawl(pin,cardno);
        } else if(e.getSource() == b7){
            setVisible(false);
            new main_class(pin,cardno);
        }else{
            String amount = ((JButton)e.getSource()).getText().substring(3);
            Con c = new Con();
            Date date = new Date();
            try{
            ResultSet resultSet = c.statement.executeQuery("Select * from bank where card_number = '"+cardno+"'");
            double balance = 0;
            while(resultSet.next()){
                if (resultSet.getString("type").equals("Deposit") || resultSet.getString("type").equals("Loan")) {
                    balance += Double.parseDouble(resultSet.getString("amount"));
                } else if (resultSet.getString("type").equals("Withdraw")|| resultSet.getString("type").equals("Loan Payment") ){
                    balance -= Double.parseDouble(resultSet.getString("amount"));
                }
            }
            
            if(e.getSource() != b7 && balance < Double.parseDouble(amount)){
                JOptionPane.showMessageDialog(null,"Insufficient Balance");
                return;
                }
                String q = "insert into Bank values('"+transaction_id+"','"+cardno+"' ,'"+pin+"','"+date+"','"+type+"','"+amount+"','"+interest+"')";
                c.statement.executeUpdate(q);
                JOptionPane.showMessageDialog(null,"$. " + amount + "Debited Successfully");
            }catch(Exception E){
                JOptionPane.showMessageDialog(null, "An error occurred: " + E.getMessage());
            }
            
            setVisible(false);
            new main_class(pin,cardno);
        }
    }
    public static void main(String[] args) {
        new FastCash("","");
    }
}
