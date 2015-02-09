package vn.edu.fpt.fts.pojo;

/**
 * @author Huy
 *
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Owner implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2019159454117177612L;
	private int ownerID;
	private String email;
	private String firstName;
	private String lastName;
	private int gender;
	private String phone;
	private String address;
	private int active;
	private String createBy;
	private String createTime;
	private String updateBy;
	private String updateTime;
	
	public Owner() {
		// TODO Auto-generated constructor stub
	}

	public Owner(int ownerID, String email, String firstName, String lastName,
			int gender, String phone, String address, int active,
			String createBy, String createTime, String updateBy,
			String updateTime) {
		super();
		this.ownerID = ownerID;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.phone = phone;
		this.address = address;
		this.active = active;
		this.createBy = createBy;
		this.createTime = createTime;
		this.updateBy = updateBy;
		this.updateTime = updateTime;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
