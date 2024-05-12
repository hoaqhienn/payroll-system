package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entities.Account;

public interface AuthDAO extends Remote {
	public Account login (String username, String password) throws RemoteException;
}
