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
        String sqlRecuperaTaxaDiaria = "SELECT Taxa_Diaria FROM tbCarros WHERE IDCarro = ?";
        String sqlAluguer = "INSERT INTO tbAluguer (IDCarro, IDCliente, Taxa, Data_Aluguer, Data_Devolucao) VALUES (?, ?, ?, ?, ?)";
        String sqlAtualizaCarro = "UPDATE tbCarros SET Status = 'Alugado' WHERE IDCarro = ?";

        try (Connection conn = new ConexaoSQL().BDconecta();
                PreparedStatement stmtRecuperaTaxaDiaria = conn.prepareStatement(sqlRecuperaTaxaDiaria);
                PreparedStatement stmtAluguer = conn.prepareStatement(sqlAluguer);
                PreparedStatement stmtAtualizaCarro = conn.prepareStatement(sqlAtualizaCarro)) {

            // Recuperar a Taxa Diária do carro
            stmtRecuperaTaxaDiaria.setInt(1, objAluguerDTO.getId_carro());
            try (ResultSet rs = stmtRecuperaTaxaDiaria.executeQuery()) {
                if (rs.next()) {
                    double taxaDiaria = rs.getDouble("Taxa_Diaria");

                    // Calcular a diferença em dias entre a Data de Aluguer e a Data de Devolução
                    long diferencaEmMillis = objAluguerDTO.getData_devolver().getTime()
                            - objAluguerDTO.getData_alugar().getTime();
                    long dias = diferencaEmMillis / (1000 * 60 * 60 * 24); // Converter milissegundos em dias
                    dias = (dias == 0) ? 1 : dias; // Garantir que pelo menos 1 dia seja cobrado

                    // Calcular a taxa total
                    double taxaTotal = dias * taxaDiaria;

                    // Inserir o registro de aluguer
                    stmtAluguer.setInt(1, objAluguerDTO.getId_carro());
                    stmtAluguer.setInt(2, objAluguerDTO.getId_cliente());
                    stmtAluguer.setDouble(3, taxaTotal); // Definir a taxa total calculada
                    stmtAluguer.setDate(4, objAluguerDTO.getData_alugar());
                    stmtAluguer.setDate(5, objAluguerDTO.getData_devolver());
                    stmtAluguer.executeUpdate();

                    // Atualizar o status do carro para 'Alugado'
                    stmtAtualizaCarro.setInt(1, objAluguerDTO.getId_carro());
                    stmtAtualizaCarro.executeUpdate();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar o aluguer: " + e.getMessage());
        }
    }

    // READ
    public ArrayList<AluguerDTO> PesquisaAluguer() {

        String sql = "SELECT a.Codigo, a.IDCarro, a.Taxa, a.Data_Aluguer, a.Data_Devolucao, a.Estado, "
                + "c.Nome AS NomeCliente, c.Apelido, c.Telefone, "
                + "car.Modelo, car.Marca, car.Matricula "
                + "FROM tbAluguer a "
                + "JOIN tbClientes c ON a.IDCliente = c.IDCliente "
                + "JOIN tbCarros car ON a.IDCarro = car.IDCarro "
                + "WHERE a.Estado = 'Concluido'";

        conexao = new ConexaoSQL().BDconecta();

        try {
            prepS = conexao.prepareStatement(sql);
            rSet = prepS.executeQuery();

            while (rSet.next()) {
                AluguerDTO objAluguerDTO = new AluguerDTO();
                objAluguerDTO.setCodigo(rSet.getInt("Codigo"));
                // objAluguerDTO.setId_carro(rSet.getInt("IDCarro"));
                // objAluguerDTO.setId_cliente(rSet.getInt("IDCliente"));
                objAluguerDTO.setTaxa(rSet.getDouble("Taxa"));
                objAluguerDTO.setData_alugar(rSet.getDate("Data_Aluguer"));
                objAluguerDTO.setData_devolver(rSet.getDate("Data_Devolucao"));

                // Dados do cliente
                objAluguerDTO.setNomeCliente((rSet.getString("NomeCliente") + " " + rSet.getString("Apelido")));
                objAluguerDTO.setApelidoCliente(rSet.getString("Apelido"));

                // Dados do carro
                objAluguerDTO.setModeloCarro(rSet.getString("Modelo"));
                objAluguerDTO.setMarcaCarro(rSet.getString("Marca"));
                objAluguerDTO.setMatriculaCarro(rSet.getString("Matricula"));


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
