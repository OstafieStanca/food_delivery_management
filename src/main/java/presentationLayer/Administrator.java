package presentationLayer;

import businessLayer.BaseProduct;
import businessLayer.CompositeProduct;
import businessLayer.DeliveryService;
import businessLayer.MenuItem;
import dataLayer.Serializator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Administrator implements Serializable {

    private JFrame administratorFrame ;
    private JTable productTable;
    private DeliveryService deliveryService ;
    private JButton back = new JButton("Back");
    private JButton importProducts = new JButton("Import");
    private JButton refresh = new JButton("Refresh");
    private JButton addProduct = new JButton("Add new product");
    private JButton modifyProduct = new JButton("Modify product");
    private JButton deleteProduct = new JButton("Delete product");
    private JButton createReports = new JButton("Create Reports");
    private List<CompositeProduct> compositeProducts = new ArrayList<>();
    private JButton createCompositeProduct = new JButton("Create Composite Product");
    private static final long serialVersionUID = 1L;

    public Administrator(DeliveryService ds){
        administratorFrame = new JFrame();
        this.deliveryService = ds;
        administratorFrame.setVisible(true);
        GridLayout gr = new GridLayout(7, 2);
        gr.setVgap(18);
        gr.setHgap(20);
        JPanel panel1 = new JPanel(gr);
        panel1.add(importProducts);
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        addProduct.setVisible(false);
        panel1.add(addProduct);
        panel1.add(new JLabel(""));
        modifyProduct.setVisible(false);
        panel1.add(modifyProduct);
        panel1.add(new JLabel(""));
        deleteProduct.setVisible(false);
        panel1.add(deleteProduct);
        panel1.add(new JLabel(""));
        createCompositeProduct.setVisible(false);
        panel1.add(createCompositeProduct);
        panel1.add(createReports);
        panel1.add(back);
        panel1.setBackground(new Color(20, 97, 68));

        JPanel panel2 = new JPanel(new GridLayout(1,1));
        panel2.setBackground(new Color(20, 97, 68));
        String[] columnNames = {"Id", "Title", "Rating", "Calories", "Protein", "Fats", "Sodium", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 15000);
        productTable = new JTable(tableModel);
        productTable.setModel(tableModel);
        productTable.getTableHeader().setReorderingAllowed(false);
        productTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(20);
        productTable.getColumnModel().getColumn(4).setPreferredWidth(20);
        productTable.getColumnModel().getColumn(5).setPreferredWidth(20);
        productTable.getColumnModel().getColumn(6).setPreferredWidth(20);
        productTable.getColumnModel().getColumn(7).setPreferredWidth(20);
        productTable.setRowHeight(25);
        productTable.setVisible(false);
        productTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(10, 10, 750, 200);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setAlignmentY(Component.CENTER_ALIGNMENT);
        scrollPane.setViewportView(productTable);
        scrollPane.setVisible(false);
        panel2.add(scrollPane);
        GridLayout gr2 = new GridLayout(2, 1);
        gr2.setVgap(10);
        gr2.setHgap(10);
        JPanel panel3 = new JPanel(gr2);
        panel3.add(panel1);
        panel3.add(panel2);
        administratorFrame.add(panel3);
        administratorFrame.setTitle("Administrator management system");
        administratorFrame.getContentPane().setBackground(new Color(63, 208, 145));
        administratorFrame.setSize(850, 650);

        importProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setVisible(true);
                productTable.setVisible(true);
                addProduct.setVisible(true);
                modifyProduct.setVisible(true);
                deleteProduct.setVisible(true);
                createCompositeProduct.setVisible(true);
                Serializator s = new Serializator();
                try {
                    deliveryService = (DeliveryService) s.deserialization("deliveryService.txt");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                completeTable(deliveryService.getBaseProducts(),compositeProducts );
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                administratorFrame.setVisible(false);
                productTable.removeAll();
                addProduct.setVisible(false);
                modifyProduct.setVisible(false);
                deleteProduct.setVisible(false);
                createCompositeProduct.setVisible(false);
                deliveryService.saveInf();
                LogInGUI logInGUI = new LogInGUI();
            }
        });

        deleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(productTable.getSelectedRow() != -1){
                    int idProduct = Integer.parseInt(productTable.getValueAt(productTable.getSelectedRow(), 0).toString());
                    BaseProduct baseProduct = new BaseProduct(idProduct, "", 0,0,0,0,0,0);
                    deliveryService.deleteProduct(baseProduct);
                    completeTable(deliveryService.getBaseProducts(), compositeProducts);
                    JOptionPane.showMessageDialog(administratorFrame, "The product with id "+ idProduct + " was deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        modifyProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(productTable.getEditingRow() != -1){
                    int row = productTable.getEditingRow();
                    int column = 0;
                    int id = Integer.parseInt(productTable.getValueAt(row, column++).toString());
                    String title = productTable.getValueAt(row, column++).toString();
                    float rating = Float.parseFloat(productTable.getValueAt(row, column++).toString());
                    int calories = Integer.parseInt(productTable.getValueAt(row, column++).toString());
                    int proteins = Integer.parseInt(productTable.getValueAt(row, column++).toString());
                    int fats = Integer.parseInt(productTable.getValueAt(row, column++).toString());
                    int sodium = Integer.parseInt(productTable.getValueAt(row, column++).toString());
                    int price  = Integer.parseInt(productTable.getValueAt(row, column).toString());
                    deliveryService.modifyProduct(title,rating, calories, proteins, fats, sodium, price, id);
                    completeTable(deliveryService.getBaseProducts(), compositeProducts);
                    JOptionPane.showMessageDialog(administratorFrame, "The product with id "+ id + " was modified successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });

        createCompositeProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = "Daily Menu "+deliveryService.getNumberMenu();
                int v = deliveryService.getNumberMenu();
                deliveryService.setNumberMenu(v + 1);
                int idProduct = deliveryService.getIndexOfProductCP();
                List<MenuItem> menuItems = new ArrayList<>();
                int column ;
                for(int row : productTable.getSelectedRows()){
                    column = 0;
                    int id = Integer.parseInt(productTable.getValueAt(row, column++).toString());
                    String title = productTable.getValueAt(row, column++).toString();
                    float rating = Float.parseFloat(productTable.getValueAt(row, column++).toString());
                    int calories = Integer.parseInt(productTable.getValueAt(row, column++).toString());
                    int proteins = Integer.parseInt(productTable.getValueAt(row, column++).toString());
                    int fats = Integer.parseInt(productTable.getValueAt(row, column++).toString());
                    int sodium = Integer.parseInt(productTable.getValueAt(row, column++).toString());
                    int price  = Integer.parseInt(productTable.getValueAt(row, column).toString());
                    BaseProduct baseProduct = new BaseProduct(id, title, rating, calories, proteins, fats, sodium, price);
                    menuItems.add(baseProduct);

                }
                if(menuItems.isEmpty() == false) {
                    CompositeProduct cp = deliveryService.createCompositeProduct(menuItems, itemName, idProduct);
                    compositeProducts.add(cp);
                    productTable.removeAll();
                    completeTable(deliveryService.getBaseProducts(), compositeProducts);
                    deliveryService.saveInf();
                    JOptionPane.showMessageDialog(administratorFrame, "The product with id "+ cp.getItemID() + " was created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(administratorFrame, "Select items from table!!", "Inane Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateNewProduct createNewProduct = new CreateNewProduct(deliveryService);
                administratorFrame.setVisible(false);
            }
        });
        createReports.addActionListener(e1->{
            try {
                deliveryService.reportBasedOnHourInterval(10, 20);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                deliveryService.reportMostOrdersProducts(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                deliveryService.reportClients(2,50);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                deliveryService.reportMostOrderedProductsOnDay(new Date());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void completeTable(HashSet<BaseProduct> baseProducts, List<CompositeProduct> compositeProducts){
        int rowIndex= 0;
        for(BaseProduct baseProduct : baseProducts){
            productTable.getModel().setValueAt(baseProduct.getItemID(), rowIndex, 0);
            productTable.getModel().setValueAt(baseProduct.getTitle(), rowIndex, 1);
            productTable.getModel().setValueAt(baseProduct.getCalories(), rowIndex, 3);
            productTable.getModel().setValueAt(baseProduct.getRating(), rowIndex, 2);
            productTable.getModel().setValueAt(baseProduct.getProteins(), rowIndex, 4);
            productTable.getModel().setValueAt(baseProduct.getFats(), rowIndex, 5);
            productTable.getModel().setValueAt(baseProduct.getSodium(), rowIndex, 6);
            productTable.getModel().setValueAt(baseProduct.getPrice(), rowIndex, 7);
            rowIndex++;
        }

        for(CompositeProduct compositeProduct : compositeProducts){
            productTable.getModel().setValueAt(compositeProduct.getItemID(), rowIndex, 0);
            productTable.getModel().setValueAt(compositeProduct.getItemName(), rowIndex, 1);
            productTable.getModel().setValueAt(0, rowIndex, 3);
            productTable.getModel().setValueAt(0, rowIndex, 2);
            productTable.getModel().setValueAt(0, rowIndex, 4);
            productTable.getModel().setValueAt(0, rowIndex, 5);
            productTable.getModel().setValueAt(0, rowIndex, 6);
            productTable.getModel().setValueAt(compositeProduct.getItemPrice(), rowIndex, 7);
            rowIndex++;
        }
    }

}
