package vn.edu.fpt.fts.api;

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

import vn.edu.fpt.fts.dao.GoodsCategoryDAO;
import vn.edu.fpt.fts.pojo.GoodsCategory;

@Path("/GoodsCategory")
public class GoodsCategoryAPI {

	private final static String TAG = "GoodsCategoryAPI";
	GoodsCategoryDAO goodsCategoryDAO = new GoodsCategoryDAO();

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
	public List<GoodsCategory> JSON() {
		List<GoodsCategory> categories = goodsCategoryDAO.getAllGoodsCategory();
		return categories;
	}

	@POST
	@Path("Create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String createGoodsCategory(MultivaluedMap<String, String> goodsParams) {
		GoodsCategory goodsCategory = new GoodsCategory();
		try {
			goodsCategory.setName(goodsParams.getFirst("goodsCategoryName"));

			int ret = goodsCategoryDAO.insertGoodsCategory(goodsCategory);
			if (ret <= 0) {
				return "Fail";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return "Success";
	}

}
