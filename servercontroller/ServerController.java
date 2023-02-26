package servercontroller;


import model.*;
import sellersview.SellerTable;
import servermodel.AccountMenager;
import servermodel.ProductMenager;
import serverview.ClientTable;
import serverview.ProductTable;
import serverview.ServerFrame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;


public class ServerController {
    ServerFrame serverFrame;
    List<SubmittedOrder> submittedOrderList;
    List<Client> clients;
    AccountMenager accountMenager;
    JButton saveChanges = new JButton("SAVE CHANGES");
    JButton removeClient = new JButton("REMOVE CLIENT");
    JButton addClient =new JButton("ADD CLIENT");
    JButton addProduct = new JButton("ADD PRODUCT");
    JButton removeProduct = new JButton("REMOVE PRODUCT");
    int savemode = 0;
    JTable table;
    ProductMenager productMenager;

    public ServerController( List<SubmittedOrder> submittedOrderList, AccountMenager accountMenager, ProductMenager productMenager){
        this.productMenager=productMenager;
        this.accountMenager=accountMenager;
        this.clients = accountMenager.clients;
        this.submittedOrderList=submittedOrderList;
        serverFrame = new ServerFrame(submittedOrderList);
        serverFrame.closeServer(new closeSever());
        serverFrame.addProduct(new addProduct());
        savetofileListener(new SaveToFile());
        removeClientListenet(new RemoveClientListener());
        addClientListener(new addClientListener2());
        addProductListener(new addProduct2());
        removeProductListener(new removeProduct());

        serverFrame.addClient(new addClient());
        Timer timer = new Timer(1000, new TimerListener());
        timer.start();
    }
    private void productsETC(){
        JFrame frameofProducts = new JFrame();
        frameofProducts.setResizable(false);
        JPanel buttonPanel = new JPanel();
        table = new JTable(new ProductTable(productMenager.getOfferedItems()));
        JScrollPane jScrollPane = new JScrollPane(table);
        frameofProducts.add(jScrollPane, BorderLayout.EAST);
        buttonPanel.add(addProduct);
        buttonPanel.add(removeProduct);

        buttonPanel.add(saveChanges);
        int i =0;
        addProduct.setBounds(20,130*i+20,130,50);i++;
        removeProduct.setBounds(20,130*i+20,130,50);i++;
        saveChanges.setBounds(20,130*i+20,130,50);
        buttonPanel.setPreferredSize(new Dimension(170,100));
        buttonPanel.setLayout(null);
        frameofProducts.add(buttonPanel, BorderLayout.WEST);
        frameofProducts.pack();
        frameofProducts.setVisible(true);
        frameofProducts.setLocationRelativeTo(null);
    }
    private void clientsETC(){
        JFrame jFrame = new JFrame();
        jFrame.setResizable(false);
        JPanel buttonPanel = new JPanel();
        table = new JTable(new ClientTable(clients));
        JScrollPane jScrollPane = new JScrollPane(table);
        jFrame.add(jScrollPane, BorderLayout.EAST);

        buttonPanel.add(addClient);
        int i = 0;
        addClient.setBounds(20,130*i+20,130,50);i++;
        buttonPanel.add(removeClient);
        removeClient.setBounds(20,130*i+20,130,50);i++;
        buttonPanel.add(saveChanges);
        saveChanges.setBounds(20,130*i+20,130,50);

        buttonPanel.setPreferredSize(new Dimension(170,100));
        buttonPanel.setLayout(null);
        jFrame.add(buttonPanel, BorderLayout.WEST);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);

    }
    class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            serverFrame.table.setModel(new SellerTable(submittedOrderList));
        }
    }
    class addClient implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            clientsETC();
        }
    }
    public void savetofileListener(ActionListener listenforsave){saveChanges.addActionListener(listenforsave);}
    public void removeClientListenet(ActionListener listenforremoveclient){removeClient.addActionListener(listenforremoveclient);}
    public void addClientListener(ActionListener listenforaddClient){addClient.addActionListener(listenforaddClient);}
    public void addProductListener(ActionListener listenforaddproduct){addProduct.addActionListener(listenforaddproduct);}
    public void removeProductListener(ActionListener listenforremoveproduct){removeProduct.addActionListener(listenforremoveproduct);}
    class addClientListener2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Client c = new Client();
            c.setName(" ");
            try {
                accountMenager.addClient(c);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            table.setModel(new ClientTable(accountMenager.clients));
            table.repaint();
        }
    }
    class RemoveClientListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int num = table.getSelectedRow();
            if(num!=-1){
                int tempid=clients.get(num).getId();
                System.out.println(num+" "+tempid);
                synchronized (this) {
                    Client clienttoremove=null;
                    for (Client c : clients) {
                        if (c.getId() == tempid) clienttoremove=c;
                    }
                    if(clienttoremove!=null)
                        clients.remove(clienttoremove);
                    table.repaint();
                    try {
                        accountMenager.saveToFile();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        }
    }
    class addProduct2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ItemType it = new ItemType();
            it.setCategory(0);
            it.setName(" ");
            it.setPrice(0);
            try {
                productMenager.addProduct(it);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            table.setModel(new ProductTable(productMenager.getOfferedItems()));
            table.repaint();
        }
    }
    class removeProduct implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int num = table.getSelectedRow();
            productMenager.getOfferedItems().remove(num);
            table.repaint();
            try {
                productMenager.saveToFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class SaveToFile implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(savemode==0)
                    accountMenager.saveToFile();
                if(savemode==1)
                    productMenager.saveToFile();
            } catch (IOException ex) {
                System.out.println("Brak pliku");
            }
        }
    }
    class addProduct implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            savemode=1;
            productsETC();
        }
    }
    class closeSever implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {System.exit(0);}
    }
}
