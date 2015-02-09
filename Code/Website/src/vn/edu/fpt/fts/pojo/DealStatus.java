/**
 * 
 */
package vn.edu.fpt.fts.pojo;

import java.io.Serializable;
import java.util.List;

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

	private List<Deal> listDeal;

	public DealStatus() {
		// TODO Auto-generated constructor stub
	}

	public DealStatus(int dealStatusID, String dealStatusName,
			List<Deal> listDeal) {
		super();
		this.dealStatusID = dealStatusID;
		this.dealStatusName = dealStatusName;
		this.listDeal = listDeal;
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

	public List<Deal> getListDeal() {
		return listDeal;
	}

	public void setListDeal(List<Deal> listDeal) {
		this.listDeal = listDeal;
	}

}
