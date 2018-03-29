package laptop.bag.webapi.post;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import laptop.bag.webapi.get.RestHelper.RestHelper;
import laptop.bag.webapi.get.RestHelper.RestHelper.Methods;

import static io.restassured.RestAssured.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostActions {
	String id = (int) (1000 * (Math.random())) + "";
	String jsonBody = "{" + 
			"\"BrandName\": \"Apple\"," +
			"\"Features\": {" + 
				"\"Feature\": [\"8GB RAM\","+
			    			"\"1TB Hard Drive\"]" + 
				"}," + 
			    			"\"Id\": " + id + "," + 
				"\"LaptopName\": \"MacBook Pro\"" + 
			    			"}";

	/**
	 * Given Accept and ConTent-type is Json When is perform post Action Then status
	 * code is 200 And Id is same as my post
	 * @throws URISyntaxException 
	 */

	@Test
	public void addALaptop() throws URISyntaxException {
		System.out.println("ID: "+id);
		
		URI addUri = new URI(RestHelper.buildURI(Methods.ADD));
		
		given().contentType(ContentType.JSON)
		.with().accept(ContentType.JSON)
		.and().body(jsonBody)
		.when().post(addUri)
		.then().assertThat().statusCode(200)
		.and().assertThat().body("Id", is(Integer.parseInt(id)));
	}
	
	/**
	 * With Headers: Content-Type: application/json
	 * 				 Accept:	   application/json
	 * And Body as Json
	 * When I perform post action
	 * Then Features must match
	 * @throws URISyntaxException 
	 */
	
	@Test
	public void PostWithHeaders() throws URISyntaxException {
		URI addUri=new URI(RestHelper.buildURI(Methods.ADD));
		
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		
		with().headers(headers).and().body(jsonBody)
		.when().post(addUri)
		.then().assertThat()
		.body("Features.Feature", hasItems("8GB RAM",
										   "1TB Hard Drive"));
		
		JsonPath jsonPath=new JsonPath(with().headers(headers)
										.and().body(jsonBody)
										.when().post(addUri)
										.asString());
		
		List<String> expectedFeatures=jsonPath.getList("Features.Feature");
		assertTrue(jsonPath.getList("Features.Feature").containsAll(expectedFeatures));
	}
	
	public static Map<String, String> headers(){
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		return headers;
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
