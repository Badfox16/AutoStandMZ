package badfox16.autostandmz.dal;
import javax.swing.*;
import java.sql.*;
/**
 *
 * @author badfox16
 */
public class ConexaoSQL {
   
    public Connection BDconecta(){
        
        Connection connect = null;
        
        try {
            String url = "jdbc:mysql://localhost:3306/bdAutoStand?user=root&password=";
            connect = DriverManager.getConnection(url);
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ConexaoSQL: " + erro.getMessage());
        }
        return connect;
        
    }
    
} 
