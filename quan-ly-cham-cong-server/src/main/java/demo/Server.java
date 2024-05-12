package demo;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import jakarta.persistence.EntityManager;
import services.AccountService;
import services.AuthService;
import services.DepartmentService;
import services.EmployeeService;
import services.EntityManagerFactoryUtil;
import services.HelloService;
import services.PayrollService;
import services.TimeKeepingService;

public class Server {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		
		Registry registry = LocateRegistry.createRegistry(2024);

		EntityManagerFactoryUtil entityManagerFactory = new EntityManagerFactoryUtil();
		EntityManager entityManager = entityManagerFactory.getEnManager();

		// Bind registration
		registry.bind("Hello", new HelloService(entityManager));
		registry.bind("AuthService", new AuthService(entityManager));
		registry.bind("AccountService", new AccountService(entityManager));
		registry.bind("DepartmentService", new DepartmentService(entityManager));
		registry.bind("EmployeeService", new EmployeeService(entityManager));
		registry.bind("TimekeepingService", new TimeKeepingService(entityManager));
		registry.bind("PayrollService", new PayrollService(entityManager));

		System.out.println("Server Running...");
	}
}