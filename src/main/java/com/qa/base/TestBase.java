package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.client.RestClient;
import com.qa.data.Comments;
import com.qa.data.Posts;
import com.qa.data.Users;

public class TestBase {

	public static Logger Add_Log = null;
	public static int RESPONSE_STATUS_CODE_200 = 200;
	public static int RESPONSE_STATUS_CODE_500 = 500;
	public static int RESPONSE_STATUS_CODE_400 = 400;
	public static int RESPONSE_STATUS_CODE_401 = 401;
	public static int RESPONSE_STATUS_CODE_201 = 201;
	public static int statusCode;

	public static JSONArray jsonArray;
	public static Properties configprop;

	// Variables used for PostsServiceTest
	public static String postsServiceUrl;
	public static String commentsServiceUrl;
	public static String usersServiceUrl;

	public static String apiUrl;
	public static String postsCompleteUrl;
	public static RestClient restClient;
	public static CloseableHttpResponse closebaleHttpResponse;

	public static String jsonInString;
	public static String postsIdToUpdate;
	public static String commentsIdToUpdate;
	public static String usersIdToUpdate;

	public static String comentsCompleteUrl;
	public static String usersCompleteUrl;
	public static String putCompleteUrl;

	public static HashMap<String, String> headerMap;
	public static ObjectMapper mapper;
	public static JSONObject responseJson;
	public static String responseString;

	public static Posts expectedPostObject;
	public static Posts actualPostObject;

	public static Comments expectedCommentsObject;
	public static Comments actualCommentsObject;

	public static Users expectedUsersObject;
	public static Users actualUsersObject;

	public TestBase() {
		try {

			// Initialization Part
			Add_Log = Logger.getLogger("rootLogger");
			mapper = new ObjectMapper();
			restClient = new RestClient();

			headerMap = new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");

			configprop = new Properties();

			FileInputStream fis1 = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/com/qa/config/config.properties");
			configprop.load(fis1);
			Add_Log.info("Config.properties file loaded successfully.");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
