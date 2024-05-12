package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.AccountDAO;
import entities.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class AccountService extends UnicastRemoteObject implements AccountDAO {

	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public AccountService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	public List<Account> findAll() throws RemoteException {
		return entityManager.createNamedQuery("Account.findAll", Account.class).getResultList();
	}

	public Account findById(String id) throws RemoteException {
	    TypedQuery<Account> query = entityManager.createNamedQuery("Account.findById", Account.class);
	    query.setParameter("id", id);
	    return query.getSingleResult();
	}

	public Boolean changePassword(Account a) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(a);
			trans.commit();
			return true;
		} catch (Exception ex) {
			if (trans.isActive()) {
				trans.rollback();
			}
			ex.printStackTrace();
		}
		return false;
	}
}
