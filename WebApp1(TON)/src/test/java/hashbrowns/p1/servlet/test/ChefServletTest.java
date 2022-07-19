package hashbrowns.p1.servlet.test;

import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import hashbrowns.p1.servlets.ChefServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChefServletTest {

	  @Spy private ChefServlet servlet; 
	  @Mock private ServletConfig servletConfig;
	  @Mock private HttpServletRequest request;
	  @Mock private HttpServletResponse response;
	  // might need to change this 
	  @Mock private ServletOutputStream outputStream;
	  
	  
	@Test
	@Disabled
	public void testSpecificGet() throws IOException {
		StringBuilder uriString = new StringBuilder();
		StringBuilder uriString1 = new StringBuilder("/2");
		Mockito.when(uriString.replace(0, request.getContextPath().length() + 1, "")).thenReturn(uriString1);
		
		Mockito.when(servlet.getServletConfig()).thenReturn(servletConfig);
	    Mockito.when(response.getOutputStream()).thenReturn(outputStream);
	   // servlet.doGet(request, response);

	}
	
	
	
	
}
