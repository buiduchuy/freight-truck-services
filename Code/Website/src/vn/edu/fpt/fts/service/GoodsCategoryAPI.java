package vn.edu.fpt.fts.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import vn.edu.fpt.fts.dao.GoodsCategoryDAO;
import vn.edu.fpt.fts.model.GoodsCategory;

@Path("/GoodsCate")
public class GoodsCategoryAPI {
	GoodsCategoryDAO categoryDAO = new GoodsCategoryDAO();
	
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
		List<GoodsCategory> categories = categoryDAO.getAllGoodsCategory();
		return categories;
	}
}
