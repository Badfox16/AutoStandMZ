package badfox16.autostandmz.telas;

//import das modelobliotecas necessarias
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import badfox16.autostandmz.dal.*;
import badfox16.autostandmz.dto.*;

import com.toedter.calendar.JDateChooser;
import java.sql.*;

public class TelaMain extends JFrame {
    // variaveis relevantes para a tela principal
    ImageIcon icone, mainIcon, carIcon, clnIcon, algrIcon, dvlvrIcon;
    private JPanel esquerdaP, direitaM, direitaCar, direitaCln, direitaAlgr, direitaDvlvr;
    private JLabel mainLabel, titulo, subt;
    private JButton abaCarros, abaCliente, abaAlugar, abaDevolver, logoutB;
    private JTable tbClientes, tbCarros;
    private DefaultTableModel modelo = new DefaultTableModel();
    private DefaultTableModel modeloCar = new DefaultTableModel();
    private DefaultTableModel modeloD = new DefaultTableModel();
    private static Point point = new Point();

    // variaveis para tela cliente
    private JLabel idClnL, nomeL, biL, telefoneL;
    private JTextField idClnF, nomeF, biF, telefoneF;
    private JButton salvarCln, carregar, apagarCln, editarCln, limparCln;

    // tela carros
    private JLabel idL, marcaL, modeloCarL, matriculaL, precoL, statusL;
    private JTextField idF, marcaF, modeloF, matriculaF, precoF;
    private JButton salvarCar, apagarCar, editarCar, limparCar, carregarCar;
    private JComboBox status;

    // tela de aluguer
    private JDateChooser dtInicial, dtFinal;
    private JPanel redPanel;
    private JButton confirmar, cancelar;
    private JLabel car_idLbl, id_ClnL, nomeClnL, taxAlgrL, dataL, dataFL;
    private JTextField id_ClnF, nomeClnF, taxAlgrF, statusF;
    private JComboBox car_idJcb;

    Connection conn;
    PreparedStatement prep;
    ResultSet algrSet;

    // tela de devolver
    private JPanel redPanelD;
    private JLabel codigoDvlvr, idClnDvl, idCarDvlvr;
    private JTextField codigoDvlvrF, clnDvF, idCarDvlvrF;
    private JButton btnDevolver;
    private JTable tbAluguer;
    Connection Dconn;
    PreparedStatement prepD;
    ResultSet dvlvSet;

