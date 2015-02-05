package vn.edu.fpt.fts.model;

// Generated Feb 4, 2015 3:30:12 PM by Hibernate Tools 3.6.0

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Role generated by hbm2java
 */
public class Role implements java.io.Serializable {

	private int roleId;
	private Serializable roleName;
	private Set<Account> accounts = new HashSet<Account>(0);

	public Role() {
	}

	public Role(int roleId) {
		this.roleId = roleId;
	}

	public Role(int roleId, Serializable roleName, Set<Account> accounts) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.accounts = accounts;
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Serializable getRoleName() {
		return this.roleName;
	}

	public void setRoleName(Serializable roleName) {
		this.roleName = roleName;
	}

	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

}
