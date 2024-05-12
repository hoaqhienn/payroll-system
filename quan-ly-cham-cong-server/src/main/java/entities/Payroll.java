package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PAYROLLS", uniqueConstraints = @UniqueConstraint(columnNames = { "_id", "_month", "_year" }))
public class Payroll implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "_id", length = 20)
	private String employeeId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "_id", insertable = false, updatable = false)
	private Employee employee;

	@Id
	@Column(name = "_month", nullable = false)
	private Month month;

	@Id
	@Column(name = "_year", nullable = false)
	private Year year;

	@Column(name = "_dWork")
	private int daysWorked;

	@Column(name = "_hWork")
	private int hoursWorked;

	@Column(name = "_allowance")
	private BigDecimal allowance;

	@Column(name = "_salary")
	private BigDecimal salary;

	@Column(name = "_total")
	private BigDecimal total;

	public Payroll() {
		super();
	}

	public Payroll(String employeeId, Month month, Year year, int daysWorked, int hoursWorked, BigDecimal allowance,
			BigDecimal salary, BigDecimal total, Employee e) {
		super();
		this.employeeId = employeeId;
		this.month = month;
		this.year = year;
		this.daysWorked = daysWorked;
		this.hoursWorked = hoursWorked;
		this.allowance = allowance;
		this.salary = salary;
		this.total = total;
		this.employee = e;
	}

	@Override
	public String toString() {
		return "Payroll [employeeId=" + employeeId + ", month=" + month + ", year=" + year + ", daysWorked="
				+ daysWorked + ", hoursWorked=" + hoursWorked + ", allowance=" + allowance + ", salary=" + salary
				+ ", total=" + total + "]";
	}

}
