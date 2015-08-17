package ru.sbrf.oir.toolbox.toolbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class WsdlCheckHelper {

	public ArrayList<String> getWsdlListFromFile(String filePath) throws IOException {
		ArrayList<String> wsdlList = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = null;
		while ((line = br.readLine()) != null) {
			wsdlList.add(line);
		}
		br.close();
		return wsdlList;
	}

	public ArrayList<String> addHostPortToWsdlList(ArrayList<String> wsdlList,
			String host, String port) {
		for (String oldUrl : wsdlList) {
			String newUrl = oldUrl.replaceFirst("host", host);
			newUrl = newUrl.replaceFirst("port", port);
			Collections.replaceAll(wsdlList,oldUrl,newUrl);
		}
		return wsdlList;
	}

	public String getHttpsPageCode(String urlString) throws Exception {
		URL url;
		try {
			url = new URL(urlString);

		} catch (MalformedURLException e) {
			System.out.println("URL: " + urlString + " - invalid.");
			e.printStackTrace();
			return "404";
		}
		if (url.getProtocol().contentEquals("http")) {
			if (isHttpAvailable(url))
				return "OK";
		} else if (url.getProtocol().contentEquals("https")) {
			if (isHttpsAvailable(url))
				return "OK";
		}
		return "ERROR";
	}

	private boolean isHttpsAvailable(URL url) throws Exception {

		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(new KeyManager[0],
				new TrustManager[] { new DefaultTrustManager() },
				new SecureRandom());
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

	private boolean isHttpAvailable(URL u) {
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