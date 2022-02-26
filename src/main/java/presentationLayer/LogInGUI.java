package presentationLayer;

import businessLayer.DeliveryService;
import dataLayer.Serializator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogInGUI {
    List<User> loggedUsers= new ArrayList<>();
    private JTextField username = new JTextField();
    private JTextField password = new JTextField();
    private JButton logInButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private JFrame loginFrame;
    private DeliveryService deliveryService = new DeliveryService();

    public LogInGUI(){
        Serializator serializator = new Serializator();
        try {
            deliveryService = (DeliveryService) serializator.deserialization("deliveryService.txt");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        deliveryService.setIndexIDProduct(1);
        initialize();
    }

    public void initialize() {
        loginFrame = new JFrame();
        loginFrame.setVisible(true);
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN,   18));
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN,   18));
        GridLayout gr = new GridLayout(7, 2);
        gr.setVgap(18);
        gr.setHgap(20);
        JPanel panel1 = new JPanel(gr);
        panel1.setBackground(new Color(46, 153, 108));
        panel1.add(loginLabel);
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel1.add(usernameLabel);
        panel1.add(username);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel1.add(passwordLabel);
        panel1.add(password);
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        panel1.add(logInButton);
        panel1.add(registerButton);
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        loginFrame.add(panel1);
        loginFrame.setTitle("Food delivery management system");
        loginFrame.getContentPane().setBackground(new Color(63, 208, 145));
        loginFrame.setBounds(200, 200, 540, 450);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean found = false;
                if(username.getText().isEmpty() || password.getText().isEmpty()){
                    JOptionPane.showMessageDialog(loginFrame, "Insert both username and password","Inane Error", JOptionPane.ERROR_MESSAGE);
                }else
                {
                    Serializator serializator = new Serializator();
                    try {
                        loggedUsers = (List<User>)serializator.deserialization("loggedUsers.txt");
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }
                    String userName= username.getText();
                    String userPassword = password.getText();
                    if(loggedUsers.isEmpty()){
                        JOptionPane.showMessageDialog(loginFrame, "Thre are no users! Register first!", "Inane Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        for(User user : loggedUsers){
                            if(user.getUsername().equals(userName) && user.getPassword().equals(userPassword)){
                                found = true;
                                if(user.getUserRole() == UserRole.ADMINISTRATOR){
                                    Administrator administratorGUI = new Administrator(deliveryService);
                                    loginFrame.dispose();

                                }
                                else if(user.getUserRole() == UserRole.CLIENT){
                                    Client clientGUI = new Client(user.getUserId(), deliveryService );
                                    loginFrame.dispose();
                                }else{
                                    Employee empGUI = new Employee(deliveryService);
                                    loginFrame.dispose();
                                }
                                break;
                            }
                        }
                        if(found == false)
                            JOptionPane.showMessageDialog(loginFrame, "There is no user with username  "+ userName + " and password "+userPassword + " !!", "Inane error", JOptionPane.ERROR_MESSAGE);

                    }

                }
            }
        });
       registerButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               RegistrationGUI registrationGUI = new RegistrationGUI();
               loginFrame.dispose();
           }
       });

    }
}
