package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Pin extends JFrame implements ActionListener {
    JButton b1,b2;
    
    JPasswordField p1,p2;
    String pin;
    String cardno;
    Pin(String pin, String cardno){
        this.pin = pin;
        this.cardno = cardno;
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/atm.png"));
        Image i2 = i1.getImage().getScaledInstance(1550,830,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0,0,1550,830);
        add(l3);
        
        JLabel label1 = new JLabel("CHANGE YOUR PIN");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(430,180,400,35);
        l3.add(label1);
        
        JLabel label2 = new JLabel("NEW PIN: ");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System", Font.BOLD, 16));
        label2.setBounds(430,220,150,35);
        l3.add(label2);
        
        p1 = new JPasswordField();
        p1.setForeground(Color.WHITE);
        p1.setBounds(600,220,180,25);
        p1.setFont(new Font("Raleway", Font.BOLD,22));
        l3.add(p1);
        
        JLabel label3 = new JLabel("RE-ENTER NEW PIN: ");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("System", Font.BOLD, 16));
        label3.setBounds(430,250,400,35);
        l3.add(label3);
        
        
        p2 = new JPasswordField();
        p2.setForeground(Color.WHITE);
        p2.setBounds(600,255,180,25);
        p2.setFont(new Font("Raleway", Font.BOLD,22));
        l3.add(p2 );
        
        b1 = new JButton("CHANGE");
        b1.setBounds(700,362,150,35);
        b1.setForeground(Color.BLACK);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(700,406,150,35);
        b2.setForeground(Color.BLACK);
        b2.addActionListener(this);
        l3.add(b2);
        setSize(1550,1080);
        setLayout(null);
        setLocation(0,0);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        
        try{
            
            String pin1 = p1.getText();
            String pin2 = p2.getText();
            
            if(!pin1.equals(pin2)){
                JOptionPane.showMessageDialog(null,"PIN does not match.Please enter PIN again");
                return;
            }
            if(e.getSource() == b1){
                if(p1.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Enter NEW PIN");
                    return;
                }
                
                if(p2.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Re-Enter NEW PIN ");
                    return;
                }
                
                Con c = new Con();
                String q1 = "update BANK set pin = '"+pin1+"' where pin = '"+pin+"' AND card_number = '"+cardno+"'";
                String q2 = "update LOGIN set pin = '"+pin1+"' where pin = '"+pin+"' AND card_number = '"+cardno+"'";
                String q3 = "update ACCOUNT set pin = '"+pin1+"' where pin = '"+pin+"' AND card_number = '"+cardno+"'";
                
                c.statement.executeUpdate(q1);
                c.statement.executeUpdate(q2);
                c.statement.executeUpdate(q3);
                
                JOptionPane.showMessageDialog(null,"PIN change successfully");
                setVisible(false);
                new main_class(pin,cardno);
            }else if(e.getSource() == b2){
                new main_class(pin,cardno);
                setVisible(false);
            }
            
        }catch(Exception E){
            E.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Pin("","");
        
    }
    
}
