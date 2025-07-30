package in.nic.OauthClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import com.google.common.base.Strings;

@SuppressWarnings("deprecation")

// Handle all responses on redirect URI
public class GetAccessTokenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(GetAccessTokenServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("GetAccessTokenServlet doGet Running");

		String at_request = "";
		String dType = "";

		try {
			at_request = request.getParameter("revokeToken").toString();
		} catch (Exception e) {
			// NO ACTION REQUIRED
		}

		try {
			dType = request.getParameter("dType").toString();
		} catch (Exception e) {
			// NO ACTION REQUIRED
		}

		if (!Strings.isNullOrEmpty(at_request)) {
			System.out.println("Revoke Request");

			revokeAccessToken(request, response, at_request, dType);
		} else {
			System.out.println("Access Token Request");
			getAccessToken(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("GetAccessTokenServlet doPost Running");

		getAccessToken(request, response);
	}

	public void getAccessToken(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException {

		System.out.println("getAccessToken called");
		ResourceBundle rbOauth = ResourceBundle.getBundle("oauth");

		String dType = request.getParameter("state");

		String client_id = rbOauth.getString("client_id" + dType);
		String redirect_uri = rbOauth.getString("redirect_uri");
		String code = request.getParameter("code");
		String client_secret = rbOauth.getString("client_secret" + dType);
		String code_verifier = rbOauth.getString("code_verifier");
		String grant_type = rbOauth.getString("grant_type");

		System.out.println("getAccessToken called");

		String accessTokenURL = "client_id=" + client_id + "&client_secret=" + client_secret + "&redirect_uri="
				+ redirect_uri + "&code=" + code + "&grant_type=" + grant_type + "&code_verifier=" + code_verifier;

		System.out.println("Access Token Client URL: " + accessTokenURL);

		// Creating BODY for Token Call -- Using Google token endpoint
		JSONObject json = new JSONObject();
		json.put("client_id", client_id);
		json.put("redirect_uri", redirect_uri);
		json.put("code", code);
		json.put("client_secret", client_secret);
		json.put("grant_type", grant_type);
		json.put("code_verifier", code_verifier);

		System.out.println("Response : " + json.toString());

		// add request parameter, form parameters
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("client_id", client_id));
		urlParameters.add(new BasicNameValuePair("client_secret", client_secret));
		urlParameters.add(new BasicNameValuePair("code", code));
		urlParameters.add(new BasicNameValuePair("redirect_uri", redirect_uri));
		urlParameters.add(new BasicNameValuePair("code_verifier", code_verifier));
		urlParameters.add(new BasicNameValuePair("grant_type", grant_type));

		HttpPost post = new HttpPost(rbOauth.getString("OAUTH_TOKEN_API" + dType));
		post.addHeader("Accept", "application/json");

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		Object tokenJson = "";

		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				CloseableHttpResponse postResponse = httpClient.execute(post)) {

			// Receive response from Google
			tokenJson = JSON.parse(EntityUtils.toString(postResponse.getEntity()));

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Consent Denied");

			return;
		}

		System.out.println("Response from Parichay : " + tokenJson.toString());

		// SUCCESS CASE
		getUserdetails(request, response, tokenJson.toString(), dType);
	}

	public void getUserdetails(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String tokenJson, String dType) throws IOException {

		System.out.println("getUserdetails called");
		ResourceBundle rbOauth = ResourceBundle.getBundle("oauth");

		JSONObject json = new JSONObject(tokenJson);

		// Use access_token received from Google
		String access_token = json.getString("access_token");

		Map<String, String> childMap = new HashMap<>();

		System.out.println("Access_token : " + access_token);
		// Sending access token to Google for user details
		HttpGet get = new HttpGet(ResourceBundle.getBundle("oauth").getString("OAUTH_TOKEN_URL" + dType));

		get.setHeader("Authorization", "Bearer " + access_token);

		Object userJsonObj = "";

		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				CloseableHttpResponse postResponse = httpClient.execute(get);) {

			// Received response from Google.
			userJsonObj = JSON.parse(EntityUtils.toString(postResponse.getEntity()));

			// FETCH RESPECTIVE COOKIES FROM BROWSER COOKIE STORE

			System.out.println("User details JSON Object: " + userJsonObj);
			DBObject userJson = (DBObject) userJsonObj;
			System.out.println("User details JSON: " + userJson);

			// Sending response back to front end
			System.out.println("Latest Update");
			String userName = "";
			try {
				userName = userJson.get("given_name") + " " + userJson.get("family_name");

			} catch (Exception e) {

				userName = userJson.get("family_name").toString();

			}

			try {
				if (userName.isEmpty() || userName.contains("null") || userName == null) {
					userName = userJson.get("name").toString();
				}
			} catch (Exception e) {
				userName = userJson.get("first_name") + " " + userJson.get("last_name");
			}

			String eCode = "";
			try {
				eCode = userJson.get("id").toString();
			} catch (Exception e) {
				eCode = "";
			}

			String Mobile = "";
			try {
				Mobile = userJson.get("MobileNo").toString();
			} catch (Exception e) {
				Mobile = "";
			}

			String primaryemail = "";
			try {
				primaryemail = userJson.get("email").toString();
			} catch (Exception e) {
				primaryemail = "";
			}

			String gender = "";
			try {
				gender = userJson.get("Gender").toString();
			} catch (Exception e) {
				gender = "";
			}

			String ServiceName = "";
			System.out.println("dType : " + ServiceName);

			switch (dType) {

			case "0":
				ServiceName = "Google";
				break;
			case "1":
				ServiceName = "GitHub";
				break;
			case "2":
				ServiceName = "Zoom";
				break;
			default:
				ServiceName = "Oauth Service";
				break;
			}

			System.out.println("Service Name : " + ServiceName);

			// Response Sent
			response.sendRedirect(rbOauth.getString("HOME_PAGE") + "?userName=" + userName + "&eCode=" + eCode
					+ "&Mobile=" + Mobile + "&primaryEmailId=" + primaryemail + "&token=" + access_token + "&gender="
					+ gender + "&serviceName=" + ServiceName + "&dType=" + dType);
			return;

		} catch (Exception e) {

			System.out.println("Some error occurred !!!");
			e.printStackTrace();
		}
	}

	public void revokeAccessToken(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String revokeToken, String dType) throws IOException {

		System.out.println("revokeAccessToken called");

		Map<String, String> childMap = new HashMap<>();

		System.out.println("Access_token : " + revokeToken);
		HttpGet get = new HttpGet(ResourceBundle.getBundle("oauth").getString("PARICHAY_REVOKE_URL" + dType));

		get.setHeader("Authorization", revokeToken);

		Object userJsonObj = "";

		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				CloseableHttpResponse postResponse = httpClient.execute(get);) {

			userJsonObj = JSON.parse(EntityUtils.toString(postResponse.getEntity()));

			DBObject userJson = (DBObject) userJsonObj;
			System.out.println("Revoke Response: : " + userJson);

			/*
			 * PrintWriter pw = response.getWriter();
			 * 
			 * pw.print("<br>JanParichay Response: " + userJson.toString() + "</div>");
			 * 
			 * pw.print("<br> Back to Welcome page : " + userJson.toString() + "</div>");
			 */

			response.sendRedirect(ResourceBundle.getBundle("oauth").getString("WELCOME_PAGE") + "?status="
					+ userJson.get("Status") + "&message=" + userJson.get("Description"));

			return;

		} catch (Exception e) {

			System.out.println("Some error occurred !!!");
			e.printStackTrace();
		}
		// return userJsonObj.toString();
	}

}
