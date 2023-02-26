package sellersview;


import model.OrderLine;


import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtendedTable extends AbstractTableModel {
    private List<OrderLine> orderLineList;

    private final String[] columnNames = new String[] {
            "NAME", "PRICE" , "CATEGORY" , "QUANTITY", "ADVERT"
    };
    private final Class[] columnClass = new Class[] {
            String.class, Float.class, Integer.class, Integer.class, String.class
    };

    public ExtendedTable(List<OrderLine> orderLineList)
    {
        this.orderLineList=orderLineList;
    }

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        if(columnIndex>1) return true;
        else return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }


    @Override
    public int getRowCount()
    {
        return orderLineList.size();
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {

        OrderLine row= orderLineList.get(rowIndex);

        if(0 == columnIndex) {
            return row.getIt().getName();
        }
        else if(1 == columnIndex) {
            return row.getIt().getPrice();
        }
        else if(2 == columnIndex) {
            return row.getIt().getCategory();
        }
        else if(3 == columnIndex) {
            return row.getQuantity();
        }
        else if(4 == columnIndex) {
            return row.getAdvert();
        }

        return null;
    }
}

