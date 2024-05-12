package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

import dao.TimeKeepingDAO;
import entities.Timekeeping;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class TimeKeepingService extends UnicastRemoteObject implements TimeKeepingDAO {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public TimeKeepingService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;

	}

	public List<Timekeeping> findById(String employeeId) {
		String jpql = "SELECT t FROM Timekeeping t WHERE t.employeeId = :employeeId";
		TypedQuery<Timekeeping> query = entityManager.createQuery(jpql, Timekeeping.class);
		query.setParameter("employeeId", employeeId);
		return query.getResultList();
	}

	public Boolean createTimekeeping(Timekeeping timekeeping) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(timekeeping);
			trans.commit();
			return true;
		} catch (Exception e) {
			if (trans.isActive()) {
				trans.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public Boolean deleteTimekeeping(String employeeId, Date date) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			Timekeeping timekeeping = entityManager.find(Timekeeping.class, employeeId);
			if (timekeeping != null && timekeeping.getDate().equals(date)) {
				entityManager.remove(timekeeping);
				transaction.commit();
				return true;
			} else {
				transaction.rollback();
				return false;
			}
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public Boolean updateTimekeeping(Timekeeping timekeeping) throws RemoteException {
		int start = timekeeping.getStartTime().getHour() * 60 + timekeeping.getStartTime().getMinute();
		int end = timekeeping.getEndTime().getHour() * 60 + timekeeping.getEndTime().getMinute();
		int total = end - start;
		timekeeping.setOverTime(total - 9 * 60);
		if (timekeeping.getOverTime() < 0) {
			timekeeping.setOverTime(0);
		}
		timekeeping.setTotalHours(total);
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.merge(timekeeping);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public int countTimeKeepingById() throws RemoteException {
		return entityManager
				.createQuery("SELECT COUNT(t) FROM Timekeeping t WHERE t.employeeId = :employeeId", Long.class)
				.getSingleResult().intValue();
	}

	public List<Timekeeping> findTimekeepingDate(Date date) throws RemoteException {
		return entityManager.createQuery("SELECT t FROM Timekeeping t WHERE t.date = :date", Timekeeping.class)
				.setParameter("date", date).getResultList();
	}

	public Timekeeping findTimekeepingByIdAndDate(String employeeId, Date date) throws RemoteException {
		return entityManager
				.createQuery("SELECT t FROM Timekeeping t WHERE t.employeeId = :employeeId AND t.date = :date",
						Timekeeping.class)
				.setParameter("employeeId", employeeId).setParameter("date", date).getSingleResult();
	}

	public List<Timekeeping> findAll() throws RemoteException {
		return entityManager.createQuery("SELECT t FROM Timekeeping t", Timekeeping.class).getResultList();
	}

	@Override
	public List<Timekeeping> findTimekeepingByEmployeeIdMonthYear(String employeeId, Month month, Year year)
			throws RemoteException {
		// Calculate start and end dates using java.time
		YearMonth yearMonth = YearMonth.of(year.getValue(), month);
		LocalDate startDate = yearMonth.atDay(1); // First day of the month
		LocalDate endDate = yearMonth.atEndOfMonth(); // Last day of the month

		return entityManager.createQuery(
				"SELECT t FROM Timekeeping t WHERE t.employeeId = :employeeId AND t.date BETWEEN :startDate AND :endDate",
				Timekeeping.class).setParameter("employeeId", employeeId).setParameter("startDate", startDate)
				.setParameter("endDate", endDate).getResultList();
	}

}
