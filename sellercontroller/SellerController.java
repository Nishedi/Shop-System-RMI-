package sellercontroller;



import interfaces.IShop;
import sellersview.ExtendedTable;
import sellersview.SellerFrame;
import sellersview.SellerTableRemote;
import servermodel.MyPolicy;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Policy;

public class SellerController {
    SellerFrame sellerFrame;
    IShop iShop;
    Registry reg;

    public SellerController(String host, String port){
        Policy.setPolicy(new MyPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("Security");
        }
        try {
            reg = LocateRegistry.getRegistry(host, Integer.valueOf(port));
            iShop = (IShop) reg.lookup("Server");
            System.out.println("Server found");
            sellerFrame = new SellerFrame(iShop);
            sellerFrame.listenforrefrehs(new ButtonListener());

        } catch (NotBoundException | RemoteException e) {
            throw new RuntimeException(e);
        }

        sellerFrame.table.getSelectionModel().addListSelectionListener(event -> {
            if(sellerFrame.table.getSelectedRow()>=0) {
                System.out.println(sellerFrame.table.getSelectedRow());
                ExtendedTable extendedTable = new ExtendedTable(sellerFrame.submittedOrders.get(sellerFrame.table.getSelectedRow()).getOrder().getOll());
                sellerFrame.JextendedTable.setModel(extendedTable);
                sellerFrame.repaint();
                sellerFrame.JextendedTable.repaint();
            }
        });
    }
    class ButtonListener implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                sellerFrame.submittedOrders=iShop.getSubmittedOrders();
                sellerFrame.table.setModel(new SellerTableRemote(iShop.getSubmittedOrders(), iShop));
                TableColumn testColumn = sellerFrame.table.getColumnModel().getColumn(3);
                JComboBox<String> comboBox = new JComboBox<>();
                comboBox.addItem("PROCESSING");
                comboBox.addItem("DELIVERED");
                comboBox.addItem("READY");
                testColumn.setCellEditor(new DefaultCellEditor(comboBox));
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
