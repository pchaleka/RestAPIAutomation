package com.qa.tests;

import java.io.File;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.qa.base.TestBase;
import com.qa.data.Users;

public class UsersServiceTest extends TestBase {

	@BeforeTest
	public void setUp() throws ClientProtocolException, IOException {

		apiUrl = configprop.getProperty("URL");
		usersServiceUrl = configprop.getProperty("usersServiceUrl");

		usersCompleteUrl = apiUrl + usersServiceUrl;
		Add_Log.info("USers Url link formed is --->" + usersCompleteUrl);

		usersIdToUpdate = configprop.getProperty("usersIdToUpdate");

	}

	@Test(priority = 1)
	public void posts_GETAPITest() throws ClientProtocolException, IOException {

		closebaleHttpResponse = restClient.get(usersCompleteUrl);

		// a. Status Code:
		statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		Add_Log.info("Status Code from GET API is -->" + statusCode);

		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		// b. Json String:
		responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");

		// Printing Json Response elements One by One.
		jsonArray = new JSONArray(responseString.toString());

		for (int i = 0; i < jsonArray.length(); i++) {

			System.out.println(" Json Array Element is - " + jsonArray.get(i));

		}

	}

	@Test(priority = 2)
	public void posts_POSTAPITest() throws JsonGenerationException, JsonMappingException, IOException {

		expectedUsersObject = new Users("888999475", "testd@m.com", "546568865", "Test USername53", "www.dtrr.com",
				"India"); // expected Users object

		// object to json file:
		mapper.writeValue(new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\data\\users.json"),
				expectedUsersObject);

		// java object to json in String:
		jsonInString = mapper.writeValueAsString(expectedUsersObject);
		Add_Log.info("Json in POST String value as -> " + jsonInString);

		closebaleHttpResponse = restClient.post(usersCompleteUrl, jsonInString, headerMap); // call the API

		// validate response from API:
		// 1. status code:
		statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201);

		// 2. JsonString:
		responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");

		responseJson = new JSONObject(responseString);
		Add_Log.info("The response from POST API is:" + responseJson);

		// json to java object:
		actualUsersObject = mapper.readValue(responseString, Users.class); // actual Users2 object
		System.out.println(actualUsersObject);

		Assert.assertTrue(expectedUsersObject.getId().equals(actualUsersObject.getId()));
		Assert.assertTrue(expectedUsersObject.getEmail().equals(actualUsersObject.getEmail()));
		Assert.assertTrue(expectedUsersObject.getPhone().equals(actualUsersObject.getPhone()));
		Assert.assertTrue(expectedUsersObject.getUsername().equals(actualUsersObject.getUsername()));
		Assert.assertTrue(expectedUsersObject.getWebsite().equals(actualUsersObject.getWebsite()));
		Assert.assertTrue(expectedUsersObject.getAddress().equals(actualUsersObject.getAddress()));

		Add_Log.info("ID value updated from POST API is -> " + actualUsersObject.getId());
		Add_Log.info("Username Value updated from POST API is -> " + actualUsersObject.getUsername());

	}

	@Test(priority = 3)
	public void posts_PUTAPITest() throws JsonGenerationException, JsonMappingException, IOException {

		expectedUsersObject = new Users(usersIdToUpdate, "testd@m.com", "99999999", "Test USername53", "www.dtrr.com",
				"India"); // expected Users object

		// object to json file:
		mapper.writeValue(new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\data\\users.json"),
				expectedUsersObject);

		// java object to json in String:
		jsonInString = mapper.writeValueAsString(expectedUsersObject);
		Add_Log.info("Json in PUT String value as -> " + jsonInString);

		putCompleteUrl = usersCompleteUrl + "/" + usersIdToUpdate;
		Add_Log.info("PUT URL formd is -> " + putCompleteUrl);

		closebaleHttpResponse = restClient.put(putCompleteUrl, jsonInString, headerMap); // call the API

		// validate response from API:
		// 1. status code:
		statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);

		// 2. JsonString:
		responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");

		responseJson = new JSONObject(responseString);
		Add_Log.info("The response from PUT API is:" + responseJson);

		// json to java object:
		actualUsersObject = mapper.readValue(responseString, Users.class); // actual Users2 object
		System.out.println(actualUsersObject);

		Assert.assertTrue(expectedUsersObject.getId().equals(actualUsersObject.getId()));
		Assert.assertTrue(expectedUsersObject.getEmail().equals(actualUsersObject.getEmail()));
		Assert.assertTrue(expectedUsersObject.getPhone().equals(actualUsersObject.getPhone()));

		Assert.assertTrue(expectedUsersObject.getUsername().equals(actualUsersObject.getUsername()));
		Assert.assertTrue(expectedUsersObject.getWebsite().equals(actualUsersObject.getWebsite()));
		Assert.assertTrue(expectedUsersObject.getAddress().equals(actualUsersObject.getAddress()));

		Add_Log.info("ID value updated from PUT API is -> " + actualUsersObject.getId());
		Add_Log.info("Website Value updated from PUT API is -> " + actualUsersObject.getWebsite());
	}

}
