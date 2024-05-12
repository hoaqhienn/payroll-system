package services;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Month;
import java.time.Year;
import java.util.List;

import dao.PayrollDAO;
import entities.Employee;
import entities.Payroll;
import entities.Timekeeping;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class PayrollService extends UnicastRemoteObject implements PayrollDAO {

	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public PayrollService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;

	}

	@Override
	public int countPayroll() throws RemoteException {
		Query query = entityManager.createQuery("SELECT COUNT(p) FROM Payroll p");
		return ((Number) query.getSingleResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Payroll> findAll() throws RemoteException {
		Query query = entityManager.createQuery("SELECT p FROM Payroll p");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Payroll> findByEmployee(String employeeId) throws RemoteException {
		Query query = entityManager.createQuery("SELECT p FROM Payroll p WHERE p.employee.id = :employeeId");
		query.setParameter("employeeId", Long.parseLong(employeeId));
		return query.getResultList();
	}

	@Override
	public List<Payroll> findByDate(Month month, Year year) {
		TypedQuery<Payroll> query = entityManager
				.createQuery("SELECT p FROM Payroll p WHERE p.month = :month AND p.year = :year", Payroll.class);
		query.setParameter("month", month);
		query.setParameter("year", year);
		return query.getResultList();
	}

	@Override
	public Payroll findByIdAndDate(String employeeId, Month month, Year year) throws RemoteException {
	    TypedQuery<Payroll> query = entityManager.createQuery("SELECT p FROM Payroll p WHERE p.employeeId = :employeeId AND p.month = :month AND p.year = :year", Payroll.class);
	    query.setParameter("employeeId", employeeId);
	    query.setParameter("month", month);
	    query.setParameter("year", year);
	    return query.getSingleResult();
	}

	@Override
	public boolean updatePayroll(Payroll payroll) throws RemoteException {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(payroll);
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deletePayrollByMonthAndYear(Month month, Year year) throws RemoteException {
		Query query = entityManager.createQuery("DELETE FROM Payroll p WHERE p.month = :month AND p.year = :year");
		query.setParameter("month", month);
		query.setParameter("year", year);

		try {
			entityManager.getTransaction().begin();
			int deletedCount = query.executeUpdate();
			entityManager.getTransaction().commit();
			System.out.println("Deleted " + deletedCount + " payroll entries for " + month + " " + year);
			return true;
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deletePayrollByEmployee(String employeeId) throws RemoteException {
		Query query = entityManager.createQuery("DELETE FROM Payroll p WHERE p.employee.id = :employeeId");
		query.setParameter("employeeId", Long.parseLong(employeeId));

		try {
			entityManager.getTransaction().begin();
			query.executeUpdate();
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean createPayroll(Month month, Year year) throws RemoteException {
		deletePayrollByMonthAndYear(month, year);
		List<Employee> list = new EmployeeService(entityManager).findAll();
		try {
			entityManager.getTransaction().begin();
			for (Employee e : list) {
				List<Timekeeping> timekeepings = new TimeKeepingService(entityManager)
						.findTimekeepingByEmployeeIdMonthYear(e.getId(), month, year);
				int days = 0;
				int totalMinues = 0;
				for (Timekeeping timekeeping : timekeepings) {
					days++;
					totalMinues += timekeeping.getTotalHours();
				}

				int hours = totalMinues / 60;

				Payroll p = new Payroll(e.getId(), month, year, days, hours, new BigDecimal(days * 25000),
						new BigDecimal((e.getSalary().intValue() / 8 / 26) * hours),
						new BigDecimal((days * 25000) + (e.getSalary().intValue() / 8 / 26) * hours), e);

				entityManager.persist(p);
			}
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

//	@Override
//	public boolean createPayroll(Month month, Year year) throws RemoteException {
//		LocalDate now = LocalDate.now();
//		Month month = now.getMonth();
//		Year year = Year.of(now.getYear());
//		deletePayrollByMonthAndYear(month, year);
//		List<Employee> list = new EmployeeService(entityManager).findAll();
//		try {
//			entityManager.getTransaction().begin();
//			for (Employee e : list) {
//				Payroll p = new Payroll(e.getId(), month, year, 0, 0, new BigDecimal(0), new BigDecimal(0),
//						new BigDecimal(0));
//				entityManager.persist(p);
//			}
//			entityManager.getTransaction().commit();
//			return true;
//		} catch (Exception e) {
//			if (entityManager.getTransaction().isActive()) {
//				entityManager.getTransaction().rollback();
//			}
//			e.printStackTrace();
//			return false;
//		}
//	}

}
