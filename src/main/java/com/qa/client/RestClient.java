package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {

	// 1. GET Method :
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); // http get request
		CloseableHttpResponse closebaleHttpResponse = httpClient.execute(httpget); // hit the GET URL

		return closebaleHttpResponse;

	}

	// 2. POST Method:
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url); // http post request
		httppost.setEntity(new StringEntity(entityString)); // for payload

		// for headers:
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httppost.addHeader(entry.getKey(), entry.getValue());
		}

		CloseableHttpResponse closebaleHttpResponse = httpClient.execute(httppost);
		return closebaleHttpResponse;

	}

	// 3. PUT Method:
	public CloseableHttpResponse put(String url, String entityString, HashMap<String, String> headerMap)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpput = new HttpPut(url); // http put request
		httpput.setEntity(new StringEntity(entityString)); // for payload

		// for headers:
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpput.addHeader(entry.getKey(), entry.getValue());
		}

		CloseableHttpResponse closebaleHttpResponse = httpClient.execute(httpput);
		return closebaleHttpResponse;

	}

}
