package demo;

import java.math.BigDecimal;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.DepartmentDAO;
import dao.EmployeeDAO;
import dao.PayrollDAO;
import dao.TimeKeepingDAO;
import entities.Department;
import entities.Employee;
import entities.Timekeeping;
import jakarta.persistence.EntityManager;
import services.DepartmentService;
import services.EmployeeService;
import services.EntityManagerFactoryUtil;
import services.PayrollService;
import services.TimeKeepingService;

public class DropAndCreate {
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {

		EntityManagerFactoryUtil entityManagerFactory = new EntityManagerFactoryUtil();
		EntityManager entityManager = entityManagerFactory.getEnManager();

		DepartmentDAO departmentDAO = new DepartmentService(entityManager);
		EmployeeDAO employeeDAO = new EmployeeService(entityManager);
		TimeKeepingDAO timeKeepingDAO = new TimeKeepingService(entityManager);
		PayrollDAO payrollDAO = new PayrollService(entityManager);
		

		employeeDAO.deleteAllEmployees();

		Department d = departmentDAO.findById("1");
		Department d2 = departmentDAO.findById("2");

		Employee admin = new Employee("ADMIN", "ADMIN", new Date(), true, "@", "@", "@", new Date(), new BigDecimal(4160000),
				true, "ADMIN", true, d);

		Employee e = new Employee("TAI", "VÕ TRỌNG TÀI", new Date(), true, "@", "@", "@", new Date(), new BigDecimal(20800000),
				true, "QUẢN LÝ", true, d2);
		Employee e1 = new Employee("KHA", "HUỲNH THỊ NGỌC KHÁ", new Date(), true, "@", "@", "@", new Date(),
				new BigDecimal(10400000), true, "NHÂN VIÊN", true, d2 );
		Employee e2 = new Employee("E2", "HỌ VÀ TÊN", new Date(), true, "@", "@", "@", new Date(), new BigDecimal(10400000),
				true, "NHÂN VIÊN", false, d2);
		Employee e3 = new Employee("E3", "HỌ VÀ TÊN", new Date(), true, "@", "@", "@", new Date(), new BigDecimal(10400000),
				true, "NHÂN VIÊN", false, d2);
		Employee e4 = new Employee("E4", "HỌ VÀ TÊN", new Date(), true, "@", "@", "@", new Date(), new BigDecimal(10400000),
				true, "NHÂN VIÊN", false, d2);
		Employee e5 = new Employee("E5", "HỌ VÀ TÊN", new Date(), true, "@", "@", "@", new Date(), new BigDecimal(10400000),
				true, "NHÂN VIÊN", false, d2);
		Employee e6 = new Employee("E6", "HỌ VÀ TÊN", new Date(), true, "@", "@", "@", new Date(), new BigDecimal(10400000),
				true, "NHÂN VIÊN", false, d2);
		Employee e7 = new Employee("E7", "HỌ VÀ TÊN", new Date(), true, "@", "@", "@", new Date(), new BigDecimal(10400000),
				true, "NHÂN VIÊN", false, d2);
		Employee e8 = new Employee("E8", "HỌ VÀ TÊN", new Date(), true, "@", "@", "@", new Date(), new BigDecimal(10400000),
				true, "NHÂN VIÊN", false, d2);
		Employee e9 = new Employee("E9", "HỌ VÀ TÊN", new Date(), true, "@", "@", "@", new Date(), new BigDecimal(10400000),
				true, "NHÂN VIÊN", false, d2);

		employeeDAO.createAdmin(admin);
		List<Employee> employees = new ArrayList<>();
		employees.add(e);
		employees.add(e1);
		employees.add(e2);
		employees.add(e3);
		employees.add(e4);
		employees.add(e5);
		employees.add(e6);
		employees.add(e7);
		employees.add(e8);
		employees.add(e9);

		
		for (Employee ee : employees) {
			employeeDAO.createEmployee(ee);
		}
		
		employees.add(admin);
		
		for (int day = 1; day <= 30; day++) {
            Date date = new Date(2024 - 1900, 5, day);
            Timekeeping t = new Timekeeping(admin.getId(), date, 0, LocalTime.of(8, 0),
                                             LocalTime.of(17, 0), 0, 0, e);
            timeKeepingDAO.createTimekeeping(t);
            t.setStatus(1);
            timeKeepingDAO.updateTimekeeping(t);
		}
		
		for (Employee ee : employees) {
			for (int day = 1; day <= 31; day++) {
	            Date date = new Date(2024 - 1900, 4, day);
	            Timekeeping t = new Timekeeping(ee.getId(), date, 0, LocalTime.of(8, 0),
	                                             LocalTime.of(17, 30), 0, 0, e);
	            timeKeepingDAO.createTimekeeping(t);
	            t.setStatus(1);
	            timeKeepingDAO.updateTimekeeping(t);
			}
		}

		payrollDAO.createPayroll(Month.MAY, Year.now());
		payrollDAO.createPayroll(Month.JUNE, Year.now());
		entityManager.close();
		
		System.out.println("finished!");

	}
	
	private void createData() {
		
	}
}
