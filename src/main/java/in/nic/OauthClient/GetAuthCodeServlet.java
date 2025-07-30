package in.nic.OauthClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAuthCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Authorize call -- 1st call -- Redirect call --- Provide your details to
	// Google using their authorization endpoint
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath() + "\n");
		// PrintWriter out = response.getWriter();
		System.out.println("Oauth client called");

		String dType = request.getParameter("dType");

		System.out.println(request.getQueryString());

		System.out.println("dType : " + dType);

		ResourceBundle rbOauth = ResourceBundle.getBundle("oauth");

		// Adding our details
		String oauthURL = "";
				
				if(!dType.equals("2")) {
					oauthURL = "client_id=" + rbOauth.getString("client_id" + dType) + "&" + "redirect_uri="
							+ rbOauth.getString("redirect_uri") + "&" + "scope=" + rbOauth.getString("scope" + dType) + "&"
							+ "code_challenge=" + rbOauth.getString("code_challenge") + "&" + "code_challenge_method="
							+ rbOauth.getString("code_challenge_method"+ dType) + "&" + "response_type="
							+ rbOauth.getString("response_type") + "&" + "state=" + dType;
				}else {
					
					oauthURL = "client_id=" + rbOauth.getString("client_id" + dType) + "&" + "redirect_uri="
							+ rbOauth.getString("redirect_uri")+"&" + "response_type="
									+ rbOauth.getString("response_type") + "&" + "state=" + dType;
				}
				
				

		// Using Google's auth endpoint
		System.out.println(rbOauth.getString("BASE_URL" + dType) + "?" + oauthURL);
		response.sendRedirect(rbOauth.getString("BASE_URL" + dType) + "?" + oauthURL);

		return;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);

	}

	public static void main(String[] args) {

		System.out.println("Oauth client called");

		ResourceBundle rbOauth = ResourceBundle.getBundle("oauth");

		String oauthURL = "client_id=" + rbOauth.getString("client_id") + "&" + "redirect_uri="
				+ rbOauth.getString("redirect_uri") + "&" + "scope=" + rbOauth.getString("scope") + "&"
				+ "code_challenge=" + rbOauth.getString("code_challenge") + "&" + "code_challenge_method="
				+ rbOauth.getString("code_challenge_method") + "&" + "response_type="
				+ rbOauth.getString("response_type") + "&";

		System.out.println(rbOauth.getString("BASE_URL") + "?" + oauthURL);

	}

}
