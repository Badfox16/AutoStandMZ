package badfox16.autostandmz.dal;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import badfox16.autostandmz.dto.UsuariosDTO;

public class UsuarioDal {

    Connection conexao;
    PreparedStatement prepS;
    ResultSet rSet;  
    ArrayList<UsuariosDTO> listaUsr = new ArrayList<>();
    
    //metodo para cadastrar um usuario no sistema
    public void signupUsuario(UsuariosDTO objUsuariosDTO){
        String sql = "insert into tbUsuarios(Nome,Apelido,Telefone,Username,Senha) values (?,?,?,?,?)";

        conexao = new ConexaoSQL().BDconecta();

        try {
            prepS = conexao.prepareStatement(sql);
            prepS.setString(1, objUsuariosDTO.getNome());
            prepS.setString(2, objUsuariosDTO.getApelido());
            prepS.setString(3, objUsuariosDTO.getTelefone());
            prepS.setString(4, objUsuariosDTO.getUsername());
            prepS.setString(5, objUsuariosDTO.getSenha());

            prepS.execute();
            prepS.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Cadastro de Usuario: "+e);
        }
    }

    //metodo para listar os usuarios
    public List<UsuariosDTO> listaUsr (){
        conexao = new ConexaoSQL().BDconecta();
        prepS = null;
        rSet = null;
        List<UsuariosDTO>  usrLs = new ArrayList<>(null);

        try {
            prepS = conexao.prepareStatement("select * from tbUsuarios");
            rSet=prepS.executeQuery();

            while (rSet.next()) {
                UsuariosDTO objUsuariosDTO = new UsuariosDTO();

                objUsuariosDTO.setId_user(rSet.getInt("IDUser"));
                objUsuariosDTO.setNome(rSet.getString("Nome"));
                objUsuariosDTO.setApelido(rSet.getString("Apelido"));
                objUsuariosDTO.setTelefone(rSet.getString("Telefone"));
                objUsuariosDTO.setUsername(rSet.getString("Username"));
                objUsuariosDTO.setSenha(rSet.getString("Senha"));

                usrLs.add(objUsuariosDTO);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Listar usuarios: "+e);
        }
        return usrLs;
    }

   
}
    
