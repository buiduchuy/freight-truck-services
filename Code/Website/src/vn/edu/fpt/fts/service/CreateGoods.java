package vn.edu.fpt.fts.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/CreateGoods")
public class CreateGoods {
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
	@Produces(MediaType.APPLICATION_JSON)
	public String JSON(@QueryParam("action") String name) {
		return "<html> " + "<title>" + name + "</title>"
				+ "<body><h1>" + name + "</body></h1>" + "</html> ";
	}
}
