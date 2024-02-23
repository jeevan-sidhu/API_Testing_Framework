package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User payload;
	public Logger logger;
	
	@BeforeTest
	public void setup() {
		faker = new Faker();
		payload = new User();
		payload.setId(faker.idNumber().hashCode());
		payload.setUsername(faker.name().username());
		payload.setFirstName(faker.name().firstName());
		payload.setLastName(faker.name().lastName());
		payload.setEmail(faker.internet().safeEmailAddress());
		payload.setPassword(faker.internet().password(5, 10));
		payload.setPhone(faker.phoneNumber().cellPhone());
		
		logger = LogManager.getLogger(getClass());
	}
	
	@Test(priority=1)
	public void testPostUser() {
		logger.info("************* Creating User *************");
		
		Response res = UserEndPoints.createUser(payload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		
		logger.info("************* User Created *************");
	}
	
	@Test(priority=2)
	public void testGetUser() {
		Response res = UserEndPoints.getUser(payload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
	}
	
	@Test(priority=3)
	public void testUpdateUser() {
		payload.setFirstName(faker.name().firstName());
		payload.setLastName(faker.name().lastName());
		payload.setEmail(faker.internet().safeEmailAddress());
		
		Response res = UserEndPoints.updateUser(payload, payload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		
		Response res2 = UserEndPoints.getUser(payload.getUsername());
		res2.then().log().all();
		Assert.assertEquals(res2.getStatusCode(), 200);
	}
	
	@Test(priority=4)
	public void testDeleteUser() {
		Response res = UserEndPoints.deleteUser(payload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
	}
	
	

}
