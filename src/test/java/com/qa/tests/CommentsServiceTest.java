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
import com.qa.data.Comments;

public class CommentsServiceTest extends TestBase {

	@BeforeTest
	public void setUp() throws ClientProtocolException, IOException {

		apiUrl = configprop.getProperty("URL");
		commentsServiceUrl = configprop.getProperty("commentsServiceUrl");

		comentsCompleteUrl = apiUrl + commentsServiceUrl;
		Add_Log.info("Comments Url link formed is --->" + comentsCompleteUrl);

		commentsIdToUpdate = configprop.getProperty("commentsIdToUpdate");

	}

	@Test(priority = 1)
	public void comments_GETAPITest() throws ClientProtocolException, IOException {

		closebaleHttpResponse = restClient.get(comentsCompleteUrl);

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
	public void comments_POSTAPITest() throws JsonGenerationException, JsonMappingException, IOException {

		expectedCommentsObject = new Comments("932367", "823674344", "Test NAme22", "test@mail.com",
				"This is Test Body 8891"); // expected Comments object

		// object to json file:
		mapper.writeValue(new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\data\\comments.json"),
				expectedCommentsObject);

		// java object to json in String:
		jsonInString = mapper.writeValueAsString(expectedCommentsObject);
		Add_Log.info("Json in POST String value as -> " + jsonInString);

		closebaleHttpResponse = restClient.post(comentsCompleteUrl, jsonInString, headerMap); // call the API

		// validate response from API:
		// 1. status code:
		statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201);

		// 2. JsonString:
		responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");

		responseJson = new JSONObject(responseString);
		Add_Log.info("The response from POST API is:" + responseJson);

		// json to java object:
		actualCommentsObject = mapper.readValue(responseString, Comments.class); // actual Comments2 object
		System.out.println(actualCommentsObject);

		Assert.assertTrue(expectedCommentsObject.getPostId().equals(actualCommentsObject.getPostId()));
		Assert.assertTrue(expectedCommentsObject.getId().equals(actualCommentsObject.getId()));
		Assert.assertTrue(expectedCommentsObject.getName().equals(actualCommentsObject.getName()));
		Assert.assertTrue(expectedCommentsObject.getEmail().equals(actualCommentsObject.getEmail()));
		Assert.assertTrue(expectedCommentsObject.getBody().equals(actualCommentsObject.getBody()));

		Add_Log.info("ID value updated from POST API is -> " + actualCommentsObject.getId());
		Add_Log.info("Post ID Value updated from POST API is -> " + actualCommentsObject.getPostId());

	}

	@Test(priority = 3)
	public void comments_PUTAPITest() throws JsonGenerationException, JsonMappingException, IOException {

		expectedCommentsObject = new Comments("29", commentsIdToUpdate, "Test Title Latest 77", "testd@gmail.com2",
				"This is Test Body 88844"); // expected Comments object

		// object to json file:
		mapper.writeValue(new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\data\\comments.json"),
				expectedCommentsObject);

		// java object to json in String:
		jsonInString = mapper.writeValueAsString(expectedCommentsObject);
		Add_Log.info("Json in PUT String value as -> " + jsonInString);

		putCompleteUrl = comentsCompleteUrl + "/" + commentsIdToUpdate;
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
		actualCommentsObject = mapper.readValue(responseString, Comments.class); // actual Comments2 object
		System.out.println(actualCommentsObject);

		Assert.assertTrue(expectedCommentsObject.getPostId().equals(actualCommentsObject.getPostId()));
		Assert.assertTrue(expectedCommentsObject.getId().equals(actualCommentsObject.getId()));
		Assert.assertTrue(expectedCommentsObject.getName().equals(actualCommentsObject.getName()));
		Assert.assertTrue(expectedCommentsObject.getEmail().equals(actualCommentsObject.getEmail()));
		Assert.assertTrue(expectedCommentsObject.getBody().equals(actualCommentsObject.getBody()));

		Add_Log.info("ID value updated from PUT API is -> " + actualCommentsObject.getId());
		Add_Log.info("Post ID Value updated from PUT API is -> " + actualCommentsObject.getPostId());
	}

}
