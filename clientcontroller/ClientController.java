package clientcontroller;


import clientmain.IStatusListenerConnection;
import clientview.ClientFrame;
import clientview.ClientTableRemote;
import clientview.ScrollingPanel;
import clientview.StatusPanel;
import interfaces.IShop;
import interfaces.IStatusListener;
import model.*;


import sellersview.ExtendedTable;
import servermodel.MyPolicy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    IStatusListener iStatusListener = new IStatusListenerConnection(this);
    int myID;
    Timer timer;
    public ClientFrame clientFrame;
    IShop iShop;
    Registry reg;
    Client client;
    JTextField quantity;
    JTextField advert;

    public ClientController(String host, String port) throws NotBoundException, RemoteException {
        clientFrame = new ClientFrame();
        timer = new Timer(1000, new TimerListener());
        clientFrame.login(new LoginListener());
        clientFrame.myorders(new MyOrdersListener());
        clientFrame.browser(new BrowserListener());
        clientFrame.unsubsribe(new Unsubscribe());
        clientFrame.eg(new EgListener());
        clientFrame.addtocartListener(new AddtocartListener());
        clientFrame.removefromcartListener(new RemoveFromcartListener());
        clientFrame.submit(new SubmitCart());
        clientFrame.subscribe(new SubscribeListener());
        clientFrame.setRefresh(new RefreshListener());
        Policy.setPolicy(new MyPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("Security");
        }

        reg = LocateRegistry.getRegistry(host,Integer.valueOf(port));
        iShop = (IShop) reg.lookup("Server");

        timer.start();
    }
    class TimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            clientFrame.repaint();
        }
    }
    class EgListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame egF=new JFrame();
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("clientside\\view\\3.jpg"));
            } catch (IOException ex) {
                System.out.println("Kotka nie ma bo śpi :(");
            }
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setSize(new Dimension(500,500));
            egF.setSize(new Dimension(500,500));
            egF.add(picLabel);
            egF.pack();
            egF.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            egF.setVisible(true);
        }
    }
    class BrowserListener implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(clientFrame.scrollingPanel==null){
                try {
                    clientFrame.scrollingPanel=new ScrollingPanel(iShop.getItemList());
                    clientFrame.add(clientFrame.scrollingPanel);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(clientFrame.statusPanel!=null) clientFrame.statusPanel.setVisible(false);
            clientFrame.scrollingPanel.setVisible(true);
            if(clientFrame.subscribe !=null)clientFrame.subscribe.setVisible(false);
            clientFrame.setButtonsVisibility(false,false,true,true,true, false);
        }
    }
    class Unsubscribe implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                iShop.unsubscribe(myID);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class SubscribeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                iShop.subscribe(iStatusListener, myID);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class MyOrdersListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(clientFrame.scrollingPanel!=null) clientFrame.scrollingPanel.setVisible(false);
            clientFrame.setButtonsVisibility(true, true, false, false, false,true);
            clientFrame.statusPanel.setVisible(true);
            clientFrame.statusPanel.submittedOrders= clientFrame.submittedOrderList;
            if(clientFrame.statusPanel.jRemote==null) {
                clientFrame.remove(clientFrame.statusPanel);
                clientFrame.statusPanel=new StatusPanel(clientFrame.submittedOrderList);
                clientFrame.add(clientFrame.statusPanel);
            }else {
                clientFrame.statusPanel.jRemote.setModel(new ClientTableRemote(clientFrame.statusPanel.submittedOrders));
            }

        }
    }
    class LoginListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            client = new Client();
            client.setName(clientFrame.loginPanel.name.getText());
            try {
                myID= iShop.register(client);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            clientFrame.submittedOrderList=getMyOrders();
            clientFrame.remove(clientFrame.loginPanel);
            clientFrame.statusPanel = new StatusPanel(clientFrame.submittedOrderList);
            clientFrame.add(clientFrame.statusPanel);
            clientFrame.setButtons();
            clientFrame.setVisible(true);
        }
    }
    class AddtocartListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> str = showDialog();
            Integer quatity = Integer.valueOf(str.get(0));
            String advert = str.get(1);
            int num = clientFrame.scrollingPanel.table.getSelectedRow();
            if(num<0) return;
            ItemType it = clientFrame.scrollingPanel.products.get(num);
            ItemType itemtosend = new ItemType();
            itemtosend.setPrice(it.getPrice());
            itemtosend.setCategory(it.getCategory());
            itemtosend.setName(it.getName());

            OrderLine orderLine = new OrderLine(itemtosend, quatity, advert);
            orderLine.getIt().setPrice(orderLine.getCost());
            clientFrame.scrollingPanel.orderLineList.add(orderLine);
            clientFrame.scrollingPanel.jExtendedTable.setModel(new ExtendedTable(clientFrame.scrollingPanel.orderLineList));
        }
    }
    public ArrayList<String> showDialog() {
        ArrayList<String> str=new ArrayList<>();
        JDialog dialog = new JDialog(clientFrame, Dialog.ModalityType.APPLICATION_MODAL);
        JButton accept = new JButton("ACCEPT");



        JButton close=new JButton("CANCEL");
        quantity = new JTextField("quantity");
        advert = new JTextField("advert");
        close.setBounds(100,125,100,25);
        accept.setBounds(200,125,100,25);
        advert.setBounds(200,100,100,25);
        quantity.setBounds(100,100,100,25);
        dialog.setLayout(null);
        dialog.add(quantity);
        dialog.add(close);
        dialog.add(accept);
        dialog.add(advert);
        accept.addActionListener(e -> {
            str.add(quantity.getText());
            str.add(advert.getText());
            dialog.dispose();
        });
        close.addActionListener(e -> dialog.dispose());
        dialog.setPreferredSize(new Dimension(400,200));
        dialog.setBounds(350, 350, 400, 200);
        dialog.setVisible(true);
        return str;
    }
    class RemoveFromcartListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int num=clientFrame.scrollingPanel.jExtendedTable.getSelectedRow();
            if(num<0) return ;
            clientFrame.scrollingPanel.orderLineList.remove(num);
            clientFrame.scrollingPanel.jExtendedTable.setModel(new ExtendedTable(clientFrame.scrollingPanel.orderLineList));
        }
    }
    class SubmitCart implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Order order = new Order(myID);
            for(OrderLine ol: clientFrame.scrollingPanel.orderLineList) {
                order.addOrderLine(ol);
            }
            try {
                clientFrame.submittedOrderList.add(new SubmittedOrder(order,iShop.placeOrder(order)));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class RefreshListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedrow = clientFrame.statusPanel.jRemote.getSelectedRow();
            int idtorefresh = clientFrame.submittedOrderList.get(selectedrow).getId();
            try {
                clientFrame.submittedOrderList.get(selectedrow).setStatus(iShop.getStatus(idtorefresh));
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private List<SubmittedOrder> getMyOrders(){
        List<SubmittedOrder> submittedOrderforclient=new ArrayList<>();
        try {
            for (SubmittedOrder so : iShop.getSubmittedOrders()) {
                int currentID = so.getOrder().getClientID();
                if (currentID == myID) submittedOrderforclient.add(so);
            }
        }catch (RemoteException r){
            System.out.println("Trouble with connection and getting submittedorders");
        }
        return submittedOrderforclient;
    }
}
