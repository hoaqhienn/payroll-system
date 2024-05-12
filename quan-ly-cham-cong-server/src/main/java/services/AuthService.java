package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dao.AuthDAO;
import entities.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class AuthService extends UnicastRemoteObject implements AuthDAO {

	private static final long serialVersionUID = 1L;
	private EntityManager e;

	public AuthService(EntityManager e) throws RemoteException {
		this.e = e;
	}

    public Account login(String accountId, String password) {
        TypedQuery<Account> query = e.createQuery(
                "SELECT a FROM Account a WHERE a.id = :id AND a.password = :password", Account.class);
        query.setParameter("id", accountId);
        query.setParameter("password", password);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
