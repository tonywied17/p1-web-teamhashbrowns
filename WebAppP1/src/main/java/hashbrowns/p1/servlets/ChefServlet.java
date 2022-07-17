package hashbrowns.p1.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import hashbrowns.p1.data.ORM;
import hashbrowns.p1.data.ORMImpl;
import hashbrowns.p1.exceptions.RecipeNameAlreadyExists;
import hashbrowns.p1.exceptions.UsernameAlreadyExistsException;
import hashbrowns.p1.models.Chef;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChefServlet extends HttpServlet {

	private Chef chef = new Chef();
	private ORM orm = new ORMImpl();
	private ObjectMapper objMapper = new ObjectMapper();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		StringBuilder uriString = new StringBuilder(req.getRequestURI());
		
		//we have a slash	
		if(uriString.indexOf("/") != -1) {
			
			//remove slash
			uriString.replace(0, uriString.indexOf("/") + 1, "");
			
			//grab id from path
			String[] ids = uriString.toString().split(" ");
			
			for(String id : ids) {
				
				//create object and apply path number as its id
				Chef reqChef = new Chef();
				reqChef.setId(Integer.valueOf(id));
				//send that chef object with id applied to the orm
				Object chefId = orm.findById(reqChef);
				
				//write as json
				PrintWriter writer = resp.getWriter();
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(chefId);
				writer.write(json);
				
			}
			
		}else {
			
			//no path get all chefs
			List<Object> chefs = orm.getAll(chef);
			PrintWriter writer = resp.getWriter();
			
			//Write to response body
			//writer.write(objMapper.writeValueAsString(chefs));
			
			// write as json
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(chefs);
			writer.write(json);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		Chef chef  = objMapper.readValue(req.getInputStream(), Chef.class);
		
		try {
			orm.insertObject(chef);
		} catch (UsernameAlreadyExistsException | RecipeNameAlreadyExists e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(chef);
		writer.write(json);

	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		Chef chef  = objMapper.readValue(req.getInputStream(), Chef.class);

		orm.updateObject(chef);
		
		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(chef);
		writer.write(json);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		Chef chef  = objMapper.readValue(req.getInputStream(), Chef.class);

		orm.deleteObject(chef);
		
		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(chef);
		writer.write(json);
	}
	
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
	}
	
	
	
}
