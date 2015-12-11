package vn.edu.fpt.fts.classes;

public class Deal {
	private int active;
	private String createBy;
	private String createTime;
	private int dealID;
	private int dealStatusID;
	private int goodsID;
	private String notes;
	private double price;	
	private int routeID;
	
	public Deal() {
		// TODO Auto-generated constructor stub
	}

		public Deal(double price, String notes, String createTime, String createBy,
				int routeID, int goodsID, int dealStatusID, int active) {
			super();
			this.price = price;
			this.notes = notes;
			this.createTime = createTime;
			this.createBy = createBy;
			this.routeID = routeID;
			this.goodsID = goodsID;
			this.dealStatusID = dealStatusID;
			this.active = active;
		}

		
	public int getActive() {
		return active;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public int getDealID() {
		return dealID;
	}

	public int getDealStatusID() {
		return dealStatusID;
	}

	public int getGoodsID() {
		return goodsID;
	}

	public String getNotes() {
		return notes;
	}

	public double getPrice() {
		return price;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setDealID(int dealID) {
		this.dealID = dealID;
	}

	public void setDealStatusID(int dealStatusID) {
		this.dealStatusID = dealStatusID;
	}

	public void setGoodsID(int goodsID) {
		this.goodsID = goodsID;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}


	public void setPrice(double price) {
		this.price = price;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}
	
	
}
