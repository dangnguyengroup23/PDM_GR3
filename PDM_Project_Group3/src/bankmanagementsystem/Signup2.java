package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup2 extends JFrame implements ActionListener {
    JComboBox comboBox,comboBox2,comboBox3,comboBox4;
    JTextField textPan,textAadhar;
    JRadioButton r1,r2, e1,e2;
    JButton next;
    String formno;
    
    Signup2(String formno){
         super("APPLICATION FORM");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/bank1.png"));
        Image i2 = i1.getImage().getScaledInstance(100,100,1);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(100,5,100,100);
        add(image);

        this.formno = formno;

        JLabel l1 = new JLabel("Page 2:");
        l1.setFont(new Font("Raleway", Font.BOLD,22));
        l1.setForeground(Color.black);
        l1.setBounds(450,30,600,40);
        add(l1);

        JLabel l2 = new JLabel("Additonal Details");
        l2.setFont(new Font("Raleway", Font.BOLD,22)); 
        l2.setForeground(Color.black);
        l2.setBounds(390,60,600,40);
        add(l2);

        JLabel l3 = new JLabel("Religion :");
        l3.setFont(new Font("Raleway", Font.BOLD,18));
        l3.setForeground(Color.black); 
        l3.setBounds(100,120,100,30);
     
        add(l3);

        String religion[] = {"Kinh", "Other"};
        comboBox = new JComboBox(religion);
        comboBox.setBackground(new Color(255,255,255));
        comboBox.setFont(new Font("Raleway",Font.BOLD,14));
        comboBox.setBounds(350,120,320,30);
        add(comboBox);


        JLabel l5 = new JLabel("Income : ");
        l5.setFont(new Font("Raleway", Font.BOLD,18));
        l5.setForeground(Color.black);
        l5.setBounds(100,180,100,30);
        add(l5);

        String income [] = {"Null","<$.10,000","<$.20,000", "$.50,000", "Upto $.100,000","Above $.100,000"};
        comboBox2 = new JComboBox(income);
        comboBox2.setBackground(new Color(255,255,255));
        comboBox2.setFont(new Font("Raleway",Font.BOLD,14));
        comboBox2.setBounds(350,180,320,30);
        add(comboBox2);

        JLabel l6 = new JLabel("Educational : ");
        l6.setFont(new Font("Raleway", Font.BOLD,18)); 
        l6.setForeground(Color.black);
        l6.setBounds(100,240,150,30);
       
        add(l6);

        String educational [] = {"Non-Graduate","Graduate","Post-Graduate", "Doctrate", "Others"};
        comboBox3 = new JComboBox(educational);
        comboBox3.setBackground(new Color(255,255,255));
        comboBox3.setFont(new Font("Raleway",Font.BOLD,14));
        comboBox3.setBounds(350,240,320,30);
        add(comboBox3);


        JLabel l7 = new JLabel("Occupation : ");
        l7.setFont(new Font("Raleway", Font.BOLD,18));
        l7.setForeground(Color.black);
        l7.setBounds(100,300,150,30);
        add(l7);

        String Occupation [] = {"Salaried","Self-Employed","Business", "Student", "Retired", "Other"};
        comboBox4 = new JComboBox(Occupation);
        comboBox4.setBackground(new Color(255,255,255));
        comboBox4.setFont(new Font("Raleway",Font.BOLD,14));
        comboBox4.setBounds(350,300,320,30);
        add(comboBox4);

        JLabel l8 = new JLabel("PAN Number : ");
        l8.setFont(new Font("Raleway", Font.BOLD,18));
        l8.setForeground(Color.black);
        l8.setBounds(100,360,150,30);
        add(l8);

        textPan = new JTextField();
        textPan.setFont(new Font("Raleway", Font.BOLD,18));
        textPan.setForeground(Color.black);
        textPan.setBounds(350,360,320,30);
        add(textPan);
        
        JLabel description1 = new JLabel("(Credit Card Number)");
        description1.setForeground(Color.black);
        description1.setFont(new Font("Raleway", Font.BOLD,18));
        description1.setBounds(100,400,250,30);
        add(description1);

        JLabel l9 = new JLabel("CI Number : ");
        l9.setForeground(Color.black);
        l9.setFont(new Font("Raleway", Font.BOLD,18));
        l9.setBounds(100,460,180,30);
        add(l9);

        textAadhar = new JTextField();
        textAadhar.setFont(new Font("Raleway", Font.BOLD,18));
        textAadhar.setBounds(350,440,320,30);
        add(textAadhar);
        
        JLabel description2 = new JLabel("(CI : Citizen Idenfication)");
        description2.setForeground(Color.black);
        description2.setFont(new Font("Raleway", Font.BOLD,18));
        description2.setBounds(100,500,250,30);
        add(description2);

        

        JLabel l10 = new JLabel("Senior Citizen : ");
        l10.setFont(new Font("Raleway", Font.BOLD,18));
        l10.setForeground(Color.black);
        l10.setBounds(100,560,180,30);
        add(l10);

        r1 = new JRadioButton("Yes");
        r1.setFont(new Font("Raleway", Font.BOLD,14));
        r1.setBackground(new Color(176,212,184));
        r1.setBounds(350,560,100,30);
        add(r1);
        
        r2 = new JRadioButton("No");
        r2.setFont(new Font("Raleway", Font.BOLD,14));
        r2.setBackground(new Color(176,212,184));
        r2.setBounds(460,560,100,30);
        add(r2);
        
        ButtonGroup seniorCitizenGroup = new ButtonGroup();
        seniorCitizenGroup.add(r1);
        seniorCitizenGroup.add(r2);

        JLabel l11 = new JLabel("Existing Account : ");
        l11.setFont(new Font("Raleway", Font.BOLD,18));
        l11.setForeground(Color.black);
        l11.setBounds(100,600,180,30);
        add(l11);

        e1 = new JRadioButton("Yes");
        e1.setFont(new Font("Raleway", Font.BOLD,14));
        e1.setBackground(new Color(176,212,184));
        e1.setBounds(350,600,100,30);
        add(e1);
        e2 = new JRadioButton("No");
        e2.setFont(new Font("Raleway", Font.BOLD,14));
        e2.setBackground(new Color(176,212,184));
        e2.setBounds(460,600,100,30);
        add(e2);
        
        ButtonGroup existingAccountGroup = new ButtonGroup();
        existingAccountGroup.add(e1);
        existingAccountGroup.add(e2);

        JLabel l12 = new JLabel("Form No : ");
        l12.setFont(new Font("Raleway", Font.BOLD,14));
        l12.setForeground(Color.black);
        l12.setBounds(700,10,100,30);
        add(l12);

        JLabel l13 = new JLabel(formno);
        l13.setFont(new Font("Raleway", Font.BOLD,14));
        l13.setForeground(Color.black);
        l13.setBounds(760,10,60,30);
        add(l13);

        next = new JButton("Next");
        next.setFont(new Font("Raleway",Font.BOLD,14));
        next.setBackground(Color.WHITE);
        next.setForeground(Color.BLACK);
        next.setBounds(700,680,100,30);
        next.addActionListener(this);
        add(next);
        
        setLayout(null);
        setSize(850,780);
        setLocation(350,0);
        getContentPane().setBackground(new Color(224,224,224));
        setVisible(true);
    }
     @Override
    public void actionPerformed(ActionEvent e) {
        String rel = (String) comboBox.getSelectedItem();
        String inc = (String) comboBox3.getSelectedItem();
        String edu = (String) comboBox4.getSelectedItem();
        String occ = (String) comboBox4.getSelectedItem();

        String pan = textPan.getText();
        String addhar = textAadhar.getText();

        String scitizen = " ";
        if ((r1.isSelected())){
            scitizen = "Yes";
        } else if (r2.isSelected()) {
            scitizen ="No";
        }
        String eAccount = " ";
        if ((r1.isSelected())){
            eAccount = "Yes";
        } else if (r2.isSelected()) {
            eAccount ="No";
        }

        try{
            if (textPan.getText().equals("") || textAadhar.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Fill all the fields");
            }else {
                Con c1 = new Con();
                String q = "insert into CustomerDetails values('"+formno+"', '"+rel+"', '"+inc+"','"+edu+"','"+occ+"','"+pan+"','"+addhar+"','"+scitizen+"','"+eAccount+"')";
                c1.statement.executeUpdate(q);
                new Signup3(formno);
                setVisible(false);
            }

        }catch (Exception E){
            E.printStackTrace();
        }


    }
    
    public static void main(String[] args) {
        new Signup2("");
    }
    
}
