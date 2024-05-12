package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.EmployeeDAO;
import entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EmployeeService extends UnicastRemoteObject implements EmployeeDAO {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public EmployeeService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	public List<Employee> findAll() throws RemoteException {
		return entityManager.createNamedQuery("Employee.findAll", Employee.class).getResultList();
	}

	public Employee findById(String id) throws RemoteException {
		return entityManager.find(Employee.class, id);
	}

	public boolean createEmployee(Employee e) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(e);
			e.createAccount();
			entityManager.persist(e.getAccount());

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
	
	public boolean createAdmin(Employee e) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(e);
			e.createAdminAccount();
			entityManager.persist(e.getAccount());

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

	public boolean updateEmployee(Employee e) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(e);
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

	public boolean deleteEmployee(String id) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			Employee e = entityManager.find(Employee.class, id);
			entityManager.remove(e);
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

	public List<Employee> findByDepartment(String departmentId) throws RemoteException {
		return entityManager
				.createQuery("SELECT e FROM Employee e WHERE e.department.id = :departmentId", Employee.class)
				.setParameter("departmentId", departmentId).getResultList();
	}

	public int countEmployees() throws RemoteException {
		return entityManager.createQuery("SELECT COUNT(e) FROM Employee e", Long.class).getSingleResult().intValue();
	}

	@Override
	public boolean deleteAllEmployees() throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			List<Employee> employees = findAll();
			for (Employee employee : employees) {
				entityManager.remove(employee);
			}
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
