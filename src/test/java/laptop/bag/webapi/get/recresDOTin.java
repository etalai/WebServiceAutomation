package laptop.bag.webapi.get;

import static org.junit.Assert.*;
import java.util.*;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.omg.CORBA.RepositoryIdHelper;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static laptop.bag.webapi.get.RestHelper.RestHelper.*;

import laptop.bag.webapi.get.RestHelper.RestHelper;
import laptop.bag.webapi.get.RestHelper.RestHelper.Methods;

import static io.restassured.RestAssured.*;

public class recresDOTin {

	@Test
	public void getValuesFromResource() throws Exception {
		URI uri = new URI("https://reqres.in/api/users?page=3");
		System.out.println(uri.toString());

		String body = given().accept(ContentType.JSON).when().get(uri).thenReturn().body().asString();

		JsonPath json = new JsonPath(body);

		System.out.println(json.getString("page"));
		System.out.println(json.get("per_page"));
		System.out.println(json.getString("total"));
		System.out.println(json.get("total_pages"));
	}

	@Test
	public void JsonPathPractice() throws URISyntaxException {
		String ping = buildURI(Methods.PING);
		System.out.println(when().get(ping).asString());

		/**
		 * Given accept type is JSON When i perform GET request using ALL method Then i
		 * can see all IDs
		 */

		String json = given().accept(ContentType.JSON).when().get(RestHelper.buildURI(Methods.ALL)).andReturn().body()
				.asString();

		// parsing Json string into JsonPath object
		JsonPath jsonBody = new JsonPath(json);

		// for(Object id:jsonBody.getList("Id")) {
		// System.out.println(id);
		// }

		java.util.List<Integer> ids = jsonBody.getList("Id");
		for (int id : ids) {
			System.out.print(id + " ");
		}

		java.util.List<String> pcNames = jsonBody.get("LaptopName");
		System.out.println("\n" + pcNames);
		System.out.println(jsonBody.get("BrandName"));

		List<String> features = jsonBody.get("Features.Feature");
		System.out.println(features);

		// Get all laptopnames for laptops whose ID is less than 108
		List<String> laptopNames = jsonBody.getList("findAll{it.Id<108}.LaptopName");
		System.out.println(laptopNames);

		// Get all IDs for laptops whose brandname is ASUS
		List<Integer> id = jsonBody.getList("findAll{it.BrandName=='ASUS'}.Id");
		System.out.println("ID: " + id);

		// Get all IDs for laptops whose brandname is NOT ASUS
		List<Integer> idNOT = jsonBody.getList("findAll{it.BrandName!='ASUS'}.Id");
		System.out.println("ID: " + idNOT);

	}

	@Test
	public void XmlPathSamples() throws URISyntaxException {
		/**
		 * Given Accept type is xml When i perform GET request to get all laptops Then
		 * status is 200 And I can navigate through XML using XmlPath
		 */
		URI allUri = new URI("http://localhost:8085/laptop-bag/webapi/api/all");
		Response response = given().accept(ContentType.XML)
							.when().get(allUri);

		// assertEquals(200, response.getStatusCode());
		XmlPath xml = new XmlPath(response.getBody().asString());

		 List<String> brands=
				 xml.getList("LaptopDetails.Laptop.BrandName");
		 System.out.println(brands);
	}

}
