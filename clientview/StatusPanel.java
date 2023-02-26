package clientview;




import model.SubmittedOrder;
import sellersview.ExtendedTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StatusPanel extends JPanel {
    ClientTableRemote clientTableRemote;
    public JTable jRemote;
    public ExtendedTable extendedTable;
    JTable jExtendedTable;
    public List<SubmittedOrder> submittedOrders;
    public StatusPanel( List<SubmittedOrder> submittedOrders){
        if(submittedOrders.size()>0) {
            this.submittedOrders=submittedOrders;
            clientTableRemote = new ClientTableRemote(submittedOrders);
            jRemote = new JTable(clientTableRemote);

            extendedTable=new ExtendedTable(submittedOrders.get(0).getOrder().getOll());
            jExtendedTable = new JTable(extendedTable);
            this.add(new JScrollPane(jRemote), BorderLayout.WEST);
            this.add(new JScrollPane(jExtendedTable), BorderLayout.EAST);
            controlTable();
        }else{
            JLabel label=new JLabel("NOTHING TO SHOW");
            label.setFont(new Font("x", 1, 40));
            this.add(label, BorderLayout.CENTER);
        }
    }

    public void controlTable(){
        jRemote.getSelectionModel().addListSelectionListener(e -> {
            int selected = jRemote.getSelectedRow();
            if(selected>=0) {
                ExtendedTable extendedTable1 = new ExtendedTable(submittedOrders.get(selected).getOrder().getOll());
                jExtendedTable.setModel(extendedTable1);
                jExtendedTable.repaint();
            }
        });
    }
}