package clientview;



import model.ItemType;
import model.OrderLine;
import sellersview.ExtendedTable;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScrollingPanel extends JPanel {
    public JTable table=null;
    public List<ItemType> products = new ArrayList<>();
    public List<OrderLine> orderLineList=new ArrayList<>();
    ExtendedTable extendedTable;
    public JTable jExtendedTable;
    public ScrollingPanel(List<ItemType> offeredItems) {
        super(new GridLayout(1, 0));
        this.products =offeredItems;
        table = new JTable(new Table(products));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(4);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.WEST);

        extendedTable = new ExtendedTable(orderLineList);
        jExtendedTable = new JTable(extendedTable);
        add(new JScrollPane(jExtendedTable),BorderLayout.EAST);
    }
}