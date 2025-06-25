package in.nic.OauthClient;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.codec.binary.Base64;
//import org.apache.http.HttpException;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.ThreadContext;
//import org.bson.Document;
//
//import com.google.common.base.Strings;
//import com.google.gson.Gson;
//import com.mongodb.client.MongoCursor;
//import com.rackspacecloud.client.cloudfiles.FilesException;
//
//import in.nic.helper.HMACAlgorithamToTestNotification;
//import in.nic.model.AuthProfile;
//import in.nic.model.Generics;
//import in.nic.model.LogEvent;
//import in.nic.model.LoginModel;
//import in.nic.model.NotificationModel;
//import in.nic.model.ParichayLogEvent;
//import in.nic.repository.MongoRepositoryImpl;
//import in.nic.repository.RabbitMQRepositoryImpl;
//import in.nic.repository.RedisRepositoryImpl;
//import in.nic.service.LoginServiceImpl;
//import net.sf.json.JSONException;
//import net.sf.json.JSONObject;
//import net.sf.json.JSONSerializer;
import java.net.*;
import java.nio.charset.*;
import org.json.*;
import java.io.*;

/*
 */
@SuppressWarnings("deprecation")
public class Utility {

	// private static Date dateObj = new Date();
	// private static SimpleDateFormat dateFormat = new SimpleDateFormat(
	// ResourceBundle.getBundle("app").getString("DATE_FORMAT"));

	// private Date dateObj = new Date();
	// private SimpleDateFormat dateFormat = new SimpleDateFormat(
	// ResourceBundle.getBundle("app").getString("DATE_FORMAT"));
	private static ResourceBundle rbMongo = ResourceBundle.getBundle("mongo");

//	private static final Logger log = LogManager.getLogger(Utility.class.getName());

	public static String toJsonString(String jsonObj) {
//		log.debug("toJsonString called");
		return "\"" + jsonObj.replaceAll("\"", Matcher.quoteReplacement("\\\"")) + "\"";

	}

	// CREATE AFTER LOGIN OTP KEY
	public static String getAfterLoginOTPKey(String postSID) {
		return "afterLoginOTP@" + postSID;
	}

