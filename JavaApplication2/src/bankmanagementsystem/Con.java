package bankmanagementsystem;

import java.sql.*;

public class Con {
    Connection connection;
    Statement statement;
    
    public Con(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankSystem","root","123456");
            statement = connection.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
