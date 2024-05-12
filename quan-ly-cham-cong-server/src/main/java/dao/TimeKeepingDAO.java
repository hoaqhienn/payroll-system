package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;

import entities.Timekeeping;

public interface TimeKeepingDAO extends Remote {
	public int countTimeKeepingById() throws RemoteException;

	public List<Timekeeping> findById(String employeeId) throws RemoteException;

	public Boolean createTimekeeping(Timekeeping timekeeping) throws RemoteException;

	public Boolean deleteTimekeeping(String employeeId, Date date) throws RemoteException;

	public Boolean updateTimekeeping(Timekeeping timekeeping) throws RemoteException;

	public List<Timekeeping> findTimekeepingDate(Date date) throws RemoteException;

	public Timekeeping findTimekeepingByIdAndDate(String employeeId, Date date) throws RemoteException;

	public List<Timekeeping> findTimekeepingByEmployeeIdMonthYear(String employeeId, Month month, Year year)
			throws RemoteException;

	public List<Timekeeping> findAll() throws RemoteException;
}
