package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class VideoGameAPITest {
	@Test(priority=1)
	public void test_getAllVideoGames() {
		given()
		.when()
		.get("http://localhost:8080/app/videogames")
		.then()
		.statusCode(200);
		
	}
	
	/*{
		  "id": 0,
		  "name": "string",
		  "releaseDate": "2024-02-01T20:11:15.731Z",
		  "reviewScore": 0,
		  "category": "string",
		  "rating": "string"
		}*/
	@Test(priority=2)
	public void test_postNewVideoGame() {
		HashMap<String,String> data = new HashMap<String, String>();
		data.put("id", "11");
		data.put("name","Kiara");
		data.put("releaseDate", "2024-02-01T20:11:15.731Z");
		data.put("reviewScore", "6");
		data.put("category","Adventure");
		data.put( "rating","Universal");
		Response res =
		given()
		.contentType("application/json")
		.body(data)
		.when()
		.post("http://localhost:8080/app/videogames")
		

		.then()
		.statusCode(200)
		.log()
		.body()
		.extract().response();
		
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);
		
	}
	@Test(priority = 3)
	public void test_getNewVideoGame() {
		given()
		.when()
		.get("http://localhost:8080/app/videogames/11")

		.then()
		.statusCode(200)
		.log().body()
		.body("videoGame.id",equalTo("11"))
		.body("videoGame.name", equalTo("Kiara"))
		.body("videoGame.reviewScore",equalTo("6"));
		
	}
	@Test(priority =4)
	public void test_updateNewVideoGame() {
		HashMap<String,String> data = new HashMap<String, String>();
		data.put("id", "11");
		data.put("name","Pokemon");
		data.put("releaseDate", "2024-02-01T20:11:15.731Z");
		data.put("reviewScore", "4");
		data.put("category","Adventure");
		data.put( "rating","Universal");
		given()
		.contentType("application/json")
		.body(data)
		.when()
		.put("http://localhost:8080/app/videogames/11")

		.then()
		.statusCode(200)
		.log().body()
		.body("videoGame.name", equalTo("Pokemon"))
		.body("videoGame.reviewScore",equalTo("4"));
	}
	@Test
	public void test_deleteNewVideoGame() {
		Response res =given()
		.when()
		.delete("http://localhost:8080/app/videogames/11")

		.then()
		.statusCode(200)
		.log().body()
		.extract().response(); //inorder to extract response
		String jsonString  = res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);
		
	}
	

}
