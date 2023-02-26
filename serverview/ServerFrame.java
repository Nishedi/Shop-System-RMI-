package serverview;



import model.SubmittedOrder;
import sellersview.SellerTable;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ServerFrame extends JFrame {
    List<SubmittedOrder> submittedOrders;
    public SellerTable sellerTable;
    public JTable table;
    public JButton addClient;
    public JButton addProduct;
    public JButton closeServer;
    public ServerFrame(List<SubmittedOrder> submittedOrders){
        this.submittedOrders=submittedOrders;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sellerTable = new SellerTable(submittedOrders);
        table = new JTable(sellerTable);
        addClient = new JButton("CLIENTS");
        addProduct = new JButton("PRODUCTS");
        closeServer = new JButton("CLOSE \nSERVER");

        this.add(new JScrollPane(table), BorderLayout.WEST);
        this.pack();
        this.setLocationRelativeTo(null);
        addButtons();
        this.setVisible(true);
        System.out.println(this.getWidth());
    }
    public void addButtons(){
        JPanel buttons = new JPanel();
        buttons.add(addClient);
        buttons.add(addProduct);
        buttons.add(closeServer);
        addClient.setBounds(22,25,110,50);
        addProduct.setBounds(154,25,110,50);
        closeServer.setBounds(286,25,140,50);
        buttons.setLayout(null);
        buttons.setPreferredSize(new Dimension(200,100));
        this.add(buttons, BorderLayout.SOUTH);
        buttons.setVisible(true);}
    public void addClient(ActionListener listenforaddClient){addClient.addActionListener(listenforaddClient);}
    public void addProduct(ActionListener listenforaddProduct){addProduct.addActionListener(listenforaddProduct);}
    public void closeServer(ActionListener listenforclose){closeServer.addActionListener(listenforclose);}

}
