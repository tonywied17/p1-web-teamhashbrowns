package hashbrowns.p1.servlets;

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
import hashbrowns.p1.models.Player;
import hashbrowns.p1.models.Recipe;
import hashbrowns.p1.services.PlayerServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PlayerServlet extends HttpServlet {

	private Player player = new Player();
	private PlayerServiceImpl playerService = new PlayerServiceImpl();
	private ObjectMapper objMapper = new ObjectMapper();

	Logger logger = Logger.getLogger();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StringBuilder uriString = new StringBuilder(req.getRequestURI()); // /p1/hello/id
		uriString.replace(0, req.getContextPath().length() + 1, "");
		// if there is a slash
		if (uriString.indexOf("/") != -1) {
			logger.log("User is requsting a specific Player (--DO_GET()--)", LoggingLevel.TRACE);
			uriString.replace(0, uriString.indexOf("/") + 1, ""); // 6
			String path = uriString.toString();

			try {
				int id = Integer.parseInt(path);
				Player player = new Player();
				player.setId(id);

				Object returnPlayer = playerService.findByID(player);

				PrintWriter writer = resp.getWriter();
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(returnPlayer);
				writer.write(json);
			} catch (Exception e) {
				logger.log("User Entered an invalid Player ID", LoggingLevel.INFO);
				PrintWriter writer = resp.getWriter();
				writer.write("Not an id!");
			}
		} else {

			List<Object> pl = playerService.findAll(player);
			PrintWriter writer = resp.getWriter();
			logger.log("User is requsting all Players (--DO_GET()--)", LoggingLevel.TRACE);
			// write as json
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(pl);
			writer.write(json);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.log("User is Inserting a Player (--DO_POST()--)", LoggingLevel.TRACE);
		Player player = objMapper.readValue(req.getInputStream(), Player.class);

		playerService.registerObject(player);

		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(player);
		writer.write(json);

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		StringBuilder uriString = new StringBuilder(req.getRequestURI()); // /p1/hello/id
		uriString.replace(0, req.getContextPath().length() + 1, "");

		if (uriString.indexOf("/") != -1) {
			logger.log("User is updating a specific Player (--DO_PUT()--)", LoggingLevel.TRACE);
			uriString.replace(0, uriString.indexOf("/") + 1, ""); // 6
			
			String path = uriString.toString();
			objMapper.setSerializationInclusion(Include.NON_NULL);
			Player player = objMapper.readValue(req.getInputStream(), Player.class);

			int id = Integer.parseInt(path);

			player.setId(id);
			
			

				Object returnPlayer = playerService.updateObject(player);

				PrintWriter writer = resp.getWriter();
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(returnPlayer);
				writer.write(json);

			

		} else {
			logger.log("ID not supplied", LoggingLevel.INFO);
			PrintWriter writer = resp.getWriter();
			writer.write("Put requires an id!");
		}

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.log("User is Deleting a Player (--DO_DELETE()--)", LoggingLevel.TRACE);
		Player player = objMapper.readValue(req.getInputStream(), Player.class);

		playerService.deleteObj(player);

		PrintWriter writer = resp.getWriter();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(player);
		writer.write(json);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
