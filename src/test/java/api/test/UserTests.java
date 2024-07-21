package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User userpayload;
	Logger logger;
	
	@BeforeClass
	public void setup()
	{
		faker=new Faker();
		userpayload=new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstname(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5, 10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger=LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("********** creating user **********");
		Response response = UserEndpoints.createUser(userpayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("************ User created ***********");
	}
	
	@Test(priority=2)
	public void testGetUserByUsername()
	{
		logger.info("************ reading user ***********");
		Response response = UserEndpoints.getUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("************ user details displayed ***********");
	}
	
	@Test(priority=3)
	public void testUpdateUserByUsername()
	{
		logger.info("************ updating user ***********");
		userpayload.setFirstname(faker.name().firstName());
        userpayload.setLastName(faker.name().lastName());
        userpayload.setEmail(faker.internet().emailAddress());
        
		Response response = UserEndpoints.updateUser(this.userpayload.getUsername(), userpayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("************ User updated ***********");
	   // checking data after update
		
		Response responseAfterUpdate=UserEndpoints.getUser(this.userpayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
	}
	
	@Test(priority=4)
	public void testDeleteUserByUsername()
	{
		logger.info("************ deleting user ***********");
		Response response=UserEndpoints.deleteUrl(this.userpayload.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("************ User deleted ***********");
	}
}
