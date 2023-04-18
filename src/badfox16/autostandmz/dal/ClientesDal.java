package badfox16.autostandmz.dal;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import badfox16.autostandmz.dto.ClientesDTO;

public class ClientesDal {

    Connection conexao;
    PreparedStatement prepS;
    ResultSet rSet;  
    ArrayList<ClientesDTO> listaCln = new ArrayList<>();

    //metodo para cadastrar um usuario no sistema
    public void signupClientes(ClientesDTO objClientesDTO){
        String sql = "insert into tbClientes(Nome,BI,Telefone) values (?,?,?)";

        conexao = new ConexaoSQL().BDconecta();

        try {
            prepS = conexao.prepareStatement(sql);
            prepS.setString(1, objClientesDTO.getNome());
            prepS.setString(2, objClientesDTO.getBI());
            prepS.setString(3, objClientesDTO.getTelefone());
            
            prepS.execute();
            prepS.close();

            JOptionPane.showMessageDialog(null,"Cliente adicionado com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Cadastro de Cliente: "+e);
        }
    }

    //metodo para listar os Clientes
    public ArrayList<ClientesDTO> PesquisaCln() {

        String sql = "select * from tbClientes";
        conexao = new ConexaoSQL().BDconecta();

        try {
            prepS= conexao.prepareStatement(sql);
            rSet = prepS.executeQuery();

            while (rSet.next()) {
                ClientesDTO objClientesDTO = new ClientesDTO();
                objClientesDTO.setId_cliente(rSet.getInt("IDCliente"));
                objClientesDTO.setNome(rSet.getString("Nome"));
                objClientesDTO.setBI(rSet.getString("BI"));
                objClientesDTO.setTelefone(rSet.getString("Telefone"));

                //lista para armazenar cada linha da tabela
                listaCln.add(objClientesDTO);
                
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Pesquisa do Cliente: "+ e);
        }

        return listaCln;
        
    }
    

    public void editarCln(ClientesDTO objClientesDTO){
        String sql = "update tbClientes set Nome = ?, BI = ?, Telefone = ? where IDCliente = ?";


        conexao = new ConexaoSQL().BDconecta();

        try {
            prepS = conexao.prepareStatement(sql);
            prepS.setString(1, objClientesDTO.getNome());
            prepS.setString(2, objClientesDTO.getBI());
            prepS.setString(3, objClientesDTO.getTelefone());
            prepS.setInt(4, objClientesDTO.getId_cliente());


            prepS.execute();
            prepS.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Editar dados do Cliente: " + e);
        }


        }

        

        public void apagarCln(ClientesDTO objClientesDTO){
            String sql = "delete from tbClientes where IDCliente = ?";

            conexao = new ConexaoSQL().BDconecta();

            try {
                prepS = conexao.prepareStatement(sql);

                prepS.setInt(1, objClientesDTO.getId_cliente());
    
    
                prepS.execute();
                prepS.close();
    
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Apagar dados do Cliente: " + e);
            }


        }
}
