package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dao.Hello;
import jakarta.persistence.EntityManager;

public class HelloService extends UnicastRemoteObject implements Hello {
    private static final long serialVersionUID = 1L;
    public HelloService(EntityManager entityManager) throws RemoteException {
        super();
    }

    public String sayHello() throws RemoteException {
        return "Hello from server!";
    }
}
