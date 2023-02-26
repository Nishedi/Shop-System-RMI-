package clientview;


import model.ItemType;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class Table extends AbstractTableModel {
    List<ItemType> itemTypeList;
    private String[] columnNames = {"Product","Price", "Type"};

    private final Class[] columnClass = new Class[] {
            String.class, Float.class, Integer.class
    };

    public Table(List<ItemType> itemTypeList){
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
        if(c!=2) {
            return getValueAt(0, c).getClass();
        }
        return String.class;
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ItemType row = itemTypeList.get(rowIndex);
        if(0 == columnIndex) {
            if(row.getName().compareTo(" ")!=0)
                return row.getName();
        }
        if(1 == columnIndex) {
            return row.getPrice();
        }
        else if(2 == columnIndex) {
            if(row.getCategory()!=0)
                return row.getCategory();
        }

        return null;
    }
}