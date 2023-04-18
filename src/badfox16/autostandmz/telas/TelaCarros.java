package badfox16.autostandmz.telas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import badfox16.autostandmz.dal.CarrosDal;
import badfox16.autostandmz.dto.CarrosDTO;
import java.awt.*;
import java.awt.event.*;

public class TelaCarros extends JFrame {
    // variaveis relevantes
    private JLabel idL, marcaL, modeloCarL, matriculaL, precoL, statusL;
    private JTextField idF, marcaF, modeloF, matriculaF, precoF;
    private JButton salvarCar, apagarCar, editarCar, limparCar, carregarCar;
    private JComboBox status;
    private JPanel direitaCar;

    // tabela de carros
    private JTable tbCarros;
    private DefaultTableModel modelo = new DefaultTableModel();

    // construtor
    public TelaCarros() {
  

        // painel para as coisas todas que eu quero fazer


        direitaCar = new JPanel();
        direitaCar.setLayout(null);
        direitaCar.setBounds(0, 0, 920, 700);
        direitaCar.setBackground(new Color(225,230,235,255));

        // labels e fields obrigatorios para a inscricao de um novo usuario no sistema
        marcaL = new JLabel("Marca");
        modeloCarL = new JLabel("Modelo");
        matriculaL = new JLabel("Matricula");
        marcaF = new JTextField(30);
        modeloF = new JTextField(30);
        matriculaF = new JTextField(15);
        precoL = new JLabel("Preço");
        precoF = new JTextField(20);
        statusL = new JLabel("Status");
        idL = new JLabel("ID");
        idF = new JTextField(20);

        // posicionamento e tamanho dos componentes (labels e fields)
        idL.setBounds(100, 100, 50, 30);
        idF.setBounds(100, 125, 50, 30);
        idF.setEnabled(false);
        marcaL.setBounds(180, 100, 100, 30);
        marcaF.setBounds(180, 125, 100, 30);
        modeloCarL.setBounds(310, 100, 100, 30);
        modeloF.setBounds(310, 125, 100, 30);
        matriculaL.setBounds(440, 100, 100, 30);
        matriculaF.setBounds(440, 125, 100, 30);
        precoL.setBounds(730, 100, 100, 30);
        precoF.setBounds(730, 125, 100, 30);


        //estilo para os textfields
        idF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
        marcaF.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent focusEvent){
                marcaF.setBorder(BorderFactory.createLineBorder(new Color(241, 5, 34)));
    
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                marcaF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
    
            }
    
        });
        modeloF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
        modeloF.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent focusEvent){
                modeloF.setBorder(BorderFactory.createLineBorder(new Color(241, 5, 34)));
    
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                modeloF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
    
            }
    
        });
        matriculaF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
        matriculaF.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent focusEvent){
                matriculaF.setBorder(BorderFactory.createLineBorder(new Color(241, 5, 34)));
    
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                matriculaF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
    
            }
    
        });
        precoF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
        precoF.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent focusEvent){
                precoF.setBorder(BorderFactory.createLineBorder(new Color(241, 5, 34)));
    
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                precoF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
    
            }
    
        });



        //ComboBox 
        String[] sCar = { "Disponivel", "Ocupado" };
        status = new JComboBox(sCar);
        statusL.setBounds(570, 100, 120, 30);
        status.setBounds(570, 125, 120, 30);
        status.setBorder(BorderFactory.createLineBorder(new Color(241, 5, 34)));

        // botao para cadastro de novo usuario que so o administrador podera realizar
        salvarCar = new JButton("Salvar");
        salvarCar.setForeground(Color.lightGray);
        salvarCar.setBackground(new Color(197, 4, 27));
        salvarCar.setFont(new Font("Lucida", Font.BOLD, 16));

        // cadastro do usuario na base de dados
        salvarCar.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        CadastrarCar();
                        MostrarCar();
                    }
                });

        // tabela para os carros
        Object[] Carcolumn = { "IDCarro", "Marca", "Modelo", "Matricula", "Preço", "Disponivel" };
        modelo.setColumnIdentifiers(Carcolumn);

        tbCarros = new JTable();
        tbCarros.setModel(modelo);
        tbCarros.setBackground(Color.lightGray);
        tbCarros.setFont(new Font("Lucida", Font.PLAIN, 12));
        tbCarros.setRowHeight(tbCarros.getRowHeight() + 10);

        JScrollPane rolagemCar = new JScrollPane(tbCarros);
        rolagemCar.getViewport().setViewPosition(new Point(280, 280));
        rolagemCar.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        rolagemCar.setBounds(0, 370, 920, 350);
        carregarCar = new JButton("Selecionar");
        
        carregarCar.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        CarregarCampoCar();
                    }
                });

        //posicao dos butoes para as operacoes do CRUD



        // botao para apagar linha da tabela
        apagarCar = new JButton("Apagar");
        apagarCar.setForeground(Color.lightGray);
        apagarCar.setBackground(new Color(197, 4, 27));
        apagarCar.setFont(new Font("Lucida", Font.BOLD, 16));

        apagarCar.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ApagarCar();
                        MostrarCar();
                        LimparCampoCar();
                    }
                });

        // botao para apagar linha da tabela
        editarCar = new JButton("Alterar");
        editarCar.setForeground(Color.lightGray);
        editarCar.setBackground(new Color(197, 4, 27));
        editarCar.setFont(new Font("Lucida", Font.BOLD, 16));

        editarCar.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        AlterarCar();
                        MostrarCar();
                    }
                });

        // bota para limpar campos
        limparCar = new JButton("Limpar");
        limparCar.setForeground(Color.lightGray);
        limparCar.setBackground(new Color(197, 4, 27));
        limparCar.setFont(new Font("Lucida", Font.BOLD, 16));

        limparCar.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        LimparCampoCar();

                    }
                });


        //posicionamento e tamanho dos butoes  
        apagarCar.setBounds(145, 200, 120, 40);
        limparCar.setBounds(305, 200, 120, 40);
        editarCar.setBounds(475, 200, 120, 40);       
        salvarCar.setBounds(645, 200, 120, 40);
        carregarCar.setBounds(295, 310, 200, 20);
        

        // adicionar os componentes ao painel
        direitaCar.add(idL);
        direitaCar.add(idF);
        direitaCar.add(marcaF);
        direitaCar.add(marcaL);
        direitaCar.add(modeloF);
        direitaCar.add(modeloCarL);
        direitaCar.add(matriculaF);
        direitaCar.add(matriculaL);
        direitaCar.add(precoF);
        direitaCar.add(precoL);
        direitaCar.add(status);
        direitaCar.add(statusL);
        direitaCar.add(salvarCar);
        direitaCar.add(rolagemCar);
        direitaCar.add(editarCar);
        direitaCar.add(carregarCar);
        direitaCar.add(apagarCar);
        direitaCar.add(limparCar);

        // tamanho, visiblidade e cenas
        add(direitaCar);
        setSize(920, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void CadastrarCar() {
        int preco;
        String marca, modelo, matricula, disponivel;

        marca = marcaF.getText();
        modelo = modeloF.getText();
        matricula = matriculaF.getText();
        preco = Integer.parseInt(precoF.getText());
        disponivel = (String) status.getSelectedItem();

        // transferencia dos dados inseridos para o objeto objCarrosDTO
        CarrosDTO objCarrosDTO = new CarrosDTO();
        objCarrosDTO.setMarca(marca);
        objCarrosDTO.setModelo(modelo);
        objCarrosDTO.setMatricula(matricula);
        objCarrosDTO.setPreco(preco);
        objCarrosDTO.setStatus(disponivel);

        // passagem dos dados de cadastro para base de dados
        CarrosDal objCarroDal = new CarrosDal();
        objCarroDal.signupCarro(objCarrosDTO);

    }

    private void MostrarCar() {
        CarrosDal objCarrosDal = new CarrosDal();
        modelo.setNumRows(0);

        // ArrayList<CarrosDTO> list = objCarrosDal.PesquisaCln();

        for (CarrosDTO list : objCarrosDal.PesquisaCar()) {
            modelo.addRow(new Object[] {
                    list.getId_carro(),
                    list.getMarca(),
                    list.getModelo(),
                    list.getMatricula(),
                    list.getPreco(),
                    list.getStatus()
            });
        }
    }

    private void CarregarCampoCar() {
        int set = tbCarros.getSelectedRow();
        idF.setText(tbCarros.getModel().getValueAt(set, 0).toString());
        marcaF.setText(tbCarros.getModel().getValueAt(set, 1).toString());
        modeloF.setText(tbCarros.getModel().getValueAt(set, 2).toString());
        matriculaF.setText(tbCarros.getModel().getValueAt(set, 3).toString());
        precoF.setText(tbCarros.getModel().getValueAt(set, 4).toString());

    }

    private void LimparCampoCar() {
        idF.setText("");
        marcaF.setText("");
        modeloF.setText("");
        matriculaF.setText("");
        precoF.setText("");
        marcaF.requestFocus();
    }

    private void AlterarCar() {
        int id_carro, preco;
        String marca, modelo, matricula;

        id_carro = Integer.parseInt(idF.getText());
        preco = Integer.parseInt(precoF.getText());
        marca = marcaF.getText();
        modelo = modeloF.getText();
        matricula = matriculaF.getText();

        CarrosDTO objCarrosDTO = new CarrosDTO();

        objCarrosDTO.setId_carro(id_carro);
        objCarrosDTO.setMarca(marca);
        objCarrosDTO.setMatricula(matricula);
        objCarrosDTO.setModelo(modelo);
        objCarrosDTO.setPreco(preco);


        CarrosDal objCarroDal = new CarrosDal();
        objCarroDal.editarCar(objCarrosDTO);

    }


    public void ApagarCar(){
        int id_carro;

        id_carro = Integer.parseInt(idF.getText());

        CarrosDTO objCarrosDTO = new CarrosDTO();

        objCarrosDTO.setId_carro(id_carro);

        CarrosDal objCarroDal = new CarrosDal();
        objCarroDal.apagarCar(objCarrosDTO);

    }

    public static void main(String[] args) {
        new TelaCarros().MostrarCar();

    }
}