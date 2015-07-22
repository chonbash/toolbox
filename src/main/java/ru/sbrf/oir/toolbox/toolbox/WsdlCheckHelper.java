package ru.sbrf.oir.toolbox.toolbox;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class WsdlCheckHelper {

	public static String getHttpsPageCode(String urlString) throws Exception {
		URL url;
		try {
			url = new URL(urlString);

		} catch (MalformedURLException e) {
			System.out.println("URL: " + urlString + " - invalid.");
			e.printStackTrace();
			return "404";
		}
		if (url.getProtocol().contentEquals("http")) {
			if (isHttpAvailable(url)) return "OK";
		} else if (url.getProtocol().contentEquals("https")) {
			if (isHttpsAvailable(url)) return "OK";
		}  
		return "ERROR";
	}

	private static boolean isHttpsAvailable(URL url) throws Exception {
		
		SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);

		
		HttpsURLConnection c = null;
		try {
			c = (HttpsURLConnection) url.openConnection();
			c.setHostnameVerifier(new HostnameVerifier() {				
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			c.setRequestMethod("HEAD");
			c.getInputStream();
			return c.getResponseCode() == 200;
		} catch (Exception e) {
			return false;
		} finally {
			if (c != null)
				c.disconnect();
		}
	}

	private static boolean isHttpAvailable(URL u) {
		HttpURLConnection c = null;
		try {
			c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("HEAD");
			c.getInputStream();
			return c.getResponseCode() == 200;
		} catch (Exception e) {
			return false;
		} finally {
			if (c != null)
				c.disconnect();
		}
	}

	
}