package laptop.bag.webapi.get.RestHelper;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

public class RestHelper {
	
	static {
		baseURI = "http://localhost";
		port = 8085;
		basePath = "/laptop-bag/webapi/api/";
	}
	
	public static Map<String, String> getHeaders(HeaderType type) throws Exception{
		Map<String, String> headers=new HashMap<String, String>();
		
		switch (type) {
		case JSON:
			headers.put("Content-Type", "application/json");
			headers.put("Accept", "application/json");
			break;
		case XML:
			headers.put("Content-Type", "application/xml");
			headers.put("Accept", "application/xml");
			break;
		default:
			throw new Exception("Invalid type");
		}
		return headers;
		
	}
	
	public static enum HeaderType{
		JSON, XML
	}
	
	public static enum Methods {
		PING, ALL, FIND, ADD
	}
	
	public static String buildURI(Methods method) {

		String apiMethod = null;

		switch (method) {
		case PING:
			apiMethod = "ping/World";
			break;
		case ALL:
			apiMethod = "all";
			break;
		case FIND:
			apiMethod = "find/";
			break;
		case ADD:
			apiMethod = "add";
			break;
		default:
			throw new RuntimeException("Invalid API method");
		}
		return apiMethod;
	}

	
}
