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
import com.qa.data.Posts;

public class PostsServiceTest extends TestBase {

	@BeforeTest
	public void setUp() throws ClientProtocolException, IOException {

		apiUrl = configprop.getProperty("URL");
		postsServiceUrl = configprop.getProperty("postsServiceUrl");

		postsCompleteUrl = apiUrl + postsServiceUrl;
		Add_Log.info("Get Url link formed is --->" + postsCompleteUrl);

		postsIdToUpdate = configprop.getProperty("postsIdToUpdate");

	}

	@Test(priority = 1)
	public void posts_GETAPITest() throws ClientProtocolException, IOException {

		closebaleHttpResponse = restClient.get(postsCompleteUrl);

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

		expectedPostObject = new Posts("888979", "22", "Test Title 12", "This is Test Body 8891"); // expected Posts
																									// object

		// object to json file:
		mapper.writeValue(new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\data\\posts.json"),
				expectedPostObject);

		// java object to json in String:
		jsonInString = mapper.writeValueAsString(expectedPostObject);
		Add_Log.info("Json in POST String value as -> " + jsonInString);

		// call the API
		closebaleHttpResponse = restClient.post(postsCompleteUrl, jsonInString, headerMap);

		// validate response from API:
		// 1. status code:
		statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201);

		// 2. JsonString:
		responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");

		responseJson = new JSONObject(responseString);
		Add_Log.info("The response from POST API is:" + responseJson);

		// json to java object:
		actualPostObject = mapper.readValue(responseString, Posts.class); // actual Posts2 object
		System.out.println(actualPostObject);

		Assert.assertTrue(expectedPostObject.getId().equals(actualPostObject.getId()));
		Assert.assertTrue(expectedPostObject.getUserId().equals(actualPostObject.getUserId()));
		Assert.assertTrue(expectedPostObject.getTitle().equals(actualPostObject.getTitle()));
		Assert.assertTrue(expectedPostObject.getBody().equals(actualPostObject.getBody()));

		Add_Log.info("ID value updated from POST API is -> " + actualPostObject.getUserId());
		Add_Log.info("Title Value updated from POST API is -> " + actualPostObject.getTitle());

	}

	@Test(priority = 3)
	public void posts_PUTAPITest() throws JsonGenerationException, JsonMappingException, IOException {

		expectedPostObject = new Posts(postsIdToUpdate, "276", "Test Title Latest 44", "This is Test Body 888944"); // expected
																													// Posts
																													// object

		// object to json file:
		mapper.writeValue(new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\data\\posts.json"),
				expectedPostObject);

		// java object to json in String:
		jsonInString = mapper.writeValueAsString(expectedPostObject);
		Add_Log.info("Json in PUT String value as -> " + jsonInString);

		putCompleteUrl = postsCompleteUrl + "/" + postsIdToUpdate;
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
		actualPostObject = mapper.readValue(responseString, Posts.class); // actual Posts2 object
		System.out.println(actualPostObject);

		Assert.assertTrue(expectedPostObject.getId().equals(actualPostObject.getId()));
		Assert.assertTrue(expectedPostObject.getUserId().equals(actualPostObject.getUserId()));
		Assert.assertTrue(expectedPostObject.getTitle().equals(actualPostObject.getTitle()));
		Assert.assertTrue(expectedPostObject.getBody().equals(actualPostObject.getBody()));

		Add_Log.info("ID value updated from PUT API is -> " + actualPostObject.getUserId());
		Add_Log.info("Title Value updated from PUT API is -> " + actualPostObject.getTitle());
	}

}
