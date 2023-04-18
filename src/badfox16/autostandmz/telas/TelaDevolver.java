package badfox16.autostandmz.telas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import badfox16.autostandmz.dal.AluguerDal;
import badfox16.autostandmz.dal.ConexaoSQL;
import badfox16.autostandmz.dto.AluguerDTO;
import java.awt.*;

public class TelaDevolver extends JFrame {
    private JPanel direitaDvlvr, redPanelD;
    private JLabel idcarDvl, idClnDvl, nomeDvl;
    private JTextField carDvF, clnDvF, nomeDvF;
    private JButton btnDevolver;
    Connection Dconn;
    PreparedStatement prepD;
    ResultSet dvlvSet;

    private JTable tbAluguer;
    private DefaultTableModel modeloD = new DefaultTableModel();

    

    public TelaDevolver() {
        direitaDvlvr = new JPanel();
        direitaDvlvr.setLayout(null);
        direitaDvlvr.setBounds(0, 0, 640, 800);
        direitaDvlvr.setBackground(new Color(225, 230, 235, 255));
        add(direitaDvlvr);

        redPanelD = new JPanel(null);
        redPanelD.setBounds(720, 0, 200, 800);
        redPanelD.setBackground(new Color(197, 4, 27));
        direitaDvlvr.add(redPanelD);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        idcarDvl = new JLabel("ID Carro"); carDvF = new JTextField(30);
        idClnDvl  = new JLabel("ID Cliente"); clnDvF = new JTextField(30);
        nomeDvl = new JLabel("Nome"); nomeDvF = new JTextField(30);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        idcarDvl.setBounds(100, 100, 100, 30);
        direitaDvlvr.add(idcarDvl);

        carDvF.setBounds(100, 125, 80, 30);
        direitaDvlvr.add(carDvF);
        // .setEnabled(false);
        idClnDvl.setBounds(250, 100, 70, 30);
        direitaDvlvr.add(idClnDvl);

        clnDvF.setBounds(250, 125, 100, 30);
        direitaDvlvr.add(clnDvF);

        nomeDvl.setBounds(410, 100, 50, 30);
        direitaDvlvr.add(nomeDvl);

        nomeDvF.setBounds(410, 125, 200, 30);
        direitaDvlvr.add(nomeDvF);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        btnDevolver = new JButton("Devolver");
        btnDevolver.setBounds(280, 200, 140, 40);
        direitaDvlvr.add(        btnDevolver        );
        btnDevolver.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Devolver();
                }
            });

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // tabela para ler os dados inseridos
        Object[] columnD = { "Codigo", "ID Cliente", "ID Carro", "Taxa", "Data Aluguer", "Data Devolução" };
        modeloD.setColumnIdentifiers(columnD);

        tbAluguer = new JTable();
        tbAluguer.setModel(modeloD);
        tbAluguer.setBackground(Color.lightGray);
        tbAluguer.setFont(new Font("Lucida", Font.PLAIN, 12));
        tbAluguer.setRowHeight(tbAluguer.getRowHeight() + 10);

        JScrollPane rolagemD = new JScrollPane(tbAluguer);
        rolagemD.getViewport().setViewPosition(new Point(280, 280));
        rolagemD.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        rolagemD.setBounds(0, 400, 720, 400);
        direitaDvlvr.add(rolagemD);

        //listener para carregar os dados
        KeyListener listener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent ev) {
                    carIDLoad(ev);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                    // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                    // TODO Auto-generated method stub

            }
    };

    carDvF.addKeyListener(listener);

        setSize(920, 800);
        setResizable(false);
        setVisible(true);
    }

    private void MostrarAluguer() {
        AluguerDal objAluguerDal = new AluguerDal();
        modeloD.setNumRows(0);

        // ArrayList<AluguerDTO> list = objAluguerDal.PesquisaCln();

        for (AluguerDTO list : objAluguerDal.PesquisaAluguer()) {
            modeloD.addRow(new Object[] {
                    list.getCodigo(),
                    list.getId_carro(),
                    list.getId_cliente(),
                    list.getTaxa(),
                    list.getData_alugar(),
                    list.getData_devolver()
            });
        }
    }

    public void Devolver() {
        int DcarId;

        DcarId  = Integer.parseInt(carDvF.getText());

        AluguerDTO objAluguerDTO = new AluguerDTO();
        objAluguerDTO.setId_carro(DcarId);

        String sql = "update tbCarros set Status = 'Disponivel' where IDCarro = ?";
        Dconn = new ConexaoSQL().BDconecta();

        try {
                prepD = Dconn.prepareStatement(sql);
                prepD.setInt(1, objAluguerDTO.getId_carro());

                prepD.executeUpdate();
                prepD.close();
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Alugar Carro" + e);
        }
    }

    private void carIDLoad(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                String carID = carDvF.getText();

                try {
                        Dconn = new ConexaoSQL().BDconecta();
                        prepD = Dconn.prepareStatement("select * from tbClientes where IDCliente = ?");
                        prepD.setString(1, carID);

                        dvlvSet = prepD.executeQuery();

                        if (dvlvSet.next() == true) {
                                JOptionPane.showMessageDialog(null,
                                                "O carro está disponivel ou não existe");
                        } else {
                                String nomeC = dvlvSet.getString("Nome");
                                nomeDvF.setText(nomeC.trim());
                                
                                int idC = dvlvSet.getInt("IDCliente");
                                String idCln = Integer.toString(idC);
                                clnDvF.setText(idCln.trim());
                        }
                } catch (Exception e) {
                        // TODO: handle exception
                }
        }
}

    public static void main(String[] args) {
        new TelaDevolver().MostrarAluguer();
        // new TelaDevolver().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
