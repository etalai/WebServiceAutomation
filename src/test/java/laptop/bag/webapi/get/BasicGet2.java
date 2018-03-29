package laptop.bag.webapi.get;

import static io.restassured.RestAssured.when;

public class BasicGet2 {
	static String pingUrl="http://localhost:8085/laptop-bag/webapi/api/ping/Talai";
	public static void main(String[] args) {
		/**
		 * When i perform ET request to:
		 * http://localhost:8085/laptop-bag/webapi/api/ping/Talai
		 */
		when().get(pingUrl);
		/**
		 * it should return Hi! Talai
		 */
		String greeting=when().get(pingUrl).thenReturn().body().asString();
		System.out.println(greeting);
		
		int statusCode=when().get(pingUrl).thenReturn().statusCode();
		
		System.out.println("Status code: "+statusCode);
	}
}
