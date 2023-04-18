
package badfox16.autostandmz.telas;

//importacao das bibliotecas e pacotes
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import badfox16.autostandmz.dto.*;
import badfox16.autostandmz.dal.*;
/**
 *
 * @author badfox16
 */
public class TelaLogin extends JFrame {
    private JLabel usernLabel, passwordLabel, bvLabel, sobreL, logoL;
    private JTextField usernField, passwordField;
    private JButton loginButton;
    private ImageIcon loginIco,icone, fecharIcon,logoIcon;

    //para arrastar a JFrame pela tela
    private static Point point = new Point();
    //Construtor
   public TelaLogin (){
    super("Login - AUTOSTAND");
    //icone da janela
    this.icone = new ImageIcon("src/badfox16/autostandmz/icon/icon.png");
    setIconImage(this.icone.getImage());

    //paineis para login
    JPanel left = new JPanel();
    JPanel left1 = new JPanel();
    JPanel right = new JPanel();
    left.setBounds(0, 0, 500, 600);
    left1.setBounds(100, 50, 300, 400);
    right.setBounds(500, 0, 350, 600);
    left.setLayout(null);
    left1.setLayout(null);
    right.setLayout(null);

    //textfield e label para Username
    usernLabel  = new JLabel("Username");
    usernField = new JTextField(50);
    
    usernField.addFocusListener(new FocusListener(){
        @Override
        public void focusGained(FocusEvent focusEvent){
            usernField.setBorder(BorderFactory.createLineBorder(new Color(241, 5, 34)));

        }

        @Override
        public void focusLost(FocusEvent e) {
            usernField.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));

        }

    });
    
    //textfield e label para Password
    passwordLabel  = new JLabel("Senha");
    passwordField = new JPasswordField(30);

    passwordField.addFocusListener(new FocusListener(){
        @Override
        public void focusGained(FocusEvent focusEvent){
            passwordField.setBorder(BorderFactory.createLineBorder(new Color(241, 5, 34)));

        }

        @Override
        public void focusLost(FocusEvent e) {
            passwordField.setBorder(BorderFactory.createLineBorder(new Color(175, 2, 23)));

        }

    });

    //label de bem vindo
    bvLabel = new JLabel("Bem-vindo :-)");
    bvLabel.setBounds(47, 30, 200, 25);
    bvLabel.setForeground(Color.BLACK);
    bvLabel.setFont(new Font("Lucida",Font.BOLD,26));

    //label sobre o programa
    sobreL = new JLabel("AUTOSTAND por Mutizo Maita, 2022");
    sobreL.setBounds(41, 370, 250, 20);
    sobreL.setFont(new Font("Lucida",Font.BOLD,11));

    //Botao de login 
    loginIco = new ImageIcon("src/badfox16/autostandmz/icon/login.png","Login");
    loginButton = new JButton("LOGIN",loginIco);
    loginButton.setForeground(Color.white);
    loginButton.setBackground(new Color(175, 2, 23));
    loginButton.setBounds(80, 300, 120, 35);
    loginButton.setBorder(BorderFactory.createBevelBorder(2,new Color(203, 1, 27, 1),new Color(203, 1, 27, 1)));

   //Limites e tamanho dos componentes (labels e fields)
    usernLabel.setBounds(20, 125, 100, 25);
    passwordLabel.setBounds(20, 205, 50, 25);
    usernField.setBounds(20, 150, 250, 35);
    passwordField.setBounds(20, 235, 250, 35);

    //Mudar fonte, tamanho e cor desses componentes malandros
    usernLabel.setFont(new Font("Lucida",Font.BOLD,14));
    usernLabel.setForeground(Color.BLACK);
    passwordLabel.setFont(new Font("Lucida",Font.BOLD,14));
    passwordLabel.setForeground(Color.BLACK);
    usernField.setFont(new Font("Lucida",Font.BOLD,14));
    usernField.setForeground(Color.BLACK);
    passwordField.setFont(new Font("Lucida",Font.BOLD,14));
    loginButton.setFont(new Font("Lucida",Font.BOLD,13));

    //imagem para a tela de login
    logoIcon = new ImageIcon("src/badfox16/autostandmz/icon/autostand.png");
    logoL = new JLabel(logoIcon);
    logoL.setBounds(545, 100, 260, 260);
   
     //botao predefinido(default) + evento para o botao de login
     getRootPane().setDefaultButton(loginButton);//no caso de clicar enter vai para o login

     loginButton.addActionListener(
         new ActionListener() {
             public void actionPerformed(ActionEvent e){
                try {
                    String usrnome, usrsenha;
                    usrnome = usernField.getText();
                    usrsenha = passwordField.getText(); 
    
                    //instancia de um objeto(objUser) para verificar as credenciais a partir da classe Usuarios
                    UsuariosDTO objUser = new UsuariosDTO();
                    objUser.setUsername(usrnome);
                    objUser.setSenha(usrsenha);

                    //importacao da classe UsuarioDAL (Data Acess Layer)
                    UsrLogin objUserDal = new UsrLogin();
                    ResultSet rSetUsrDal = objUserDal.autenticacaoUsr(objUser);

                    if (rSetUsrDal.next()) {
                        //chama a tela principal, so se as credenciais do login estiverem corretas
                        TelaMain tMain = new TelaMain();
                        tMain.setVisible(true);

                        dispose();
                    } else {
                        //envia msg de erro, no caso das credenciais usadas no login estiverem incorretas
                        JOptionPane.showMessageDialog(null, "Username ou Senha Incorreta(s)!");
                        
                    }

                } catch (SQLException erro) {
                    JOptionPane.showMessageDialog(null, "Login: " + erro);
                }
             }
         }
     );
    //botao para fechar a tela
    fecharIcon = new ImageIcon("src/badfox16/autostandmz/icon/fechar.png","Login");
    JButton fecharButton = new JButton(fecharIcon);
    fecharButton.setBounds(820, 5, 20, 20);
    fecharButton.setBorderPainted(false); 
    fecharButton.setContentAreaFilled(false); 
    fecharButton.setFocusPainted(false); 
    fecharButton.setOpaque(false);
    fecharButton.addActionListener(e -> System.exit(0));


    //cor dos paineis
    left.setBackground(new Color(225,230,235,255));
    left1.setBackground(new Color(255,255,255,255));
    right.setBackground(new Color(197, 4, 27));

    //adicionar os componentes aos respetivos paineis
    add(logoL);
    add(fecharButton);
    left1.add(usernLabel);
    left1.add(passwordLabel);
    left1.add(usernField);
    left1.add(passwordField);
    left1.add(loginButton);
    left1.add(bvLabel);
    left1.add(sobreL);
    left.add(left1);
   
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

    //Visualizar
    add(left);
    add(right);
    setSize(850,500);
    setResizable(false);
    setLocationRelativeTo(null);
    setUndecorated(true);
    setLayout(new BorderLayout());
} 
    public static void main(String[] args) {
    TelaLogin app = new TelaLogin();
        app.setVisible(true);

      
    }
}