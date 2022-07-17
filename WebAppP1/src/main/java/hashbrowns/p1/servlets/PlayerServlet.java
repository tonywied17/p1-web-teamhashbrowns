package hashbrowns.p1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import hashbrowns.p1.data.ORM;
import hashbrowns.p1.data.ORMImpl;
import hashbrowns.p1.exceptions.RecipeNameAlreadyExists;
import hashbrowns.p1.exceptions.UsernameAlreadyExistsException;
import hashbrowns.p1.models.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PlayerServlet extends HttpServlet {

	private Player player = new Player();
	private ORM orm = new ORMImpl();
	private ObjectMapper objMapper = new ObjectMapper();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		//get Chefs
		List<Object> players = orm.getAll(player);
		PrintWriter writer = resp.getWriter();
		//Write to response body
		//writer.write(objMapper.writeValueAsString(chefs));
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(players);
		writer.write(json);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		Player player  = objMapper.readValue(req.getInputStream(), Player.class);
		
		try {
			orm.insertObject(player);
		} catch (UsernameAlreadyExistsException | RecipeNameAlreadyExists e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(player);
		writer.write(json);

	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		Player player  = objMapper.readValue(req.getInputStream(), Player.class);

		orm.updateObject(player);
		
		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(player);
		writer.write(json);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		Player player  = objMapper.readValue(req.getInputStream(), Player.class);

		orm.deleteObject(player);
		
		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(player);
		writer.write(json);
	}
	
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
	}
	
	
	
}
