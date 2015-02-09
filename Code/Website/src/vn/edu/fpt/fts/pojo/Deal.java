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
public class Deal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7427665539357586169L;
	private int dealID;
	private double price;
	private String notes;
	private String createTime;
	private String sender;
	private int routeID;
	private int goodsID;
	private int dealStatusID;
	private int active;

	private List<Goods> listGoods;
	private List<Route> listRoute;
	private List<DealStatus> listDealStatus;

	public Deal(int dealID, double price, String notes, String createTime,
			String sender, int routeID, int goodsID, int dealStatusID,
			int active, List<Goods> listGoods, List<Route> listRoute,
			List<DealStatus> listDealStatus) {
		super();
		this.dealID = dealID;
		this.price = price;
		this.notes = notes;
		this.createTime = createTime;
		this.sender = sender;
		this.routeID = routeID;
		this.goodsID = goodsID;
		this.dealStatusID = dealStatusID;
		this.active = active;
		this.listGoods = listGoods;
		this.listRoute = listRoute;
		this.listDealStatus = listDealStatus;
	}

	public List<DealStatus> getListDealStatus() {
		return listDealStatus;
	}

	public void setListDealStatus(List<DealStatus> listDealStatus) {
		this.listDealStatus = listDealStatus;
	}

	public Deal(int dealID, double price, String notes, String createTime,
			String sender, int routeID, int goodsID, int dealStatusID,
			int active, List<Goods> listGoods, List<Route> listRoute) {
		super();
		this.dealID = dealID;
		this.price = price;
		this.notes = notes;
		this.createTime = createTime;
		this.sender = sender;
		this.routeID = routeID;
		this.goodsID = goodsID;
		this.dealStatusID = dealStatusID;
		this.active = active;
		this.listGoods = listGoods;
		this.listRoute = listRoute;
	}

	public Deal(double price, String notes, String createTime, String sender,
			int routeID, int goodsID, int dealStatusID, int active,
			List<Goods> listGoods, List<Route> listRoute) {
		super();
		this.price = price;
		this.notes = notes;
		this.createTime = createTime;
		this.sender = sender;
		this.routeID = routeID;
		this.goodsID = goodsID;
		this.dealStatusID = dealStatusID;
		this.active = active;
		this.listGoods = listGoods;
		this.listRoute = listRoute;
	}

	public List<Goods> getListGoods() {
		return listGoods;
	}

	public void setListGoods(List<Goods> listGoods) {
		this.listGoods = listGoods;
	}

	public List<Route> getListRoute() {
		return listRoute;
	}

	public void setListRoute(List<Route> listRoute) {
		this.listRoute = listRoute;
	}

	public Deal() {
		// TODO Auto-generated constructor stub
	}

	public int getDealID() {
		return dealID;
	}

	public void setDealID(int dealID) {
		this.dealID = dealID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	public int getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(int goodsID) {
		this.goodsID = goodsID;
	}

	public int getDealStatusID() {
		return dealStatusID;
	}

	public void setDealStatusID(int dealStatusID) {
		this.dealStatusID = dealStatusID;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Deal(double price, String notes, String createTime, String sender,
			int routeID, int goodsID, int dealStatusID, int active) {
		super();
		this.price = price;
		this.notes = notes;
		this.createTime = createTime;
		this.sender = sender;
		this.routeID = routeID;
		this.goodsID = goodsID;
		this.dealStatusID = dealStatusID;
		this.active = active;
	}

	public Deal(int dealID, double price, String notes, String createTime,
			String sender, int routeID, int goodsID, int dealStatusID,
			int active) {
		super();
		this.dealID = dealID;
		this.price = price;
		this.notes = notes;
		this.createTime = createTime;
		this.sender = sender;
		this.routeID = routeID;
		this.goodsID = goodsID;
		this.dealStatusID = dealStatusID;
		this.active = active;
	}

}
