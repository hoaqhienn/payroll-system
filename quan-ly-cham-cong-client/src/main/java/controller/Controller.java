package controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import dao.AccountDAO;
import dao.AuthDAO;
import dao.DepartmentDAO;
import dao.EmployeeDAO;
import dao.Hello;
import dao.PayrollDAO;
import dao.TimeKeepingDAO;
import entities.Account;
import entities.Department;
import entities.Employee;
import entities.Payroll;
import entities.Timekeeping;

public class Controller {
	//Server URL
	private static final String URL = "rmi://127.0.0.1:2024/";

	private static String string;
	private static Account account = null;

	public static Boolean connectServer() {
		try {
			Hello hello = (Hello) Naming.lookup(URL + "Hello");
			setString(hello.sayHello());
			System.out.println("From Server: " + string);
			return true;
		} catch (RemoteException e) {
			System.out.println("Không thể kết nối đến server: " + e.getMessage());
		} catch (NotBoundException | MalformedURLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void login(String username, String password) {
		try {
			AuthDAO authService = (AuthDAO) Naming.lookup(URL + "AuthService");
			setAccount(authService.login(username, password)); // Set account isLogin
			if (account != null) {
				System.out.println("Đăng nhập thành công! Tài khoản: " + getAccount());
			} else {
				System.out.println("Thông tin đăng nhập không hợp lệ!");
			}
		} catch (Exception e) {

		}
	}

	public static void changePassword(String newPassword) {
		try {
			AccountDAO a = (AccountDAO) Naming.lookup(URL + "AccountService");
			Account myAccount = getAccount();
			myAccount.setPassword(newPassword);
			if (a.changePassword(myAccount)) {
				JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!", null, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(null, "Đổi mật khẩu thất bại!", null, JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
		}
	}

	public static void resetPassword(Account account) {
		try {
			AccountDAO a = (AccountDAO) Naming.lookup(URL + "AccountService");
			account.setPassword(account.getId());
			if (a.changePassword(account)) {
				JOptionPane.showMessageDialog(null, "Khôi phục mật khẩu thành công!", null,
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(null, "Khôi phục mật khẩu thất bại!", null, JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
		}
	}

	public static List<Account> allAccount() {
		try {
			AccountDAO a = (AccountDAO) Naming.lookup(URL + "AccountService");
			return a.findAll();
		} catch (Exception e) {
		}
		return null;
	}

	public static Account findAccount(String accountId) {
		try {
			AccountDAO a = (AccountDAO) Naming.lookup(URL + "AccountService");
			return a.findById(accountId);
		} catch (Exception e) {
		}
		return null;
	}

	public static List<Department> allDepartments() {
		try {
			DepartmentDAO e = (DepartmentDAO) Naming.lookup(URL + "DepartmentService");
			return e.findAll();
		} catch (Exception e) {
		}
		return null;
	}

	public static List<Employee> allEmployees() {
		try {
			EmployeeDAO e = (EmployeeDAO) Naming.lookup(URL + "EmployeeService");
			return e.findAll();
		} catch (Exception e) {
		}
		return null;
	}

	public static List<Employee> employeeByDepartment(String departmentID) {
		try {
			EmployeeDAO e = (EmployeeDAO) Naming.lookup(URL + "EmployeeService");
			return e.findByDepartment(departmentID);
		} catch (Exception e) {
		}
		return null;
	}

	public static Employee employeeById(String employeeId) {
		try {
			EmployeeDAO e = (EmployeeDAO) Naming.lookup(URL + "EmployeeService");
			return e.findById(employeeId);
		} catch (Exception e) {

		}

		return null;
	}

	public static int countEmployee() {
		try {
			EmployeeDAO e = (EmployeeDAO) Naming.lookup(URL + "EmployeeService");
			return e.countEmployees();
		} catch (Exception e) {

		}
		return 0;
	}

	public static Boolean createEmployee(Employee emp) {
		try {
			EmployeeDAO e = (EmployeeDAO) Naming.lookup(URL + "EmployeeService");
			if (e.createEmployee(emp)) {
				return true;
			}
		} catch (Exception e2) {
		}
		return false;
	}

	public static Boolean removeEmployee(String employeeId) {
		try {
			EmployeeDAO e = (EmployeeDAO) Naming.lookup(URL + "EmployeeService");
			if (e.deleteEmployee(employeeId)) {
				return true;
			}
		} catch (Exception e2) {
		}
		return false;
	}

	public static List<Timekeeping> allTimekeepings() {
		try {
			TimeKeepingDAO t = (TimeKeepingDAO) Naming.lookup(URL + "TimekeepingService");
			return t.findAll();
		} catch (Exception e) {
		}
		return null;
	}

	public static Boolean createTimekeeping(Timekeeping timekeeping) {
		try {
			TimeKeepingDAO t = (TimeKeepingDAO) Naming.lookup(URL + "TimekeepingService");
			if (t.createTimekeeping(timekeeping)) {
				return true;
			}
		} catch (Exception e2) {
		}
		return false;
	}

	public static Boolean updateTimekeeping(Timekeeping timekeeping) {
		try {
			TimeKeepingDAO t = (TimeKeepingDAO) Naming.lookup(URL + "TimekeepingService");
			if (t.updateTimekeeping(timekeeping)) {
				return true;
			}
		} catch (Exception e2) {
		}
		return false;
	}

	public static Boolean deleteTimekeeping(String employeeId, Date date) {
		try {
			TimeKeepingDAO t = (TimeKeepingDAO) Naming.lookup(URL + "TimekeepingService");
			if (t.deleteTimekeeping(employeeId, date)) {
				return true;
			}
		} catch (Exception e2) {
		}
		return false;
	}

	public static Timekeeping myTimekeeping(String employeeId, Date date) {
		try {
			TimeKeepingDAO t = (TimeKeepingDAO) Naming.lookup(URL + "TimekeepingService");
			return t.findTimekeepingByIdAndDate(employeeId, date);
		} catch (Exception e) {
		}
		return null;
	}
	
	public static List<Timekeeping> allMyTimekeeping(String employeeId) {
		try {
			TimeKeepingDAO t = (TimeKeepingDAO) Naming.lookup(URL + "TimekeepingService");
			return t.findById(employeeId);
		} catch (Exception e) {
		}
		return null;
	}

	public static List<Payroll> payrollByDate(Month month, Year year) {
		try {
			PayrollDAO t = (PayrollDAO) Naming.lookup(URL + "PayrollService");
			return t.findByDate(month, year);
		} catch (Exception e) {
		}
		return null;
	}

	public static Payroll payrollByIdAndDate(String employeeId, Month month, Year year) {
		try {
			PayrollDAO t = (PayrollDAO) Naming.lookup(URL + "PayrollService");
			return t.findByIdAndDate(employeeId, month, year);
		} catch (Exception e) {
		}
		return null;
	}

	public static Boolean createPayroll(Month month, Year year) {
		try {
			PayrollDAO t = (PayrollDAO) Naming.lookup(URL + "PayrollService");
			if (t.createPayroll(month, year)) {
				return true;
			}
		} catch (Exception e2) {
		}
		return false;
	}
	//

	public static String getString() {
		return string;
	}

	public static void setString(String string) {
		Controller.string = string;
	}

	public static Account getAccount() {
		return account;
	}

	public static void setAccount(Account account) {
		Controller.account = account;
	}

}
