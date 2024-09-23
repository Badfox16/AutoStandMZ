package badfox16.autostandmz.dal;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import badfox16.autostandmz.dto.CarrosDTO;

public class CarrosDal {
    // variaveis para ajudar a conexao
    Connection conexao;
    PreparedStatement prepS;
    ResultSet rSet;
    ArrayList<CarrosDTO> listaCar = new ArrayList<>();

    // metodo para cadastrar um carro no sistema (CREATE)
    public void signupCarro(CarrosDTO objCarrosDTO) {
        String sql = "insert into tbCarros(Marca, Modelo, Matricula, Taxa_Diaria, Status) values (?,?,?,?,?)";

        conexao = new ConexaoSQL().BDconecta();

        try {
            prepS = conexao.prepareStatement(sql);
            prepS.setString(1, objCarrosDTO.getMarca());
            prepS.setString(2, objCarrosDTO.getModelo());
            prepS.setString(3, objCarrosDTO.getMatricula());
            prepS.setInt(4, objCarrosDTO.getPreco());
            prepS.setString(5, "Disponível");

            prepS.execute();
            prepS.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Cadastro de carro: " + e);
        }

    }

    // metodo para listar os carros (READ)
    public ArrayList<CarrosDTO> PesquisaCar() {

        String sql = "select * from tbCarros";
        conexao = new ConexaoSQL().BDconecta();

        try {
            prepS = conexao.prepareStatement(sql);
            rSet = prepS.executeQuery();

            while (rSet.next()) {
                CarrosDTO objCarrosDTO = new CarrosDTO();
                objCarrosDTO.setId_carro(rSet.getInt("IDCarro"));
                objCarrosDTO.setMarca(rSet.getString("Marca"));
                objCarrosDTO.setModelo(rSet.getString("Modelo"));
                objCarrosDTO.setMatricula(rSet.getString("Matricula"));
                objCarrosDTO.setPreco(rSet.getInt("Taxa_Diaria"));
                objCarrosDTO.setStatus(rSet.getString("Status"));

                // lista para armazenar cada linha da tabela
                listaCar.add(objCarrosDTO);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Pesquisa dos carros: " + e);
        }

        return listaCar;

    }

    // metodo para atualizar os dados da tabela (UPDATE)
    public void editarCar(CarrosDTO objCarrosDTO) {
        String sql = "update tbCarros set Marca = ?, Modelo = ?, Matricula = ?, Taxa_Diaria = ?, Status = ? where IDCarro = ?";

        conexao = new ConexaoSQL().BDconecta();

        try {
            prepS = conexao.prepareStatement(sql);
            prepS.setString(1, objCarrosDTO.getMarca());
            prepS.setString(2, objCarrosDTO.getModelo());
            prepS.setString(3, objCarrosDTO.getMatricula());
            prepS.setInt(4, objCarrosDTO.getPreco());
            prepS.setString(5, objCarrosDTO.getStatus());
            prepS.setInt(6, objCarrosDTO.getId_carro());

            prepS.execute();
            prepS.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Editar dados do carro: " + e);
        }

    }

    // metodo para apagar um registro da base de dados (DELETE)
    public void apagarCar(CarrosDTO objCarrosDTO) {
        String sql = "delete from tbCarros where IDCarro = ?";

        conexao = new ConexaoSQL().BDconecta();

        try {
            prepS = conexao.prepareStatement(sql);

            prepS.setInt(1, objCarrosDTO.getId_carro());

            prepS.execute();
            prepS.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Apagar dados do Carro: " + e);
        }

    }

    public ResultSet listarIdCarro() {
        conexao = new ConexaoSQL().BDconecta();
        String sql = "select IDCarro from tbCarros order by IDCarro";
        try {
            prepS = conexao.prepareStatement(sql);
            return prepS.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar: " + e.getMessage());
            return null;
        }
    }
}
