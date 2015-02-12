package vn.edu.fpt.fts.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.pojo.Goods;

@Path("/Goods")
public class GoodsAPI {
	private final static String TAG = "GoodsAPI";
	GoodsDAO goodsDao = new GoodsDAO();

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String Html() {
		return "<html> " + "<title>" + "Web service" + "</title>"
				+ "<body><h1>" + "WEB SERVICE IS ACTIVE" + "</body></h1>"
				+ "</html> ";
	}

	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Goods> JSON() {
		List<Goods> l_goods = goodsDao.getAllGoods();
		return l_goods;
	}

	@POST
	@Path("Create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String createGoods(MultivaluedMap<String, String> goodsParams) {
		Goods goods = new Goods();
		int ret = 0;
		try {
			goods.setWeight(Integer.valueOf(goodsParams.getFirst("weight")));
			goods.setPrice(Double.valueOf(goodsParams.getFirst("price")));
			goods.setPickupTime(goodsParams.getFirst("pickupTime"));
			goods.setPickupAddress(goodsParams.getFirst("pickupAddress"));
			goods.setDeliveryTime(goodsParams.getFirst("deliveryTime"));
			goods.setDeliveryAddress(goodsParams.getFirst("deliveryAddress"));
			goods.setPickupMarkerLongtitude(Float.valueOf(goodsParams
					.getFirst("pickupMarkerLongtitude")));
			goods.setPickupMarkerLatidute(Float.valueOf(goodsParams
					.getFirst("pickupMarkerLatidute")));
			goods.setDeliveryMarkerLongtitude(Float.valueOf(goodsParams
					.getFirst("deliveryMarkerLongtitude")));
			goods.setDeliveryMarkerLatidute(Float.valueOf(goodsParams
					.getFirst("deliveryMarkerLatidute")));
			goods.setNotes(goodsParams.getFirst("notes"));
			goods.setCreateTime(goodsParams.getFirst("createTime"));
			goods.setActive(Integer.valueOf(goodsParams.getFirst("active")));
			goods.setOwnerID(Integer.valueOf(goodsParams.getFirst("ownerID")));
			goods.setGoodsCategoryID(Integer.valueOf(goodsParams
					.getFirst("goodsCategoryID")));
			System.out.println(goodsParams.getFirst("deliveryAddress"));

			ret = goodsDao.insertGoods(goods);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		} catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return String.valueOf(ret);
	}

	@POST
	@Path("getListGoodsByOwnerID")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Goods> getListGoodsByOwnerID(
			MultivaluedMap<String, String> params) {
		List<Goods> l_goods = new ArrayList<Goods>();
		try {
			l_goods = goodsDao.getListGoodsByOwnerID(Integer.valueOf(params
					.getFirst("ownerID")));
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return l_goods;
	}

	@POST
	@Path("getGoodsByID")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Goods getGoodsByID(MultivaluedMap<String, String> params) {
		Goods goods = new Goods();
		goods = goodsDao.getGoodsByID(Integer.valueOf(params
				.getFirst("goodsID")));
		return goods;
	}
}
