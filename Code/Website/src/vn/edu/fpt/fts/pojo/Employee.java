/**
 * 
 */
package vn.edu.fpt.fts.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Duc Huy
 *
 */
@XmlRootElement
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1394747226125344929L;
	private int employeeID;
	private String email;
	private String firstName;
	private String lastName;
	private String phone;
	private int gender;
	private String image;
	private int active;

	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public Employee(int employeeID, String email, String firstName,
			String lastName, String phone, int gender, String image, int active) {
		super();
		this.employeeID = employeeID;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.gender = gender;
		this.image = image;
		this.active = active;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	
}
