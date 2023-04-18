package badfox16.autostandmz.dal;

import java.sql.*;
import javax.swing.JOptionPane;
import badfox16.autostandmz.dto.*;

public class UsrLogin {
    
    Connection conexao;
    PreparedStatement prepStat;
    ResultSet rSet;

    public ResultSet autenticacaoUsr(UsuariosDTO objUser){
        conexao = new ConexaoSQL().BDconecta();

        try {
            String sql = "select * from tbUsuarios where Username = ? and Senha = ?";

            prepStat = conexao.prepareStatement(sql);
            prepStat.setString(1, objUser.getUsername());
            prepStat.setString(2, objUser.getSenha());

            rSet = prepStat.executeQuery();
            return rSet;

        } catch (SQLException erro) {
           JOptionPane.showMessageDialog(null, "Verificar credenciais: "+ erro);
           return null;
        }

    }

}
