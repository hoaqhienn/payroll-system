package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.Department;

public interface DepartmentDAO extends Remote{
	public List<Department> findAll() throws RemoteException; 

	public Department findById(String id) throws RemoteException;
}
