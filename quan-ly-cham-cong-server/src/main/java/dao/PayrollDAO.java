package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Month;
import java.time.Year;
import java.util.List;

import entities.Payroll;

public interface PayrollDAO extends Remote {
	public int countPayroll() throws RemoteException;

	public List<Payroll> findAll() throws RemoteException;

	public List<Payroll> findByEmployee(String employeeId) throws RemoteException;

	public List<Payroll> findByDate(Month month, Year year) throws RemoteException;

	public Payroll findByIdAndDate(String employeeId, Month month, Year year) throws RemoteException;

	public boolean createPayroll(Month month, Year year) throws RemoteException;

	public boolean updatePayroll(Payroll payroll) throws RemoteException;

	public boolean deletePayrollByMonthAndYear(Month month, Year year) throws RemoteException;

	public boolean deletePayrollByEmployee(String employeeId) throws RemoteException;

}