	// CREATE EDIT PROFILE OTP KEY
	public static String getEditProfileOTPKey(String postSID) {
		return "editProfileOTP@" + postSID;
	}
//
//	// RETURNS SERVICE NAME AND STATE USING THE SERVICE ID
//	public static List<String> getServiceNameAndState(String serviceId) {
//		log.debug("getServiceNameAndState");
//
//		// COMMONLY USED OBJECT
//		List<String> serviceDetailsList = new ArrayList<>();
//		Map<String, String> childMap = null;
//
//		try (RedisRepositoryImpl redisObj = new RedisRepositoryImpl();) {
//
//			// CHECK FOR EMPTY SERVICE ID
//			if (serviceId == null || serviceId.equals("")) {
//				serviceId = ResourceBundle.getBundle("app").getString("DEFAULT_SERVICE_ID");
//			}
//
//			/*
//			 * FETCH SERVICE DETAILS FROM REDIS IF NOT FOUND THEN FETCH FROM MONGODB
//			 */
//			childMap = redisObj.getAllValuesFromHashSet(Utility.getRedisServiceKey(serviceId));
//			log.debug("Services details fetched from redis: " + childMap);
//
//			if (childMap == null || childMap.isEmpty()) {
//
//				log.debug("Service details not found in redis\nFetch from MongoDB");
//				MongoRepositoryImpl mongoObj = new MongoRepositoryImpl();
//
//				Map<String, Object> criteriaMap = new HashMap<>();
//				criteriaMap.put("serviceId", serviceId);
//				MongoCursor<Document> mongoCursorObj = mongoObj.getCursor(
//						ResourceBundle.getBundle("mongo").getString("JAN_PARICHAY_SERVICES_COLLECTION"), criteriaMap);
//
//				if (!mongoCursorObj.hasNext()) {
//					log.debug("Service details not found in MongoDB as well");
//					log.error("Service is not registered");
//
//					serviceDetailsList.add("None");
//					serviceDetailsList.add("None");
//
//				} else {
//					log.debug("Service details found in MongoDB");
//					Document serviceDoc = mongoCursorObj.next();
//					log.debug("Services details fetched from MongoDB: " + serviceDoc);
//
//					log.debug("Cache service details in redis");
//					childMap = new HashMap<>();
//
//					// SET SERVICE DETAILS IN REDIS
//					childMap.put("serviceName", serviceDoc.getString("serviceName"));
//					childMap.put("aesIV", serviceDoc.getString("aesIV"));
//					try {
//						childMap.put("pnv1", serviceDoc.getString("isNew"));
//
//					} catch (Exception e) {
//						childMap.put("pnv1", "0");
//					}
//					try {
//						childMap.put("instance", serviceDoc.getString("instance"));
//
//					} catch (Exception e) {
//						childMap.put("instance", "");
//					}
//					childMap.put("authKey", serviceDoc.getString("authKey"));
//					childMap.put("homeURL", serviceDoc.getString("homeUrl"));
//					childMap.put("loginURL", serviceDoc.getString("loginUrl"));
//					childMap.put("logoutURL", serviceDoc.getString("logoutUrl"));
//					childMap.put("serviceId", serviceDoc.getString("serviceId"));
//					childMap.put("enforceMultiFactor", String.valueOf(serviceDoc.getInteger("enforceMultiFactor")));
//					childMap.put("forceMultiFactor", String.valueOf(serviceDoc.getInteger("forceMultiFactor")));
//					childMap.put("createdOn", serviceDoc.getString("createdOn"));
//					childMap.put("updatedOn", serviceDoc.getString("updatedOn"));
//					childMap.put("loginType", String.valueOf(serviceDoc.getInteger("loginType")));
//					childMap.put("serviceState", serviceDoc.getString("serviceState"));
//
//					if (redisObj.insertInHashSet(Utility.getRedisServiceKey(serviceId), childMap)) {
//						log.debug("Successfully cached service details in redis");
//
//					} else {
//						// NO NEED TO DISPLAY ERROR AS IT WILL NOT ALTER THE LOGIN PROCCESS
//						log.info("Error occurred while caching service details in redis\n Will cache it later");
//					}
//
//					serviceDetailsList.add(serviceDoc.getString("serviceName"));
//					serviceDetailsList.add(serviceDoc.getString("serviceState"));
//
//					// CLOSE THE RESOURCES
//					serviceDoc = null;
//				}
//
//				// CLOSE THE RESOURCES
//				mongoObj.close();
//				mongoCursorObj = null;
//				criteriaMap = null;
//
//			} else {
//
//				log.debug("Details found in redis");
//				serviceDetailsList.add(childMap.get("serviceName"));
//				serviceDetailsList.add(childMap.get("serviceState"));
//				log.debug("ServiceList: " + serviceDetailsList);
//
//			}
//			return serviceDetailsList;
//		} catch (Exception e) {
//			log.error("Error occurred while fetching service details: " + e.getClass().getCanonicalName());
//			e.printStackTrace();
//
//			serviceDetailsList.add("None");
//			serviceDetailsList.add("None");
//			return serviceDetailsList;
//		} finally {
//			// CLOSE ALL THE RESOURCES
//
//			childMap = null;
//			serviceDetailsList = null;
//		}
//
//	}
//
//	public static JSONObject toJSON(String jsonStr) {
//		log.debug("JSON String: " + jsonStr);
//		return (JSONObject) JSONSerializer.toJSON(jsonStr);
//	}
//
//	// RETURN THE USER'S MACHINE IP ADDRESS
//	public static String getIPAddress(HttpServletRequest request) {
//		log.debug("getIpAddress called");
//
//		String ipAddreass = "";
//		if (request != null) {
//			try {
//				ipAddreass = request.getHeader("X-FORWARDED-FOR");
//				if (ipAddreass == null) {
//					ipAddreass = request.getRemoteAddr();
//				}
//			} catch (Exception ex) {
//				ipAddreass = request.getRemoteAddr();
//			}
//		}
//		log.debug("IPAddress: " + ipAddreass);
//		return ipAddreass;
//	}
//
//	// RETURN THE USER AGENT STRING
//	public static String getUserAgent(HttpServletRequest request) {
//		// return request.getHeader("");
//		return request.getHeader("user-agent").replaceAll("\\s", "");
//		// return
//		// "Mozilla/5.0(WindowsNT10.0;Win64;x64)AppleWebKit/537.36(KHTML,likeGecko)Chrome/83.0.4103.116Safari/537.36";
//	}
//
//	// RETURN CURRENT DATE WITH TIME
//	public static String getCurrentDateTime() {
//		SimpleDateFormat sdfDate = null;
//		Calendar cal = null;
//		Date now = null;
//		try {
//			log.debug("getCurrentDateTime called");
//			sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//			cal = GregorianCalendar.getInstance();
//			now = new Date();
//			cal.setTime(now);
//			return sdfDate.format(cal.getTime());
//		} finally {
//			sdfDate = null;
//			cal = null;
//			now = null;
//		}
//		// cal.add(Calendar.HOUR_OF_DAY, 1);
//		// cal.add(Calendar.MINUTE, Cookies.SESSION_EXPIRE_MINUTES_IN_MONGODB);
//
//	}
//
//	// RETURN CURRENT ISO DATE WITH TIME
//	public static Date getISODateTime() {
//		Date now = null;
//		try {
//			log.debug("getISODateTime called");
//			now = new Date();
//
//			return now;
//		} finally {
//			now = null;
//		}
//		// cal.add(Calendar.HOUR_OF_DAY, 1);
//		// cal.add(Calendar.MINUTE, Cookies.SESSION_EXPIRE_MINUTES_IN_MONGODB);
//
//	}
//
//	// CREATE PRE LOGIN SESSION KEY
//	public static String getPreLoginSessionKey(String preSID, String brwID, String userAgent) {
//		return "preLoginSession@" + preSID + "@" + brwID + "@" + userAgent;
//	}
//
//	// CREATE POST LOGIN SESSION KEY
//	public static String getPostLoginSessionKey(String postSID, String brwID, String userAgent) {
//		return "postLoginSession@" + postSID + "@" + brwID + "@" + userAgent;
//		// return postSID + "@" + pid + "@" + brwID + "@" + userAgent;
//	}
//
//	// GET LOGOUT CALL BACK KEY
//	public static String getLogoutCallBackKey(String postSID, String brwID, String userAgent) {
//		return "logoutCallBack@" + postSID + "@" + brwID + "@" + userAgent;
//		// return postSID + "@" + pid + "@" + brwID + "@" + userAgent;
//	}
//
//	// CREATE BROWSER ID EXISTANCE KEY
//	public static String getBrowserIdKey(String brwID) {
//		return "browserId_" + brwID.charAt(brwID.length() - 1);
//	}
//
//	// CREATE BROWSER ID EXISTANCE KEY
//	public static String getFingerPrintCollection(String brwID) {
//		return ResourceBundle.getBundle("mongo").getString("BROWSER_FINGER_PRINT_COLLECTION")
//				+ brwID.toUpperCase().charAt(0);
//	}
//
//	// CREATE POST LOGIN SESSION ID HASH BASED KEY
//	public static String getPostLoginCollectionKey(String postSId) {
//		return "postLoginSession_" + postSId.charAt(postSId.length() - 1);
//	}
//
//	// CREATE LOGIN OTP KEY
//	public static String getOTPKey(String preSID) {
//		return "otp@" + preSID;
//	}
//
//	// RETURN TIME OUT CASE OTP KEY
//	public static String getTimeoutCaseOTPKey(String postSID) {
//		return "timeoutotp@" + postSID;
//	}
//
//	// CREATE LOGIN OTP KEY
//	public static String getChangePwdOTPKey(String postSID) {
//		return "changePwdOTP@" + postSID;
//	}
//
//	// CREATE OTP COUNT KEY
//	public static String getOTPCountKey(String preSID) {
//		return "otpcount@" + preSID;
//	}
//
//	// CREATE LOGGED IN OTP KEY
//	public static String getLoggedInOTPKey(String postSID) {
//		return "loggedInOTP@" + postSID;
//	}
//
//	// CREATE SERVICE KEY FOR CURRENT DAY
//	public static String getCurrentServiceKey(String serviceId, String userAgent) {
//		return serviceId + "@" + userAgent;
//	}
//
//	// CREATE SERVICE KEY CORRESPONDING TO JANPARICHAY SERVICES COLLECTION
//	public static String getRedisServiceKey(String serviceId) {
//		log.debug("getRedisServiceKey called");
//		return "janParichayService@serviceId@" + serviceId;
//	}
//
//	// RETURN RESPONSE OBJECT IN STRING FORM
//	public static String getResponseObj(String status, String message, String statusCode, String pageName,
//			JSONObject data) {
//		log.debug("getResponseObj called");
//		log.debug("Status: " + status + "\nMessage: " + message + "\nStatusCode: " + statusCode + "\nPageName: "
//				+ pageName + "\nData: " + data);
//		JSONObject responseJSON = new JSONObject();
//
//		responseJSON.put("status", status);
//		responseJSON.put("message", message);
//		// responseJSON.put("statusCode", statusCode);
//		responseJSON.put("pageName", pageName);
//		responseJSON.put("data", data);
//
//		// log.info("Response object: " + responseJSON);
//		return responseJSON.toString();
//	}
//
//	// RETURN RESPONSE OBJECT IN STRING FORM
//	public static String getResponseObj(String status, String message, String statusCode, String pageName,
//			JSONObject data, JSONObject responseObj) {
//		log.debug("getResponseObj called");
//		log.debug("Status: " + status + "\nMessage: " + message + "\nStatusCode: " + statusCode + "\nPageName: "
//				+ pageName + "\nData: " + data);
//		// log.debug("Response object: " + responseObj);
//
//		responseObj.put("status", status);
//		responseObj.put("message", message);
//		responseObj.put("pageName", pageName);
//		responseObj.put("data", data);
//
//		log.debug("Response JSON Object: " + responseObj);
//		// return responseObj.toString();
//		return responseObj.toString();
//		// try {
//		// return URLEncoder.encode(responseObj.toString(), "UTF-8");
//		// } catch (UnsupportedEncodingException e) {
//		// return responseObj.toString();
//		// // e.printStackTrace();
//		// }
//	}
//
//	// RETURN CLINET RESPONSE OBJECT IN STRING FORM
//	public static String getClientResponseObj(JSONObject responseObj, String status, String message, String errorCode) {
//		log.debug("getClientResponseObj called");
//		log.debug("Status: " + status + "\nMessage: " + message + "\nErrorCode: " + errorCode);
//		log.debug("Response object: " + responseObj);
//
//		responseObj.put("Status", status);
//		responseObj.put("Message", message);
//		responseObj.put("ErrorCode", errorCode);
//
//		log.info("Response JSON Object: " + responseObj);
//		return responseObj.toString();
//	}
//
//	// // RETURN RESPONSE OBJECT IN STRING FORM
//	// public static String getResponseObjForSendSeal(String status, String message,
//	// String statusCode, String pageName,
//	// String seal, JSONObject responseObj) throws Exception {
//	// log.debug("getResponseObjForSendSeal method called with parameter Status: " +
//	// status + "\nMessage: " + message
//	// + "\nStatusCode: " + statusCode + "\nPageName: " + pageName + "\nSeal: " +
//	// seal);
//	// // JSONObject responseJSON = new JSONObject();
//	//
//	// responseObj.put("status", status);
//	// responseObj.put("message", message);
//	// // responseJSON.put("statusCode", statusCode);
//	// responseObj.put("pageName", pageName);
//	// responseObj.put("seal", seal);
//	//
//	// log.info("Response object: " + responseObj);
//	// return responseObj.toString();
//	// }
//
//	// RETURN RESPONSE OBJECT IN STRING FORM
//	public static String getResponseObjForSendSeal(String status, String message, String statusCode, String pageName,
//			String seal, JSONObject responseObj) throws Exception {
//		log.debug("getResponseObjForSendSeal method called with parameter Status: " + status + "\nMessage: " + message
//				+ "\nStatusCode: " + statusCode + "\nPageName: " + pageName + "\nSeal: " + seal);
//
//		JSONObject dataJSON = new JSONObject();
//		try {
//			dataJSON.put("hideFlag", "0");
//			responseObj.put("status", status);
//			responseObj.put("message", message);
//			// responseJSON.put("statusCode", statusCode);
//			responseObj.put("pageName", pageName);
//			responseObj.put("data", dataJSON);
//			responseObj.put("seal", seal);
//
//		} finally {
//			dataJSON = null;
//		}
//		log.info("Response object: " + responseObj);
//		return responseObj.toString();
//	}
//
//	public static String encode(String input) {
//		StringBuilder resultStr = new StringBuilder();
//
//		for (char ch : input.toCharArray()) {
//			if (isUnsafe(ch)) {
//				resultStr.append('%');
//				resultStr.append(toHex(ch / 16));
//				resultStr.append(toHex(ch % 16));
//			} else {
//				resultStr.append(ch);
//			}
//		}
//		return resultStr.toString();
//	}
//
//	// public static JSONObject getJSONFromString(String jsonString) throws
//	// JSONException {
//	// return new JSONObject(jsonString);
//	//
//	// }
//
//	public static void showPopUp(HttpServletResponse response, String message) throws IOException {
//		log.debug("showPopUp called");
//		PrintWriter out = response.getWriter();
//		out.print("<html><head>");
//		out.print("<script type=\"text/javascript\">alert(" + message + ");</script>");
//		out.print("</head><body></body></html>");
//	}
//
//	public static boolean isUnsafe(char ch) {
//		// if (ch > 128 || ch < 0) {
//		// return true;
//		// }
//		if (ch > 128) {
//			return true;
//		}
//
//		try {
//			if (Double.parseDouble(String.valueOf(ch)) < 0) {
//				return true;
//			}
//		} catch (NumberFormatException e) {
//			// System.out.println("String " + test1 + "is not a number");
//		}
//
//		return " %$&+,/:;=?@<>#%!^~`{}[]()-_\"\\*'|".indexOf(ch) >= 0;
//	}
//
//	public static char toHex(int ch) {
//		return (char) (ch < 10 ? '0' + ch : 'A' + ch - 10);
//	}
//
//	public static boolean createDirectory(String folderNameWithFullPath) throws Exception {
//		log.debug("createDirectory method called ");
//		boolean status = false;
//
//		File directory = new File(folderNameWithFullPath);
//		// if the directory does not exist, create it
//		if (directory.exists()) {
//			log.debug("Directory already exists ...");
//			status = true;
//
//		} else {
//			log.debug("Directory not exists, creating now");
//
//			status = directory.mkdir();
//			if (status) {
//				log.debug("Successfully created new directory ");
//			} else {
//				log.debug("Failed to create new directory ");
//			}
//		}
//		log.debug("createDirectory method returns boolean value : " + status);
//		return status;
//
//	}
//
//	public static String hideEmailIdBySomeCharacters(String emailId, String hideCharacter, int start, int end)
//			throws Exception {
//		log.debug("hideEmailIdBySomeCharacters method called with parameter emailId :" + emailId
//				+ " and hideCharacter :" + hideCharacter);
//
//		StringBuilder emailIdStrBuilder = new StringBuilder(emailId);
//		for (int i = start; i < emailIdStrBuilder.length() - end; i++) {
//			if (emailIdStrBuilder.toString().charAt(i) == '@') {
//
//			} else {
//				emailIdStrBuilder.setCharAt(i, '*');
//			}
//		}
//		log.debug("Get Hide email id : " + emailIdStrBuilder);
//		return emailIdStrBuilder.toString();
//	}
//
//	public static String hideMobileNoBySomeCharacters(String mobileNo, String hideCharacter, int start, int end)
//			throws Exception {
//		log.debug("hideMobileNoBySomeCharacters method called with parameter mobileNo :" + mobileNo
//				+ " and hideCharacter :" + hideCharacter);
//
//		StringBuilder mobileNoStrBuilder = new StringBuilder(mobileNo);
//		try {
//			for (int i = start; i < mobileNoStrBuilder.length() - end; i++) {
//
//				mobileNoStrBuilder.setCharAt(i, '*');
//			}
//
//			// mobileNo = mobileNoStrBuilder.toString();
//			// if (!mobileNo.contains("+")) {
//			// StringBuilder _sb = new StringBuilder(mobileNo);
//			// mobileNo = _sb.insert(0, "+91").toString();
//			// _sb = null;
//			//
//			// }
//			log.debug("Get Hide mobile no : " + mobileNoStrBuilder);
//			return mobileNoStrBuilder.toString();
//		} finally {
//			mobileNoStrBuilder = null;
//		}
//
//	}
//
//	public static String hideStringBySomeCharacters(String str, String hideCharacter, int start, int end)
//			throws Exception {
//		// log.debug("hideStringBySomeCharacters method called with parameter String :"
//		// + str + " and hideCharacter :"
//		// + hideCharacter);
//
//		StringBuilder strBuilder = new StringBuilder(str);
//		for (int i = start; i < strBuilder.length() - end; i++) {
//
//			strBuilder.setCharAt(i, '*');
//		}
//		log.debug("Get Hide mobile no : " + strBuilder);
//		return strBuilder.toString();
//
//	}
//
//	// public static int getASCIIValueRem(String str) {
//	// log.debug("getASCIIValueRem called");
//	// log.debug("Requested string: " + str);
//	//
//	// // int sum_char = 0;
//	// // // LOOP TO SUM THE ASCII VALUE OF CHARS
//	// // for (int i = 0; i < str.length(); i++) {
//	// // sum_char += (int) str.charAt(i);
//	// // }
//	// // // System.out.println("Sum chars: " + sum_char);
//	// // return sum_char % 10;
//	// return RemainderOfBigNumber.findRemainderOfBigNumber(str, 10);
//	// }
//
//	// REPOSITORY HELPER METHOD
//	public static Map<String, String> getMapObjectFromJavaObjectNew(Class<?> modelObj) {
//		log.debug("getMapObjectFromJavaObjectNew method called");
//		Map<String, String> mapObj = null;
//
//		try {
//			mapObj = new HashMap<String, String>();
//
//			Field[] allFields = modelObj.getDeclaredFields();
//
//			for (Field field : allFields) {
//				mapObj.put(field.getName(), field.get(modelObj).toString());
//			}
//		} catch (Exception e) {
//
//		}
//		log.debug("getMapObjectFromJavaObjectNew method returns map object:" + modelObj);
//
//		return mapObj;
//
//	}
//
//	@SuppressWarnings({ "resource" })
//	public static boolean sendNotification(String userName, String status, String sessionId, String usedCodeId)
//			throws Exception {
//		log.debug("sendNotification called");
//
//		// COMMONLY USED OBJECTS
//		NotificationModel notificationObj = null;
//		URIBuilder urlb = null;
//		DefaultHttpClient httpClient = null;
//		long currentTime = 0L;
//		HttpPost postRequest = null;
//		String s1 = null;
//		JSONObject jsonObj = null;
//		String hmacValue = null;
//		String notificatioURL = null;
//
//		try {
//
//			notificatioURL = ResourceBundle.getBundle("config").getString("AUTHENTICATOR_BASE_URL")
//					+ ResourceBundle.getBundle("app").getString("NOTIFICATION_URL");
//
//			urlb = new URIBuilder(notificatioURL);
//			httpClient = new DefaultHttpClient();
//
//			notificationObj = new NotificationModel();
//			notificationObj.setUsedCodeId(usedCodeId);
//			currentTime = new Date().getTime();
//			log.debug("CurrentTime: " + currentTime);
//			urlb.setParameter("Message", notificationObj.GetDateJson1(notificationObj));
//			urlb.setParameter("UserName", userName);
//
//			postRequest = new HttpPost(urlb.build());
//			s1 = (notificationObj.GetDateJson1(notificationObj));
//			jsonObj = new JSONObject();
//			jsonObj.put("Message", s1);
//			jsonObj.put("UserName", userName);
//
//			hmacValue = new HMACAlgorithamToTestNotification()
//					.getHMACResponse(HMACAlgorithamToTestNotification.SERVICENAME, "user", notificatioURL,
//							jsonObj.toString(), HMACAlgorithamToTestNotification.REST_AUTHKEY, currentTime)
//					.get("signature").toString();
//			log.debug("Hash Value:- " + hmacValue);
//			if (hmacValue != null) {
//				postRequest.setHeader("Accept", "application/json");
//				postRequest.setHeader("Content-type", "application/json");
//				postRequest.setHeader("ttl", Long.toString(currentTime));
//				postRequest.setHeader("signature", hmacValue);
//				postRequest.setHeader("restauthid", HMACAlgorithamToTestNotification.REST_AUTHKEY);
//				log.debug("postRequest value :" + postRequest);
//				HttpResponse response = httpClient.execute(postRequest);
//				if (response.getStatusLine().getStatusCode() != 200) {
//					// throw new RuntimeException("Failed : HTTP error code : "
//					// +
//					// response.getStatusLine().getStatusCode());
//					log.error("Error occurred while sending notification");
//					return false;
//				} else if (response.getStatusLine().getStatusCode() == 200) {
//					log.debug("Mobile notification response: " + EntityUtils.toString(response.getEntity()));
//					return true;
//				} else {
//					return false;
//				}
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("Error occurred while sending notification with respective reason: "
//					+ e.getClass().getCanonicalName());
//			return false;
//		} finally {
//			// CLOSE THE RESOURCES
//			log.debug("sendNotification finally called");
//			notificationObj = null;
//			urlb = null;
//			httpClient = null;
//			currentTime = 0L;
//			postRequest = null;
//			s1 = null;
//			jsonObj = null;
//			hmacValue = null;
//			notificatioURL = null;
//
//		}
//	}
//
//	public static String getOSFromUserAgent(String userAgent) {
//		log.debug("getOSFromUserAgent called");
//		log.debug("UserAgent: " + userAgent);
//		String os = "";
//		try {
//			// =================OS=======================
//			if (userAgent.toLowerCase().indexOf("windows") >= 0) {
//				os = "Windows";
//			} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
//				os = "Mac";
//			} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
//				os = "Unix";
//			} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
//				os = "Android";
//			} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
//				os = "IPhone";
//			}
//			return os;
//		} catch (Exception e) {
//			return "Windows";
//		}
//	}
//
//	public static String getBrowserFromUserAgent(String userAgent) {
//		log.debug("getBrowserFromUserAgent called");
//		log.debug("UserAgent: " + userAgent);
//		String browser = "";
//		String user = userAgent.toLowerCase();
//
//		try {
//			// ===============Browser===========================
//			if (user.contains("msie")) {
//				// String substring =
//				// userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
//				// browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" +
//				// substring.split(" ")[1];
//				browser = "IE";
//			} else if (user.contains("safari") && user.contains("version")) {
//				// browser =
//				// (userAgent.substring(userAgent.indexOf("Safari")).split("
//				// ")[0]).split("/")[0] + "-"
//				// + (userAgent.substring(userAgent.indexOf("Version")).split("
//				// ")[0]).split("/")[1];
//				browser = "Safari";
//			} else if (user.contains("opr") || user.contains("opera")) {
//				if (user.contains("opera"))
//					browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
//							+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
//				else if (user.contains("opr"))
//					// browser =
//					// ((userAgent.substring(userAgent.indexOf("OPR")).split("
//					// ")[0]).replace("/", "-"))
//					// .replace("OPR", "Opera");
//					browser = "Opera";
//			} else if (user.contains("chrome")) {
//				// browser =
//				// (userAgent.substring(userAgent.indexOf("Chrome")).split("
//				// ")[0]).replace("/", "-");
//				browser = "Chrome";
//			} else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)
//					|| (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1)
//					|| (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
//				// browser=(userAgent.substring(userAgent.indexOf("MSIE")).split("
//				// ")[0]).replace("/", "-");
//				// browser = "Netscape-?";
//				browser = "Netscape";
//
//			} else if (user.contains("firefox")) {
//				// browser =
//				// (userAgent.substring(userAgent.indexOf("Firefox")).split("
//				// ")[0]).replace("/", "-");
//				browser = "Firefox";
//			} else if (user.contains("rv")) {
//				// browser = "IE-" + user.substring(user.indexOf("rv") + 3,
//				// user.indexOf(")"));
//				browser = "IE";
//			} else {
//				browser = "UnKnown, More-Info: " + userAgent;
//			}
//			// log.info("Browser Name==========>" + browser);
//			return browser;
//		} catch (Exception e) {
//			return "UnKnown";
//		}
//	}
//
//	// GENERATE LOG EVENTS BASED ON PARICHAY
//	public static void parichayLogGenerator(ParichayLogEvent logEventObj, HttpServletRequest request) {
//		log.debug("parichayLogGenerator called");
//		log.debug("LogEvent Object: " + logEventObj);
//
//		// COMMONLY USED OBJECTS
//		Gson gsonObj = null;
//		String ip = null;
//
//		JSONObject locationJSON = new JSONObject();
//		Date dateObj = new Date();
//		SimpleDateFormat dateFormat = new SimpleDateFormat(ResourceBundle.getBundle("app").getString("DATE_FORMAT"));
//
//		try {
//
//			ip = Utility.getIPAddress(request);
//
//			ThreadContext.put("ip", ip);
//
//			// dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
//			// System.out.println("Date Format: "+dateFormat.format(dateObj));
//
//			logEventObj.setTimeStamp(dateFormat.format(dateObj) + " IST");
//
//			try {
//				logEventObj.setUserAgent(Utility.getUserAgent(request));
//				logEventObj.setMobileNo(Utility.hideEmailIdBySomeCharacters(logEventObj.getMobileNo(), "*", 4, 4));
//				logEventObj.setEmailId(Utility.hideEmailIdBySomeCharacters(logEventObj.getEmailId(), "*", 4, 4));
//			} catch (Exception e) {
//				logEventObj.setUserAgent("");
//				logEventObj.setMobileNo("");
//				logEventObj.setEmailId("");
//			}
//
//			logEventObj.setIPAddress(ip);
//			logEventObj.setEventID(String.valueOf(genrateRandomNumber(10)));
//			locationJSON = LocationUtility.getLocation(ip, locationJSON);
//			logEventObj.setState(locationJSON.getString("State"));
//			logEventObj.setCity(locationJSON.getString("City"));
//			logEventObj.setCountry(locationJSON.getString("Country"));
//
//			gsonObj = new Gson();
//			log.info(gsonObj.toJson(logEventObj));
//		} catch (Exception e) {
//			log.error("Error occured (parichayLogGenerator) while generating log events: "
//					+ e.getClass().getCanonicalName());
//			e.printStackTrace();
//
//		} finally {
//			log.debug("parichayLogGenerator finally called");
//			gsonObj = null;
//			ip = null;
//			locationJSON = null;
//		}
//
//	}
//
//	// CHECK FOR NEW DEVICE AND SEND ALERT
//	public static JSONObject userMappedBrowserIdAndAlertImpl(HttpServletRequest request, MongoRepositoryImpl mongoObj,
//			JSONObject responseObj, String brwId, String parichayId, int isAlertOn, String primaryEmailId,
//			String primaryMobielNo, String firstName, String lastName) {
//		log.debug("userMappedBrowserIdAndAlertImpl called");
//		log.debug("BrowserId: " + brwId + "\nParichayId: " + parichayId + "\tIsAlertOn: " + isAlertOn + "\tFirstName: "
//				+ firstName + "\tLastName: " + lastName + "\tPrimary EmailId: " + primaryEmailId
//				+ "\tPrimary MobileNo: " + primaryMobielNo);
//
//		// COMMONLY USED OBJECTS
//		Map<String, Object> criteriaMap = new HashMap<>();
//		Map<String, Object> fieldMap = new HashMap<>();
//		JSONObject jsonRes = new JSONObject();
//		MongoCursor<Document> mongoCursor = null;
//
//		try (RabbitMQRepositoryImpl rabbitMQObj = new RabbitMQRepositoryImpl();) {
//
//			// CHECK BROWSER ID EXISTANCE W.R.T USER
//			criteriaMap.put("parichayId", parichayId);
//			criteriaMap.put("brwId", brwId);
//			mongoCursor = mongoObj.getCursor(
//					ResourceBundle.getBundle("mongo").getString("USER_MAPPED_BROWSER_ID_COLLECTION"), criteriaMap);
//
//			if (mongoCursor.hasNext()) {
//				log.debug("BrowserId has used previously");
//
//				// NO NEED TO CHECK FOR NEW DEVICE ALERT
//				jsonRes.put("status", "success");
//				jsonRes.put("reason", "exists");
//
//			} else {
//				log.debug("BrowserId has not used previously");
//
//				// INSERT NEW BROWSER ID
//				criteriaMap.remove("brwId");
//				fieldMap.put("brwId", brwId);
//				Date isoDate = Utility.getISODateTime();
//				fieldMap.put("createdAt", isoDate);
//				fieldMap.put("updatedAt", isoDate);
//				isoDate = null;
//
//				if (mongoObj.upsertMultipleFields(
//						ResourceBundle.getBundle("mongo").getString("USER_MAPPED_BROWSER_ID_COLLECTION"), criteriaMap,
//						fieldMap)) {
//					log.debug("Successfully inserted in mongodb");
//
//					// NOW CHECK FOR NEW DEVICE ALERT
//					if (isAlertOn == 1) {
//						log.debug("Send alert");
//
//						if (!primaryEmailId.equals("")) {
//							log.debug("Send new device alert on emailId");
//
//							if (rabbitMQObj.sentOtpOnEmail(request, primaryEmailId, firstName + " " + lastName + "@@"
//									+ Utility.getOSFromUserAgent(Utility.getUserAgent(request)), 8)) {
//								jsonRes.put("status", "success");
//								jsonRes.put("reason", "sent");
//							} else {
//								jsonRes.put("status", "success");
//								jsonRes.put("reason", "Internal Server Error");
//							}
//						} else {
//							log.debug("Send new device alert on mobileNo");
//							if (rabbitMQObj.sentOtpOnMobile(request, primaryMobielNo, firstName + " " + lastName + "@@"
//									+ Utility.getOSFromUserAgent(Utility.getUserAgent(request)), 8)) {
//								jsonRes.put("status", "success");
//								jsonRes.put("reason", "sent");
//							} else {
//								jsonRes.put("status", "success");
//								jsonRes.put("reason", "Internal Server Error");
//							}
//						}
//					} else {
//						log.debug("Alert is not enabled");
//						jsonRes.put("status", "success");
//						jsonRes.put("reason", "Upserted");
//
//					}
//
//				} else {
//					log.debug("Error occurred while inserting in User Mapped  collection");
//					jsonRes.put("status", "failure");
//					jsonRes.put("reason", "Internal Server Error");
//
//				}
//			}
//
//			return jsonRes;
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("Error occurred in userMappedBrowserIdAndAlertImpl method: " + e.getClass().getCanonicalName());
//			jsonRes.put("status", "failure");
//			jsonRes.put("reason", e.getMessage());
//			return jsonRes;
//		} finally {
//			log.debug("userMappedBrowserIdAndAlertImpl finally called");
//			// CLOSE THE RESOURCES
//			criteriaMap = null;
//			fieldMap = null;
//			jsonRes = null;
//			mongoCursor = null;
//			// FORCE GC
//
//		}
//
//	}
//
//	// public static String createHashingMechanismBasedCollectionName(String
//	// collectionName, String modularDivision,
//	// String verificationId) throws Exception {
//	// log.debug("createHashingMechanismBasedCollectionName method called with
//	// parameters collectionName : "
//	// + collectionName + " , modularDivision : " + modularDivision + " and
//	// verificationId : "
//	// + verificationId);
//	//
//	// // MODULAR DIVISION IS APPLICABLE FOR NUMBER THAT MADE WITH ANY NO OF
//	// // DIGITS
//	// // (VERIFICATION ID IS MADE OF ANY NO OF DIGITS)
//	//
//	// return collectionName +
//	// RemainderOfBigNumber.findRemainderOfBigNumber(verificationId,
//	// Integer.parseInt(modularDivision.trim()));
//	//
//	// }
//
//	// RETURN END TIME FOR SESSION IN JUST 12 HRS
//	public static Date convertDateStringInToDateObj(String dateStr) {
//		log.debug("convertDateStringInToDateObj called");
//		Date date = null;
//		;
//		try {
//			date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dateStr);
//		} catch (ParseException e) {
//			log.debug("Escape Exception");
//			e.printStackTrace();
//		}
//		return date;
//	}
//
//	public static Map<String, Object> createMapObjectFromLoginModelObject(LoginModel loginModelObj) {
//		log.debug("createMapObjectFromLoginModelObject method called");
//
//		Map<String, Object> mapObj = null;
//
//		try {
//			mapObj = new HashMap<String, Object>();
//
//			mapObj.put("parichayId", loginModelObj.getParichayId());
//			mapObj.put("createdOn", Utility.getCurrentDateTime());
//			mapObj.put("updatedOn", Utility.getCurrentDateTime());
//			mapObj.put("userId", loginModelObj.getUserId());
//			mapObj.put("firstName", loginModelObj.getFirstName());
//			mapObj.put("lastName", loginModelObj.getLastName());
//			mapObj.put("dob", loginModelObj.getDob());
//			mapObj.put("loginFromNewDeviceAlert", loginModelObj.getLoginFromNewDeviceAlert());
//			mapObj.put("seal", "");
//			mapObj.put("sealType", "1");
//			mapObj.put("designation", loginModelObj.getDesignation());
//			mapObj.put("department", loginModelObj.getDepartment());
//			mapObj.put("zimOTP", loginModelObj.getZimOTP());
//			if (loginModelObj.getMailEquivalentAddressList() != null) {
//				mapObj.put("mailEquivalentAddressList", loginModelObj.getMailEquivalentAddressList());
//			} else {
//				mapObj.put("mailEquivalentAddressList", new ArrayList<String>());
//			}
//			// mapObj.put("mailAleternateAddress", loginModelObj.getMailAlternateAddress());
//			mapObj.put("userSeed", loginModelObj.getUserSeed());
//			mapObj.put("profileStatus", 0);// 0-profile is temp and 1-profile is permanent
//			mapObj.put("profileRegisteredBy", loginModelObj.getLoginId());
//			mapObj.put("profileType", loginModelObj.getDepartment());
//			mapObj.put("profilePic", "");
//			mapObj.put("isBackupCodeEnable", loginModelObj.getIsBackupCodeEnable());
//			mapObj.put("IsTwoFactorEnable", loginModelObj.getIsTwoFactorEnable());
//			mapObj.put("IsGeoFencingEnable", loginModelObj.getIsGeoFencingEnable());
//			mapObj.put("geoFencingType", loginModelObj.getGeoFencingType());
//
//		} catch (Exception e) {
//
//		}
//		log.debug("createMapObjectFromAuthProfileInterfaceJavaObject method returns map object:" + mapObj);
//
//		return mapObj;
//
//	}
//
//	// ADDED BY RAJEEV ON 21-10-2020
//	// Thsi function to generate a random string of length n
//	public static String getAlphaNumericStringOfSpecificLenth(int lenthOfString) {
//
//		// chose a Character random from this String
//		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
//
//		// create StringBuffer size of AlphaNumericString
//		StringBuilder sb = new StringBuilder(lenthOfString);
//
//		for (int i = 0; i < lenthOfString; i++) {
//
//			// generate a random number between
//			// 0 to AlphaNumericString variable length
//			int index = (int) (AlphaNumericString.length() * Math.random());
//
//			// add Character one by one in end of sb
//			sb.append(AlphaNumericString.charAt(index));
//		}
//
//		return sb.toString();
//	}
//
//	public static String getAlphaNumericString(int lenthOfString) {
//
//		// chose a Character random from this String
//		String AlphaNumericString = "0123456789" + "abcdefghijklmnopqrstuvxyz";
//
//		// create StringBuffer size of AlphaNumericString
//		StringBuilder sb = new StringBuilder(lenthOfString);
//
//		for (int i = 0; i < lenthOfString; i++) {
//
//			// generate a random number between
//			// 0 to AlphaNumericString variable length
//			int index = (int) (AlphaNumericString.length() * Math.random());
//
//			// add Character one by one in end of sb
//			sb.append(AlphaNumericString.charAt(index));
//		}
//
//		return sb.toString();
//	}
//
//	public static void generateCaptchaStyle1(HttpServletRequest request, HttpServletResponse response,
//			String captchaStr) {
//
//		log.debug("generateCaptchaStyle1 called with parameter capcha string :" + captchaStr);
//
//		ResourceBundle rbAPP = ResourceBundle.getBundle("app");
//
//		int width, height;
//
//		try {
//			// MAKE ITS BUFFER IMAGE
//			width = Integer.parseInt(rbAPP.getString("CAPTCHA_WIDTH"));
//			height = Integer.parseInt(rbAPP.getString("CAPTCHA_HEIGHT"));
//
//			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//			Graphics2D g2d = bufferedImage.createGraphics();
//			Font font = new Font("Consolas", Font.BOLD, 22);
//			g2d.setFont(font);
//
//			RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//			rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
//			g2d.setRenderingHints(rh);
//
//			// GradientPaint gp = new GradientPaint(0, 0, Color.red, 0, height / 2,
//			// Color.black, true);
//			GradientPaint gp = new GradientPaint(0, 0, Color.CYAN, 0, height / 2, Color.blue, true);
//			g2d.setPaint(gp);
//			g2d.fillRect(0, 0, width, height);
//			g2d.setColor(new Color(255, 153, 0));
//			captchaStr = captchaStr.replaceAll("\\.", "$0 ");
//			log.debug("To add space character in enerated captcha string and found modified captcha String "
//					+ captchaStr);
//			g2d.drawString(captchaStr, 10, 35);
//			g2d.dispose();
//			response.setContentType("image/png");
//
//			OutputStream os = response.getOutputStream();
//			ImageIO.write(bufferedImage, "png", os);
//			os.flush();
//			os.close();
//
//		} catch (Exception e) {
//			log.debug("Escape Exception");
//		} finally {
//
//		}
//	}
//
//	public static void generateCaptchaStyle2(HttpServletRequest request, HttpServletResponse response,
//			String captchaStr) {
//
//		log.debug("generateCaptchaStyle2 called with parameter capcha string :" + captchaStr);
//
//		int width, height;
//		BufferedImage bufferedImage = null;
//
//		try {
//			Color backgroundColor = Color.white;
//			Color borderColor = Color.black;
//			Color textColor = Color.black;
//			Color circleColor = new Color(190, 160, 150);
//			Font textFont = new Font("Consolas", Font.BOLD, 35);
//			int charsToPrint = 6;
//			width = 160;
//			height = 50;
//			int circlesToDraw = 25;
//			float horizMargin = 10.0f;
//			double rotationRange = 0.7;
//			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//			Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
//			g.setColor(backgroundColor);
//			g.fillRect(0, 0, width, height);
//
//			// lets make some noisey circles
//			g.setColor(circleColor);
//			for (int i = 0; i < circlesToDraw; i++) {
//				int L = (int) (Math.random() * height / 2.0);
//				int X = (int) (Math.random() * width - L);
//				int Y = (int) (Math.random() * height - L);
//				g.draw3DRect(X, Y, L * 2, L * 2, true);
//			}
//			g.setColor(textColor);
//			g.setFont(textFont);
//			FontMetrics fontMetrics = g.getFontMetrics();
//			int maxAdvance = fontMetrics.getMaxAdvance();
//			int fontHeight = fontMetrics.getHeight();
//
//			char[] chars = captchaStr.toCharArray();
//			float spaceForLetters = -horizMargin * 2 + width;
//			float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);
//			StringBuffer finalString = new StringBuffer();
//			for (int i = 0; i < charsToPrint; i++) {
//
//				char characterToShow = chars[i];
//				finalString.append(characterToShow);
//
//				// this is a separate canvas used for the character so that
//				// we can rotate it independently
//				int charWidth = fontMetrics.charWidth(characterToShow);
//				int charDim = Math.max(maxAdvance, fontHeight);
//				int halfCharDim = (int) (charDim / 2);
//				BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
//				Graphics2D charGraphics = charImage.createGraphics();
//				charGraphics.translate(halfCharDim, halfCharDim);
//				double angle = (Math.random() - 0.5) * rotationRange;
//				charGraphics.transform(AffineTransform.getRotateInstance(angle));
//				charGraphics.translate(-halfCharDim, -halfCharDim);
//				charGraphics.setColor(textColor);
//				charGraphics.setFont(textFont);
//				int charX = (int) (0.5 * charDim - 0.5 * charWidth);
//				charGraphics.drawString("" + characterToShow, charX,
//						(int) ((charDim - fontMetrics.getAscent()) / 2 + fontMetrics.getAscent()));
//
//				float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
//				int y = (int) ((height - charDim) / 2);
//				g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
//				charGraphics.dispose();
//			}
//			g.setColor(borderColor);
//			g.drawRect(0, 0, width - 1, height - 1);
//			g.dispose();
//
//			response.setContentType("image/png");
//
//			OutputStream os = response.getOutputStream();
//			ImageIO.write(bufferedImage, "png", os);
//			os.flush();
//			os.close();
//
//			// return bufferedImage;
//		} catch (Exception ioe) {
//			throw new RuntimeException("Unable to build image, please try after some times.", ioe);
//		}
//	}
//
//	// ADD BY RAJEEV ON 18-11-2020
//	public static boolean checkServiceAuthrizationBasedDeptForParichayApplication(
//			String getDeptAllowValueFromServiceDetails, String getDeptValueFromUserBasicDetails) throws Exception {
//		log.debug("checkServiceAuthrizationBasedDeptForParichayApplication service helper method called");
//		boolean serviceAuthrizationStatus = false;
//
//		if (getDeptAllowValueFromServiceDetails.equals("ALL")) {
//			log.debug("User is applicable for access the service");
//			serviceAuthrizationStatus = true;
//			log.debug("checkServiceAuthrizationForParichayApplication service helper method returns boolean value :"
//					+ serviceAuthrizationStatus);
//			return serviceAuthrizationStatus;
//		}
//		if (getDeptAllowValueFromServiceDetails.equals(getDeptValueFromUserBasicDetails)) {
//			log.debug("User is applicable for access the service");
//			serviceAuthrizationStatus = true;
//		} else {
//			log.debug("User is not applicable for access the service");
//			serviceAuthrizationStatus = false;
//		}
//		log.debug(
//				"checkServiceAuthrizationBasedDeptForParichayApplication service helper method returns boolean value :"
//						+ serviceAuthrizationStatus);
//		return serviceAuthrizationStatus;
//	}
//
//	// ADD BY RAJEEV ON 18-11-2020
//	public static boolean checkServiceAuthrizationBasedGeoLocationForParichayApplication(String userName, String ip,
//			String serviceId, int zimOTP) {
//		log.debug("checkServiceAuthrizationBasedDeptForParichayApplication called");
//
//		// COMMONLY USED OBJECTS
//		boolean serviceAuthrizationStatus = false;
//		JSONObject respJsonObj = null;
//		//
//		try {
//			if (zimOTP == 0) {
//				// zimOTP = 1;
//				return true;
//			}
//			respJsonObj = Utility.toJSON(
//					LoginServiceImpl.getServiceThenUserBasedGeoFencingStatusOfUser(userName, ip, serviceId, zimOTP));
//			log.debug("Get respJsonObj :" + respJsonObj);
//			if (respJsonObj.getString("status").equals("success")) {
//				serviceAuthrizationStatus = true;
//			} else {
//				serviceAuthrizationStatus = false;
//			}
//
//		} catch (Exception e) {
//			log.debug("Escape Exception");
//			return true;
//		}
//
//		log.debug(
//				"checkServiceAuthrizationBasedDeptForParichayApplication service helper method returns boolean value :"
//						+ serviceAuthrizationStatus);
//		// serviceAuthrizationStatus = true;
//		return serviceAuthrizationStatus;
//	}
//
//	// FETCH SERVICE DETAILS FROM MONGODB
//	public static Document getServiceDetailsFromMongoDB(MongoRepositoryImpl mongoObj, String serviceId) {
//		log.debug("getServiceDetailsFromMongoDB called");
//
//		// COMMONLY USED OBJECTS
//		Map<String, Object> criteriaMap;
//		MongoCursor<Document> serviceCursor;
//		Document serviceDoc = null;
//
//		try {
//			criteriaMap = new HashMap<>();
//			criteriaMap.put("serviceId", serviceId);
//			serviceCursor = mongoObj.getCursor(rbMongo.getString("JAN_PARICHAY_SERVICES_COLLECTION"), criteriaMap);
//			serviceDoc = serviceCursor.next();
//			log.debug("ServiceDoc: " + serviceDoc);
//
//		} catch (Exception e) {
//			log.debug("Escape Exception");
//			criteriaMap = null;
//		}
//		return serviceDoc;
//	}
//
//	// CHECK WHETHER SERVICE AUTHORIZE THE USER TO ACCESS OR NOT
//	public static boolean getServiceAuthorizationStatusForParichay(MongoRepositoryImpl mongoObj, String serviceId,
//			String dept, String mobileNo, String emailId) {
//
//		log.debug("getServiceAuthorizationStatusForParichay called");
//		log.debug("ServiceId: " + serviceId + "\tMobile No: " + mobileNo + "\tEmailId: " + emailId);
//
//		// COMMONLY USED OBJECTS
//		boolean serviceAuthorizationStatus = false;
//		String getDeptValueFromUserBasicDetails = null;
//
//		try {
//
//			Document serviceDoc = Utility.getServiceDetailsFromMongoDB(mongoObj, serviceId);
//			log.debug("Service document for service authorization: " + serviceDoc);
//			if (serviceDoc == null) {
//				log.debug("Service not found");
//				return true;
//			} else {
//
//				switch (rbAPP.getString("PRODUCT_NO")) {
//
//				case "1": // PARICHY - DEPARTMENT CHECK
//					getDeptValueFromUserBasicDetails = dept.trim().toLowerCase();
//					getDeptValueFromUserBasicDetails = getDeptValueFromUserBasicDetails.replaceAll("\\s", "");
//					log.debug("Get dept value from user basic details :" + getDeptValueFromUserBasicDetails);
//					log.debug("**********************************");
//					try {
//						Document deptDoc = (Document) serviceDoc.get("verificationTypes");
//						log.debug("Department Document fetched: " + deptDoc);
//						log.debug("DOC: " + deptDoc.getInteger("all"));
//
//						if (deptDoc.getInteger("all") != null && deptDoc.getInteger("all") != 0) {
//							log.debug("1");
//							serviceAuthorizationStatus = true;
//						} else {
//							log.debug("2");
//							if (deptDoc.getInteger(getDeptValueFromUserBasicDetails) != null
//									&& deptDoc.getInteger(getDeptValueFromUserBasicDetails) != 0) {
//								log.debug("3");
//								serviceAuthorizationStatus = true;
//							} // NO NEED FOR ELSE BLOCK
//						}
//						deptDoc = null;
//
//					} catch (Exception e) {
//						log.debug("Inside catch");
//						try {
//							serviceAuthorizationStatus = Utility
//									.checkServiceAuthrizationBasedDeptForParichayApplication(
//											serviceDoc.getString("verificationTypes"),
//											getDeptValueFromUserBasicDetails);
//						} catch (Exception ex) {
//							log.debug("Inside another catch");
//							serviceAuthorizationStatus = true;
//
//						}
//					}
//					serviceDoc = null;
//					getDeptValueFromUserBasicDetails = null;
//					break;
//				case "2": // JAN PARICHAY KERALA - VERIFICATION PARAMETER CHECK
//
//					try {
//						Document veriftDoc = (Document) serviceDoc.get("verificationTypes");
//						log.debug("Verification Document fetched: " + veriftDoc);
//						log.debug("DOC: " + veriftDoc.getInteger("all"));
//
//						if (veriftDoc.getInteger("all") != null && veriftDoc.getInteger("all") != 0) {
//							log.debug("User is authorized");
//							return true;
//						} else {
//
//							// CHECK MOBILE NO VERIFICATION
//							if (veriftDoc.getInteger("MOBILE") != null && veriftDoc.getInteger("MOBILE") != 0
//									&& Strings.isNullOrEmpty(mobileNo)) {
//								log.debug("Mobile No is required");
//								serviceAuthorizationStatus = true;
//
//							} else {
//								veriftDoc = null;
//
//								log.debug("Mobile No is not verified");
//								return false;
//
//							}
//
//							// CHECK EMAIL ID VERIFICATION
//							if (veriftDoc.getInteger("EMAIL") != null && veriftDoc.getInteger("EMAIL") != 0
//									&& Strings.isNullOrEmpty(emailId)) {
//								log.debug("Email Id is required");
//								serviceAuthorizationStatus = true;
//								veriftDoc = null;
//
//							} else {
//								veriftDoc = null;
//
//								log.debug("Email Id is not verified");
//								return false;
//
//							}
//						}
//					} catch (Exception e) {
//						log.debug("Inside catch");
//						log.error("Error occurred while fetching verification types");
//						e.printStackTrace();
//					}
//					serviceDoc = null;
//					getDeptValueFromUserBasicDetails = null;
//					break;
//				default: // PARICHAY - DEPARTMENT CHECK
//					getDeptValueFromUserBasicDetails = dept.trim().toLowerCase();
//					getDeptValueFromUserBasicDetails = getDeptValueFromUserBasicDetails.replaceAll("\\s", "");
//					log.debug("Get dept value from user basic details :" + getDeptValueFromUserBasicDetails);
//					log.debug("**********************************");
//					try {
//						Document deptDoc = (Document) serviceDoc.get("verificationTypes");
//						log.debug("Department Document fetched: " + deptDoc);
//						log.debug("DOC: " + deptDoc.getInteger("all"));
//
//						if (deptDoc.getInteger("all") != null && deptDoc.getInteger("all") != 0) {
//							log.debug("1");
//							serviceAuthorizationStatus = true;
//						} else {
//							log.debug("2");
//							if (deptDoc.getInteger(getDeptValueFromUserBasicDetails) != null
//									&& deptDoc.getInteger(getDeptValueFromUserBasicDetails) != 0) {
//								log.debug("3");
//								serviceAuthorizationStatus = true;
//							} // NO NEED FOR ELSE BLOCK
//						}
//						deptDoc = null;
//
//					} catch (Exception e) {
//						log.debug("Inside catch");
//						try {
//							serviceAuthorizationStatus = Utility
//									.checkServiceAuthrizationBasedDeptForParichayApplication(
//											serviceDoc.getString("verificationTypes"),
//											getDeptValueFromUserBasicDetails);
//						} catch (Exception ex) {
//							log.debug("Inside another catch");
//							serviceAuthorizationStatus = true;
//
//						}
//					}
//					serviceDoc = null;
//					getDeptValueFromUserBasicDetails = null;
//					break;
//
//				}
//			}
//		} catch (Exception e) {
//			log.debug("Escape Exception");
//			e.printStackTrace();
//		}
//		log.debug("getServiceAuthorizationStatusForParichay method returns boolean value for service authorization :"
//				+ serviceAuthorizationStatus);
//		return serviceAuthorizationStatus;
//	}
//
//	public static String getClientIp(HttpServletRequest request) {
//		log.debug("getClientIp method called");
//		String remoteAddr = "";
//
//		if (request != null) {
//			remoteAddr = request.getHeader("X-FORWARDED-FOR");
//			log.debug("remoteAddr : " + remoteAddr);
//			if (remoteAddr == null || "".equals(remoteAddr)) {
//				remoteAddr = request.getRemoteAddr();
//			}
//		}
//
//		log.debug("getClientIp method returns remote client ip:" + remoteAddr);
//		return remoteAddr;
//	}
//
//	// CHECK FOR NEW DEVICE AND SEND ALERT
//	public static JSONObject userMappedBrowserIdAndAlertImpl(HttpServletRequest request, MongoRepositoryImpl mongoObj,
//			JSONObject responseObj, String brwId, String parichayId, String isAlertOn, String primaryEmailId,
//			String primaryMobielNo, String firstName, String lastName, String postSID, String serviceId,
//			String serviceName, String serviceState, String loginId, String mobileNo, String uid) {
//		log.debug("userMappedBrowserIdAndAlertImpl called");
//		log.debug("BrowserId: " + brwId + "\nParichayId: " + parichayId + "\tIsAlertOn: " + isAlertOn + "\tFirstName: "
//				+ firstName + "\tLastName: " + lastName + "\tPrimary EmailId: " + primaryEmailId
//				+ "\tPrimary MobileNo: " + primaryMobielNo);
//
//		// COMMONLY USED OBJECTS
//		Map<String, Object> criteriaMap = null;
//		Map<String, Object> fieldMap = null;
//		JSONObject jsonRes = new JSONObject();
//		MongoCursor<Document> mongoCursor = null;
//
//		try (RabbitMQRepositoryImpl rabbitMQObj = new RabbitMQRepositoryImpl();) {
//
//			// CHECK BROWSER ID EXISTANCE W.R.T USER
//			criteriaMap = new HashMap<>();
//			criteriaMap.put("parichayId", parichayId);
//			criteriaMap.put("brwId", brwId);
//			mongoCursor = mongoObj.getCursor(
//					ResourceBundle.getBundle("mongo").getString("USER_MAPPED_BROWSER_ID_COLLECTION"), criteriaMap);
//
//			if (mongoCursor.hasNext()) {
//				log.debug("BrowserId has used previously");
//
//				// NO NEED TO CHECK FOR NEW DEVICE ALERT
//				jsonRes.put("status", "success");
//				jsonRes.put("reason", "exists");
//
//			} else {
//				log.debug("BrowserId has not used previously");
//
//				// INSERT NEW BROWSER ID
//				criteriaMap.remove("brwId");
//				fieldMap = new HashMap<>();
//				fieldMap.put("brwId", brwId);
//				Date isoDate = Utility.getISODateTime();
//				fieldMap.put("createdAt", isoDate);
//				fieldMap.put("updatedAt", isoDate);
//				isoDate = null;
//
//				if (mongoObj.upsertMultipleFields(
//						ResourceBundle.getBundle("mongo").getString("USER_MAPPED_BROWSER_ID_COLLECTION"), criteriaMap,
//						fieldMap)) {
//					log.debug("Successfully inserted in mongodb");
//
//					// NOW CHECK FOR NEW DEVICE ALERT
//					if (isAlertOn.equals("1")) {
//						log.debug("Send alert");
//
//						if (!primaryEmailId.equals("")) {
//							log.debug("Send new device alert on emailId");
//
//							if (rabbitMQObj.sentOtpOnEmail(request, primaryEmailId, firstName + " " + lastName + "@@"
//									+ Utility.getOSFromUserAgent(getUserAgent(request)) + "@@"
//									+ Utility.getBrowserFromUserAgent(getUserAgent(request)) + "@@"
//									+ Utility.getClientIp(request) + "@@" + rbAPP.getString("USER_DEFAULT_COUNTRY"),
//									8)) {
//								jsonRes.put("status", "success");
//								jsonRes.put("reason", "sent");
//
//								/* LOG EVENT STARTS */
//								// ParichayLogEvent logEventObj = new ParichayLogEvent();
//								// ResourceBundle rbLog = ResourceBundle.getBundle("logevent");
//								//
//								// logEventObj.setEventName(rbLog.getString("Event_LoginAlertFromNewdevice"));
//								// logEventObj.setEventType(rbLog.getString("Event_LoginAlertFromNewdeviceJANP_Type"));
//								// logEventObj.setBrowserID(brwId);
//								// logEventObj.setTokenID(postSID);
//								// logEventObj.setReason("User logged in from new device");
//								// logEventObj.setServiceId(serviceId);
//								// logEventObj.setServiceName(serviceName);
//								// logEventObj.setServiceState(serviceState);
//								// logEventObj.setUserName(loginId);
//								// logEventObj.setUId(uid);
//								// logEventObj.setSessionID(postSID);
//								// logEventObj.setEmailId(primaryEmailId);
//								// logEventObj.setMobileNo(mobileNo);
//								//
//								// String lang = "en";
//								// try {
//								//
//								// lang = request.getParameter("lang");
//								// if (lang == null) {
//								// lang = "en";
//								// }
//								//
//								// logEventObj.setLang(lang);
//								// } catch (Exception e) {
//								// logEventObj.setLang("en");
//								// } finally {
//								// lang = null;
//								// }
//								//
//								// Utility.parichayLogGenerator(logEventObj, request);
//								// // CLOSE THE RESOURCES
//								// logEventObj = null;
//								// rbLog = null;
//
//								/* LOG EVENT ENDS */
//
//							} else {
//								jsonRes.put("status", "success");
//								jsonRes.put("reason", "Internal Server Error");
//							}
//						} else {
//							log.debug("Send new device alert on mobileNo");
//							if (rabbitMQObj.sentOtpOnMobile(request, primaryMobielNo, firstName + " " + lastName + "@@"
//									+ Utility.getOSFromUserAgent(Utility.getUserAgent(request)), 8)) {
//								jsonRes.put("status", "success");
//								jsonRes.put("reason", "sent");
//
//								/* LOG EVENT STARTS */
//								// ParichayLogEvent logEventObj = new ParichayLogEvent();
//								// ResourceBundle rbLog = ResourceBundle.getBundle("logevent");
//								//
//								// logEventObj.setEventName(rbLog.getString("Event_LoginAlertFromNewdevice"));
//								// logEventObj.setEventType(rbLog.getString("Event_LoginAlertFromNewdeviceJANP_Type"));
//								// logEventObj.setBrowserID(brwId);
//								// logEventObj.setTokenID(postSID);
//								// logEventObj.setReason("User logged in from new device");
//								// logEventObj.setServiceId(serviceId);
//								// logEventObj.setServiceName(serviceName);
//								// logEventObj.setServiceState(serviceState);
//								// logEventObj.setUId(uid);
//								// logEventObj.setEmailId(primaryEmailId);
//								// logEventObj.setMobileNo(mobileNo);
//								//
//								// String lang = "en";
//								// try {
//								//
//								// lang = request.getParameter("lang");
//								// if (lang == null) {
//								// lang = "en";
//								// }
//								//
//								// logEventObj.setLang(lang);
//								// } catch (Exception e) {
//								// logEventObj.setLang("en");
//								// } finally {
//								// lang = null;
//								// }
//								// Utility.parichayLogGenerator(logEventObj, request);
//								// // CLOSE THE RESOURCES
//								// logEventObj = null;
//								// rbLog = null;
//
//								/* LOG EVENT ENDS */
//							} else {
//								jsonRes.put("status", "success");
//								jsonRes.put("reason", "Internal Server Error");
//							}
//						}
//					} else {
//						log.debug("Alert is not enabled");
//						jsonRes.put("status", "success");
//						jsonRes.put("reason", "Upserted");
//
//					}
//
//				} else {
//					log.debug("Error occurred while inserting in User Mapped  collection");
//					jsonRes.put("status", "failure");
//					jsonRes.put("reason", "Internal Server Error");
//
//				}
//			}
//
//			return jsonRes;
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("Error occurred in userMappedBrowserIdAndAlertImpl method: " + e.getClass().getCanonicalName());
//			jsonRes.put("status", "failure");
//			jsonRes.put("reason", e.getMessage());
//			return jsonRes;
//		} finally {
//			log.debug("userMappedBrowserIdAndAlertImpl finally called");
//			// CLOSE THE RESOURCES
//			criteriaMap = null;
//			fieldMap = null;
//			jsonRes = null;
//			mongoCursor = null;
//			// FORCE GC
//
//		}
//
//	}
//
//	// Generic function to convert set to list
//	public static List<String> convertSetToList(Set<String> set) {
//		// create an empty list
//		List<String> list = new ArrayList<>();
//
//		// push each element in the set into the list
//		for (String s : set)
//			list.add(s);
//
//		// return the list
//		log.debug("convertSetToList method returns list :" + list);
//		return list;
//	}
//
//	// SEND OTP THORUGH GIMS
//	@SuppressWarnings("resource")
//	public static boolean sendNotificationThroughGIMS(HttpServletRequest request, String message, String receipentId)
//			throws IOException, HttpException, FilesException, InvocationTargetException, KeyManagementException,
//			URISyntaxException {
//		log.debug("senNotificationThroughGIMS called");
//		log.debug("Message: " + message + "\tReceipent Id: " + receipentId);
//
//		ThreadContext.put("ip", Utility.getIPAddress((HttpServletRequest) request));
//
//		// COMMLONLY USED OBJECTS
//		String gimsBaseUrl = null;
//		String gimsCompleteURL = null;
//		String apiResponse = null;
//		JSONObject apiResponseJSON = null;
//		HttpClient httpClient = null;
//		HttpResponse clientResponse = null;
//		JSONParser parser = null;
//		HttpGet httpGet = null;
//
//		try {
//			ResourceBundle rbForGims = ResourceBundle.getBundle("gims");
//
//			gimsBaseUrl = rbForGims.getString("GIMS_SEND_NOTIFICATION_BASE_URL");
//			gimsCompleteURL = gimsBaseUrl + "?receiverid=" + receipentId + "&msg=" + message
//					+ rbForGims.getString("GIMS_SEND_NOTIFICATION_QUERY_PARAM");
//			log.debug("GIMS URL: " + gimsCompleteURL);
//
//			try {
//
//				httpGet = new HttpGet(gimsCompleteURL);
//
//				httpClient = new DefaultHttpClient();
//				clientResponse = httpClient.execute(httpGet);
//				try {
//					apiResponse = EntityUtils.toString(clientResponse.getEntity());
//					log.debug("API response:- " + apiResponse);
//
//					// CONVERT RESPONSE STRING TO JSON ARRAY
//					parser = new JSONParser();
//					apiResponseJSON = (JSONObject) parser.getJSONObject(apiResponse);
//
//				} catch (Exception e) {
//					e.printStackTrace();
//					log.error("GIMS API is unreachable");
//					return false;
//
//				}
//				switch (apiResponseJSON.get("status").toString()) {
//				case "failure":
//					log.error("Unable to send message through GIMS Gateway");
//					log.error("Reason: " + apiResponseJSON.get("message").toString() + "\tCode: "
//							+ apiResponseJSON.get("code").toString());
//
//					return false;
//				case "success":
//					log.debug("Message successfully sent through GIMS Gateway");
//					return true;
//				default:
//					log.error("Unrecognised response from GIMS server");
//					return false;
//				}
//
//			} catch (Exception e) {
//				log.error("Error occured while calling GIMS API: " + e.getClass().getCanonicalName());
//				e.printStackTrace();
//				return false;
//			} finally {
//				// CLOSE THE RESOURCES
//				httpGet = null;
//				httpClient = null;
//				clientResponse = null;
//				parser = null;
//
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("Error occurred while sending message through GIMS Gateway: " + e.getMessage());
//			return false;
//		}
//
//	}
//
//	public static String getUserLogonId(String entryDn) {
//		try {
//
//			// String entryDn="entryDN: uid=usermfid,ou=people,dc=zimbra,dc=auth,dc=local";
//			// entryDn = entryDn.split(":")[1].trim();
//			// System.out.println("entryDn : "+entryDn);
//			String[] dnArray = entryDn.split(",");
//			StringBuffer buffer = new StringBuffer();
//			for (String val : dnArray) {
//				if (val.contains("uid=")) {
//					buffer.append(val.split("=")[1]);
//					buffer.append("#");
//				} else {
//					buffer.append(val.split("=")[1]);
//					buffer.append(".");
//				}
//			}
//			// System.out.println("buffer=="+buffer.substring(0, buffer.length()-1));
//			return buffer.substring(0, buffer.length() - 1);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "";
//		}
//
//	}
//
//	static ResourceBundle rbAPP = ResourceBundle.getBundle("app");
//
//	public static boolean isFileExists(String fileName) {
//		log.debug("isFileExists called: " + fileName);
//
//		ResourceBundle rbForConfig = ResourceBundle.getBundle("config");
//
//		File f = null;
//		try {
//			log.debug("Path: " + rbForConfig.getString("IMAGE_PATH") + fileName + ".png");
//			// Get the file
//			f = new File(rbForConfig.getString("IMAGE_PATH") + fileName + ".png");
//
//			// Check if the specified file
//			// Exists or not
//			if (f.exists()) {
//				log.debug("Exists");
//				return true;
//			} else {
//				log.debug("Does not Exists");
//				return false;
//			}
//
//		} catch (Exception e) {
//			return false;
//		} finally {
//			f = null;
//		}
//	}
//
//	@SuppressWarnings("resource")
//	public static boolean logoutCallBack(HttpServletRequest request, HttpServletResponse response,
//			RedisRepositoryImpl redisObj, Map<String, String> postLoginSessionMap, String logoutCallBackKey,
//			String currentServiceId, String postSID, String brwID) {
//		log.debug("Logout callback called");
//		log.debug("Post Login Session Map: " + postLoginSessionMap);
//		log.debug("Logout Call Back Key: " + logoutCallBackKey);
//		log.debug("Cuurent Service Id: " + currentServiceId);
//		log.debug("Post SId: " + postSID);
//		log.debug("BrowserId: " + brwID);
//
//		// COMMONY USED OBJECTS
//		Map<String, String> logoutCallBackMap = new HashMap<>();
//		List<JSONObject> logoutCallBackList = new ArrayList<>();
//		JSONObject serviceJSON = null;
//		String[] detailsArray = null;
//
//		try {
//			logoutCallBackMap = redisObj.getAllValuesFromHashSet(logoutCallBackKey);
//			log.debug("Logout Call Back Map: " + logoutCallBackMap);
//
//			if (logoutCallBackMap == null || logoutCallBackMap.isEmpty()) {
//				log.debug("Logout callback mapping not exists");
//				return true;
//			}
//
//			for (Map.Entry<String, String> entry : logoutCallBackMap.entrySet()) {
//				log.debug("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//				if (!entry.getKey().equals(currentServiceId)) {
//					serviceJSON = new JSONObject();
//
//					detailsArray = entry.getValue().split("@");
//
//					serviceJSON.put("serviceName", detailsArray[0]);
//					serviceJSON.put("loginLocalToken", detailsArray[1]);
//					serviceJSON.put("restAuthId", detailsArray[2]);
//					serviceJSON.put("serviceSecretKey", detailsArray[3]);
//					serviceJSON.put("logoutAPI", detailsArray[4]);
//
//					logoutCallBackList.add(serviceJSON);
//					serviceJSON = null;
//
//				} // ELSE CONTINUE
//
//			}
//			log.debug("Logout Call Back List: " + logoutCallBackList);
//
//			// COMMON VARIABLES
//			HttpPost httpPost = null;
//			StringEntity entity = null;
//			long clientTimeStamp = 0L;
//			String hmacString = null;
//			httpPost = new HttpPost();
//			httpPost.setHeader("Content-type", "application/json");
//			String clientSignature = null;
//			JSONObject apiResponseJSON = null;
//			HttpClient httpClient = null;
//			HttpResponse clientResponse = null;
//			String apiResponse = null;
//			JSONParser parser = null;
//			ResourceBundle rbAPP = ResourceBundle.getBundle("app");
//
//			// MAKE REQUEST BODY
//			// httpPost = new
//			// HttpPost(ResourceBundle.getBundle("config").getString("GOLANG_BASED_API_BASE_URL")
//			// + rbAPP.getString("LOGOUT_CALL_BACK_URL"));
//			httpPost = new HttpPost(ResourceBundle.getBundle("config").getString("GOLANG_BASED_API_BASE_URL")
//					+ rbAPP.getString("LOGOUT_CALL_BACK_URL"));
//
//			// requestBody = "{\"serviceLoginList\":\"" + logoutCallBackList +
//			// "\",\"userName\":\""
//			// + postLoginSessionMap.get("userId") + "\",\"sessionId\":\"" + postSID +
//			// "\",\"browserId\":\""
//			// + brwID + "\",\"sessionStartTime\":\"" +
//			// postLoginSessionMap.get("sessionStartTime") + "\"}";
//			JSONObject requestBodyJSON = new JSONObject();
//			requestBodyJSON.put("serviceLoginList", logoutCallBackList);
//			requestBodyJSON.put("userName", postLoginSessionMap.get("userId"));
//			requestBodyJSON.put("sessionId", postSID);
//			requestBodyJSON.put("browserId", brwID);
//			requestBodyJSON.put("sessionStartTime", postLoginSessionMap.get("sessionStartTime"));
//
//			log.debug("Request Body: " + requestBodyJSON);
//
//			entity = new StringEntity(requestBodyJSON.toString());
//			httpPost.setEntity(entity);
//
//			clientTimeStamp = System.currentTimeMillis();
//			log.debug("Client TimeStamp: " + clientTimeStamp);
//
//			hmacString = rbAPP.getString("LOGOUT_CALL_BACK_SERVICE_NAME") + clientTimeStamp
//					+ ResourceBundle.getBundle("config").getString("GOLANG_BASED_API_BASE_URL")
//					+ rbAPP.getString("LOGOUT_CALL_BACK_URL") + postLoginSessionMap.get("userId") + postSID + brwID
//					+ postLoginSessionMap.get("sessionStartTime");
//			log.debug("HMAC String: " + hmacString);
//
//			// CREATE CLIENT SIGNATURE
//			clientSignature = HMACAuthentication.calculateHMAC(rbAPP.getString("HMAC_KEY"), hmacString);
//			log.debug("Client Signature: " + clientSignature);
//
//			// SET REQUEST HEADERS
//			httpPost.setHeader("Accept", "application/json");
//			httpPost.setHeader("Content-Type", "application/json");
//			httpPost.setHeader("RestAuthID", rbAPP.getString("HMAC_REST_ID"));
//			httpPost.setHeader("TTL", String.valueOf(clientTimeStamp));
//			httpPost.setHeader("Signature", clientSignature);
//
//			try {
//				// httpClient = new DefaultHttpClient();
//				// httpClient = new DefaultHttpClient();
//				DefaultHttpClient httpClient1 = new DefaultHttpClient();
//				httpClient = HttpSecureCon.wrapClient(httpClient1);
//				clientResponse = httpClient.execute(httpPost);
//
//				try {
//					apiResponse = EntityUtils.toString(clientResponse.getEntity());
//					log.debug("API response:- " + apiResponse);
//
//					// CONVERT RESPONSE STRING TO JSON ARRAY
//					parser = new JSONParser();
//					apiResponseJSON = (JSONObject) parser.getJSONObject(apiResponse);
//
//					// FOR GC
//					parser = null;
//
//				} catch (Exception e) {
//					log.error("Timeout occurs w.r.t logout call back API");
//					return false;
//
//				}
//			} catch (Exception e) {
//				log.error("Error occured while calling API: " + e.getClass().getCanonicalName());
//				e.printStackTrace();
//				return false;
//
//			} finally {
//				// CLOSE THE RESOURCES
//				httpPost = null;
//				entity = null;
//				hmacString = null;
//				clientSignature = null;
//				httpClient = null;
//				clientResponse = null;
//				parser = null;
//				rbAPP = null;
//
//			}
//
//			// CHECK API RESPONSE
//			switch (apiResponseJSON.get("status").toString()) {
//			case "SUCCESS":
//				log.info("Logout callback success");
//				return true;
//
//			case "FAILURE":
//				log.info("Logout callback failure: " + apiResponseJSON.get("msg").toString());
//				return true;
//
//			default:
//				log.error("Unknown response from logout callback API");
//				return false;
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("Error occurred during logout callback: " + e.getMessage());
//			return false;
//		} finally {
//			logoutCallBackMap = null;
//			logoutCallBackList = null;
//			serviceJSON = null;
//			detailsArray = null;
//		}
//
//	}
//
//	public static List<String> generateListOfSixDigitRandomNos(int sizeList) {
//		log.debug("generateListOfSixDigitRandomNos() method called with sizeList:" + sizeList);
//		List<String> backupCodesList = null;
//		try {
//			backupCodesList = new ArrayList<String>();
//			for (int i = 0; i < sizeList; i++) {
//				backupCodesList.add(i, genrateRandomNumber(6));
//			}
//		} catch (Exception e) {
//			log.debug("Escape Exception");
//		}
//
//		log.debug("generateListOfSixDigitRandomNos() method returns List of backup codes : " + backupCodesList);
//
//		return backupCodesList;
//	}
//
//	private static SecureRandom secureRandom = new SecureRandom();
//
//	public static String genrateRandomNumber(final int lengthOfOTP) {
//
//		StringBuilder generatedOTP = new StringBuilder();
//
//		try {
//
//			secureRandom = SecureRandom.getInstance(secureRandom.getAlgorithm());
//
//			for (int i = 0; i < lengthOfOTP; i++) {
//				generatedOTP.append(secureRandom.nextInt(9));
//			}
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//
//		return generatedOTP.toString();
//	}
//
//	// ADDED BY RAJEEV ON 23-09-2021
//	public static String getCapitalizedOfFirstCharInString(String str) {
//		log.debug("getCapitalizedOfFirstCharInString method called with parameter string : " + str);
//		try {
//			log.debug("try");
//			if (!str.isEmpty() && str != null) {
//				log.debug("if");
//				str = str.toLowerCase();
//				log.debug("get string as lower case : " + str);
//				str = str.substring(0, 1).toUpperCase() + str.substring(1);
//				log.debug("getCapitalizedOfFirstCharInString method returns capitalized string : " + str);
//				return str;
//			}
//		} catch (Exception e) {
//			log.debug("Escape Exception");
//		}
//		return null;
//	}
//
//	// ADDED BY RAJEEV ON 23-09-2021
//	public static String getCapitalizedOfFirstCharInStringArray(String strArray[]) {
//		log.debug("getCapitalizedOfFirstCharInString method called with parameter strArray : " + strArray);
//		try {
//			log.debug("try");
//			if (strArray != null) {
//				log.debug("if");
//				String capitalizedStr = "";
//				for (String str : strArray) {
//					capitalizedStr = capitalizedStr + " " + getCapitalizedOfFirstCharInString(str);
//
//				}
//
//				log.debug("getCapitalizedOfFirstCharInString method returns capitalized string : " + capitalizedStr);
//				return capitalizedStr;
//			}
//		} catch (Exception e) {
//			log.debug("Escape Exception");
//		}
//		return null;
//	}
//
//	public static String generateSixDigitRandomNo() {
//		log.debug("generateSixDigitRandomNo() method called");
//		SecureRandom rand = new SecureRandom();
//		int generatecode = 100000 + rand.nextInt(900000);
//		log.debug("Code has generated : " + generatecode);
//		String generatedCode = String.valueOf(generatecode);
//		log.debug("generateSixDigitRandomNo() method returns generate code as string : " + generatedCode);
//
//		return generatedCode;
//	}
//
//	// public Utility() {
//	// System.out.println("Hello");
//	// }
//	// public static void main(String[] args) {
//	// String date = Utility.getCurrentDateTime();
//	// System.out.println(date);
//	//
//	//// Calendar now = Calendar.getInstance();
//	//// now.add(Calendar.MINUTE, 30);
//	//
//	// // Date isoDate = (Date) doc.get("createdAt");
//	// Date isoDate = new Date();
//	// System.out.println("Before: "+isoDate);
//	// Calendar now = Calendar.getInstance();
//	// now.setTime(isoDate);
//	// now.add(Calendar.HOUR, 12);
//	// System.out.println("After: "+now.getTime());
//	//
//	// }
//
//	// CREATE LOG EVENT JSON
//	public static void logGenerator(LogEvent logEventObj, HttpServletRequest httpReq) {
//		log.debug("logGenerator called");
//		log.debug("LogEvent Object: " + logEventObj);
//
//		// COMMONLY USED OBJECT
//		Gson gsonObj = null;
//
//		try {
//
//			logEventObj.setTimeStamp(Utility.getCurrentDateTime());
//			logEventObj.setEventID(String.valueOf(Utility.generateRandomDigits(10)));
//			logEventObj.setIPAddress(Utility.getIPAddress(httpReq));
//			logEventObj.setUserAgent(httpReq.getAttribute("userAgent").toString());
//			logEventObj.setCountry("India");
//			logEventObj.setState(logEventObj.getServiceState());
//			gsonObj = new Gson();
//
//			log.info(gsonObj.toJson(logEventObj));
//		} catch (Exception e) {
//			log.error("Error occured while generating log events: " + e.getClass().getCanonicalName());
//			e.printStackTrace();
//
//		} finally {
//			log.debug("logGenerator finally called");
//
//			// CLOSE THE RESOURCES
//			gsonObj = null;
//			System.gc();
//		}
//	}
//
//	public static int generateRandomDigits(int n) {
//		int m = (int) Math.pow(10, n - 1);
//		return m + new Random().nextInt(9 * m);
//	}
//
//	// CREATE RESEND OTP COUNT KEY
//	public static String getRegistrationResendOTPCountKey(String registrationReqStatus) {
//		return "resendotpcount@" + registrationReqStatus;
//	}
//
//	// added by rajeev on 09-02-2021
//	public static String getRegistrationOTPKey(String key, String registrationReqStatus) {
//		return "otp@" + key + "@" + registrationReqStatus;
//	}
//
//	// CREATE OTP COUNT KEY
//	public static String getRegistrationOTPCountKey(String key, String registrationReqStatus) {
//		return "otpcount@" + key + "@" + registrationReqStatus;
//	}
//
//	// ADDED BY RAJEEV ON 15-11-2021
//	public static String getResendOTPCountKey(String key) {
//		return "resendotpcount@" + key;
//	}
//
//	public static String generateTenDigitRandomNo() {
//		log.debug("generateTenDigitRandomNo() method called");
//		SecureRandom rand = new SecureRandom();
//		long generatecode = 1000000000 + rand.nextLong();
//		log.debug("Code has generated : " + generatecode);
//		String generatedCode = String.valueOf(generatecode);
//		log.debug("generateSixDigitRandomNo() method returns generate code as string : " + generatedCode);
//
//		return generatedCode;
//	}
//
//	// CREATE LOG EVENT JSON
//	public static void logGenerator(LogEvent logEventObj) {
//		log.debug("logGenerator called");
//		log.debug("LogEvent Object: " + logEventObj);
//
//		// COMMONLY USED OBJECTS
//		Gson gsonObj = null;
//		Date today = new Date();
//		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		try {
//			String date = DATE_FORMAT.format(today);
//
//			logEventObj.setTimeStamp(date);
//			logEventObj.setCountry("India");
//			logEventObj.setState(logEventObj.getServiceState());
//
//			// TEMPORARY
//			logEventObj.setUserAgent(logEventObj.getUserAgent());
//			logEventObj.setEventID(String.valueOf(Utility.generateRandomDigits(10)));
//			logEventObj.setLang("en");
//
//			gsonObj = new Gson();
//			log.info(gsonObj.toJson(logEventObj));
//		} catch (Exception e) {
//			log.error("Error occured while generating log events: " + e.getClass().getCanonicalName());
//			e.printStackTrace();
//
//		} finally {
//			log.debug("logGenerator finally called");
//			gsonObj = null;
//			today = null;
//			DATE_FORMAT = null;
//		}
//
//	}
//
//	public static String getISODateString() {
//		String isoDateStr = null;
//		Date date = null;
//
//		try {
//			date = new Date();
//			isoDateStr = ISODate.toISO(date);
//			log.debug("Current Date as ISO Date Format : " + isoDateStr);
//			return isoDateStr;
//		} catch (Exception e) {
//			return null;
//		}
//
//	}
//
//	public static Map<String, Object> getMapObjectFromGenericsJavaObject(Generics modelClass) {
//		log.debug("getMapObjectFromGenericsJavaObject method called");
//
//		Map<String, Object> mapObj = null;
//
//		try {
//			mapObj = new HashMap<String, Object>();
//
//			mapObj.put("createdOn", modelClass.getCreatedOn());
//			mapObj.put("updatedOn", modelClass.getUpdatedOn());
//			mapObj.put("authType", modelClass.getAuthType());
//			mapObj.put("authId", modelClass.getAuthId());
//			mapObj.put("authStatus", String.valueOf(modelClass.getAuthStatus()));
//			mapObj.put("userId", modelClass.getUserId());
//			// mapObj.put("userId", modelClass.);
//			mapObj.put("data", "");
//			mapObj.put("parichayId", modelClass.getParichayId());
//
//		} catch (Exception e) {
//
//		}
//		log.debug("getMapObjectFromGenericsJavaObject method returns map object:" + mapObj);
//
//		return mapObj;
//
//	}
//
//	public static Map<String, Object> createMapObjectFromAuthProfileObject(AuthProfile authProfileObj) {
//		log.debug("createMapObjectFromAuthProfileInterfaceJavaObject method called");
//
//		Map<String, Object> mapObj = null;
//
//		try {
//			mapObj = new HashMap<String, Object>();
//
//			mapObj.put("parichayId", authProfileObj.getParichayId());
//			mapObj.put("createdOn", authProfileObj.getCreatedOn());
//			mapObj.put("updatedOn", authProfileObj.getUpdatedOn());
//			mapObj.put("createdDateAt", Utility.getISODateTime());
//			mapObj.put("updatedDateAt", Utility.getISODateTime());
//			mapObj.put("userId", authProfileObj.getUserId());
//			mapObj.put("userPassword", authProfileObj.getUserPassword());
//			mapObj.put("firstName", authProfileObj.getFirstName());
//
//			if (authProfileObj.getLastName() != null && !authProfileObj.getLastName().isEmpty()) {
//				mapObj.put("lastName", authProfileObj.getLastName());
//			} else {
//				mapObj.put("lastName", "");
//			}
//			try {
//				if (authProfileObj.getFatherName() != null && !authProfileObj.getFatherName().isEmpty()) {
//					mapObj.put("fatherName", authProfileObj.getFatherName());
//				}
//			} catch (Exception e) {
//				mapObj.put("fatherName", "");
//			}
//
//			if (authProfileObj.getMotherName() != null && !authProfileObj.getMotherName().isEmpty()) {
//				mapObj.put("motherName", authProfileObj.getMotherName());
//			}
//			try {
//				if (authProfileObj.getGender().equalsIgnoreCase("M")) {
//					mapObj.put("gender", "Male");
//				} else {
//					if (authProfileObj.getGender().equalsIgnoreCase("F")) {
//						mapObj.put("gender", "Female");
//					} else {
//						mapObj.put("gender", authProfileObj.getGender());
//					}
//				}
//
//			} catch (Exception e) {
//				mapObj.put("gender", authProfileObj.getGender());
//			}
//
//			if (authProfileObj.getDob() != null && !authProfileObj.getDob().isEmpty()) {
//				mapObj.put("dob", authProfileObj.getDob());
//			} else {
//				mapObj.put("dob", "");
//			}
//
//			if (authProfileObj.getPrimaryMobileNo() != null && !authProfileObj.getPrimaryMobileNo().isEmpty()) {
//				mapObj.put("primaryMobileNo", authProfileObj.getPrimaryMobileNo());
//			} else {
//				mapObj.put("primaryMobileNo", "");
//			}
//
//			if (authProfileObj.getSecondaryMobileNo() != null && !authProfileObj.getSecondaryMobileNo().isEmpty()) {
//				mapObj.put("secondaryMobileNo", authProfileObj.getSecondaryMobileNo());
//			} else {
//				mapObj.put("secondaryMobileNo", "");
//			}
//
//			if (authProfileObj.getGurdianMobileNo() != null && !authProfileObj.getGurdianMobileNo().isEmpty()) {
//				mapObj.put("gurdianMobileNo", authProfileObj.getGurdianMobileNo());
//			} else {
//				mapObj.put("gurdianMobileNo", "");
//			}
//
//			if (authProfileObj.getPrimaryEmailId() != null && !authProfileObj.getPrimaryEmailId().isEmpty()) {
//				mapObj.put("primaryEmailId", authProfileObj.getPrimaryEmailId());
//			} else {
//				mapObj.put("primaryEmailId", "");
//			}
//
//			if (authProfileObj.getSecondaryEmailId() != null && !authProfileObj.getSecondaryEmailId().isEmpty()) {
//				mapObj.put("secondaryEmailId", authProfileObj.getSecondaryEmailId());
//			} else {
//				mapObj.put("secondaryEmailId", "");
//			}
//
//			try {
//				if (authProfileObj.getPresentAddress() != null && !authProfileObj.getPresentAddress().isEmpty()) {
//					mapObj.put("presentAddress", authProfileObj.getPresentAddress());
//				}
//			} catch (Exception e) {
//				mapObj.put("presentAddress", "");
//			}
//			try {
//				if (authProfileObj.getPresentCountry() != null && !authProfileObj.getPresentCountry().isEmpty()) {
//					mapObj.put("presentCountry", authProfileObj.getPresentCountry());
//				}
//			} catch (Exception e) {
//				mapObj.put("presentCountry", "");
//			}
//
//			try {
//				if (authProfileObj.getPresentSate() != null && !authProfileObj.getPresentSate().isEmpty()) {
//					mapObj.put("presentState", authProfileObj.getPresentSate());
//				}
//			} catch (Exception e) {
//				mapObj.put("presentState", "");
//			}
//			try {
//				if (authProfileObj.getPresentDist() != null && !authProfileObj.getPresentDist().isEmpty()) {
//					mapObj.put("presentDist", authProfileObj.getPresentDist());
//				}
//			} catch (Exception e) {
//				mapObj.put("presentDist", "");
//			}
//			try {
//				if (authProfileObj.getPresentPin() != null && !authProfileObj.getPresentPin().isEmpty()) {
//					mapObj.put("presentPin", authProfileObj.getPresentPin());
//				}
//			} catch (Exception e) {
//				mapObj.put("presentPin", "");
//			}
//			try {
//				if (authProfileObj.getPresentDist() != null && !authProfileObj.getPresentDist().isEmpty()) {
//					mapObj.put("presentDist", authProfileObj.getPresentDist());
//				}
//
//			} catch (Exception e) {
//				mapObj.put("presentDist", "");
//			}
//			try {
//				if (authProfileObj.getPresentPin() != null && !authProfileObj.getPresentPin().isEmpty()) {
//					mapObj.put("presentPin", authProfileObj.getPresentPin());
//				}
//			} catch (Exception e) {
//				mapObj.put("presentPin", "");
//			}
//			// try {
//			// if (authProfileObj.getPermanentAddress() != null &&
//			// !authProfileObj.getPermanentAddress().isEmpty()) {
//			// mapObj.put("permanentAddress", authProfileObj.getPermanentAddress());
//			//
//			// }
//			// } catch (Exception e) {
//			// mapObj.put("permanentAddress", "");
//			// }
//			//
//			// try {
//			// if (authProfileObj.getPresentCountry() != null &&
//			// !authProfileObj.getPresentCountry().isEmpty()) {
//			// mapObj.put("permanentCountry", authProfileObj.getPresentCountry());
//			// }
//			// } catch (Exception e) {
//			// mapObj.put("permanentCountry", "");
//			// }
//			// try {
//			// if (authProfileObj.getPermanentSate() != null &&
//			// !authProfileObj.getPermanentSate().isEmpty()) {
//			// mapObj.put("persentState", authProfileObj.getPermanentSate());
//			// }
//			// } catch (Exception e) {
//			// mapObj.put("persentState", "");
//			// }
//			// try {
//			// if (authProfileObj.getPermanentDist() != null &&
//			// !authProfileObj.getPermanentDist().isEmpty()) {
//			// mapObj.put("permanentDist", authProfileObj.getPermanentDist());
//			// }
//			// } catch (Exception e) {
//			// mapObj.put("permanentDist", "");
//			// }
//			// try {
//			// if (authProfileObj.getPermanentPin() != null &&
//			// !authProfileObj.getPermanentPin().isEmpty()) {
//			// mapObj.put("permanentPin", authProfileObj.getPermanentPin());
//			// }
//			// } catch (Exception e) {
//			// mapObj.put("permanentPin", "");
//			// }
//			if (authProfileObj.getProfilePic() != null && !authProfileObj.getProfilePic().isEmpty()) {
//				mapObj.put("profilePic", authProfileObj.getProfilePic());
//			}
//			if (authProfileObj.getProfileType() != null && !authProfileObj.getProfileType().isEmpty()) {
//				mapObj.put("profileType", authProfileObj.getProfileType());
//			}
//			mapObj.put("profileStatus", String.valueOf(authProfileObj.getProfileStatus()));
//			try {
//				if (authProfileObj.getAadharVerifiedStatus() == 1) {
//					// mapObj.put("profileRegisteredBy",
//					// Utility.hideStringBySomeCharacters(authProfileObj.getProfileRegisteredBy(),
//					// "*", 2, 2));
//					mapObj.put("profileRegisteredBy", "Aadhaar");
//				} else {
//					mapObj.put("profileRegisteredBy", authProfileObj.getProfileRegisteredBy());
//				}
//			} catch (Exception e) {
//				log.debug("Ignore the esception");
//			}
//
//			mapObj.put("twoStepOnPhone", String.valueOf(authProfileObj.getTwoStepOnPhone()));
//			mapObj.put("twoStepOnEmail", String.valueOf(authProfileObj.getTwoStepOnEmail()));
//			mapObj.put("loginFromNewDeviceAlert", "1");
//
//			if (authProfileObj.getSeal() != null && !authProfileObj.getSeal().isEmpty()) {
//				mapObj.put("seal", authProfileObj.getSeal());
//			}
//
//			if (authProfileObj.getDesignation() != null && !authProfileObj.getDesignation().isEmpty()) {
//				mapObj.put("designation", authProfileObj.getDesignation());
//			} else {
//				mapObj.put("designation", "");
//			}
//
//			if (authProfileObj.getUserSeed() != null && !authProfileObj.getUserSeed().isEmpty()) {
//				mapObj.put("userSeed", authProfileObj.getUserSeed());
//			} else {
//				mapObj.put("userSeed", "");
//			}
//
//			mapObj.put("parichayAuthenticator", String.valueOf(authProfileObj.getParichayAuthenticator()));
//			// mapObj.put("loginType", authProfileObj.getLoginType());
//			// mapObj.put("loginId", authProfileObj.getLoginId());
//			mapObj.put("isPasswordLess", 0);
//			if (authProfileObj.getSealType() != null && !authProfileObj.getSealType().isEmpty()) {
//				mapObj.put("sealType", authProfileObj.getSealType());
//			}
//			mapObj.put("plOnPhone", "0");
//			mapObj.put("plOnEmail", "0");
//
//			// ADDED BY RAJEEV ON 01-09-2020 FOR GEO-FENCING
//			mapObj.put("IsBackupCodeEnable", authProfileObj.getIsBackupCodeEnable());
//			mapObj.put("geoFencingType", authProfileObj.getGeoFencingType());
//			mapObj.put("IsGeoFencingEnable", authProfileObj.getIsGeoFencingEnable());
//			mapObj.put("IsProfileSealEnable", authProfileObj.getIsProfileSealEnable());
//			mapObj.put("IsTwoFactorEnable", authProfileObj.getIsTwoFactorEnable());
//			mapObj.put("isAuthAppEnable", 0);
//			mapObj.put("isAuthAppConfigure", 0);
//
//			try {
//				if (authProfileObj.getAadharVerifiedStatus() == 1 || authProfileObj.getDlVerifiedStatus() == 1
//						|| authProfileObj.getPanVerifiedStatus() == 1) {
//					if (authProfileObj.getAadharVerifiedStatus() == 1) {
//						mapObj.put("aadharVerifiedStatus", authProfileObj.getAadharVerifiedStatus());
//						mapObj.put("aadharVerifiedDate", Utility.getISODateTime());
//						mapObj.put("aadharNo", authProfileObj.getAadhaarNo());
//						mapObj.put("hideAadhaar", authProfileObj.getHideAadhaar());
//
//					}
//					if (authProfileObj.getPanVerifiedStatus() == 1) {
//						mapObj.put("panVerifiedStatus", authProfileObj.getAadharVerifiedStatus());
//						mapObj.put("panVerifiedDate", Utility.getISODateTime());
//					}
//					if (authProfileObj.getDlVerifiedStatus() == 1) {
//						mapObj.put("dlVerifiedStatus", authProfileObj.getAadharVerifiedStatus());
//						mapObj.put("dlVerifiedDate", Utility.getISODateTime());
//					}
//
//					mapObj.put("identifiedUser", 1);
//				} else {
//					mapObj.put("identifiedUser", 0);
//				}
//			} catch (Exception e) {
//				log.debug("Escape Exception");
//			}
//
//		} catch (Exception e) {
//
//		}
//		log.debug("createMapObjectFromAuthProfileInterfaceJavaObject method returns map object:" + mapObj);
//
//		return mapObj;
//
//	}
//
//	private static void elseif(boolean equalsIgnoreCase) {
//		// TODO Auto-generated method stub
//
//	}
//
//	public static String getGIMSOTP(String otp) {
//		StringBuilder input1 = new StringBuilder();
//		try {
//			input1.append(otp);
//
//			input1.reverse();
//			return input1.toString();
//		} catch (Exception e) {
//			return otp;
//		} finally {
//			input1 = null;
//
//		}
//		// print reversed String
//		// System.out.println(input1);
//	}
//
//	// GENERATE LOG EVENTS BASED ON PARICHAY
//	public static void janParichayLogGenerator(LogEvent logEventObj, HttpServletRequest request) {
//		log.debug("janParichayLogGenerator called");
//		log.debug("LogEvent Object: " + logEventObj);
//
//		// COMMONLY USED OBJECTS
//		Gson gsonObj = null;
//		String ip = null;
//
//		JSONObject locationJSON = new JSONObject();
//		Date dateObj = new Date();
//		SimpleDateFormat dateFormat = new SimpleDateFormat(ResourceBundle.getBundle("app").getString("DATE_FORMAT"));
//
//		try {
//
//			ip = Utility.getIPAddress(request);
//
//			ThreadContext.put("ip", ip);
//
//			// dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
//			// System.out.println("Date Format: "+dateFormat.format(dateObj));
//
//			logEventObj.setTimeStamp(dateFormat.format(dateObj) + " IST");
//
//			try {
//				logEventObj.setUserAgent(Utility.getUserAgent(request));
//				logEventObj.setMobileNo(Utility.hideEmailIdBySomeCharacters(logEventObj.getMobileNo(), "*", 4, 4));
//				logEventObj.setEmailId(Utility.hideEmailIdBySomeCharacters(logEventObj.getEmailId(), "*", 4, 4));
//			} catch (Exception e) {
//				logEventObj.setUserAgent("");
//				logEventObj.setMobileNo("");
//				logEventObj.setEmailId("");
//			}
//
//			logEventObj.setIPAddress(ip);
//			logEventObj.setEventID(String.valueOf(genrateRandomNumber(10)));
//			locationJSON = LocationUtility.getLocation(ip, locationJSON);
//			logEventObj.setState(locationJSON.getString("State"));
//			logEventObj.setCity(locationJSON.getString("City"));
//			logEventObj.setCountry(locationJSON.getString("Country"));
//
//			gsonObj = new Gson();
//			log.info(gsonObj.toJson(logEventObj));
//		} catch (Exception e) {
//			log.error("Error occured (parichayLogGenerator) while generating log events: "
//					+ e.getClass().getCanonicalName());
//			e.printStackTrace();
//
//		} finally {
//			log.debug("parichayLogGenerator finally called");
//			gsonObj = null;
//			ip = null;
//			locationJSON = null;
//		}
//
//	}
//
//	public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
//		// Static getInstance method is called with hashing SHA
//		MessageDigest md = MessageDigest.getInstance("SHA-256");
//
//		// digest() method called
//		// to calculate message digest of an input
//		// and return array of byte
//		return md.digest(input.getBytes(StandardCharsets.UTF_8));
//	}
//
//	public static String toHexString(byte[] hash) {
//		// Convert byte array into signum representation
//		BigInteger number = new BigInteger(1, hash);
//
//		// Convert message digest into hex value
//		StringBuilder hexString = new StringBuilder(number.toString(16));
//
//		// Pad with leading zeros
//		while (hexString.length() < 32) {
//			hexString.insert(0, '0');
//		}
//
//		return hexString.toString();
//	}
//
//	// ADDED BY RAJEEV ON 29-10-2021
//
//	public static String getEncryptedString(String plainText, String aesKey)
//			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
//			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//		log.debug(
//				"getEncryptedString method called with parameter plainText : " + plainText + " and aesKey : " + aesKey);
//
//		ResourceBundle rbForEncryption = ResourceBundle.getBundle("encryption");
//		String encrypAlgo = null;
//		String initVector = null;
//		try {
//			encrypAlgo = rbForEncryption.getString("AES_ALGORITHM");
//			initVector = rbForEncryption.getString("AES_INITVECTOR");
//
//			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//			SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes("UTF-8"), "AES");
//
//			Cipher cipher = Cipher.getInstance(encrypAlgo);
//			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//
//			byte[] encrypted = cipher.doFinal(plainText.getBytes());
//
//			log.debug("getEncryptedString method called returns encrypted string : "
//					+ Base64.encodeBase64String(encrypted));
//			return Base64.encodeBase64String(encrypted);
//		} catch (Exception e) {
//			log.debug("getEncryptedString method called returns encrypted string  as null ");
//			return null;
//		}
//
//	}
//
//	/*
//	 * public static String getDecryptedString(String encryptedStr, String
//	 * secretKey, String ivKey) {
//	 * log.debug("getDecryptedString method called with parameter encryptedStr : " +
//	 * encryptedStr + " , secretKey : " + secretKey + " and ivKey : " + ivKey);
//	 * ResourceBundle rbForEncryption = ResourceBundle.getBundle("encryption");
//	 * 
//	 * String original = null;
//	 * 
//	 * try {
//	 * 
//	 * log.debug("Base64 encryptedStr : "+Base64.decodeBase64(encryptedStr));
//	 * log.debug("Base64 secretKey : "+Base64.decodeBase64(secretKey));
//	 * log.debug("Base64 ivKey : "+Base64.decodeBase64(ivKey));
//	 * 
//	 * byte[] encryptedByteData = AES.hexStringToByteArray(encryptedStr);
//	 * 
//	 * IvParameterSpec ivs = new IvParameterSpec(Base64.decodeBase64(ivKey)); Key
//	 * key = new SecretKeySpec(Base64.decodeBase64(secretKey),"AES");
//	 * 
//	 * // original = AES.decrypt(Base64.encodeBase64String(encryptedByteData), k,
//	 * ivs); // System.out.println("original Text : " + original); Cipher cipher =
//	 * Cipher.getInstance(rbForEncryption.getString("AES_ALGORITHM"));
//	 * cipher.init(Cipher.DECRYPT_MODE, key, ivs);
//	 * 
//	 * byte[] stringBytes = cipher.doFinal(Base64.decodeBase64(encryptedByteData));
//	 * original = stringBytes.toString();
//	 * 
//	 * log.debug("getDecryptedString method called returns decrypted string : " +
//	 * original.toString()); return original.toString();
//	 * 
//	 * } catch (Exception ex) { ex.printStackTrace(); return null; } finally {
//	 * original = null; // encrypAlgo = null; }
//	 * 
//	 * }
//	 */
//
//	public static String getDecryptedDataByAES(String encryptedData)
//			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
//			BadPaddingException, InvalidAlgorithmParameterException, IOException {
//		log.debug("getDecryptedDataByAES method called with parameter encryptedData : " + encryptedData);
//		String decryptValue = null;
//		String data[] = null;
//		String iv = null;
//		String keyString = null;
//		byte[] encryptedByteData = null;
//		IvParameterSpec ivs = null;
//		Key k = null;
//		try {
//			data = encryptedData.split(":");
//			iv = data[0];
//			encryptedByteData = AES.hexStringToByteArray(data[1]);
//			keyString = data[2];
//
//			ivs = new IvParameterSpec(Base64.decodeBase64(iv));
//			k = new SecretKeySpec(Base64.decodeBase64(keyString), "AES");
//			// k = new SecretKeySpec(AES.hexStringToByteArray(keyString), "AES");
//
//			decryptValue = AES.decrypt(Base64.encodeBase64String(encryptedByteData), k, ivs);
//			// log.debug("Decrypted String:" + decryptValue);
//			return decryptValue;
//		} catch (Exception e) {
//			log.error("Exception occured durng decryption of data by AES in getDecryptedDataByAES method");
//			e.printStackTrace();
//			return null;
//		} finally {
//			decryptValue = null;
//			data = null;
//			iv = null;
//			keyString = null;
//			encryptedByteData = null;
//			ivs = null;
//			k = null;
//		}
//	}
//
//	public static String getEncryptedString(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException,
//			InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//		log.debug("getEncryptedString method called with parameter plainText : " + plainText);
//
//		ResourceBundle rbForEncryption = ResourceBundle.getBundle("encryption");
//		String encrypAlgo = null;
//		String initVector = null;
//		String aesKey = null;
//		try {
//			encrypAlgo = rbForEncryption.getString("AES_ALGORITHM");
//			initVector = rbForEncryption.getString("AES_INITVECTOR");
//			aesKey = rbForEncryption.getString("AES_KEY");
//
//			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//			SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes("UTF-8"), "AES");
//
//			Cipher cipher = Cipher.getInstance(encrypAlgo);
//			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//
//			byte[] encrypted = cipher.doFinal(plainText.getBytes());
//			return Base64.encodeBase64String(encrypted);
//		} catch (Exception e) {
//			log.debug("getEncryptedString method called returns encrypted string  as null ");
//			return null;
//		}
//
//	}
//
//	public static String getDecryptedString(String encryptedStr) {
//		log.debug("getDecryptedString method called with parameter encryptedStr : " + encryptedStr);
//		ResourceBundle rbForEncryption = ResourceBundle.getBundle("encryption");
//		String encrypAlgo = null;
//		String initVector = null;
//		String aesKey = null;
//		try {
//			encrypAlgo = rbForEncryption.getString("AES_ALGORITHM");
//			initVector = rbForEncryption.getString("AES_INITVECTOR");
//			aesKey = rbForEncryption.getString("AES_KEY");
//
//			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//			SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes("UTF-8"), "AES");
//
//			Cipher cipher = Cipher.getInstance(encrypAlgo);
//			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//			byte[] original = cipher.doFinal(Base64.decodeBase64(encryptedStr));
//
//			log.debug("getDecryptedString method called returns decrypted string : " + original.toString());
//			return original.toString();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		log.debug("getDecryptedString method called returns encrypted string  as null ");
//		return null;
//	}
//
//	// RETURN SHA256 FORM OF A STRING
//	public static String getSha256(String txt) throws NoSuchAlgorithmException {
//		return toHexString(getSHA(txt));
//	}
//
//	public static String getSha512(String input) throws NoSuchAlgorithmException {
//
//		MessageDigest md = MessageDigest.getInstance("SHA-512");
//
//		return toHexString(md.digest(input.getBytes(StandardCharsets.UTF_8)));
//	}
//
//	public static String getOTPLockoutKey(String preSID) {
//		return "otpLock@" + preSID;
//	}
//
//	public static String servicePlusUserAuthentication(String userName, String signal, String saltKey) {
//		log.debug("servicePlusAuthentication called");
//		log.debug("Username: " + userName);
//		log.debug("Signal: " + signal);
//		log.debug("Salt Key: " + saltKey);
//
//		String dataToSign = null;
//		String restAPIURL = null;
//		String restApiRespAsJsonStr = "failure";
//		String postParams = null;
//		HttpClient httpClient = null;
//		StringEntity entity = null;
//		JSONObject apiResponseJSON = null;
//		HttpResponse clientResponse = null;
//		String apiResponse = null;
//		JSONParser parser = null;
//		HttpPost httpPost = null;
//
//		ResourceBundle rbConfig = ResourceBundle.getBundle("config");
//		try {
//
//			restAPIURL = rbConfig.getString("SERVICE_PLUS_USER_AUTHENTICATION");
//			httpPost = new HttpPost(restAPIURL);
//
//			// postParams = "{\"userId\":\"" + userName + "\",\"passwd\":\"" +
//			// SPPasswordAlgo.getSPEncryptedPwd(signal)
//			// + "\"}";
//			postParams = "{\"userId\":\"" + userName + "\",\"passwd\":\"" + signal + "\",\"seed\":\"" + saltKey + "\"}";
//			log.debug("Post Params: " + postParams);
//
//			dataToSign = ParichayAESEncryption.encryptIntoHex(postParams, rbConfig.getString("SERVICE_PLUS_SECRET"));
//
//			entity = new StringEntity(dataToSign);
//			httpPost.setEntity(entity);
//
//			// SET REQUEST HEADERS
//			try {
//				httpClient = new DefaultHttpClient();
//				// DefaultHttpClient httpClient1 = new DefaultHttpClient();
//				// httpClient = HttpSecureCon.wrapClient(httpClient1);
//				clientResponse = httpClient.execute(httpPost);
//				try {
//					apiResponse = EntityUtils.toString(clientResponse.getEntity());
//					log.debug("API response: " + apiResponse);
//
//					if (apiResponse.contains("404 page not found")) {
//						return "na";
//					} else {
//
//						// CONVERT RESPONSE STRING TO JSON OBJECT
//						parser = new JSONParser();
//						apiResponseJSON = (JSONObject) parser.getJSONObject(apiResponse);
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//					log.debug("Timeout occurs");
//					return "na";
//
//				}
//			} catch (Exception e) {
//				log.error("Error occured while calling API: " + e.getClass().getCanonicalName());
//				e.printStackTrace();
//				// return "na";
//				return "success";
//
//			} finally {
//				// CLOSE THE RESOURCES
//				httpPost = null;
//				entity = null;
//				httpClient = null;
//				clientResponse = null;
//				parser = null;
//
//			}
//
//			switch (apiResponseJSON.get("status").toString()) {
//
//			case "success":
//				log.debug("Get success response from ServicePlus API");
//				restApiRespAsJsonStr = "success";
//				break;
//
//			case "failure":
//				log.debug("Get failure response from ServicePlus API");
//				restApiRespAsJsonStr = "failure";
//				break;
//
//			case "fail":
//				log.debug("Get failure response from ServicePlus API");
//				restApiRespAsJsonStr = "failure";
//				break;
//			default:
//				log.error("ServicePlus API is not responding");
//				restApiRespAsJsonStr = "na";
//
//			}
//
//			httpClient = null;
//			log.debug("http client connection  has closed ");
//			return restApiRespAsJsonStr;
//
//		} catch (Exception e) {
//			log.error("Error occurred while calling Service API");
//			e.printStackTrace();
//			// return "na";
//			return "success";
//
//		} finally {
//			dataToSign = null;
//			restAPIURL = null;
//			restApiRespAsJsonStr = null;
//			postParams = null;
//			apiResponseJSON = null;
//			apiResponse = null;
//			rbConfig = null;
//
//		}
//
//	}
//
//	public static String odishaUserAuthentication(String userName, String signal) {
//		log.debug("odishaUserAuthentication called");
//		log.debug("Username: " + userName);
//		log.debug("Signal: " + signal);
//
//		String restAPIURL = null;
//		String restApiRespAsJsonStr = "failure";
//		HttpClient httpClient = null;
//		JSONObject apiResponseJSON = null;
//		HttpResponse clientResponse = null;
//		String apiResponse = null;
//		JSONParser parser = null;
//		HttpPost httpPost = null;
//		ArrayList<NameValuePair> postParameters;
//
//		ResourceBundle rbConfig = ResourceBundle.getBundle("config");
//		try {
//
//			restAPIURL = rbConfig.getString("ODISHA_USER_AUTHENTICATION");
//			httpPost = new HttpPost(restAPIURL);
//
//			// SET ENCODED FORM PARAMS
//			postParameters = new ArrayList<NameValuePair>();
//			postParameters.add(new BasicNameValuePair("username", AesUtil.getEncryptedString(userName)));
//			postParameters.add(new BasicNameValuePair("password", AesUtil.getEncryptedString(signal)));
//			postParameters.add(new BasicNameValuePair("grant_type", "password"));
//			postParameters.add(new BasicNameValuePair("scope", "server"));
//
//			httpPost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
//
//			// SET REQUEST HEADERS
//			// httpPost.setHeader("Authorization",
//			// "Basic
//			// b2Rpc2hhLW9uZS1zc286TVRjeFlUZzNaalEyTnpFd016RTRNMkkxTldJNE16WXdOak5qWW1ReFpHVTZPbUZpWTJNME5UUXdaakppTTJabU9HUXhZV0ppWVRjM1ptWmtZV0V5T0RSaU9qcDRZblYxWTA1S1pFSjRNSFU0UVV3M2RuaE9iU3RSUFQwPQ==");
//
//			httpPost.setHeader("Authorization", rbConfig.getString("ODISHA_AUTHORIZATION_TOKEN"));
//
//			try {
//				httpClient = new DefaultHttpClient();
//				clientResponse = httpClient.execute(httpPost);
//				try {
//					apiResponse = EntityUtils.toString(clientResponse.getEntity());
//					log.debug("API response: " + apiResponse);
//
//					if (apiResponse.contains("404 page not found")) {
//						return "na";
//					} else {
//
//						// CONVERT RESPONSE STRING TO JSON OBJECT
//						parser = new JSONParser();
//						apiResponseJSON = (JSONObject) parser.getJSONObject(apiResponse);
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//					log.debug("Timeout occurs");
//					return "na";
//
//				}
//			} catch (Exception e) {
//				log.error("Error occured while calling API: " + e.getClass().getCanonicalName());
//				e.printStackTrace();
//				return "na";
//			} finally {
//				// CLOSE THE RESOURCES
//				httpPost = null;
//				httpClient = null;
//				clientResponse = null;
//				parser = null;
//
//			}
//
//			if (apiResponseJSON.containsKey("access_token")) {
//				return "success";
//			} else {
//				return "failure";
//			}
//
//		} catch (Exception e) {
//			log.error("Error occurred while calling Odisha API");
//			e.printStackTrace();
//
//		} finally {
//			restAPIURL = null;
//			restApiRespAsJsonStr = null;
//			apiResponseJSON = null;
//			apiResponse = null;
//			rbConfig = null;
//
//		}
//		log.debug("odishaUserAuthentication JSON string: " + restApiRespAsJsonStr);
//		return restApiRespAsJsonStr;
//	}
//
//	// public static String pushDetailsToOdisha(String firstName, String lastName,
//	// String gender, String mobileNo,
//	// String emailId, String userId, String signal) {
//	// log.debug("pushDetailsToOdisha called");
//	// log.debug("FirstName: " + firstName);
//	// log.debug("LastName: " + lastName);
//	// log.debug("Gender: " + gender);
//	// log.debug("MobileNo: " + mobileNo);
//	// log.debug("EmailId: " + emailId);
//	// log.debug("UserId: " + userId);
//	// log.debug("Signal: " + signal);
//	//
//	// String restAPIURL = null;
//	// String restApiRespAsJsonStr = "failure";
//	// String postParams = null;
//	// HttpClient httpClient = null;
//	// StringEntity entity = null;
//	// JSONObject apiResponseJSON = null;
//	// HttpResponse clientResponse = null;
//	// String apiResponse = null;
//	// JSONParser parser = null;
//	// HttpPost httpPost = null;
//	//
//	// try {
//	//
//	// restAPIURL = "http://52.172.129.49/odisha-one-sso/api/v1/sso/user/register";
//	// httpPost = new HttpPost(restAPIURL);
//	//
//	// // postParams = "{\"userId\":\"" + userName + "\",\"passwd\":\"" +
//	// // SPPasswordAlgo.getSPEncryptedPwd(signal)
//	// // + "\"}";
//	// postParams = "{\"firstName\":\"" + firstName + "\",\"lastName\":\"" +
//	// lastName + "\",\"mobile\":\""
//	// + mobileNo + "\",\"lastName\":\"" + lastName + "\",\"email\":\"" + emailId +
//	// "\",\"password\":\""
//	// + AesUtil.getEncryptedString(signal) + "\",\"username\":\"" + userId
//	//
//	// + "\"}";
//	// log.debug("Post Params: " + postParams);
//	//
//	// entity = new StringEntity(postParams);
//	//
//	// // SET REQUEST HEADERS
//	// httpPost.setHeader("Content-Type", "application/json");
//	//
//	// // SET REQUEST HEADERS
//	// try {
//	// httpClient = new DefaultHttpClient();
//	// // DefaultHttpClient httpClient1 = new DefaultHttpClient();
//	// // httpClient = HttpSecureCon.wrapClient(httpClient1);
//	// clientResponse = httpClient.execute(httpPost);
//	// try {
//	// apiResponse = EntityUtils.toString(clientResponse.getEntity());
//	// log.debug("API response: " + apiResponse);
//	//
//	// if (apiResponse.contains("404 page not found")) {
//	// return "na";
//	// } else {
//	//
//	// // CONVERT RESPONSE STRING TO JSON OBJECT
//	// parser = new JSONParser();
//	// apiResponseJSON = (JSONObject) parser.getJSONObject(apiResponse);
//	// }
//	//
//	// } catch (Exception e) {
//	// e.printStackTrace();
//	// log.debug("Timeout occurs");
//	// return "na";
//	//
//	// }
//	// } catch (Exception e) {
//	// log.error("Error occured while calling API: " +
//	// e.getClass().getCanonicalName());
//	// e.printStackTrace();
//	// // return "na";
//	// return "success";
//	//
//	// } finally {
//	// // CLOSE THE RESOURCES
//	// httpPost = null;
//	// entity = null;
//	// httpClient = null;
//	// clientResponse = null;
//	// parser = null;
//	//
//	// }
//	//
//	// try {
//	// log.debug("Odisha API Response: " + apiResponseJSON.get("message"));
//	//
//	// if ("You have been successfully registered"
//	// .equalsIgnoreCase(apiResponseJSON.get("message").toString())) {
//	// restApiRespAsJsonStr = "success";
//	// }
//	// restApiRespAsJsonStr = "success";
//	// } catch (Exception e) {
//	// // restApiRespAsJsonStr = "success";
//	// }
//	//
//	// httpClient = null;
//	// log.debug("http client connection has closed ");
//	// return restApiRespAsJsonStr;
//	//
//	// } catch (Exception e) {
//	// log.error("Error occurred while calling Service API");
//	// e.printStackTrace();
//	// // return "na";
//	// return "success";
//	//
//	// } finally {
//	// restAPIURL = null;
//	// restApiRespAsJsonStr = null;
//	// postParams = null;
//	// apiResponseJSON = null;
//	// apiResponse = null;
//	//
//	// }
//	//
//	// }
//
//	public static String pushDetailsToOdisha(String firstName, String lastName, String gender, String mobileNo,
//			String emailId, String userId, String signal, String isLegacyUser, String legacyUsername, String state) {
//		log.debug("pushDetailsToOdisha called");
//		log.debug("FirstName: " + firstName);
//		log.debug("LastName: " + lastName);
//		log.debug("Gender: " + gender);
//		log.debug("MobileNo: " + mobileNo);
//		log.debug("EmailId: " + emailId);
//		log.debug("UserId: " + userId);
//		log.debug("Signal: " + signal);
//		log.debug("Is Legacy User: " + isLegacyUser);
//		log.debug("Legacy UserName: " + legacyUsername);
//		log.debug("State: " + state);
//
//		String restAPIURL = null;
//		String restApiRespAsJsonStr = "failure";
//		String postParams = null;
//		HttpClient httpClient = null;
//		StringEntity entity = null;
//		JSONObject apiResponseJSON = null;
//		HttpResponse clientResponse = null;
//		String apiResponse = null;
//		JSONParser parser = null;
//		HttpPost httpPost = null;
//		String middleName = "";
//		String newLastName = "";
//		String splittedValue[] = null;
//
//		try {
//
//			// restAPIURL = "http://52.172.129.49/odisha-one-sso/api/v1/sso/user/register";
//			// restAPIURL = "http://52.172.129.49/odisha-one/api/v1/sso/user/register";
//			restAPIURL = ResourceBundle.getBundle("config").getString("ODISHA_USER_REGISTRATION");
//			httpPost = new HttpPost(restAPIURL);
//
//			try {
//				splittedValue = lastName.split("\\s+");
//				middleName = splittedValue[0];
//				newLastName = splittedValue[1];
//			} catch (Exception e) {
//				middleName = "";
//				newLastName = "";
//
//			}
//			// postParams = "{\"userId\":\"" + userName + "\",\"passwd\":\"" +
//			// SPPasswordAlgo.getSPEncryptedPwd(signal)
//			// + "\"}";
//
//			postParams = "{\"firstName\":\"" + firstName + "\",\"lastName\":\"" + newLastName + "\",\"middleName\":\""
//					+ middleName + "\",\"mobile\":\"" + mobileNo + "\",\"state\":\"" + state + "\",\"email\":\""
//					+ emailId + "\",\"password\":\"" + AesUtil.getEncryptedString(signal) + "\",\"username\":\""
//					+ userId.split("@")[0] + "\",\"gender\":\"" + gender + "\",\"isLegacyUser\":\"" + isLegacyUser
//					+ "\",\"legacyUsername\":\"" + legacyUsername
//
//					+ "\"}";
//			log.debug("Post Params: " + postParams);
//
//			entity = new StringEntity(postParams);
//
//			// SET REQUEST HEADERS
//			httpPost.setHeader("Content-Type", "application/json");
//			httpPost.setEntity(entity);
//
//			// SET REQUEST HEADERS
//			try {
//				httpClient = new DefaultHttpClient();
//				// DefaultHttpClient httpClient1 = new DefaultHttpClient();
//				// httpClient = HttpSecureCon.wrapClient(httpClient1);
//				clientResponse = httpClient.execute(httpPost);
//				try {
//					apiResponse = EntityUtils.toString(clientResponse.getEntity());
//					log.debug("API response: " + apiResponse);
//
//					if (apiResponse.contains("404 page not found")) {
//						return "na";
//					} else {
//
//						// CONVERT RESPONSE STRING TO JSON OBJECT
//						parser = new JSONParser();
//						apiResponseJSON = (JSONObject) parser.getJSONObject(apiResponse);
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//					log.debug("Timeout occurs");
//					return "na";
//
//				}
//			} catch (Exception e) {
//				log.error("Error occured while calling API: " + e.getClass().getCanonicalName());
//				e.printStackTrace();
//				// return "na";
//				return "success";
//
//			} finally {
//				// CLOSE THE RESOURCES
//				httpPost = null;
//				entity = null;
//				httpClient = null;
//				clientResponse = null;
//				parser = null;
//
//			}
//
//			try {
//				log.debug("Odisha API Response: " + apiResponseJSON.get("message"));
//
//				if ("You have been successfully registered"
//						.equalsIgnoreCase(apiResponseJSON.get("message").toString())) {
//					restApiRespAsJsonStr = "success";
//				}
//				restApiRespAsJsonStr = "success";
//			} catch (Exception e) {
//				// restApiRespAsJsonStr = "success";
//			}
//
//			httpClient = null;
//			log.debug("http client connection  has closed ");
//			return restApiRespAsJsonStr;
//
//		} catch (Exception e) {
//			log.error("Error occurred while calling Service API");
//			e.printStackTrace();
//			// return "na";
//			return "success";
//
//		} finally {
//			restAPIURL = null;
//			restApiRespAsJsonStr = null;
//			postParams = null;
//			apiResponseJSON = null;
//			apiResponse = null;
//
//			httpClient = null;
//			entity = null;
//			clientResponse = null;
//			parser = null;
//			httpPost = null;
//			middleName = null;
//			newLastName = null;
//			splittedValue = null;
//
//		}
//
//	}
//
//	private static volatile SecureRandom numberGenerator = null;
//	private static final long MSB = 0x8000000000000000L;
//
//	public static String unique() {
//		SecureRandom ng = numberGenerator;
//		if (ng == null) {
//			numberGenerator = ng = new SecureRandom();
//		}
//
//		return Long.toHexString(MSB | ng.nextLong()) + Long.toHexString(MSB | ng.nextLong());
//	}
//
//	public static String generateCodeChallange(String codeVerifier)
//			throws UnsupportedEncodingException, NoSuchAlgorithmException {
//		byte[] bytes = codeVerifier.getBytes("US-ASCII");
//		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//		messageDigest.update(bytes, 0, bytes.length);
//		byte[] digest = messageDigest.digest();
//		return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
//	}
//
//	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//		// try {
//		// String email = "email@gmail.com";
//		// String firstName = "First";
//		// String gender = "Male";
//		// String lastName = "Last";
//		// String mobile = "5556824245";
//		// String password = "Test@123";
//		// String username = "Test_User";
//		//
//		// String amqpMessage =
//		// "http://52.172.129.49/odisha-one-sso/api/v1/sso/user/register" + "@#@#@#@#@#"
//		// + "{\"email\":\"" + email + "\",\"firstName\":\"" + firstName +
//		// "\",\"gender\":\"" + gender
//		// + "\",\"lastName\":\"" + lastName + "\",\"mobile\":\"" + mobile +
//		// "\",\"password\":\"" + password
//		// + "\",\"username\":\"" + username + "\"}";
//		// System.out.println(amqpMessage);
//		// } catch (Exception e) {
//		//
//		// }
//		// System.out.println(unique());
//		
//		String codeVerifier1 = "b'rnfkgrbfdf'";
//		
//		System.out.println("CODE CHALLENGE: "+generateCodeChallange(codeVerifier1));
//		
////		System.out.println(UUID.randomUUID().toString());
////		System.out.println(unique());
////		MessageDigest md = null;
////		try {
////			md = MessageDigest.getInstance("SHA-256");
////		} catch (Exception e) {
////
////		}
////		// byte[] codeVerifierInBytes = codeVerifier.getBytes();
////		// codeVerifier = md.digest(codeVerifierInBytes).toString();
////		String messageDigestStr = Utility.toHexStringOAuth(md.digest("rnfkgrbfdf".getBytes(StandardCharsets.UTF_8)));
////		System.out.println("Message digest string: " + messageDigestStr);
////
////		String base64 = Base64.encodeBase64String(ParichayAESEncryption.hexStringToByteArray(messageDigestStr));
////		System.out.println("Base 64 string: " + base64);
////
////		String text = "code=17847751571110753644121410065056&code_verifier=hNbGyiCh9-MqcPp0kaw5xfbwkFvyuRxEMZUYAL6XwtU&redirect_uri=signinjanparichay789457895621478://authorize&client_id=IOSOauth&grant_type=authorization_code";
////		String splitArray[] = text.split("&");
////
////		String serviceId = "";
////		String redirectURI = "";
////		String codeVerifier = "";
////		String authCode = "";
////		String grantType = "";
////
////		for (String value : splitArray) {
////
////			String valueArr[] = value.split("=");
////			if ("code".equals(valueArr[0])) {
////				authCode = valueArr[1];
////			}
////			if ("code_verifier".equals(valueArr[0])) {
////				codeVerifier = valueArr[1];
////			}
////			if ("redirect_uri".equals(valueArr[0])) {
////				redirectURI = valueArr[1];
////			}
////			if ("client_id".equals(valueArr[0])) {
////				serviceId = valueArr[1];
////			}
////			if ("grant_type".equals(valueArr[0])) {
////				grantType = valueArr[1];
////			}
////			if ("code".equals(valueArr[0])) {
////				authCode = valueArr[1];
////			}
////		}
////
////		System.out.println("ServiceId: " + serviceId);
////		System.out.println("Code Verifier: " + codeVerifier);
////		System.out.println("Redirect URI: " + redirectURI);
////		System.out.println("Grant Type: " + grantType);
////		System.out.println("Auth Code: " + authCode);
////		System.out.println("**********************************************************");
////		String codeChallenge = Utility.generateCodeChallange("kuDbaMMbzbv1FRGYVk0i1re_66KB-R87F3WNbo6-sDI");
////		System.out.println("Generate Code Challenge: " + codeChallenge);
////		System.out.println("Is match: " + "SIny0FcUgbhskI-fjb-LEpSocg76VOsvLf3xFRsb5Nk".equals(codeChallenge));
//	}
//
//	public static String getOAuthRes(String accessToken, String tokenType, long expiresAt, String refresh_token,
//			String createdAt) {
//		log.debug("getOAuthRes called");
//		log.debug("Access Token: " + accessToken + "\t Token Type: " + tokenType + "\t Expires At: " + expiresAt
//				+ "\t Refresh Token: " + refresh_token + "\t Created At: " + createdAt);
//		JSONObject responseObj = new JSONObject();
//		try {
//			responseObj.put("access_token", accessToken);
//			responseObj.put("token_type", tokenType);
//			responseObj.put("expires_in", expiresAt);
//			responseObj.put("refresh_token", refresh_token);
//			responseObj.put("created_at", createdAt);
//			return responseObj.toString();
//
//		} catch (Exception e) {
//			log.error("Error occurred while generating OAuth response");
//			e.printStackTrace();
//			return responseObj.toString();
//		} finally {
//			responseObj = null;
//		}
//
//	}
//
//	public static String getOAuthErrRes(int code, String message, String description, String status) {
//		log.debug("getOAuthRes called");
//		log.debug("Code : " + code + "\t Message : " + message + "\t Description: " + description + "\t Statusn: "
//				+ status);
//		JSONObject responseObj = new JSONObject();
//		try {
//
//			responseObj.put("Description", description);
//			responseObj.put("token_type", message);
//			responseObj.put("Status", status);
//			responseObj.put("Code", code);
//
//			return responseObj.toString();
//
//		} catch (Exception e) {
//			log.error("Error occurred while generating OAuth response");
//			e.printStackTrace();
//			return responseObj.toString();
//		} finally {
//			responseObj = null;
//		}
//
//	}

	public static String toHexStringOAuth(byte[] hash) {
		// Convert byte array into signum representation
		BigInteger number = new BigInteger(1, hash);

		// Convert message digest into hex value
		StringBuilder hexString = new StringBuilder(number.toString(16));

		// Pad with leading zeros
		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}

		return hexString.toString();
	}

	public static String Read(Reader re) throws IOException { // class Declaration
		StringBuilder str = new StringBuilder(); // To Store Url Data In String.
		int temp;
		do {

			temp = re.read(); // reading Character By Chracter.
//			log.debug("temp : " + temp);
			str.append((char) temp);

		} while (temp != -1);
		// re.read() return -1 when there is end of buffer , data or end of file.
//		log.debug("Read called and string generated : " + str.toString());

		return str.toString();

	}

	public static org.json.JSONObject readJsonFromUrl(String text) throws IOException, JSONException {
//		log.debug("readJsonFromUrl called");
		// InputStream input = new URL(link).openStream();
		// Input Stream Object To Start Streaming.
		try { // try catch for checked exception

			org.json.JSONObject json = new org.json.JSONObject(text); // Creating A JSON
			return json; // Returning JSON
		} catch (Exception e) {
			return null;
		} finally {
			// input.close();
		}
	}

	public static Map<String, Object> toMap(org.json.JSONObject jsonObject) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = jsonObject.keys();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = jsonObject.get(key);

			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((org.json.JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((org.json.JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

//	public static Map<String, Object> toMap(org.json.JSONObject jsonObject) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
