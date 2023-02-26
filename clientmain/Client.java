package clientmain;

import clientcontroller.ClientController;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws NotBoundException, RemoteException {
        if(args.length>0) {
            ClientController clientController = new ClientController(args[0], args[1]);
        }else{
            ClientController clientController = new ClientController("localhost", "3000");
        }
    }

}
