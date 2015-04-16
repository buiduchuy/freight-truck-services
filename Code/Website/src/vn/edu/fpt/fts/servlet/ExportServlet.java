package vn.edu.fpt.fts.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.dao.OrderDAO;
import vn.edu.fpt.fts.pojo.Order;

/**
 * Servlet implementation class ExportServlet
 */
public class ExportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1121474671166226018L;
	OrderDAO orderDao = new OrderDAO();

//	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
//			Font.BOLD);
//	private static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
//			Font.NORMAL);
//	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
//			Font.BOLD);
	private static Font smallBold;
	private static Font font;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExportServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try (PrintWriter out = response.getWriter()) {
			String action = request.getParameter("btnAction");

			if (action.equalsIgnoreCase("exportOrder")) {
				String path = getServletContext().getRealPath("/");
				System.out.println(path);
				try {
					String orderIDStr = request.getParameter("orderID");
					if (!orderIDStr.isEmpty()) {
						// String FILE = path + "resources/Invoice-" +
						// orderIDStr +
						// ".pdf";

						// String pdfFileName = "Invoice.pdf";
						String FILE = path + "resources/Invoice.pdf";
						int orderID = Integer.valueOf(orderIDStr);
						Order order = orderDao.getOrderByID(orderID);

						String f = path + "resources/times.ttf";
						BaseFont courier = BaseFont.createFont(f,
								BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
						font = new Font(courier, 12);
						smallBold = new Font(courier, 12, Font.BOLD);
						Document document = new Document();
						PdfWriter.getInstance(document, new FileOutputStream(
								FILE));
						document.open();
						addOrder(document, order);
						document.close();

						response.sendRedirect("resources/Invoice.pdf");
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	protected void addOrder(Document document, Order order)
			throws DocumentException, MalformedURLException, IOException {

		// Image image = Image.getInstance(path + "resources/logo.png");

		Paragraph information = new Paragraph();
		information.add(new Paragraph("Freight Truck Services", smallBold));
		information.add(new Paragraph("FPT University", smallBold));
		information.add(new Paragraph("Công viên Phần Mềm Quang Trung",
				smallBold));

		PdfPTable table = new PdfPTable(2);
		PdfPCell cell1 = new PdfPCell();
		cell1.addElement(information);
		cell1.setBorder(Rectangle.NO_BORDER);
		// PdfPCell cell2 = new PdfPCell(image);
		// cell2.setBorder(Rectangle.NO_BORDER);
		// cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		// table.addCell(cell1);
		// table.addCell(cell2);

		Paragraph id = new Paragraph();
		id.add(new Paragraph("Mã hóa đơn #OD" + order.getOrderID(), smallBold));
		id.add(new Paragraph("Ngày tạo hóa đơn: " + order.getCreateTime(), font));
		id.add(new Paragraph("Ngày thanh toán: "
				+ order.getDeal().getGoods().getDeliveryTime(), font));
		addEmptyLine(id, 1);
		id.add(new Paragraph("Tên khách hàng: "
				+ order.getDeal().getGoods().getOwner().getFirstName() + " "
				+ order.getDeal().getGoods().getOwner().getLastName(),
				smallBold));
		id.add(new Paragraph("Địa chỉ: "
				+ order.getDeal().getGoods().getOwner().getAddress(), font));
		id.add(new Paragraph("Số điện thoại: "
				+ order.getDeal().getGoods().getOwner().getPhone(), font));
		addEmptyLine(id, 2);
		id.setIndentationLeft(50);

		PdfPTable detail = new PdfPTable(2);
		float[] columnWidths = { 3f, 1f };
		detail.setWidths(columnWidths);
		PdfPCell desc = new PdfPCell(new Paragraph("Nội dung", font));
		
		desc.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell price = new PdfPCell(new Paragraph("Thành tiền", font));
		price.setHorizontalAlignment(Element.ALIGN_CENTER);

		Paragraph goods = new Paragraph();
		goods.add(new Paragraph("Loại hàng: "
				+ order.getDeal().getGoods().getGoodsCategory().getName(), font));
		goods.add(new Paragraph("Khối lượng: "
				+ order.getDeal().getGoods().getWeight() + " kg", font));
		goods.add(new Paragraph("Thời gian: "
				+ order.getDeal().getGoods().getPickupTime() + " - "
				+ order.getDeal().getGoods().getDeliveryTime(), font));
		goods.add(new Paragraph("Địa chỉ giao hàng: "
				+ order.getDeal().getGoods().getPickupAddress(), font));
		goods.add(new Paragraph("Địa chỉ nhận hàng: "
				+ order.getDeal().getGoods().getDeliveryAddress(), font));
		
		String orderStatus = "";
		if (order.getOrderStatusID() == Common.order_accept) {
			orderStatus = "Đã giao hàng";
		} else if (order.getOrderStatusID() == Common.order_pending) {
			orderStatus = "Đang vận chuyển";
		} else if (order.getOrderStatusID() == Common.order_report) {
			orderStatus = "Đã báo mất hàng";
		}
		
		goods.add(new Paragraph("Trạng thái: " + orderStatus, font));
		// goods.add(new Paragraph("Ghi chú:", font));
		PdfPCell goodsCell = new PdfPCell();
		goodsCell.addElement(goods);
		PdfPCell priceCell = new PdfPCell(new Paragraph(order.getPrice()
				+ " nghìn VNĐ", font));
		priceCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		// PdfPCell serviceCell = new PdfPCell(new Paragraph("Service Fee",
		// font));
		// serviceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		// PdfPCell servicePrice = new PdfPCell(new Paragraph("50 nghìn đồng",
		// font));
		// servicePrice.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell total = new PdfPCell(new Paragraph("Total", font));
		total.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell totalPrice = new PdfPCell(new Paragraph(order.getPrice()
				+ " nghìn VNĐ", font));
		totalPrice.setHorizontalAlignment(Element.ALIGN_CENTER);

		detail.addCell(desc);
		detail.addCell(price);
		detail.addCell(goodsCell);
		detail.addCell(priceCell);
		// detail.addCell(serviceCell);
		// detail.addCell(servicePrice);
		detail.addCell(total);
		detail.addCell(totalPrice);

		document.add(table);
		document.add(id);
		document.add(detail);
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}
