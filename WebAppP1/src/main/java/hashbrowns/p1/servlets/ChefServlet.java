package hashbrowns.p1.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import hashbrowns.p1.utils.*;
import hashbrowns.p1.data.ORM;
import hashbrowns.p1.data.ORMImpl;
import hashbrowns.p1.exceptions.RecipeNameAlreadyExists;
import hashbrowns.p1.exceptions.UsernameAlreadyExistsException;
import hashbrowns.p1.models.Chef;
import hashbrowns.p1.models.Player;
import hashbrowns.p1.services.ChefServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChefServlet extends HttpServlet {

	private Chef chef = new Chef();
	private ChefServiceImpl chefService = new ChefServiceImpl();
	private ObjectMapper objMapper = new ObjectMapper();
	Logger logger = Logger.getLogger();


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		StringBuilder uriString = new StringBuilder(req.getRequestURI()); // /p1/hello/id
		uriString.replace(0, req.getContextPath().length() + 1, "");

		// if there is a slash
		if (uriString.indexOf("/") != -1) {
			logger.log("User is requsting a specific Chef (--DO_GET()--)", LoggingLevel.TRACE);
			uriString.replace(0, uriString.indexOf("/") + 1, ""); // 6
			String path = uriString.toString();
			
			try {
				int id = Integer.parseInt(path);
				Chef chef = new Chef();
				chef.setId(id);

				Object returnChef = chefService.findByID(chef);

				PrintWriter writer = resp.getWriter();
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(returnChef);
				writer.write(json);

			} catch (Exception e) {
				logger.log("User Entered an invalid Chef ID", LoggingLevel.INFO);
				PrintWriter writer = resp.getWriter();
				writer.write("Not an id!");

			}

		} else {
			logger.log("User is requsting all Chef's (--DO_GET()--)", LoggingLevel.TRACE);
			// no path get all chefs in database
			List<Object> chefs = chefService.findAll(chef);
			PrintWriter writer = resp.getWriter();
			// write as json
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(chefs);
			writer.write(json);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.log("User is Inserting a Chef (--DO_POST()--)", LoggingLevel.TRACE);
		Chef chef = objMapper.readValue(req.getInputStream(), Chef.class);

		chefService.registerObject(chef);

		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(chef);
		writer.write(json);

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StringBuilder uriString = new StringBuilder(req.getRequestURI()); // /p1/hello/id
		uriString.replace(0, req.getContextPath().length() + 1, "");
		
		
		if (uriString.indexOf("/") != -1) {
			logger.log("User is updating a specific Chef (--DO_PUT()--)", LoggingLevel.TRACE);
			uriString.replace(0, uriString.indexOf("/") + 1, ""); // 6
			String path = uriString.toString();
			
			try {
				objMapper.setSerializationInclusion(Include.NON_NULL);
				
				Chef chef  = objMapper.readValue(req.getInputStream(), Chef.class);
				
				int id = Integer.parseInt(path);
				
				chef.setId(id);
				
				Object returnChef = chefService.updateObject(chef);
				
				PrintWriter writer = resp.getWriter();
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(returnChef);
				writer.write(json);
				
			} catch (Exception e) {
				logger.log("User Entered an invalid Chef ID", LoggingLevel.INFO);
				e.printStackTrace();
			}

		}else {
			logger.log("ID not supplied", LoggingLevel.INFO);
			PrintWriter writer = resp.getWriter();
			writer.write("Put requires an id!");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.log("User is Deleting a Chef (--DO_DELETE()--)", LoggingLevel.TRACE);
		Chef chef = objMapper.readValue(req.getInputStream(), Chef.class);

		chefService.deleteObj(chef);

		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(chef);
		writer.write(json);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
