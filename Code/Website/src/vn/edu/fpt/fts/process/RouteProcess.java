/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import vn.edu.fpt.fts.dao.DealDAO;
import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.dao.RouteGoodsCategoryDAO;
import vn.edu.fpt.fts.dao.RouteMarkerDAO;
import vn.edu.fpt.fts.pojo.Route;
import vn.edu.fpt.fts.pojo.RouteMarker;

/**
 * @author Huy
 *
 */
public class RouteProcess {
	private final static String TAG = "RouteProcess";

	DealDAO dealDao = new DealDAO();
	RouteDAO routeDao = new RouteDAO();
	RouteMarkerDAO routeMarkerDao = new RouteMarkerDAO();
	RouteGoodsCategoryDAO routeGoodsCategoryDao = new RouteGoodsCategoryDAO();

	public int updateRoute(Route route,
			Map<String, Boolean> routeGoodsCategoryMap,
			List<RouteMarker> listRouteMarker) {
		int ret = 0;
		int routeID = route.getRouteID();

		if (routeDao.getRouteByID(routeID) != null) {

			try {
				if (dealDao.getDealByRouteID(routeID).size() == 0) {

					ret = routeDao.updateRoute(route);
					// ---------------------------------------------------------
					routeMarkerDao.deleteRouteMarkerByRouteID(routeID);
					for (int i = 0; i < listRouteMarker.size(); i++) {
						if (!listRouteMarker.get(i).getRouteMarkerLocation()
								.isEmpty()) {
							routeMarkerDao.insertRouteMarker(listRouteMarker
									.get(i));
						}
					}
					// ---------------------------------------------------------

					// Get params category true/false
					routeGoodsCategoryDao
							.deleteRouteGoodsCategoryByRouteID(routeID);
					String goodsCategoryName = "";

					String food = "Food";
					if (routeGoodsCategoryMap.get(food)) {
						goodsCategoryName = "Hàng thực phẩm";
						routeGoodsCategoryDao.insertRouteGoodsCategory(routeID,
								goodsCategoryName);
					}
					String freeze = "Freeze";
					if (routeGoodsCategoryMap.get(freeze)) {
						goodsCategoryName = "Hàng đông lạnh";
						routeGoodsCategoryDao.insertRouteGoodsCategory(routeID,
								goodsCategoryName);
					}

					String broken = "Broken";
					if (routeGoodsCategoryMap.get(broken)) {
						goodsCategoryName = "Hàng dễ vỡ";
						routeGoodsCategoryDao.insertRouteGoodsCategory(routeID,
								goodsCategoryName);
					}

					String flame = "Flame";
					if (routeGoodsCategoryMap.get(flame)) {
						goodsCategoryName = "Hàng dễ cháy nổ";
						routeGoodsCategoryDao.insertRouteGoodsCategory(routeID,
								goodsCategoryName);
					}
				}

			} catch (NumberFormatException e) {
				// TODO: handle exception
				e.printStackTrace();
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		return ret;
	}
}
