package entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TIMEKEEPINGS", uniqueConstraints = @UniqueConstraint(columnNames = { "_id", "_date" }))
public class Timekeeping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "_id", length = 20)
	private String employeeId;

	@Id
	@Temporal(TemporalType.DATE)
	@Column(name = "_date", nullable = false)
	private Date date;

	@Column(name = "_status")
	private int status;

	@Column(name = "_in")
	private LocalTime startTime;

	@Column(name = "_out")
	private LocalTime endTime;

	@Column(name = "_ot")
	private int overTime;

	@Column(name = "_total", nullable = false)
	private int totalHours;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "_id", insertable = false, updatable = false)
	private Employee employee;

	public Timekeeping() {
		super();
	}

	public Timekeeping(String employeeId, Date date, int status, LocalTime startTime, LocalTime endTime, int overTime,
			int totalHours, Employee employee) {
		super();
		this.employeeId = employeeId;
		this.date = date;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
		this.overTime = overTime;
		this.totalHours = totalHours;
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Timekeeping [employeeId=" + employeeId + ", date=" + date + ", status=" + status + ", startTime="
				+ startTime + ", endTime=" + endTime + ", overTime=" + overTime + ", totalHours=" + totalHours + "]";
	}

}