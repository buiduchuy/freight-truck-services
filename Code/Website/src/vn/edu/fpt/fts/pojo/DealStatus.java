/**
 * 
 */
package vn.edu.fpt.fts.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Huy
 *
 */
@XmlRootElement
public class DealStatus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8127803179698169881L;
	private int dealStatusID;
	private String dealStatusName;

	public DealStatus() {
		// TODO Auto-generated constructor stub
	}

	public DealStatus(int dealStatusID, String dealStatusName) {
		super();
		this.dealStatusID = dealStatusID;
		this.dealStatusName = dealStatusName;
	}

	public int getDealStatusID() {
		return dealStatusID;
	}

	public void setDealStatusID(int dealStatusID) {
		this.dealStatusID = dealStatusID;
	}

	public String getDealStatusName() {
		return dealStatusName;
	}

	public void setDealStatusName(String dealStatusName) {
		this.dealStatusName = dealStatusName;
	}

}
