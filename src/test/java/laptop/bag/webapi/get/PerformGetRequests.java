package laptop.bag.webapi.get;

import static io.restassured.RestAssured.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.junit.Assert.*;

public class PerformGetRequests {
	/**
	 * When I perform GET request to pingh method
	 * Then I can see status code 
	 * And message should be Hi! name
	 */
	
	@Test
	public void positiveTest() throws Exception {
		URI pingUri=new URI("http://localhost:8085/laptop-bag/webapi/api/ping/Talai");
		Response response=when().get(pingUri);
		assertEquals(200, response.getStatusCode());
		assertEquals("Hi! Talai", response.getBody().asString());
	}
	
	/**
	 * When I perform GET request to ping method without an arg
	 * Then I can see status code as 404
	 */
	
	@Test
	public void negativeTest() throws URISyntaxException  {
		URI pingUri=new URI("http://localhost:8085/laptop-bag/webapi/api/ping/");
		Response response=when().get(pingUri);
		
		int statusCode=response.getStatusCode();
		assertEquals(404, statusCode);
		
		String body=response.getBody().asString();
		assertNotEquals("Hi! Talai", body);
	}
	
	/**
	 * Given Accept is JSON
	 * When I perform GET request to all api method
	 * Then status code should be 200
	 * And Body should contain Laptop details in JSON
	 */
	
	@Test
	public void testAllMethod() throws URISyntaxException {
		URI allUri=new URI("http://localhost:8085/laptop-bag/webapi/api/all");
		Response response=given().accept(ContentType.JSON)
						 .when().get(allUri);
		assertEquals(200, response.getStatusCode());
		
		String body=response.getBody().asString();
		System.out.println(body);
		/**
		 * 2nd way
		 */
		given().accept(ContentType.JSON)
		.when().get(allUri)
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType(ContentType.JSON);
		
	}
	
	/**
	 * Given Accept type is JSON
	 * When i perform GET request to find method with id
	 * Then Status code should be 200
	 * And ID should be 115 
	 * And Laptop Brand name is APPLE
	 * @throws URISyntaxException 
	 */
	
	@Test
	public void testFindWithID() throws URISyntaxException {
		URI findUrl=new URI("http://localhost:8085/laptop-bag/webapi/api/find/115");
		Response response=given().accept(ContentType.JSON)
						 .when().get(findUrl);
		
		given().accept(ContentType.JSON)
		.when().get(findUrl)
		.then().assertThat().statusCode(200)
//		.and().assertThat().body("Id", Matchers.equalTo(115));
//		.and().assertThat().body("Id", equalTo(115))
//		.and().assertThat().body("BrandName", Matchers.equalToIgnoringCase("apPle"));
//		.and().assertThat().body("BrandName", equalTo("APPLE"));
	
		.and().assertThat().body("Id", Matchers.is(115), "BrandName", equalToIgnoringCase("apPle"));
	}
	
	/**
	 * Scenario: Features list content
	 * Given accept type is JSON
	 * WHen I perform GET request with id 115
	 * Then status code should be 200
	 * And Features should be 4 items
	 * And the features should contain following:
	 * 				            "16GB RAM",
            					"450GB SSD",
            					"intel i7 CPU",
            					"15 inch screen"
	 */
	
	@Test
	public void testFeaturesList() throws URISyntaxException {
		URI findUrl=new URI("http://localhost:8085/laptop-bag/webapi/api/find/115");
		given().accept(ContentType.JSON)
		.when().get(findUrl)
		.then().assertThat().statusCode(200)
		.and().assertThat().body("Features.Feature", Matchers.hasSize(4))
		.and().assertThat().body("Features.Feature", Matchers.hasItems("16GB RAM",
															            "450GB SSD",
															            "intel i7 CPU",
															            "15 inch screen"));
	}
	
	@Test
	public void testUsingJsonPath() throws URISyntaxException {
		URI findUrl=new URI("http://localhost:8085/laptop-bag/webapi/api/find/115");
		String body=given().accept(ContentType.JSON)
		.when().get(findUrl).thenReturn().body().asString();
		
		JsonPath json=new JsonPath(body); //works as key-value
		System.out.println(json.getInt("Id")); 
		assertEquals(115, json.getInt("Id"));
		
		System.out.println(json.get("BrandName"));
		assertEquals(json.getString("BrandName"), "APPLE");
		
		System.out.println(json.getString("Features.Feature"));
		
		System.out.println(json.getString("LaptopName"));
		assertNotEquals("someName", json.getString("laptopName"));
		
		List<String> features=json.getList("Features.Feature");
		System.out.println("list item: "+features.get(2));
		System.out.println("====================\n");
		System.out.println(features.toString());
		
		List<String> expFeatures=Arrays.asList("16GB RAM",
											   "450GB SSD",
											   "intel i7 CPU",
											   "15 inch screen");
//		i don't care about the order
		assertTrue(expFeatures.containsAll(features));
//		i care about the order
		assertEquals(expFeatures, features);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
