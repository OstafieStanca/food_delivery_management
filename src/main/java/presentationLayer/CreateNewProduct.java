package presentationLayer;

import businessLayer.BaseProduct;
import businessLayer.DeliveryService;
import dataLayer.Serializator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class CreateNewProduct {

    private DeliveryService deliveryService;
    private JTextField title = new JTextField();
    private JTextField calories = new JTextField();
    private JTextField proteins = new JTextField();
    private JTextField fats = new JTextField();
    private JTextField sodium = new JTextField();
    private JTextField price = new JTextField();
    private JTextField rating = new JTextField();
    private JFrame newProductFrame;
    private JButton back = new JButton("Go back");
    private JButton createProduct = new JButton("Create");

    public CreateNewProduct(DeliveryService ds){
        deliveryService = ds;
        newProductFrame = new JFrame();
        newProductFrame.setVisible(true);
        GridLayout gr = new GridLayout(10, 2);
        gr.setVgap(18);
        gr.setHgap(20);
        JPanel panel1 = new JPanel(gr);
        JLabel jl = new JLabel("Create New Product");
        panel1.setBackground(new Color(19, 146, 97));
        panel1.add(jl);
        jl.setFont(new Font("Times New Roman", Font.PLAIN,   18));
        panel1.add(new JLabel(""));
        panel1.add(new JLabel("Title : "));
        panel1.add(title);
        panel1.add(new JLabel("Rating : "));
        panel1.add(rating);
        panel1.add(new JLabel("Calories : "));
        panel1.add(calories);
        panel1.add(new JLabel("Proteins : "));
        panel1.add(proteins);
        panel1.add(new JLabel("Fats : "));
        panel1.add(fats);
        panel1.add(new JLabel("Sodium : "));
        panel1.add(sodium);
        panel1.add(new JLabel("Price : "));
        panel1.add(price);
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        panel1.add(back);
        panel1.add(createProduct);

        back.addActionListener(e->{
            newProductFrame.dispose();
            deliveryService.saveInf();
            Serializator s = new Serializator();
            try {
                deliveryService = (DeliveryService) s.deserialization("deliveryService.txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
            Administrator administrator = new Administrator(deliveryService);

        });

        createProduct.addActionListener(e->{
            if(title.toString().isEmpty() || rating.getText().toString().isEmpty() || calories.getText().isEmpty() || proteins.getText().isEmpty() || fats.getText().isEmpty() || sodium.getText().isEmpty() || price.getText().isEmpty()){
                JOptionPane.showMessageDialog(newProductFrame, "Introduce data to all fileds", "Inane Error", JOptionPane.ERROR_MESSAGE);
            }
            else{
                int id = deliveryService.getIndexOfProductCP();
                String title1 = title.getText();
                float rating1 = Float.parseFloat(rating.getText());
                int calories1 = Integer.parseInt(calories.getText());
                int proteins1 = Integer.parseInt(proteins.getText());
                int fats1 = Integer.parseInt(fats.getText());
                int sodium1 = Integer.parseInt(sodium.getText());
                int price1  = Integer.parseInt(price.getText());
                BaseProduct bp = new BaseProduct(id, title1, rating1, calories1, proteins1,fats1, sodium1, price1);
                deliveryService.addProduct(bp);
                deliveryService.saveInf();
                JOptionPane.showMessageDialog(newProductFrame, "The product with id "+ id + " was added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
                });

        newProductFrame.add(panel1);
        newProductFrame.setTitle("Create new product management system");
        newProductFrame.getContentPane().setBackground(new Color(63, 208, 145));
        newProductFrame.setSize(550, 500);
    }

}
