package badfox16.autostandmz.telas;

import javax.swing.*;
import badfox16.autostandmz.dal.UsuarioDal;
import badfox16.autostandmz.dto.UsuariosDTO;
import java.awt.*;
import java.awt.event.*;

public class TelaUsuarios extends JFrame {
    // variaveis relevantes
    private JLabel nomeL, apelidoL, telefoneL, usrnameL, senhaL;
    private JTextField nomeF, apelidoF, telefoneF, usrnameF, senhaF;
    private ImageIcon signupIco, icone;
    private JButton signupButton, pesquisaB;
    private JPanel userPanel;

    // construtor
    public TelaUsuarios() {
        super("AUTOSTAND - Aluguer de Carros");

        // icone da janela
        this.icone = new ImageIcon("src/badfox16/autostandmz/icon/Usuario.png");
        setIconImage(this.icone.getImage());

        // painel para as coisas todas
        userPanel = new JPanel();
        userPanel.setBounds(0,0,720,600);
        add(userPanel);
        // labels e fields obrigatorios para a inscricao de um novo usuario no sistema
        nomeL = new JLabel("Nome");
        apelidoL = new JLabel("Apelido");
        telefoneL = new JLabel("Telefone");
        usrnameL = new JLabel("Username");
        senhaL = new JLabel("Senha");
        nomeF = new JTextField(30);
        apelidoF = new JTextField(30);
        telefoneF = new JTextField(15);
        usrnameF = new JTextField(30);
        senhaF = new JPasswordField(30);

        // posicionamento e tamanho dos componentes (labels e fields)
        nomeL.setBounds(450, 25, 250, 30);
        nomeF.setBounds(450, 50, 300, 30);
        apelidoL.setBounds(450, 75, 250, 30);
        apelidoF.setBounds(450, 100, 300, 30);
        telefoneL.setBounds(450, 125, 250, 30);
        telefoneF.setBounds(450, 150, 300, 30);
        usrnameL.setBounds(450, 175, 250, 30);
        usrnameF.setBounds(450, 200, 300, 30);
        senhaL.setBounds(450, 225, 250, 30);
        senhaF.setBounds(450, 250, 300, 30);

        // botao para cadastro de novo usuario que so o administrador podera realizar
        signupIco = new ImageIcon("src/badfox16/autostandmz/icon/user.png", "Signup");
        signupButton = new JButton("SIGN-UP", signupIco);
        signupButton.setForeground(Color.lightGray);
        signupButton.setBackground(Color.black);
        signupButton.setBounds(750, 250, 150, 45);
        signupButton.setFont(new Font("Lucida", Font.BOLD, 14));

        // cadastro do usuario na base de dados
        signupButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String nome, apelido, telefone, usrname, senha;

                        nome = nomeF.getText();
                        apelido = apelidoF.getText();
                        telefone = telefoneF.getText();
                        usrname = usrnameF.getText();
                        senha = senhaF.getText();

                        // transferencia dos dados inseridos para o objeto objUsuariosDTO
                        UsuariosDTO objUsuariosDTO = new UsuariosDTO();
                        objUsuariosDTO.setNome(nome);
                        objUsuariosDTO.setApelido(apelido);
                        objUsuariosDTO.setTelefone(telefone);
                        objUsuariosDTO.setUsername(usrname);
                        objUsuariosDTO.setSenha(senha);

                        // passagem dos dados de cadastro para base de dados
                        UsuarioDal objUsrDal = new UsuarioDal();
                        objUsrDal.signupUsuario(objUsuariosDTO);
                    }
                });

        /*
         * /codido para tabela bb
         * 
         * 
         * String [] colunas = {"Nome", "Apelido", "Telefone", "Username", "Senha"};
         * tbUsuarios = new JTable();
         * tbUsuarios.setPreferredScrollableViewportSize(new Dimension(700,300));
         * tbUsuarios.setFillsViewportHeight(true);
         * tbUsuarios.setBounds(280, 380, 720, 300);
         * 
         * //JScrollPane scrollPane = new JScrollPane(tbUsuarios);
         */

        // botaozin pra pesquisar cenas
        pesquisaB = new JButton("PesquisaUsr");
        pesquisaB.setForeground(Color.lightGray);
        pesquisaB.setBackground(Color.black);
        pesquisaB.setBounds(750, 150, 150, 45);
        pesquisaB.setFont(new Font("Lucida", Font.BOLD, 14));

        // acao para o botao de pesquisa usr

        pesquisaB.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                    }
                });

        // adicionar os componentes ao painel da direita
        userPanel.add(nomeF);
        userPanel.add(nomeL);
        userPanel.add(apelidoF);
        userPanel.add(apelidoL);
        userPanel.add(telefoneF);
        userPanel.add(telefoneL);
        userPanel.add(usrnameF);
        userPanel.add(usrnameL);
        userPanel.add(senhaF);
        userPanel.add(senhaL);
        userPanel.add(signupButton);
        userPanel.add(pesquisaB);

        // tamanho, visiblidade
        setSize(720, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaUsuarios().setDefaultCloseOperation(EXIT_ON_CLOSE);;
    }
}
