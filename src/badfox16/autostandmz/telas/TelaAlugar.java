package badfox16.autostandmz.telas;

import javax.swing.JFrame;

import com.toedter.calendar.JDateChooser;

import badfox16.autostandmz.dal.AluguerDal;
import badfox16.autostandmz.dal.CarrosDal;
import badfox16.autostandmz.dal.ConexaoSQL;
import badfox16.autostandmz.dto.AluguerDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class TelaAlugar extends JFrame {
        private JDateChooser dtInicial, dtFinal;
        private JPanel direitaAlgr, redPanel;
        private JButton confirmar, cancelar;
        private JLabel car_idLbl, id_ClnL, nomeClnL, taxAlgrL, dataL, dataFL;
        private JTextField id_ClnF, nomeClnF, taxAlgrF, statusF;
        private JComboBox car_idJcb;

        Connection conn;
        PreparedStatement prep;
        ResultSet algrSet;

        public TelaAlugar() {

                direitaAlgr = new JPanel();
                direitaAlgr.setLayout(null);
                direitaAlgr.setBounds(0, 0, 640, 800);
                direitaAlgr.setBackground(new Color(225, 230, 235, 255));
                add(direitaAlgr);

                redPanel = new JPanel(null);
                redPanel.setBounds(640, 0, 280, 800);
                redPanel.setBackground(new Color(197, 4, 27));
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
                                                                                "Carro não encontrado");
                                                        } else {
                                                                String stat = algrSet.getString("Status");
                                                                statusF.setText(stat.trim());
                                                                String preco = algrSet.getString("Preco");
                                                                taxAlgrF.setText(preco.trim());

                                                                if (stat.equals("Disponivel")) {
                                                                        id_ClnF.setEnabled(true);

                                                                }
                                                        }
                                                } catch (SQLException e1) {
                                                        // TODO Auto-generated catch block
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
                dtInicial = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
                dtInicial.setBounds(280, 380, 200, 40);
                //dtInicial.setDateFormatString("yyyy/MM/dd");
                direitaAlgr.add(dtInicial);

                dataL = new JLabel("Data: ");
                dataL.setBounds(120, 380, 200, 40);
                direitaAlgr.add(dataL);
                // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                dtFinal = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
                dtFinal.setBounds(280, 450, 200, 40);
               // dtFinal.setDateFormatString("yyyy/MM/dd");
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
                                // TODO Auto-generated method stub

                        }

                        @Override
                        public void keyReleased(KeyEvent e) {
                                // TODO Auto-generated method stub

                        }
                };

                id_ClnF.addKeyListener(listener);

                setSize(920, 800);
                setVisible(true);
                setLocationRelativeTo(null);
        }
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        private void Alugar() {
                int id_carro, id_cliente, taxa;
                String data, dataDvlvr;
                //dtInicial.setDateFormatString("yyyy/MM/dd");
                //dtFinal.setDateFormatString("yyyy/MM/dd", "####/##/##");

                id_carro = (Integer) car_idJcb.getSelectedItem();
                id_cliente = Integer.parseInt(id_ClnF.getText());
                taxa = Integer.parseInt(taxAlgrF.getText());
                data = dtInicial.getDate().toString();
                dataDvlvr =  dtFinal.getDate().toString();

                AluguerDTO objAluguerDTO = new AluguerDTO();
                objAluguerDTO.setId_carro(id_carro);
                objAluguerDTO.setId_cliente(id_cliente);
                objAluguerDTO.setTaxa(taxa);
                objAluguerDTO.setData_alugar(data);
                objAluguerDTO.setData_devolver(dataDvlvr);

                AluguerDal objAluguerDal = new AluguerDal();
                objAluguerDal.createAlugar(objAluguerDTO);

                // atualizar o status do carro para ocupado

                String sql = "update tbCarros set Status = 'Ocupado' where IDCarro = ?";
                conn = new ConexaoSQL().BDconecta();

                try {
                        prep = conn.prepareStatement(sql);
                        prep.setInt(1, objAluguerDTO.getId_carro());

                        prep.executeUpdate();
                        prep.close();
                } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Alugar Carro" + e);
                }
        }

        Vector<Integer> id_car = new Vector<>();

        public void dadosComboBox() {

                try {
                        CarrosDal objCarrosDal = new CarrosDal();
                        ResultSet rs = objCarrosDal.listarIdCarro();

                        while (rs.next()) {
                                id_car.addElement(rs.getInt(1));
                                car_idJcb.addItem(rs.getInt(1));
                        }

                } catch (Exception e) {
                        // TODO: handle exception
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
                                        nomeClnF.setText(nomeC.trim());
                                        dtInicial.setEnabled(true);
                                        dtFinal.setEnabled(true);
                                }
                        } catch (Exception e) {
                                // TODO: handle exception
                        }
                }
        }

        public static void main(String[] args) {
                new TelaAlugar().dadosComboBox();
        }
}
