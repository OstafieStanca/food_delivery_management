package presentationLayer;

import businessLayer.DeliveryService;
import dataLayer.Serializator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationGUI {

    private int userID;
    private JFrame registerFrame;
    private JTextField username = new JTextField();
    private JTextField password = new JTextField();
    private JTextField email = new JTextField();
    private JComboBox userRoleCB = new JComboBox();
    private List<User> users = new ArrayList<>();
    private JButton createAccount = new JButton("Create");
    private JButton back = new JButton("Back");
    private DeliveryService ds;

    public RegistrationGUI()  {

        Serializator s = new Serializator();
        try {
            users = (List<User>) s.deserialization("loggedUsers.txt");
            ds = (DeliveryService) s.deserialization("deliveryService.txt");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.userID = 2;
        registerFrame = new JFrame();
        registerFrame.setVisible(true);
        JLabel registerLabel = new JLabel("Register");
        registerLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN,   18));
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN,   18));
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Times New Roman", Font.PLAIN,   18));
        JLabel userRoleLabel = new JLabel("User role");
        userRoleLabel.setFont(new Font("Times New Roman", Font.PLAIN,   18));
        GridLayout gr = new GridLayout(10, 2);
        gr.setVgap(18);
        gr.setHgap(20);
        JPanel panel1 = new JPanel(gr);
        panel1.setBackground(new Color(63, 208, 145));
        panel1.add(registerLabel);
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel1.add(usernameLabel);
        panel1.add(username);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel1.add(passwordLabel);
        panel1.add(password);
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel1.add(emailLabel);
        panel1.add(email);
        userRoleCB.addItem(UserRole.ADMINISTRATOR);
        userRoleCB.addItem(UserRole.EMPLOYEE);
        userRoleCB.addItem(UserRole.CLIENT);
        userRoleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel1.add(userRoleLabel);
        panel1.add(userRoleCB);
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        panel1.add(createAccount);
        panel1.add(new JLabel(""));
        panel1.add(back);

        registerFrame.add(panel1);
        registerFrame.setTitle("Registration");
        registerFrame.getContentPane().setBackground(new Color(38, 141, 99));
        registerFrame.setBounds(200, 200, 540, 450);

        createAccount.addActionListener(e-> {
                if(username.getText().isEmpty() || password.getText().isEmpty() || email.getText().isEmpty() || userRoleCB.getSelectedItem().toString().isEmpty()){
                    JOptionPane.showMessageDialog(registerFrame, "All filed should contain data", "Inane Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    Serializator serializator = new Serializator();
                    User user = new User(username.getText(),email.getText(), password.getText(), (UserRole)userRoleCB.getSelectedItem(),ds.getUserID());
                    ds.saveInf();
                    users.add(user);
                    try {
                        serializator.serialize(users, "loggedUsers.txt");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(registerFrame, "The account was created with success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
        });

        back.addActionListener(e->{
            LogInGUI logIn = new LogInGUI();
            registerFrame.dispose();
        });

    }
}
