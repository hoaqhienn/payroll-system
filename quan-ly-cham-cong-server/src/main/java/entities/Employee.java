package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EMPLOYEES")
@NamedQuery(name = "Employee.findAll", query = "SELECT a FROM Employee a")
@NamedQuery(name = "Employee.findById", query = "SELECT a FROM Employee a WHERE a.id = :id")

public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "_id", length = 20)
	private String id;

	@Column(name = "_name", length = 30, nullable = false)
	private String name;

	@Column(name = "_dob", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Column(name = "_gender", nullable = false)
	private boolean gender;

	@Column(name = "_address")
	private String address;

	@Column(name = "_phone", length = 10)
	private String phone;

	@Column(name = "_email")
	private String email;

	@Column(name = "_doj", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateOfJoin;

	@Column(name = "_salary", nullable = false)
	private BigDecimal salary;

	@Column(name = "_status", nullable = false)
	private boolean status;

	@Column(name = "_role", nullable = false)
	private String role;

	@Column(name = "_isManager", nullable = false)
	private Boolean isManager;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "_did", nullable = false)
	private Department department;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
	private Account account;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Timekeeping> timekeepings;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Payroll> payrolls;

	public Employee() {
	}
	

	public void createAccount() {
		if (this.account == null) {
			this.account = new Account();
			this.account.setId(this.id);
			this.account.setPassword(this.id);
			this.account.setEmployee(this);
			this.account.setIsAdmin(false);
		}
	}

	public void createAdminAccount() {
		if (this.account == null) {
			this.account = new Account();
			this.account.setId(this.id);
			this.account.setPassword(this.id);
			this.account.setEmployee(this);
			this.account.setIsAdmin(true);
		}
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender
				+ ", address=" + address + ", phone=" + phone + ", email=" + email + ", dateOfJoin=" + dateOfJoin
				+ ", salary=" + salary + ", status=" + status + ", role=" + role + ", isManager=" + isManager
				+ ", department=" + department + "]";
	}


	public Employee(String id, String name, Date dateOfBirth, boolean gender, String address, String phone,
			String email, Date dateOfJoin, BigDecimal salary, boolean status, String role, Boolean isManager,
			Department department) {
		super();
		this.id = id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.dateOfJoin = dateOfJoin;
		this.salary = salary;
		this.status = status;
		this.role = role;
		this.isManager = isManager;
		this.department = department;
	}

}
