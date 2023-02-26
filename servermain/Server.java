package servermain;


import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Policy;
import java.util.List;
//import java.security.Policy;
import interfaces.*;
import servercontroller.ServerController;
import servermodel.AccountMenager;
import servermodel.MyPolicy;
import servermodel.ProductMenager;
import model.*;
import servermodel.SubmittedOrderMenager;


public class Server implements IShop {

    static ProductMenager productMenager=new ProductMenager();
    static SubmittedOrderMenager submittedOrderMenager = new SubmittedOrderMenager();
    static AccountMenager accountMenager = new AccountMenager();
    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", "192.168.0.17");//!!! ip komputera gdzie jest serwer
        if(args.length>0)System.out.println(args[0]);
        else System.out.println("no args");
       Policy.setPolicy(new MyPolicy());

      if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.out.println("Secured");
        }
        accountMenager.loadClients();
        productMenager.loadProducts();
        submittedOrderMenager.loadfromfile();
        ServerController serverController = new ServerController(submittedOrderMenager.submittedOrders, accountMenager, productMenager);

        try {
            Registry reg = LocateRegistry.createRegistry(3000);
            reg.bind("Server", UnicastRemoteObject.exportObject(new Server(), 0));
            System.out.println("Server is ready");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public List<ItemType> getItemList() throws RemoteException {
        return productMenager.getOfferedItems();
    }

    @Override
    public List<SubmittedOrder> getSubmittedOrders() throws RemoteException {return submittedOrderMenager.getList();}

    @Override
    public Status getStatus(int id) throws RemoteException {
        for(SubmittedOrder so: submittedOrderMenager.getList()){
            if(so.getId()==id) return so.getStatus();
        }
        return null;
    }

    @Override
    public boolean setStatus(int id, Status s) throws RemoteException {
        for(SubmittedOrder order: submittedOrderMenager.submittedOrders){
            if(order.getId()==id){
                order.setStatus(s);
                if(accountMenager.isSubscribed.containsKey(order.getOrder().getClientID())){
                    accountMenager.isSubscribed.get(order.getOrder().getClientID()).statusChanged(id, s);

                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int placeOrder(Order o) {
        SubmittedOrder submittedOrder = new SubmittedOrder(o);
        submittedOrderMenager.submittedOrders.add(submittedOrder);
        return submittedOrder.getId();
    }

    @Override
    public int register(Client c) throws IOException {
        int id = accountMenager.addClient(c);
        System.out.println("logged"+" "+c.getName());
        return id;//client id
    }

    @Override
    public boolean subscribe(IStatusListener ic, int clientId) throws RemoteException {
        accountMenager.isSubscribed.put(clientId,ic);
        return false;
    }

    @Override
    public boolean unsubscribe(int clientId) throws RemoteException{
        accountMenager.isSubscribed.remove(clientId);
        return true;
    }

}
