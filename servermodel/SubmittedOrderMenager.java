package servermodel;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.*;
import model.Status;
import servermain.ReadSave;
import servermain.Server;

import static servermain.ReadSave.read;


public class SubmittedOrderMenager {
    public List<SubmittedOrder> submittedOrders = new ArrayList<>();
    public List<SubmittedOrder> getList(){return submittedOrders;}
    public Map<Integer, SubmittedOrder> submittedOrderMap = new HashMap<>();
    public void addOrderHead(int id, int clientid, String status){
        Order order = new Order(clientid);
        SubmittedOrder sub = new SubmittedOrder(order);
        sub.setStatus(Status.valueOf(status));
        submittedOrders.add(sub);
        submittedOrderMap.put(id,sub);
    }
    public void loadDetailsfromfile(){
        URL url = Server.class.getResource("orderline"+".csv");
        ArrayList<String> data = read(url);

        for(int d=0; d<=data.size()-1;d++){
            String[] parsed = data.get(d).split(";");
            ItemType it = new ItemType();
            it.setName(parsed[1]);
            it.setPrice(Float.valueOf(parsed[2]));
            it.setCategory(Integer.valueOf(parsed[3]));
            OrderLine ol = new OrderLine(it, Integer.valueOf(parsed[4]), parsed[5]);
            int id = Integer.valueOf(parsed[0]);
            submittedOrderMap.get(id).getOrder().addOrderLine(ol);

        }
    }
    public void loadfromfile(){
        submittedOrders.clear();
        URL url = Server.class.getResource("submittedorder"+".csv");
        ArrayList<String> data = read(url);

        for(int d=0; d<=data.size()-1;d++){
            String[] parsed = data.get(d).split(";");
            addOrderHead(Integer.valueOf(parsed[0]),Integer.valueOf(parsed[1]),parsed[2]);
        }
        loadDetailsfromfile();
    }

    public void saveToFile() throws IOException {
        synchronized (this){
            //URL url = Server.class.getResource(filename);
            ArrayList<String> data = new ArrayList<>();
            for(SubmittedOrder so: submittedOrders){
                String s = so.getId()+";"+so.getOrder().getClientID()+";"+so.getStatus();
                data.add(s);
            }
            //ReadSave.save(data, "C:\\gadgets\\lab07_pop\\src\\servermain\\submittedorder.csv");
        }
    }

    public void addSubmittedOrder(SubmittedOrder so) throws IOException {
        submittedOrders.add(so);
        saveToFile();
    }
}