    // Construtor
    public TelaMain() {
        super("AUTOSTAND");// titulo

        // icone da janela
        this.icone = new ImageIcon("src/badfox16/autostandmz/icon/main.png");
        setIconImage(this.icone.getImage());

        // Paineis para os componentes da tela
        esquerdaP = new JPanel();
        esquerdaP.setLayout(null);
        esquerdaP.setBounds(0, 0, 280, 800);
        esquerdaP.setBackground(new Color(26, 9, 133));

        // painel principal
        direitaM = new JPanel();
        direitaM.setLayout(null);
        direitaM.setBounds(280, 0, 920, 800);
        direitaM.setBackground(new Color(225, 230, 235, 255));

        mainIcon = new ImageIcon("src/badfox16/autostandmz/icon/main.png");
        mainLabel = new JLabel(mainIcon);
        mainLabel.setBounds(440, 100, 600, 600);
        direitaM.add(mainLabel);

        titulo = new JLabel("AUTOSTAND");
        titulo.setBounds(300, 0, 400, 130);
        titulo.setFont(new Font("Lucida", Font.BOLD, 46));
        titulo.setForeground(new Color(26, 9, 133));
        direitaM.add(titulo);

        subt = new JLabel("Gestão de Aluguer de Viaturas");
        subt.setBounds(300, 80, 600, 130);
        subt.setFont(new Font("Lucida", Font.BOLD, 30));
        subt.setForeground(new Color(26, 9, 133));
        direitaM.add(subt);

        // painel para gerir carros
        direitaCar = new JPanel();
        direitaCar.setLayout(null);
        direitaCar.setBounds(280, 0, 920, 800);
        direitaCar.setBackground(new Color(225, 230, 235, 255));
        direitaCar.setVisible(false);

        // painel para gerir clientes
        direitaCln = new JPanel();
        direitaCln.setLayout(null);
        direitaCln.setBounds(280, 0, 920, 800);
        direitaCln.setBackground(new Color(225, 230, 235, 255));
        direitaCln.setVisible(false);

        // painel para aluguer de carros
        direitaAlgr = new JPanel();
        direitaAlgr.setLayout(null);
        direitaAlgr.setBounds(280, 0, 920, 800);
        direitaAlgr.setBackground(new Color(225, 230, 235, 255));
        direitaAlgr.setVisible(false);

        // painel para devolver carros
        direitaDvlvr = new JPanel();
        direitaDvlvr.setLayout(null);
        direitaDvlvr.setBounds(280, 0, 920, 800);
        direitaDvlvr.setBackground(new Color(225, 230, 235, 255));
        direitaDvlvr.setVisible(false);

        // Icones para os butões
        carIcon = new ImageIcon("src/badfox16/autostandmz/icon/Carros.png");
        algrIcon = new ImageIcon("src/badfox16/autostandmz/icon/Alugar.png");
        dvlvrIcon = new ImageIcon("src/badfox16/autostandmz/icon/Devolver.png");
        clnIcon = new ImageIcon("src/badfox16/autostandmz/icon/Cliente.png");

        // instanciar butões para acessar as diferentes telas/paineis
        abaCarros = new JButton("   Gestão de Carros", carIcon);
        abaCliente = new JButton("Gestão de Clientes", clnIcon);
        abaAlugar = new JButton("       Alugar Carro", algrIcon);
        abaDevolver = new JButton("    Devolver Carro", dvlvrIcon);
        logoutB = new JButton("Logout");

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // torna apenas a aba de carros visivel
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        abaCarros.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        direitaAlgr.setVisible(false);
                        direitaCln.setVisible(false);
                        direitaDvlvr.setVisible(false);
                        direitaM.setVisible(false);
                        direitaCar.setVisible(true);

                        abaCarros.setBackground(new Color(225, 230, 235, 255));
                        abaCliente.setBackground(new Color(26, 9, 133));
                        abaAlugar.setBackground(new Color(26, 9, 133));
                        abaDevolver.setBackground(new Color(26, 9, 133));
                        logoutB.setBackground(new Color(26, 9, 133));

                        MostrarCar();
                    }

                });

        abaCarros.setBorderPainted(false);
        abaCarros.setFocusPainted(false);

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

        // estilo para os textfields
        idF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));
        marcaF.requestFocus();
        marcaF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                marcaF.setBorder(BorderFactory.createLineBorder(new Color(26, 9, 133)));

            }

            @Override
            public void focusLost(FocusEvent e) {
                marcaF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));

            }

        });
        modeloF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));
        modeloF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                modeloF.setBorder(BorderFactory.createLineBorder(new Color(26, 9, 133)));

            }

            @Override
            public void focusLost(FocusEvent e) {
                modeloF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));

            }

        });
        matriculaF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));
        matriculaF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                matriculaF.setBorder(BorderFactory.createLineBorder(new Color(26, 9, 133)));

            }

            @Override
            public void focusLost(FocusEvent e) {
                matriculaF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));

            }

        });
        precoF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));
        precoF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                precoF.setBorder(BorderFactory.createLineBorder(new Color(26, 9, 133)));

            }

            @Override
            public void focusLost(FocusEvent e) {
                precoF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));

            }

        });

        // ComboBox
        String[] sCar = { "Disponivel", "Alugado", "Avariado" };
        status = new JComboBox(sCar);
        statusL.setBounds(570, 100, 120, 30);
        status.setBounds(570, 125, 120, 30);
        status.setBorder(BorderFactory.createLineBorder(new Color(26, 9, 133)));

        // botao para cadastro de novo usuario que so o administrador podera realizar
        salvarCar = new JButton("Salvar");
        salvarCar.setForeground(Color.WHITE);
        salvarCar.setBackground(new Color(26, 9, 133));
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
        Object[] Carcolumn = { "IDCarro", "Marca", "Modelo", "Matricula", "Preço", "Estado" };
        modeloCar.setColumnIdentifiers(Carcolumn);

        tbCarros = new JTable();
        tbCarros.setModel(modeloCar);
        tbCarros.setBackground(Color.lightGray);
        tbCarros.setFont(new Font("Lucida", Font.PLAIN, 12));
        tbCarros.setRowHeight(tbCarros.getRowHeight() + 10);

        JScrollPane rolagemCar = new JScrollPane(tbCarros);
        rolagemCar.getViewport().setViewPosition(new Point(280, 280));
        rolagemCar.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        rolagemCar.setBounds(0, 370, 920, 350);
        carregarCar = new JButton("Selecionar");
        carregarCar.setForeground(Color.WHITE);
        carregarCar.setBackground(new Color(26, 9, 133));
        carregarCar.setFont(new Font("Lucida", Font.BOLD, 18));

        carregarCar.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        CarregarCampoCar();
                    }
                });

        // posicao dos butoes para as operacoes do CRUD

        // botao para apagar linha da tabela
        apagarCar = new JButton("Apagar");
        apagarCar.setForeground(Color.WHITE);
        apagarCar.setBackground(new Color(26, 9, 133));
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
        editarCar.setForeground(Color.WHITE);
        editarCar.setBackground(new Color(26, 9, 133));
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
        limparCar.setForeground(Color.WHITE);
        limparCar.setBackground(new Color(26, 9, 133));
        limparCar.setFont(new Font("Lucida", Font.BOLD, 16));

        limparCar.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        LimparCampoCar();

                    }
                });

        // posicionamento e tamanho dos butoes
        apagarCar.setBounds(140, 200, 120, 40);
        limparCar.setBounds(310, 200, 120, 40);
        editarCar.setBounds(485, 200, 120, 40);
        salvarCar.setBounds(655, 200, 120, 40);
        carregarCar.setBounds(5, 740, 200, 40);

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
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // _______________________________________torna a aba de clientes
        // visivel______________________________________________
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        abaCliente.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        direitaAlgr.setVisible(false);
                        direitaCln.setVisible(true);
                        direitaDvlvr.setVisible(false);
                        direitaM.setVisible(false);
                        direitaCar.setVisible(false);

                        abaCarros.setBackground(new Color(26, 9, 133));
                        abaCliente.setBackground(new Color(225, 230, 235, 255));
                        abaAlugar.setBackground(new Color(26, 9, 133));
                        abaDevolver.setBackground(new Color(26, 9, 133));
                        logoutB.setBackground(new Color(26, 9, 133));

                        MostrarClientes();
                    }
                });

        //
        abaCliente.setBorderPainted(false);
        abaCliente.setFocusPainted(false);

        // labels e fields obrigatorios para a inscricao de um novo usuario no sistema
        nomeL = new JLabel("Nome");
        biL = new JLabel("BI");
        telefoneL = new JLabel("Telefone");
        nomeF = new JTextField(30);
        biF = new JTextField(30);
        telefoneF = new JTextField(15);
        idClnL = new JLabel("ID");
        idClnF = new JTextField(20);
        // posicionamento e tamanho dos componentes (labels e fields)
        idClnL.setBounds(100, 100, 50, 30);
        idClnF.setBounds(100, 125, 50, 30);
        idClnF.setEnabled(false);
        nomeL.setBounds(170, 100, 50, 30);
        nomeF.setBounds(170, 125, 200, 30);
        biL.setBounds(390, 100, 50, 30);
        biF.setBounds(390, 125, 200, 30);
        telefoneL.setBounds(610, 100, 100, 30);
        telefoneF.setBounds(610, 125, 200, 30);

        // estilo para as bordas dos textfields
        idClnF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));
        nomeF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));
        nomeF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                nomeF.setBorder(BorderFactory.createLineBorder(new Color(26, 9, 133)));

            }

            @Override
            public void focusLost(FocusEvent e) {
                nomeF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));

            }

        });
        biF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));
        biF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                biF.setBorder(BorderFactory.createLineBorder(new Color(26, 9, 133)));

            }

            @Override
            public void focusLost(FocusEvent e) {
                biF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));

            }

        });
        telefoneF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));
        telefoneF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                telefoneF.setBorder(BorderFactory.createLineBorder(new Color(26, 9, 133)));

            }

            @Override
            public void focusLost(FocusEvent e) {
                telefoneF.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 6)));

            }

        });

        // butoes para as operacoes do crud
        salvarCln = new JButton("Salvar");
        salvarCln.setForeground(Color.WHITE);
        salvarCln.setBackground(new Color(26, 9, 133));
        salvarCln.setBounds(330, 275, 150, 45);
        salvarCln.setFont(new Font("Lucida", Font.BOLD, 16));

        // tabela para ler os dados inseridos
        Object[] column = { "IDCliente", "Nome", "BI", "Telefone" };
        modelo.setColumnIdentifiers(column);

        tbClientes = new JTable();
        tbClientes.setModel(modelo);
        tbClientes.setBackground(Color.WHITE);
        tbClientes.setFont(new Font("Lucida", Font.PLAIN, 12));
        tbClientes.setRowHeight(tbClientes.getRowHeight() + 10);

        JScrollPane rolagem = new JScrollPane(tbClientes);
        rolagem.getViewport().setViewPosition(new Point(280, 280));
        rolagem.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        rolagem.setBounds(0, 370, 920, 350);

        carregar = new JButton("Selecionar");
        carregar.setForeground(Color.WHITE);
        carregar.setBackground(new Color(26, 9, 133));
        carregar.setFont(new Font("Lucida", Font.BOLD, 18));

        carregar.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        CarregarCampoCln();
                    }
                });

        // cadastro do usuario na base de dados
        salvarCln.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        CadastrarCln();
                        MostrarClientes();

                    }
                });

        // botao para apagar linha da tabela
        apagarCln = new JButton("Apagar");
        apagarCln.setForeground(Color.WHITE);
        apagarCln.setBackground(new Color(26, 9, 133));
        apagarCln.setFont(new Font("Lucida", Font.BOLD, 16));

        apagarCln.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ApagarCliente();
                        MostrarClientes();
                        LimparCampoCln();
                    }
                });

        // botao para apagar linha da tabela
        editarCln = new JButton("Editar");
        editarCln.setForeground(Color.WHITE);
        editarCln.setBackground(new Color(26, 9, 133));
        editarCln.setFont(new Font("Lucida", Font.BOLD, 16));

        editarCln.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        AlterarCln();
                        MostrarClientes();
                    }
                });

        limparCln = new JButton("Limpar");
        limparCln.setForeground(Color.WHITE);
        limparCln.setBackground(new Color(26, 9, 133));
        limparCln.setBounds(780, 275, 150, 45);
        limparCln.setFont(new Font("Lucida", Font.BOLD, 16));

        limparCln.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        LimparCampoCln();

                    }
                });

        // posicionamento e tamanho dos butoes
        apagarCln.setBounds(140, 200, 120, 40);
        limparCln.setBounds(310, 200, 120, 40);
        editarCln.setBounds(485, 200, 120, 40);
        salvarCln.setBounds(655, 200, 120, 40);
        carregar.setBounds(5, 740, 200, 40);

        // adicionar os componentes ao seu respectivo painel
        direitaCln.add(idClnL);
        direitaCln.add(idClnF);
        direitaCln.add(nomeF);
        direitaCln.add(nomeL);
        direitaCln.add(biF);
        direitaCln.add(biL);
        direitaCln.add(telefoneF);
        direitaCln.add(telefoneL);
        direitaCln.add(salvarCln);
        direitaCln.add(limparCln);
        direitaCln.add(apagarCln);
        direitaCln.add(editarCln);
        direitaCln.add(carregar);
        direitaCln.add(rolagem);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++ FIM DA ABA DE CLIENTES
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // torna a aba de aluguer de carros visivel
        abaAlugar.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        direitaAlgr.setVisible(true);
                        direitaCln.setVisible(false);
                        direitaDvlvr.setVisible(false);
                        direitaM.setVisible(false);
                        direitaCar.setVisible(false);

                        abaCarros.setBackground(new Color(26, 9, 133));
                        abaCliente.setBackground(new Color(26, 9, 133));
                        abaAlugar.setBackground(new Color(225, 230, 235, 255));
                        abaDevolver.setBackground(new Color(26, 9, 133));
                        logoutB.setBackground(new Color(26, 9, 133));

                    }

                });
        abaAlugar.setBorderPainted(false);
        abaAlugar.setFocusPainted(false);
        redPanel = new JPanel(null);
        redPanel.setBounds(640, 0, 280, 800);
        redPanel.setBackground(new Color(26, 9, 133));
        direitaAlgr.add(redPanel);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        String[] Carros = { "Selecione" };
        car_idJcb = new JComboBox(Carros);
        car_idJcb.setBounds(280, 100, 200, 40);
        direitaAlgr.add(car_idJcb);

        statusF = new JTextField(20);
        statusF.setBounds(500, 100, 100, 40);
        statusF.setText("Não disponivel");
        direitaAlgr.add(statusF);

        car_idLbl = new JLabel("ID Carro: ");
        car_idLbl.setBounds(120, 100, 100, 40);
        direitaAlgr.add(car_idLbl);

        // metodo para listar os carros no menu-dropdown (JComboBox)
        dadosComboBox();
        car_idJcb.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        conn = new ConexaoSQL().BDconecta();
                        String carId = car_idJcb.getSelectedItem().toString();
                        try {
                            prep = conn.prepareStatement(
                                    "select * from tbCarros where IDCarro = ?");
                            prep.setString(1, carId);

                            algrSet = prep.executeQuery();

                            if (algrSet.next() == false) {
                                JOptionPane.showMessageDialog(null,
                                        "Carro alugado");
                            } else {
                                String stat = algrSet.getString("Status");
                                statusF.setText(stat.trim());
                                String preco = algrSet.getString("Taxa_Diaria");
                                taxAlgrF.setText(preco.trim());

                                if (stat.equals("Disponível")) {
                                    id_ClnF.setEnabled(true);
                                    taxAlgrF.setEnabled(true);

                                }
                            }
                        } catch (SQLException e1) {

                            e1.printStackTrace();
                        }

                    }
                });
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        id_ClnF = new JTextField(30);
        id_ClnF.setBounds(280, 170, 200, 40);
        direitaAlgr.add(id_ClnF);

        id_ClnL = new JLabel("ID Cliente (Enter): ");
        id_ClnL.setBounds(120, 170, 200, 40);
        direitaAlgr.add(id_ClnL);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        nomeClnF = new JTextField(30);
        nomeClnF.setBounds(280, 240, 200, 40);
        direitaAlgr.add(nomeClnF);

        nomeClnL = new JLabel("Nome do Cliente: ");
        nomeClnL.setBounds(120, 240, 200, 40);
        direitaAlgr.add(nomeClnL);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        taxAlgrF = new JTextField(30);
        taxAlgrF.setBounds(280, 310, 200, 40);
        direitaAlgr.add(taxAlgrF);

        taxAlgrL = new JLabel("Taxa de Aluguer: ");
        taxAlgrL.setBounds(120, 310, 200, 40);
        direitaAlgr.add(taxAlgrL);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        dtInicial = new JDateChooser();
        dtInicial.setBounds(280, 380, 200, 40);
        dtInicial.setDateFormatString("yyyy/MM/dd");
        direitaAlgr.add(dtInicial);

        dataL = new JLabel("Data: ");
        dataL.setBounds(120, 380, 200, 40);
        direitaAlgr.add(dataL);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        dtFinal = new JDateChooser();
        dtFinal.setBounds(280, 450, 200, 40);
        dtFinal.setDateFormatString("yyyy/MM/dd");
        direitaAlgr.add(dtFinal);

        dataFL = new JLabel("Data de devolução: ");
        dataFL.setBounds(120, 450, 200, 40);
        direitaAlgr.add(dataFL);
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        confirmar = new JButton("Confirmar");
        confirmar.setBounds(80, 610, 200, 40);
        direitaAlgr.add(confirmar);

        confirmar.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Alugar();

                    }
                });
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        cancelar = new JButton("Cancelar");
        cancelar.setBounds(380, 610, 200, 40);
        direitaAlgr.add(cancelar);

        cancelar.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        direitaAlgr.setVisible(false);
                        direitaCln.setVisible(false);
                        direitaDvlvr.setVisible(false);
                        direitaM.setVisible(true);
                        direitaCar.setVisible(false);

                    }
                });

        id_ClnF.setEnabled(false);
        nomeClnF.setEnabled(false);
        taxAlgrF.setEnabled(false);
        statusF.setEnabled(false);
        dtInicial.setEnabled(false);
        dtFinal.setEnabled(false);

        KeyListener listener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent ev) {
                id_ClnL(ev);
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        id_ClnF.addKeyListener(listener);
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++FIM DA ABA
        // ALUGAR++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        abaDevolver.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        direitaAlgr.setVisible(false);
                        direitaCln.setVisible(false);
                        direitaDvlvr.setVisible(true);
                        direitaM.setVisible(false);
                        direitaCar.setVisible(false);

                        abaCarros.setBackground(new Color(26, 9, 133));
                        abaCliente.setBackground(new Color(26, 9, 133));
                        abaAlugar.setBackground(new Color(26, 9, 133));
                        abaDevolver.setBackground(new Color(225, 230, 235, 255));
                        logoutB.setBackground(new Color(26, 9, 133));

                        MostrarAluguer();
                    }

                });
        abaDevolver.setBorderPainted(false);
        abaDevolver.setFocusPainted(false);

        redPanelD = new JPanel(null);
        redPanelD.setBounds(720, 0, 200, 800);
        redPanelD.setBackground(new Color(26, 9, 133));
        direitaDvlvr.add(redPanelD);
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        codigoDvlvr = new JLabel("Codigo do Aluguer(Enter):");
        codigoDvlvrF = new JTextField(30);
        idClnDvl = new JLabel("Cliente:");
        clnDvF = new JTextField(30);
        idCarDvlvr = new JLabel("Viatura:");
        idCarDvlvrF = new JTextField(30);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        codigoDvlvr.setBounds(100, 100, 170, 30);
        direitaDvlvr.add(codigoDvlvr);

        codigoDvlvrF.setBounds(100, 125, 45, 30);
        direitaDvlvr.add(codigoDvlvrF);
        // .setEnabled(false);
        idClnDvl.setBounds(270, 100, 80, 30);
        direitaDvlvr.add(idClnDvl);

        clnDvF.setBounds(270, 125, 120, 30);
        clnDvF.setEnabled(false);
        direitaDvlvr.add(clnDvF);

        idCarDvlvr.setBounds(430, 100, 80, 30);
        direitaDvlvr.add(idCarDvlvr);

        idCarDvlvrF.setBounds(430, 125, 120, 30);
        idCarDvlvrF.setEnabled(false);
        direitaDvlvr.add(idCarDvlvrF);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        btnDevolver = new JButton("Devolver");
        btnDevolver.setBounds(280, 200, 140, 40);
        direitaDvlvr.add(btnDevolver);
        btnDevolver.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Devolver();
                    }
                });

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // tabela para ler os dados inseridos
        Object[] columnD = { "Codigo", "Cliente", "Matricula", "Taxa", "Data Aluguer", "Data Devolução" };
        modeloD.setColumnIdentifiers(columnD);

        tbAluguer = new JTable();
        tbAluguer.setModel(modeloD);
        tbAluguer.setBackground(Color.lightGray);
        tbAluguer.setFont(new Font("Lucida", Font.PLAIN, 12));
        tbAluguer.setRowHeight(tbAluguer.getRowHeight() + 10);

        JScrollPane rolagemD = new JScrollPane(tbAluguer);
        rolagemD.getViewport().setViewPosition(new Point(280, 280));
        rolagemD.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        rolagemD.setBounds(0, 400, 720, 400);
        direitaDvlvr.add(rolagemD);

        // listener para carregar os dados
        KeyListener listenerD = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent ev) {
                dvlvrCarro(ev);
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        codigoDvlvrF.addKeyListener(listenerD);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++FIM DA ABA
        // DEVOLVER++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // botao para dar logout do sistema
        logoutB.setBorderPainted(false);
        logoutB.setFocusPainted(false);
        logoutB.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        new TelaLogin().setVisible(true);
                    }

                });

        // definir cor de fundo para os butões
        abaCarros.setBackground(new Color(26, 9, 133));
        abaCliente.setBackground(new Color(26, 9, 133));
        abaAlugar.setBackground(new Color(26, 9, 133));
        abaDevolver.setBackground(new Color(26, 9, 133));
        logoutB.setBackground(new Color(26, 9, 133));

        // definir a cor das letras dos butões
        abaCarros.setForeground(Color.WHITE);
        abaCliente.setForeground(Color.WHITE);
        abaAlugar.setForeground(Color.WHITE);
        abaDevolver.setForeground(Color.WHITE);
        logoutB.setForeground(Color.WHITE);

        // definir a posicão dos butões
        abaCarros.setBounds(0, 170, 280, 75);
        abaCliente.setBounds(0, 260, 280, 75);
        abaAlugar.setBounds(0, 350, 280, 75);
        abaDevolver.setBounds(0, 440, 280, 75);
        logoutB.setBounds(0, 740, 280, 65);

        // definir fonte para os butões
        abaCarros.setFont(new Font("Lucida", Font.PLAIN, 19));
        abaCliente.setFont(new Font("Lucida", Font.PLAIN, 19));
        abaAlugar.setFont(new Font("Lucida", Font.PLAIN, 19));
        abaDevolver.setFont(new Font("Lucida", Font.PLAIN, 19));
        logoutB.setFont(new Font("Lucida", Font.PLAIN, 19));

        // adicionar os butões ao painel da esquerda
        esquerdaP.add(abaCarros);
        esquerdaP.add(abaCliente);
        esquerdaP.add(abaAlugar);
        esquerdaP.add(abaDevolver);
        esquerdaP.add(logoutB);

        // botao para fechar a tela
        ImageIcon fecharIcon = new ImageIcon("src/badfox16/autostandmz/icon/fechar.png");
        JButton fecharButton = new JButton(fecharIcon);
        fecharButton.setBounds(5, 5, 20, 20);
        fecharButton.setBorderPainted(false);
        fecharButton.setContentAreaFilled(false);
        fecharButton.setFocusPainted(false);
        fecharButton.setOpaque(false);
        fecharButton.addActionListener(e -> System.exit(0));
        esquerdaP.add(fecharButton);

        // tornar a tela arrastavel
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });

        // tamanho, visimodelolidade e posicao relativa
        add(direitaAlgr);
        add(direitaCar);
        add(direitaCln);
        add(direitaDvlvr);
        add(esquerdaP);
        add(direitaM);
        setSize(1200, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // metodo mostrar para a aba de clientes
    private void MostrarClientes() {
        ClientesDal objClientesDal = new ClientesDal();
        modelo.setNumRows(0);

        // ArrayList<ClientesDTO> list = objClientesDal.PesquisaCln();

        for (ClientesDTO list : objClientesDal.PesquisaCln()) {
            modelo.addRow(new Object[] {
                    list.getId_cliente(),
                    list.getNome(),
                    list.getBI(),
                    list.getTelefone()
            });
        }
    }

    // metodo para carregar campo da tabela de clientes
    private void CarregarCampoCln() {
        int set = tbClientes.getSelectedRow();
        idClnF.setText(tbClientes.getModel().getValueAt(set, 0).toString());
        nomeF.setText(tbClientes.getModel().getValueAt(set, 1).toString());
        biF.setText(tbClientes.getModel().getValueAt(set, 2).toString());
        telefoneF.setText(tbClientes.getModel().getValueAt(set, 3).toString());

    }

    private void CadastrarCln() {
        String nome, bi, telefone;

        nome = nomeF.getText();
        bi = biF.getText();
        telefone = telefoneF.getText();

        // transferencia dos dados inseridos para o objeto objClientesDTO
        ClientesDTO objClientesDTO = new ClientesDTO();
        objClientesDTO.setNome(nome);
        objClientesDTO.setBI(bi);
        objClientesDTO.setTelefone(telefone);

        // passagem dos dados de cadastro para base de dados
        ClientesDal objUsrDal = new ClientesDal();
        objUsrDal.signupClientes(objClientesDTO);
    }

    private void LimparCampoCln() {
        idClnF.setText("");
        nomeF.setText("");
        biF.setText("");
        telefoneF.setText("");
        nomeF.requestFocus();

    }

    private void AlterarCln() {
        int id_cliente;
        String nome, bi, telefone;

        id_cliente = Integer.parseInt(idClnF.getText());
        nome = nomeF.getText();
        bi = biF.getText();
        telefone = telefoneF.getText();

        ClientesDTO objClientesDTO = new ClientesDTO();

        objClientesDTO.setId_cliente(id_cliente);
        objClientesDTO.setNome(nome);
        objClientesDTO.setBI(bi);
        objClientesDTO.setTelefone(telefone);

        ClientesDal objCarroDal = new ClientesDal();
        objCarroDal.editarCln(objClientesDTO);

    }

    public void ApagarCliente() {
        int id_cliente;

        id_cliente = Integer.parseInt(idClnF.getText());

        ClientesDTO objClientesDTO = new ClientesDTO();

        objClientesDTO.setId_cliente(id_cliente);

        ClientesDal objCarroDal = new ClientesDal();
        objCarroDal.apagarCln(objClientesDTO);

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
        modeloCar.setNumRows(0);

        // ArrayList<CarrosDTO> list = objCarrosDal.PesquisaCln();

        for (CarrosDTO list : objCarrosDal.PesquisaCar()) {
            modeloCar.addRow(new Object[] {
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
        String marca, modelo, matricula, estado;

        id_carro = Integer.parseInt(idF.getText());
        preco = Integer.parseInt(precoF.getText());
        marca = marcaF.getText();
        modelo = modeloF.getText();
        matricula = matriculaF.getText();
        estado = (String) status.getSelectedItem();

        CarrosDTO objCarrosDTO = new CarrosDTO();

        objCarrosDTO.setId_carro(id_carro);
        objCarrosDTO.setMarca(marca);
        objCarrosDTO.setMatricula(matricula);
        objCarrosDTO.setModelo(modelo);
        objCarrosDTO.setPreco(preco);
        objCarrosDTO.setStatus(estado);

        CarrosDal objCarroDal = new CarrosDal();
        objCarroDal.editarCar(objCarrosDTO);

    }

    public void ApagarCar() {
        int id_carro;

        id_carro = Integer.parseInt(idF.getText());

        CarrosDTO objCarrosDTO = new CarrosDTO();

        objCarrosDTO.setId_carro(id_carro);

        CarrosDal objCarroDal = new CarrosDal();
        objCarroDal.apagarCar(objCarrosDTO);

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void Alugar() {
        int id_carro, id_cliente;
        double taxaTotal;
        Date dataAlugar, dataDevolver;
    
        // Recuperar os valores do formulário
        id_carro = Integer.parseInt(car_idJcb.getSelectedItem().toString());
        id_cliente = Integer.parseInt(id_ClnF.getText());
    
        // Converter java.util.Date para java.sql.Date
        dataAlugar = new Date(dtInicial.getDate().getTime());
        dataDevolver = new Date(dtFinal.getDate().getTime());
    
        // Instanciar o DTO de Aluguer
        AluguerDTO objAluguerDTO = new AluguerDTO();
        objAluguerDTO.setId_carro(id_carro);
        objAluguerDTO.setId_cliente(id_cliente);
        objAluguerDTO.setData_alugar(dataAlugar);
        objAluguerDTO.setData_devolver(dataDevolver);
    
        // Chamar o método para criar o aluguer
        AluguerDal objAluguerDal = new AluguerDal();
        objAluguerDal.createAlugar(objAluguerDTO);
    
        // Limpar os campos do formulário
        car_idJcb.setSelectedItem("Selecione");
        id_ClnF.setText("");
        taxAlgrF.setText("");
        dtInicial.setDate(null);
        dtFinal.setDate(null);
    }
    
    // Vector<Integer> id_car = new Vector<>();

    @SuppressWarnings("unchecked")
    public void dadosComboBox() {

        try {
            CarrosDal objCarrosDal = new CarrosDal();
            ResultSet rs = objCarrosDal.listarIdCarro();

            while (rs.next()) {
                // id_car.addElement(rs.getInt(1));
                car_idJcb.addItem(rs.getInt(1));
            }

        } catch (Exception e) {

        }

    }

    private void id_ClnL(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String clientID = id_ClnF.getText();

            try {
                conn = new ConexaoSQL().BDconecta();
                prep = conn.prepareStatement("select * from tbClientes where IDCliente = ?");
                prep.setString(1, clientID);

                algrSet = prep.executeQuery();

                if (algrSet.next() == false) {
                    JOptionPane.showMessageDialog(null,
                            "Cliente não encontrado");
                } else {
                    String nomeC = algrSet.getString("Nome");
                    String apelidoC = algrSet.getString("Apelido");
                    nomeClnF.setText(nomeC.trim() + " " + apelidoC.trim());
                    dtInicial.setEnabled(true);
                    dtFinal.setEnabled(true);
                }
            } catch (Exception e) {

            }
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private void MostrarAluguer() {
        AluguerDal objAluguerDal = new AluguerDal();
        modeloD.setNumRows(0);

        // ArrayList<AluguerDTO> list = objAluguerDal.PesquisaCln();

        for (AluguerDTO list : objAluguerDal.PesquisaAluguer()) {
            modeloD.addRow(new Object[] {
                    list.getCodigo(),
                    list.getNomeCliente(),
                    list.getMatriculaCarro(),
                    list.getTaxa(),
                    list.getData_alugar(),
                    list.getData_devolver()
            });
        }
    }
    // metodo para devolver carro

    private void dvlvrCarro(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String IdAluguer = codigoDvlvrF.getText();

            try {
                conn = new ConexaoSQL().BDconecta();
                String sql = "SELECT a.Codigo, a.IDCarro, a.Taxa, a.Data_Aluguer, a.Data_Devolucao, a.Estado, "
                + "c.Nome AS NomeCliente, c.Apelido, c.Telefone, "
                + "car.Modelo, car.Marca, car.Matricula "
                + "FROM tbAluguer a "
                + "JOIN tbClientes c ON a.IDCliente = c.IDCliente "
                + "JOIN tbCarros car ON a.IDCarro = car.IDCarro "
                + "WHERE a.Codigo = ?";
                prep = conn.prepareStatement(sql);
                prep.setString(1, IdAluguer);

                algrSet = prep.executeQuery();

                if (algrSet.next() == false) {
                    JOptionPane.showMessageDialog(null,
                            "Aluguer não encontrado");
                } else {
                    String Modelo = algrSet.getString("Modelo");
                    String Marca = algrSet.getString("Marca");
                    idCarDvlvrF.setText(Marca.trim() + " " + Modelo.trim());

                    String nomeC = algrSet.getString("NomeCliente");
                    String apelidoC = algrSet.getString("Apelido");
                    clnDvF.setText(nomeC.trim() + " " + apelidoC.trim());
                }
            } catch (Exception e) {

            }
        }
    }

    public void Devolver() {
        int codigoAluguer;
        codigoAluguer = Integer.parseInt(codigoDvlvrF.getText());
    
        String sqlAtualizaAluguer = "UPDATE tbAluguer SET Estado = 'Concluido', Data_Devolucao = ?, Taxa = ? WHERE Codigo = ?";
        String sqlAtualizaCarro = "UPDATE tbCarros SET Status = 'Disponível' WHERE IDCarro = (SELECT IDCarro FROM tbAluguer WHERE Codigo = ?)";
        String sqlRecuperaDadosAluguer = "SELECT a.Data_Aluguer, c.Taxa_Diaria FROM tbAluguer a "
                + "JOIN tbCarros c ON a.IDCarro = c.IDCarro WHERE a.Codigo = ?";
    
        Dconn = new ConexaoSQL().BDconecta();
    
        try (PreparedStatement stmtAtualizaAluguer = Dconn.prepareStatement(sqlAtualizaAluguer);
             PreparedStatement stmtAtualizaCarro = Dconn.prepareStatement(sqlAtualizaCarro);
             PreparedStatement stmtRecuperaDadosAluguer = Dconn.prepareStatement(sqlRecuperaDadosAluguer)) {
    
            // Recuperar os dados do aluguer (Data de Aluguer e Taxa Diária)
            stmtRecuperaDadosAluguer.setInt(1, codigoAluguer);
            try (ResultSet rs = stmtRecuperaDadosAluguer.executeQuery()) {
                if (rs.next()) {
                    Date dataAluguer = rs.getDate("Data_Aluguer");
                    double taxaDiaria = rs.getDouble("Taxa_Diaria");
    
                    // Calcular a data de devolução (data atual)
                    long millis = System.currentTimeMillis();
                    Date dataDevolucao = new java.sql.Date(millis);
    
                    // Calcular a diferença em dias entre a Data de Aluguer e a Data de Devolução
                    long diferencaEmMillis = dataDevolucao.getTime() - dataAluguer.getTime();
                    long dias = diferencaEmMillis / (1000 * 60 * 60 * 24); // Converter milissegundos em dias
    
                    // Garantir que pelo menos 1 dia seja cobrado
                    dias = (dias == 0) ? 1 : dias;
    
                    // Calcular a nova taxa com base nos dias efetivamente alugados
                    double novaTaxa = dias * taxaDiaria;
    
                    // Atualizar a data de devolução e a taxa recalculada no aluguer
                    stmtAtualizaAluguer.setDate(1, dataDevolucao); // Data de devolução (agora)
                    stmtAtualizaAluguer.setDouble(2, novaTaxa); // Nova taxa calculada
                    stmtAtualizaAluguer.setInt(3, codigoAluguer); // Código do aluguer
                    stmtAtualizaAluguer.executeUpdate();
    
                    // Atualizar o status do carro para 'Disponível'
                    stmtAtualizaCarro.setInt(1, codigoAluguer);
                    stmtAtualizaCarro.executeUpdate();
                } else {
                    JOptionPane.showMessageDialog(null, "Código de aluguer não encontrado!");
                }
            }
    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao devolver carro: " + e.getMessage());
        } finally {
            try {
                if (Dconn != null) {
                    Dconn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        // Limpar os campos do formulário
        codigoDvlvrF.setText("");
        clnDvF.setText("");
        idCarDvlvrF.setText("");
        MostrarAluguer();
    }
    

    public static void main(String[] args) {
        TelaMain T = new TelaMain();
        T.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        T.setVisible(true);
    }
}
