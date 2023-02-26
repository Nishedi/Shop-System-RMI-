package serverview;

import clientview.Table;
import model.ItemType;
import java.util.List;

public class ProductTable extends Table {
    List<ItemType> itemTypeList;
    public ProductTable(List<ItemType> itemTypeList) {
        super(itemTypeList);
        this.itemTypeList=itemTypeList;
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        ItemType it = itemTypeList.get(rowIndex);
        if(0 == columnIndex) {
            it.setName((String) aValue);
        }
        if(1==columnIndex){
            it.setCategory((Integer) aValue);
        }
    }
}
