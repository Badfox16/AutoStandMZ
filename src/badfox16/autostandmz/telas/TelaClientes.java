package badfox16.autostandmz.telas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import badfox16.autostandmz.dal.ClientesDal;
import badfox16.autostandmz.dto.ClientesDTO;
import java.awt.*;
import java.awt.event.*;

public class TelaClientes extends JFrame {
    // variaveis para tela cliente
    private JLabel idClnL, nomeL, biL, telefoneL;
    private JTextField idClnF, nomeF, biF, telefoneF;
    private JButton salvarCln, carregar, apagarCln, editarCln, limparCln;
    private JTable tbClientes;
    private DefaultTableModel modelo = new DefaultTableModel();

    private JPanel direitaCln;

    // construtor
    public TelaClientes() {

        // painel para as coisas todas que eu quero fazer

        direitaCln = new JPanel();
        direitaCln.setLayout(null);
        direitaCln.setBounds(0, 0, 920, 700);
        direitaCln.setBackground(new Color(225,230,235,255));

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

        //estilo para as bordas dos textfields
        idClnF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
        nomeF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
        nomeF.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent focusEvent){
                nomeF.setBorder(BorderFactory.createLineBorder(new Color(241, 5, 34)));
    
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                nomeF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
    
            }
    
        });
        biF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
        biF.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent focusEvent){
                biF.setBorder(BorderFactory.createLineBorder(new Color(241, 5, 34)));
    
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                biF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
    
            }
    
        });
        telefoneF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
        telefoneF.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent focusEvent){
                telefoneF.setBorder(BorderFactory.createLineBorder(new Color(241, 5, 34)));
    
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                telefoneF.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));
    
            }
    
        });



      //butoes para as operacoes do crud
        salvarCln = new JButton("Salvar");
        salvarCln.setForeground(Color.lightGray);
        salvarCln.setBackground(new Color(197, 4, 27));
        salvarCln.setBounds(330, 275, 150, 45);
        salvarCln.setFont(new Font("Lucida", Font.BOLD, 16));

        // tabela para ler os dados inseridos
        Object[] column = { "IDCliente", "Nome", "BI", "Telefone" };
        modelo.setColumnIdentifiers(column);

        tbClientes = new JTable();
        tbClientes.setModel(modelo);
        tbClientes.setBackground(Color.lightGray);
        tbClientes.setFont(new Font("Lucida", Font.PLAIN, 12));
        tbClientes.setRowHeight(tbClientes.getRowHeight() + 10);

        JScrollPane rolagem = new JScrollPane(tbClientes);
        rolagem.getViewport().setViewPosition(new Point(280, 280));
        rolagem.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        rolagem.setBounds(0, 400, 920, 400);

        carregar = new JButton("Selecionar");
        
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
        apagarCln.setForeground(Color.lightGray);
        apagarCln.setBackground(new Color(197, 4, 27));
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
        editarCln.setForeground(Color.lightGray);
        editarCln.setBackground(new Color(197, 4, 27));
        editarCln.setFont(new Font("Lucida", Font.BOLD, 16));

        editarCln.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       AlterarCln();
                       MostrarClientes();
                    }
                });

        limparCln = new JButton("Limpar");
        limparCln.setForeground(Color.lightGray);
        limparCln.setBackground(new Color(197, 4, 27));
        limparCln.setBounds(780, 275, 150, 45);
        limparCln.setFont(new Font("Lucida", Font.BOLD, 16));

        limparCln.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        LimparCampoCln();

                    }
                });

                //posicionamento e tamanho dos butoes  
                apagarCln.setBounds(165, 200, 120, 40);
                limparCln.setBounds(305, 200, 120, 40);
                editarCln.setBounds(475, 200, 120, 40);       
                salvarCln.setBounds(645, 200, 120, 40);
                carregar.setBounds(295, 310, 200, 20);

        // adicionar os componentes ao seu respectivo painel
        direitaCln.add(idClnL);direitaCln.add(idClnF);
        direitaCln.add(nomeF);direitaCln.add(nomeL);
        direitaCln.add(biF);direitaCln.add(biL);
        direitaCln.add(telefoneF);direitaCln.add(telefoneL);
        direitaCln.add(salvarCln); direitaCln.add(limparCln);
        direitaCln.add(apagarCln); direitaCln.add(editarCln);
        direitaCln.add(carregar);
        direitaCln.add(rolagem);
        // tamanho, visiblidade e cenas
        add(direitaCln);
        setSize(920, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

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


    public void ApagarCliente(){
        int id_cliente;

        id_cliente = Integer.parseInt(idClnF.getText());

        ClientesDTO objClientesDTO = new ClientesDTO();

        objClientesDTO.setId_cliente(id_cliente);

        ClientesDal objCarroDal = new ClientesDal();
        objCarroDal.apagarCln(objClientesDTO);

    }

    public static void main(String[] args) {
        new TelaClientes().MostrarClientes();
    }
}