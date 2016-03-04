package com.bluewall.feservices.controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bluewall.feservices.util.Constants;

/**
 * @author Jenil
 *
 */
@Slf4j
@RestController
public class LoginController {
	
	/**
	 * @param request OAuth URL 
	 * @param response code is generated after authorizing the user
	 * @throws Exception
	 */
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public void signin(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(Constants.DIALOG_OAUTH+"?client_id="+Constants.CLIENT_ID+
                              "&redirect_uri="+Constants.REDIRECT_URI+
                              "&scope="+Constants.SCOPE+
                              "&response_type=code");
			log.info("OAuth Request Sent to generate the code");
		} catch (Exception e) {
			log.error("Exception occured during OAuth Call");
		}
	}

	/**
	 * @param request Code generated after calling OAuth URL
	 * @param response Access Token, Refresh Token, Expiry time and User Details
	 */
	@RequestMapping(value = "/callback", params = "code", method = RequestMethod.GET)
	@ResponseBody
	public void accessCode(@RequestParam("code") String code,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
		try {
			log.debug("Intermediate Code for Access Token: " + code);
			response.setContentType("text/html");
			String urlParameters = "code="
                    + code
                    + "&client_id="+Constants.CLIENT_ID
                    + "&client_secret="+Constants.APP_SECRET
                    + "&redirect_uri=" +Constants.REDIRECT_URI
                    + "&grant_type=authorization_code";
			
			URL url = new URL(Constants.ACCESS_TOKEN);
			URLConnection urlConn = url.openConnection();
			urlConn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(
			urlConn.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			
			String line, outputString = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
            
            log.info("Fetching Access Token using Access Token URL");
            JSONObject json = new JSONObject(outputString);
            String access_token = json.getString("access_token").toString();
            log.debug("Generated Access Token: " + access_token);

            log.info("Fetching User Profile Information");
            url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + access_token);
            urlConn = url.openConnection();
            outputString = "";
            reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }            
		} catch (Exception e) {
			log.equals("Exception Occured while fetching Access token and User details");
		}
	}
}
