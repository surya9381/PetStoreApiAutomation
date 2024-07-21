package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;



import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String userId, String userName, String fname, String lname, String email, String pwd, String ph)
	{
		User userPayload=new User();
		userPayload.setId(Integer.parseInt(userId));
		userPayload.setUsername(userName);
		userPayload.setFirstname(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(email);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		 Response response = UserEndpoints.createUser(userPayload);
		 Assert.assertEquals(response.getStatusCode(),200);
	}
	
	@Test(priority = 2,dataProvider = "userNames",dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(String userName)
	{
		Response response = UserEndpoints.deleteUrl(userName);
		Assert.assertEquals(response.getStatusCode(),200);
	}
}
