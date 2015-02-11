/**
 * 
 */
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

import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.pojo.Deal;
import vn.edu.fpt.fts.process.UpdateDealProcess;

/**
 * @author Huy
 *
 */
@Path("Deal")
public class DealAPI {

	private final static String TAG = "DealAPI";
	DealDAO dealDao = new DealDAO();

	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deal> getAllDeal() {
		List<Deal> l_deals = dealDao.getAllDeal();
		return l_deals;
	}

	@POST
	@Path("getDealByID")
	@Produces(MediaType.APPLICATION_JSON)
	public Deal getDealByID(MultivaluedMap<String, String> params) {
		Deal deal = new Deal();
		try {
			deal = dealDao.getDealByID(Integer.valueOf(params
					.getFirst("dealID")));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}

		return deal;
	}

	@POST
	@Path("getDealByRouteID")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deal> getDealByRouteID(MultivaluedMap<String, String> params) {
		List<Deal> l_deals = dealDao.getDealByRouteID(Integer.valueOf(params
				.getFirst("routeID")));
		return l_deals;
	}

	@POST
	@Path("getDealByGoodsID")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deal> getDealByGoodsID(MultivaluedMap<String, String> params) {
		List<Deal> l_deals = dealDao.getDealByGoodsID(Integer.valueOf(params
				.getFirst("gouteID")));
		return l_deals;
	}

	@POST
	@Path("Create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String createDeal(MultivaluedMap<String, String> params) {
		Deal deal;
		try {
			deal = new Deal();

			deal.setPrice(Double.valueOf(params.getFirst("price")));
			deal.setNotes(params.getFirst("notes"));
			deal.setCreateTime(params.getFirst("createTime"));
			deal.setCreateBy(params.getFirst("createBy"));
			deal.setRouteID(Integer.valueOf(params.getFirst("routeID")));
			deal.setGoodsID(Integer.valueOf(params.getFirst("goodsID")));
			deal.setDealStatusID(Integer.valueOf(params
					.getFirst("dealStatusID")));
			if (!params.getFirst("refDealID").equals("")) {
				deal.setRefDealID(Integer.valueOf(params.getFirst("refDealID")));
			}
			deal.setActive(Integer.valueOf(params.getFirst("active")));

			int ret = dealDao.insertDeal(deal);
			if (ret <= 0) {
				return "Fail";
			}

		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return "Success";
	}

	@POST
	@Path("getDealByDriverID")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deal> getDealByDriverID(MultivaluedMap<String, String> params) {
		List<Deal> l_deals = dealDao.getDealByDriverID(Integer.valueOf(params
				.getFirst("driverID")));
		return l_deals;
	}

	@POST
	@Path("getDealByOwnerID")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deal> getDealByOwnerID(MultivaluedMap<String, String> params) {
		List<Deal> l_deals = dealDao.getDealByOwnerID(Integer.valueOf(params
				.getFirst("ownerID")));
		return l_deals;
	}

	@POST
	@Path("accept")
	@Produces(MediaType.APPLICATION_JSON)
	public String acceptDeal(MultivaluedMap<String, String> params) {
		try {
			int dealID = Integer.valueOf(params.getFirst("dealID"));
			UpdateDealProcess udp = new UpdateDealProcess();
			udp.changeDealOwnerAccept(dealID);
			return "Success";
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return "Fail";
	}
}
