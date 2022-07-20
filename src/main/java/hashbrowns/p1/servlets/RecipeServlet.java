package hashbrowns.p1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import hashbrowns.p1.utils.*;
import hashbrowns.p1.data.ORM;
import hashbrowns.p1.data.ORMImpl;
import hashbrowns.p1.exceptions.RecipeNameAlreadyExists;
import hashbrowns.p1.exceptions.UsernameAlreadyExistsException;
import hashbrowns.p1.models.Chef;
import hashbrowns.p1.models.Recipe;
import hashbrowns.p1.services.RecipeServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RecipeServlet extends HttpServlet {

	private Recipe recipe = new Recipe();
	private RecipeServiceImpl recipeService = new RecipeServiceImpl();
	private ObjectMapper objMapper = new ObjectMapper();
	Logger logger = Logger.getLogger();

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		StringBuilder uriString = new StringBuilder(req.getRequestURI()); // /p1/hello/id
		uriString.replace(0, req.getContextPath().length() + 1, "");

		// if there is a slash
		if (uriString.indexOf("/") != -1) {
			logger.log("User is requsting a specific Recipe (--DO_GET()--)", LoggingLevel.TRACE);
			uriString.replace(0, uriString.indexOf("/") + 1, ""); // 6

			String path = uriString.toString();
			
			try {
				int id = Integer.parseInt(path);
				Recipe recipe = new Recipe();
				recipe.setId(id);
				
				Object returnRecipe = recipeService.findByID(recipe);
				
				PrintWriter writer = resp.getWriter();
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(returnRecipe);
				writer.write(json);

			} catch (Exception e) {
				logger.log("User Entered an invalid Recipe ID", LoggingLevel.INFO);
				PrintWriter writer = resp.getWriter();
				writer.write("Not an id!");
				
			}

		} else {
			logger.log("User is requsting all Recipes (--DO_GET()--)", LoggingLevel.TRACE);
			// no path get all chefs in database
			List<Object> rec = recipeService.findAll(recipe);
			PrintWriter writer = resp.getWriter();

			// Write to response body
			// writer.write(objMapper.writeValueAsString(chefs));

			// write as json
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(rec);
			writer.write(json);
		}

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		logger.log("User is Inserting a Recipe (--DO_POST()--)", LoggingLevel.TRACE);
		Recipe recipe  = objMapper.readValue(req.getInputStream(), Recipe.class);
		
		recipeService.registerObject(recipe);
		
		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(recipe);
		writer.write(json);

	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		logger.log("User is Updating a Recipe (--DO_PUT()--)", LoggingLevel.TRACE);

		Recipe recipe  = objMapper.readValue(req.getInputStream(), Recipe.class);

		recipeService.updateObject(recipe);
		
		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(recipe);
		writer.write(json);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		logger.log("User is Deleting a Recipe (--DO_DELETE()--)", LoggingLevel.TRACE);
		Recipe recipe  = objMapper.readValue(req.getInputStream(), Recipe.class);

		recipeService.deleteObj(recipe);
		
		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(recipe);
		writer.write(json);
	}
	
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
	}
	
	
	
}
