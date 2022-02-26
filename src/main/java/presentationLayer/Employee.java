package presentationLayer;

import businessLayer.DeliveryService;
import businessLayer.MenuItem;
import businessLayer.Observable;
import businessLayer.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

public class Employee  implements Observable, Serializable {
    private JFrame employeeFrame;
    private JTextArea ta = new JTextArea();
    private JButton  back = new JButton("Back");
    private JButton performOrder = new JButton("Perform Order");
    private DeliveryService deliveryService;
    private boolean newOrder = false;
    private static final long serialVersionUID = 1L;

    public Employee(DeliveryService ds){
        employeeFrame = new JFrame();
        employeeFrame.setVisible(true);
        this.deliveryService = ds;
        JScrollPane scrollPane = new JScrollPane(ta);
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(scrollPane);
        JPanel panel1 = new JPanel(new GridLayout(7,1));
        panel1.add(performOrder);
        panel1.add(back);
        panel.add(panel1);
        employeeFrame.add(panel);
        employeeFrame.setTitle("Employee management system");
        employeeFrame.getContentPane().setBackground(new Color(63, 208, 145));
        employeeFrame.setSize(1200, 550);

        if(newOrder == false){
            writeOrdersToTa();

        }
        back.addActionListener(e->{
            LogInGUI logIn = new LogInGUI();
            employeeFrame.setVisible(false);
        });

        performOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deliveryService.processOrder();
                ta.setText(" ");
                writeOrdersToTa();
                deliveryService.saveInf();
            }
        });
    }

    public void writeOrdersToTa(){

        deliveryService.getMapOfOrders().forEach((order,items)->{
            ta.append(order.toString()+"\n");
            ta.append("Products:\n");
            items.iterator().forEachRemaining(a->ta.append(a.toString()));
            ta.append("\n");
        });

    }

    @Override
    public void update(Order order, HashSet<MenuItem> menuItems) {
        ta.append("THERE IS A NEW ORDER TO PROCESS!\n");
        ta.append(order.toString() + "\n" + "Products of the order :" + "\n");
        for(MenuItem mi : menuItems){
            ta.append(mi.toString() );
            ta.append("\n");
        }
        ta.append("\n\n\n");
    }

    public void setFrameState(boolean state){
        this.employeeFrame.setVisible(state);
    }

    public void setNewOrder(boolean newOrder) {
        this.newOrder = newOrder;
    }
}
