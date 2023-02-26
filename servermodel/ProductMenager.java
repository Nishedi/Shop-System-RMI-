package servermodel;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import model.*;
import servermain.ReadSave;
import servermain.Server;

import static servermain.ReadSave.read;


public class ProductMenager {
    protected List<ItemType> offeredItems = new ArrayList<>();
    public void loadProducts(){
        offeredItems.clear();
        URL url = Server.class.getResource("products.csv");
        ArrayList<String> data = read(url);
        for(int d=0; d<=data.size()-1;d++){
            String[] parsed = data.get(d).split(";");
            offeredItems.add(new ItemType());
            offeredItems.get(d).setName(parsed[0]);
            offeredItems.get(d).setCategory(Integer.valueOf(parsed[1]));
            offeredItems.get(d).setPrice(Float.valueOf(parsed[2]));
        }
    }
    public List<ItemType> getOfferedItems(){
        return offeredItems;
    }
    public void saveToFile() throws IOException {
        synchronized (this){
            //URL url = Server.class.getResource(filename);
            ArrayList<String> data = new ArrayList<>();
            for(ItemType it: offeredItems){
                String s = it.getName()+";"+it.getCategory()+";"+it.getPrice();
                data.add(s);

            }
           // ReadSave.save(data, "C:\\gadgets\\lab07_pop\\src\\servermain\\products.csv");
        }
    }
    public void addProduct(ItemType itemType) throws IOException {
        offeredItems.add(itemType);
        saveToFile();
    }

}
