package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.Account;

public interface AccountDAO extends Remote {
	public List<Account> findAll() throws RemoteException; 
	public Account findById(String id) throws RemoteException;
    public Boolean changePassword(Account a) throws RemoteException;
}
