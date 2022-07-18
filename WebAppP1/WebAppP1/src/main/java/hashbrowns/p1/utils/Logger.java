package hashbrowns.p1.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;


public class Logger {

private static Logger logger;
	
	private Logger() {
		
	}
	
	public static synchronized Logger getLogger() {
		if(logger == null) {
			logger = new Logger();
		}
		
		return logger;
	}
	
	
	public void log(String message, LoggingLevel level) {
		
		message = LocalDateTime.now().toString() + "- - - " + level + ": "+ message; 		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("src/main./resources/log.log", true))){
			writer.write(message);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
