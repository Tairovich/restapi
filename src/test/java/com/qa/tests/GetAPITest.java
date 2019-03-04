package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;

public class GetAPITest extends TestBase {

	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHtppResponse;
	
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		url = serviceUrl + apiUrl;

	}

	@Test
	public void getApiTest() throws ClientProtocolException, IOException {

		restClient = new RestClient();
		closeableHtppResponse = restClient.get(url);
		//1. Status Code:
		int statusCode = closeableHtppResponse.getStatusLine().getStatusCode(); // returns status code of the get method
		System.out.println("Status Code is :: " + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200,"Status Code Mismatch");
		
		// 2. Response in Json:
		String responseString = EntityUtils.toString(closeableHtppResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response Json from API:: " + responseJson);
		
		
		// 3. All Headers:
		Header[] headersArray = closeableHtppResponse.getAllHeaders();
		HashMap<String, String> headerMap = new HashMap<String, String>();

		for (Header header : headersArray){
			headerMap.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array:: " + headerMap);
	}

}
