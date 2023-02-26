package sellersview;


import interfaces.IShop;
import model.SubmittedOrder;
import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

public class SellerFrame extends JFrame {
    IShop iShop;
    public List<SubmittedOrder> submittedOrders;
    public ExtendedTable extendedTable;
    public JTable table;
    JButton button;
    public JTable JextendedTable=null;

    public SellerFrame(IShop iShop) throws RemoteException {
        this.iShop = iShop;
        this.setTitle("SELLER APPLICATION");
        submittedOrders= iShop.getSubmittedOrders();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        extendedTable = new ExtendedTable(submittedOrders.get(0).getOrder().getOll());
        JextendedTable = new JTable(extendedTable);

        table = new JTable(new SellerTableRemote(submittedOrders, iShop));
        TableColumn testColumn = table.getColumnModel().getColumn(3);
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("PROCESSING");
        comboBox.addItem("DELIVERED");
        comboBox.addItem("READY");
        testColumn.setCellEditor(new DefaultCellEditor(comboBox));
        button=new JButton("REFRESH");

        this.add(button, BorderLayout.SOUTH);
        this.add(new JScrollPane(table), BorderLayout.WEST);
        this.add(new JScrollPane(JextendedTable),BorderLayout.EAST);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
    public void listenforrefrehs(ActionListener listener){button.addActionListener(listener);}
}
