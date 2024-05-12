package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.Employee;

public interface EmployeeDAO extends Remote{
	public int countEmployees() throws RemoteException;
	
	public List<Employee> findAll() throws RemoteException; 
	public Employee findById(String id) throws RemoteException;
    public List<Employee> findByDepartment(String departmentId) throws RemoteException;

	public boolean createEmployee(Employee e) throws RemoteException;
	public boolean createAdmin(Employee e) throws RemoteException;
	
	public boolean updateEmployee(Employee e) throws RemoteException;
	public boolean deleteEmployee(String id) throws RemoteException;
	public boolean deleteAllEmployees() throws RemoteException;
}
