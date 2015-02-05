package vn.edu.fpt.fts.model;

// Generated Feb 4, 2015 3:30:12 PM by Hibernate Tools 3.6.0

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * GoodsCategory generated by hbm2java
 */
public class GoodsCategory implements java.io.Serializable {

	private int goodsCategoryId;
	private Serializable name;
	private Set<Goods> goodses = new HashSet<Goods>(0);
	private Set<Route> routes = new HashSet<Route>(0);

	public GoodsCategory() {
	}

	public GoodsCategory(int goodsCategoryId, Serializable name) {
		this.goodsCategoryId = goodsCategoryId;
		this.name = name;
	}

	public GoodsCategory(int goodsCategoryId, Serializable name,
			Set<Goods> goodses, Set<Route> routes) {
		this.goodsCategoryId = goodsCategoryId;
		this.name = name;
		this.goodses = goodses;
		this.routes = routes;
	}

	public int getGoodsCategoryId() {
		return this.goodsCategoryId;
	}

	public void setGoodsCategoryId(int goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}

	public Serializable getName() {
		return this.name;
	}

	public void setName(Serializable name) {
		this.name = name;
	}

	public Set<Goods> getGoodses() {
		return this.goodses;
	}

	public void setGoodses(Set<Goods> goodses) {
		this.goodses = goodses;
	}

	public Set<Route> getRoutes() {
		return this.routes;
	}

	public void setRoutes(Set<Route> routes) {
		this.routes = routes;
	}

}
