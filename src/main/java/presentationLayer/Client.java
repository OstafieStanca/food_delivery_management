package presentationLayer;

import businessLayer.BaseProduct;
import businessLayer.CompositeProduct;
import businessLayer.DeliveryService;
import businessLayer.Order;
import dataLayer.Serializator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Client implements Serializable {
    private JFrame clientFrame;
    private JTable menuTable;
    private DeliveryService deliveryService = new DeliveryService();
    private JButton back = new JButton("Back");
    private JButton placeOrder = new JButton("Place Order");
    private JButton search = new JButton("Search Items");
    private JButton viewMenu = new JButton("List Menu");
    private JRadioButton rbId = new JRadioButton("Id");
    private JRadioButton rbRating = new JRadioButton("Rating");
    private JRadioButton rbCalories = new JRadioButton("Calories");
    private JRadioButton rbProteins = new JRadioButton("Proteins");
    private JRadioButton rbFats = new JRadioButton("Fats");
    private JRadioButton rbSodium = new JRadioButton("Sodium");
    private JRadioButton rbPrice = new JRadioButton("Price");
    private JTextField minValue = new JTextField();
    private JTextField maxValue = new JTextField();
    private int clientId;
    private static final long serialVersionUID = 1L;
    private Employee emp ;


    public Client(int clientId, DeliveryService ds){
        this.clientId = clientId;
        this.deliveryService = ds;
        initialize();
    }

    public Client(){
        initialize();
    }

    public void initialize(){
        emp = new Employee(deliveryService);
        emp.setFrameState(false);
        clientFrame = new JFrame();
        clientFrame.setVisible(true);
        GridLayout gr = new GridLayout(4, 1);
        gr.setVgap(18);
        gr.setHgap(20);
        JPanel panel1 = new JPanel(gr);
        GridLayout gr3 = new GridLayout(1, 2);
        gr3.setVgap(18);
        gr3.setHgap(20);
        JPanel panel4 = new JPanel(gr3);

        panel4.add(search);
        panel4.add(back);

        menuTable = new JTable();
        menuTable.setVisible(true);
        String[] columnNames = {"Id", "Title", "Rating", "Calories", "Protein", "Fats", "Sodium", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 15000);
        menuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        scrollPane.setVisible(true);
        //scrollPane.setViewportView(menuTable);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rbId);
        buttonGroup.add(rbRating);
        buttonGroup.add(rbCalories);
        buttonGroup.add(rbProteins);
        buttonGroup.add(rbFats);
        buttonGroup.add(rbSodium);
        buttonGroup.add(rbPrice);
        panel1.add(viewMenu);
        panel1.add(placeOrder);
        JLabel fil = new JLabel("Filter");
        fil.setFont(new Font("Times New Roman", Font.BOLD, 40));
        fil.setHorizontalAlignment(SwingConstants.CENTER);
        panel1.add(fil);
        panel1.add(panel4);

        GridLayout gr4 = new GridLayout(7, 2);
        gr4.setVgap(18);
        gr4.setHgap(20);
        JPanel panel5 = new JPanel(gr4);
        rbId.setHorizontalAlignment(SwingConstants.CENTER);
        panel5.add(rbId);
        panel5.add(new JLabel(""));
        rbRating.setHorizontalAlignment(SwingConstants.CENTER);
        panel5.add(rbRating);
        panel5.add(new JLabel("Minimum value:"));
        rbProteins.setHorizontalAlignment(SwingConstants.CENTER);
        panel5.add(rbProteins);
        panel5.add(minValue);
        rbCalories.setHorizontalAlignment(SwingConstants.CENTER);
        panel5.add(rbCalories);
        panel5.add(new JLabel("Maximum Value :"));
        rbFats.setHorizontalAlignment(SwingConstants.CENTER);
        panel5.add(rbFats);
        panel5.add(maxValue);
        rbSodium.setHorizontalAlignment(SwingConstants.CENTER);
        panel5.add(rbSodium);
        panel5.add(new JLabel(""));
        rbPrice.setHorizontalAlignment(SwingConstants.CENTER);
        panel5.add(rbPrice);
        panel5.add(new JLabel(""));

        JPanel panel6 = new JPanel(new GridLayout(2, 1));
        panel6.add(panel1);
        panel6.add(panel5);

        GridLayout gr2 = new GridLayout(1, 1);
        gr2.setVgap(18);
        gr2.setHgap(20);
        JPanel panel2 = new JPanel(gr2);

        menuTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        menuTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        menuTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        menuTable.getColumnModel().getColumn(3).setPreferredWidth(20);
        menuTable.getColumnModel().getColumn(4).setPreferredWidth(20);
        menuTable.getColumnModel().getColumn(5).setPreferredWidth(20);
        menuTable.getColumnModel().getColumn(6).setPreferredWidth(20);
        menuTable.getColumnModel().getColumn(7).setPreferredWidth(20);
        menuTable.getTableHeader().setReorderingAllowed(false);
        menuTable.setRowHeight(25);
        panel1.setBackground(new Color(26, 134, 91));
        panel2.setBackground(new Color(26, 134, 91));
        panel4.setBackground(new Color(26, 134, 91));
        panel5.setBackground(new Color(26, 134, 91));
        panel6.setBackground(new Color(26, 134, 91));

        panel2.add(scrollPane);
        JPanel panel3 = new JPanel(new GridLayout(1,2));
        panel3.add(panel6);
        panel3.add(panel2);
        panel3.setBackground(new Color(26, 134, 91));
        clientFrame.add(panel3);
        clientFrame.setTitle("Client management system");
        clientFrame.getContentPane().setBackground(new Color(63, 208, 145));
        clientFrame.setSize(1200, 550);

        back.addActionListener(e->{
            clientFrame.setVisible(false);
            LogInGUI logInGUI = new LogInGUI();
        });

        viewMenu.addActionListener(e->{
            emp.setFrameState(false);
            try {
                deliveryService.importProducts();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            completeTable(deliveryService.getBaseProducts());
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emp.setFrameState(false);
                if(!rbId.isSelected() && !rbRating.isSelected() && !rbCalories.isSelected() && !rbProteins.isSelected() && !rbFats.isSelected() && !rbSodium.isSelected() && !rbPrice.isSelected()) {
                    JOptionPane.showMessageDialog(clientFrame, "Select one search criteria", "Inane Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(minValue.getText().isEmpty() || maxValue.getText().isEmpty()){
                    JOptionPane.showMessageDialog(clientFrame, "Minimum value and Maximum Value must contain data!", "Inane Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    Serializator ser = new Serializator();
                    try {
                        deliveryService = (DeliveryService) ser.deserialization("deliveryService.txt");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    if(rbRating.isSelected()){
                        float min = Float.parseFloat(minValue.getText());
                        float max = Float.parseFloat(maxValue.getText());
                        HashSet<BaseProduct> bs = deliveryService.orderBaseProducts(deliveryService.searchBaseProductsBasedOnRating(min, max));
                        for(BaseProduct b :  bs){
                            b.toString();
                        }
                        completeTable(bs);
                    }else{
                        int min = Integer.parseInt(minValue.getText());
                        int max = Integer.parseInt(maxValue.getText());
                        if(rbId.isSelected()){
                            completeTable(deliveryService.orderBaseProducts(deliveryService.searchBaseProductsBasedOnId(min, max)));
                        }
                        if(rbCalories.isSelected()){
                            completeTable(deliveryService.orderBaseProducts(deliveryService.searchBaseProductsBasedOnCalories(min, max)));
                        }
                        if(rbPrice.isSelected()){
                            completeTable(deliveryService.orderBaseProducts(deliveryService.searchBaseProductsBasedOnPrice(min, max)));
                        }
                        if(rbProteins.isSelected()){
                            completeTable(deliveryService.orderBaseProducts(deliveryService.searchBaseProductsBasedOnProteins(min, max)));
                        }
                        if(rbSodium.isSelected()){
                            completeTable(deliveryService.orderBaseProducts(deliveryService.searchBaseProductsBasedOnSodium(min, max)));
                        }
                        if(rbFats.isSelected()){
                            completeTable(deliveryService.orderBaseProducts(deliveryService.searchBaseProductsBasedOnFats(min, max)));
                        }
                    }
                }

            }
        });
        placeOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                deliveryService.getMenuItems().clear();
                if(menuTable.getSelectedRows().length == 0){
                    JOptionPane.showMessageDialog(clientFrame, "Select rows from the displayed table", "Inane Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    int columnIndex = 0;
                    List<Integer> ids = new ArrayList<>();
                    for(int r : menuTable.getSelectedRows()){
                        int valueId = Integer.parseInt(menuTable.getValueAt(r,columnIndex).toString());
                        ids.add(valueId);
                    }
                    deliveryService.generateMenu(ids);
                    Order order = deliveryService.createOrder(clientId, deliveryService.getMenuItems());
                    try {
                        deliveryService.generateBill(deliveryService.getMenuItems(), order);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    emp.setFrameState(true);
                    emp.update(order, deliveryService.getMenuItems());
                    clientFrame.dispose();
                    deliveryService.saveInf();
                    deliveryService.getMenuItems().clear();

                }

            }
        });

    }

    public void completeTable(HashSet<BaseProduct> baseProducts){
        int rowIndex= 0;
        for(BaseProduct baseProduct : baseProducts){
            menuTable.getModel().setValueAt(baseProduct.getItemID(), rowIndex, 0);
            menuTable.getModel().setValueAt(baseProduct.getTitle(), rowIndex, 1);
            menuTable.getModel().setValueAt(baseProduct.getCalories(), rowIndex, 3);
            menuTable.getModel().setValueAt(baseProduct.getRating(), rowIndex, 2);
            menuTable.getModel().setValueAt(baseProduct.getProteins(), rowIndex, 4);
            menuTable.getModel().setValueAt(baseProduct.getFats(), rowIndex, 5);
            menuTable.getModel().setValueAt(baseProduct.getSodium(), rowIndex, 6);
            menuTable.getModel().setValueAt(baseProduct.getPrice(), rowIndex, 7);
            rowIndex++;
        }

    }
    

}
