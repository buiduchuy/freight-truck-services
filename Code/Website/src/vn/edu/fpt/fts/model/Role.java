package vn.edu.fpt.fts.model;

/**
 * @author Huy
 *
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Role implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6383031919620384310L;
	private int roleId;
	private String roleName;

	public Role() {
		// TODO Auto-generated constructor stub
	}

	public Role(int roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
