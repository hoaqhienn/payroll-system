package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.DepartmentDAO;
import entities.Department;
import jakarta.persistence.EntityManager;

public class DepartmentService extends UnicastRemoteObject implements DepartmentDAO{
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public DepartmentService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	public Department findById(String id) throws RemoteException {
		return entityManager.find(Department.class, id);
	}

	public List<Department> findAll() throws RemoteException {
		return entityManager.createNamedQuery("Department.findAll", Department.class).getResultList();
	}
	
	
}
