package badfox16.autostandmz.dal;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import badfox16.autostandmz.dto.AluguerDTO;

public class AluguerDal {
    // variaveis para ajudar a conexao
    Connection conexao;
    PreparedStatement prepS;
    ResultSet rSet;
    ArrayList<AluguerDTO> listaAlgr = new ArrayList<>();

    // CREATE
    public void createAlugar(AluguerDTO objAluguerDTO) {
        String sql = "insert into tbAluguer(IDCarro, IDCliente, Taxa, Data_Aluguer, Data_Devolucao) values (?,?,?,?,?)";

        conexao = new ConexaoSQL().BDconecta();

        try {
            prepS = conexao.prepareStatement(sql);
            prepS.setInt(1, objAluguerDTO.getId_cliente());
            prepS.setInt(2, objAluguerDTO.getId_carro());
            prepS.setDouble(3, objAluguerDTO.getTaxa());
            prepS.setDate(4, objAluguerDTO.getData_alugar());
            prepS.setDate(5, objAluguerDTO.getData_devolver());

            prepS.execute();
            prepS.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Criar registro de aluguer: " + e);
        }

    }

    // READ
    public ArrayList<AluguerDTO> PesquisaAluguer() {

        String sql = "select * from tbAluguer";
        conexao = new ConexaoSQL().BDconecta();

        try {
            prepS = conexao.prepareStatement(sql);
            rSet = prepS.executeQuery();

            while (rSet.next()) {
                AluguerDTO objAluguerDTO = new AluguerDTO();
                objAluguerDTO.setId_carro(rSet.getInt("IDCarro"));
                objAluguerDTO.setId_cliente(rSet.getInt("IDCliente"));
                objAluguerDTO.setTaxa(rSet.getDouble("Taxa"));
                objAluguerDTO.setData_alugar(rSet.getDate("Data_Aluguer"));
                objAluguerDTO.setData_devolver(rSet.getDate("Data_Devolucao"));

                // lista para armazenar cada linha da tabela
                listaAlgr.add(objAluguerDTO);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Pesquisa dos Aluguer: " + e);
        }

        return listaAlgr;

    }

    // metodo para apagar um registro da base de dados (DELETE)
    public void apagarAlgr(AluguerDTO objAluguerDTO) {
        String sql = "delete from tbAluguer where Codigo = ?";

        conexao = new ConexaoSQL().BDconecta();

        try {
            prepS = conexao.prepareStatement(sql);

            prepS.setInt(1, objAluguerDTO.getCodigo());

            prepS.execute();
            prepS.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Apagar dados do aluguer: " + e);
        }

    }
}
