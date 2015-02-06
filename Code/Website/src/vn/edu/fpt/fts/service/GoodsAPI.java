package vn.edu.fpt.fts.service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.model.Goods;

@Path("/CreateGoods")
public class GoodsAPI {
	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String PlainText(@PathParam("action") String name) {
		return name;
	}

	// This method is called if XML is request
	@GET
	@Produces(MediaType.TEXT_XML)
	public String XML() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello" + "</hello>";
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String Html(@QueryParam("action") String name) {
		return "<html> " + "<title>" + name + "</title>"
				+ "<body><h1>" + name + "</body></h1>" + "</html> ";
	}
	
	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Goods> JSON() {
		GoodsDAO goodsDao = new GoodsDAO();
		List<Goods> l_goods = goodsDao.getAllGoods();
		return l_goods;
	}
	
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String listGoodsJson(MultivaluedMap<String, String> goodsParams) {
		GoodsDAO goodsDao = new GoodsDAO();
		Goods goods = new Goods();
	    
		goods.setWeight(Integer.valueOf(goodsParams.getFirst("weight")));
		goods.setPrice(Float.valueOf(goodsParams.getFirst("price")));
		
		goods.setPickupTime(goodsParams.getFirst("pickupTime"));
		goods.setDeliveryTime(goodsParams.getFirst("deliveryTime"));
		goods.setCreateTime(goodsParams.getFirst("createTime"));
		goods.setActive(Integer.valueOf(goodsParams.getFirst("active")));
		goods.setOwnerID(Integer.valueOf(goodsParams.getFirst("ownerID")));
		goods.setGoodsCategoryID(Integer.valueOf(goodsParams.getFirst("goodsCategoryID")));
		
		goodsDao.insertGoods(goods);
				
		return "Success";
	}
}
