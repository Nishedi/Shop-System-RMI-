package sellersview;


import model.*;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.List;

public class SellerTable extends AbstractTableModel {
    private final List<SubmittedOrder> submittedOrderList;

    private final String[] columnNames = new String[] {
            "ID", "ClientID","PRICE", "STATUS"
    };
    private final Class[] columnClass = new Class[] {
            Integer.class, Integer.class, String.class, Status.class
    };

    public SellerTable(List<SubmittedOrder> submittedOrderList)
    {
        this.submittedOrderList = submittedOrderList;
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
        return submittedOrderList.size();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        SubmittedOrder row = submittedOrderList.get(rowIndex);

        if(2 == columnIndex) {
            row.setStatus(Status.valueOf((String) aValue));
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        SubmittedOrder row = submittedOrderList.get(rowIndex);
        if(0 == columnIndex) {
            return row.getId();
        }
        else if(1 == columnIndex) {
            return row.getOrder().getClientID();
        }
        else if(2==columnIndex){
           // DecimalFormat df = new DecimalFormat("#.##");
            float f = row.getOrder().getCost();
            return String.valueOf(f);
        }
        else if(3 == columnIndex) {
            return row.getStatus();
        }
        return null;
    }
}