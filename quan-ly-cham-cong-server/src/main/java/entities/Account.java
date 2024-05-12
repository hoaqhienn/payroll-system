package entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNTS")
@NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
@NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id")

public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "_id", length = 20)
	private String id;

	@Column(name = "_pwd", length = 30, nullable = false)
	private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "_id", insertable = false, updatable = false)
    private Employee employee;

	@Column(name = "_isAdmin")
	private Boolean isAdmin;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", password=" + password + ", employee=" + employee + ", isAdmin=" + isAdmin + "]";
	}

}
