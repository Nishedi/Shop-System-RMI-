package serverview;


import model.*;

import javax.crypto.Cipher;
import javax.swing.table.AbstractTableModel;
import java.rmi.RemoteException;
import java.util.List;

public class ClientTable extends AbstractTableModel {
    List<Client> itemTypeList;
    private String[] columnNames = {"ID",
            "NAME"
    };
    private final Class[] columnClass = new Class[] {
            Integer.class, String.class
    };


    public ClientTable(List<Client> itemTypeList){
        this.itemTypeList=itemTypeList;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return itemTypeList.size();
    }

    @Override
    public String getColumnName(int column) {return columnNames[column];}

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {

        Client c = itemTypeList.get(rowIndex);

        if(1 == columnIndex) {
            c.setName((String) aValue);
        }

    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Client client = itemTypeList.get(rowIndex);
        if(0 == columnIndex) {
            return client.getId();
        }
        else if(1 == columnIndex) {
            return client.getName();
        }

        return null;
    }
}
