package clientmain;

import clientcontroller.ClientController;
import interfaces.IStatusListener;
import model.Status;
import model.SubmittedOrder;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IStatusListenerConnection extends UnicastRemoteObject implements IStatusListener {
    ClientController client;
    public IStatusListenerConnection(ClientController client) throws RemoteException {
        this.client=client;
    }
    @Override
    public void statusChanged(int id, Status status) throws RemoteException {
        for(SubmittedOrder so: client.clientFrame.submittedOrderList){
            if(so.getId()==id){
                so.setStatus(status);
            }
        }
    }
}
