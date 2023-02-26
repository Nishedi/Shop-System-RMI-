package clientview;


import model.Status;
import model.SubmittedOrder;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.List;

public class ClientTableRemote extends AbstractTableModel {
    private  List<SubmittedOrder> submittedOrderList;
    private final String[] columnNames = new String[] {
            "ID", "YOUR ID", "PRICE","STATUS"
    };
    private final Class[] columnClass = new Class[] {
            Integer.class,Integer.class, String.class, Status.class
    };

    public ClientTableRemote(List<SubmittedOrder> submittedOrderList){
        this.submittedOrderList = submittedOrderList;
    }

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
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
            DecimalFormat df = new DecimalFormat("#.##");
            float f = row.getOrder().getCost();
            return df.format(f);
        }
        else if(3 == columnIndex) {
            return row.getStatus();
        }
        return null;
    }

}
