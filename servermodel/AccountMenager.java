package servermodel;


import interfaces.IStatusListener;

import model.Client;
import servermain.ReadSave;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AccountMenager{
    public ArrayList<Client> clients = new ArrayList<>();
    public Map<Integer, IStatusListener> isSubscribed = new HashMap<>();

    public void loadClients(){
        clients.clear();
        URL url = AccountMenager.class.getResource("clients"+".csv");
        ArrayList<String> data = ReadSave.read(url);
        for(String s:data){
            String[] parsed = s.split(";");
            Client client = new Client();
            client.setId(Integer.valueOf(parsed[0]));
            client.setName(parsed[1]);
            clients.add(client);
        }
    }

    public void saveToFile(/*String filename*/) throws IOException {
        synchronized (this){
            // URL url = Server.class.getResource(filename);
            ArrayList<String> data = new ArrayList<>();
            for(Client c: clients){
                String s = c.getId()+";"+c.getName();
                data.add(s);
            }
            //ReadSave.save(data, "C:\\gadgets\\lab07_pop\\src\\servermodel\\clients.csv");
        }
    }

    public int addClient(Client c) throws IOException {
        for (Client c2 : clients) {
            if (c2.getName().compareTo(c.getName()) == 0) return c2.getId();
        }
        synchronized (this) {
            int maxid = 1;
            for (Client c2 : clients) {
                if (c2.getId() > maxid) {
                    maxid = c2.getId();
                }
            }
            c.setId(maxid+1);
            clients.add(c);
            saveToFile(/*"clients" + ".csv"*/);
            return maxid;
        }
    }
}
