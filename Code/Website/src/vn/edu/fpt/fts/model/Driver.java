/**
 * 
 */
package vn.edu.fpt.fts.model;

/**
 * @author Huy
 *
 */
public class Driver {
	private int DriverID;
	private String Email;
	private String FirstName;
	private String LastName;
	private int Sex;
	private String Phone;
	private boolean IsActive;
	private String CreateBy;
	private String CreateTime;
	private String UpdateBy;
	private String UpdateTime;
	private int Age;
	private String Image;
	private int Point;
	public Driver() {
		super();
	}
	public Driver(int driverID, String email, String firstName,
			String lastName, int sex, String phone, boolean isActive,
			String createBy, String createTime, String updateBy,
			String updateTime, int age, String image, int point) {
		super();
		DriverID = driverID;
		Email = email;
		FirstName = firstName;
		LastName = lastName;
		Sex = sex;
		Phone = phone;
		IsActive = isActive;
		CreateBy = createBy;
		CreateTime = createTime;
		UpdateBy = updateBy;
		UpdateTime = updateTime;
		Age = age;
		Image = image;
		Point = point;
	}
	public int getDriverID() {
		return DriverID;
	}
	public void setDriverID(int driverID) {
		DriverID = driverID;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public int getSex() {
		return Sex;
	}
	public void setSex(int sex) {
		Sex = sex;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public boolean isIsActive() {
		return IsActive;
	}
	public void setIsActive(boolean isActive) {
		IsActive = isActive;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getUpdateBy() {
		return UpdateBy;
	}
	public void setUpdateBy(String updateBy) {
		UpdateBy = updateBy;
	}
	public String getUpdateTime() {
		return UpdateTime;
	}
	public void setUpdateTime(String updateTime) {
		UpdateTime = updateTime;
	}
	public int getAge() {
		return Age;
	}
	public void setAge(int age) {
		Age = age;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public int getPoint() {
		return Point;
	}
	public void setPoint(int point) {
		Point = point;
	}

}
