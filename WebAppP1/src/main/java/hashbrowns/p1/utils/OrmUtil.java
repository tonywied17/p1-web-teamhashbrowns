package hashbrowns.p1.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OrmUtil {
	 private static ObjectMapper objMapper;
	 
	 static {
	        objMapper = null;
	    }

	    private OrmUtil() {
	    }

	    // factory: creates Connection objects and returns them
	    public static ObjectMapper getObjectMapper() {
	        if ( objMapper == null ) {
	            objMapper = new ObjectMapper();
	        }
	        
	        return objMapper;
	    }
}
