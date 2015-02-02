/**
 * 
 */
package vn.edu.fpt.fts.model;

/**
 * @author Huy
 *
 */
public class Driver {
	private int id;
	private String ddBatdau;
	private String ttBatdau;
	private String ddKetthuc;
	private String ttKetthuc;
	private String Gia;
	private String Diem;
	private String Khoiluong;
	private String Ghichu;

	public Driver(int id, String ddBatdau, String ttBatdau, String ddKetthuc,
			String ttKetthuc, String Gia, String Diem, String Khoiluong,
			String Ghichu) {
		this.id = id;
		this.ddBatdau = ddBatdau;
		this.ttBatdau = ttBatdau;
		this.ddKetthuc = ddKetthuc;
		this.ttKetthuc = ttKetthuc;
		this.Gia = Gia;
		this.Diem = Diem;
		this.Khoiluong = Khoiluong;
		this.Ghichu = Ghichu;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDdBatdau() {
		return ddBatdau;
	}

	public void setDdBatdau(String ddBatdau) {
		this.ddBatdau = ddBatdau;
	}

	public String getTtBatdau() {
		return ttBatdau;
	}

	public void setTtBatdau(String ttBatdau) {
		this.ttBatdau = ttBatdau;
	}

	public String getDdKetthuc() {
		return ddKetthuc;
	}

	public void setDdKetthuc(String ddKetthuc) {
		this.ddKetthuc = ddKetthuc;
	}

	public String getTtKetthuc() {
		return ttKetthuc;
	}

	public void setTtKetthuc(String ttKetthuc) {
		this.ttKetthuc = ttKetthuc;
	}

	public String getGia() {
		return Gia;
	}

	public void setGia(String Gia) {
		this.Gia = Gia;
	}

	public String getDiem() {
		return Diem;
	}

	public void setDiem(String Diem) {
		this.Diem = Diem;
	}

	public String getKhoiluong() {
		return Khoiluong;
	}

	public void setKhoiluong(String Khoiluong) {
		this.Khoiluong = Khoiluong;
	}

	public String getGhichu() {
		return Ghichu;
	}

	public void setGhichu(String Ghichu) {
		this.Ghichu = Ghichu;
	}

}
