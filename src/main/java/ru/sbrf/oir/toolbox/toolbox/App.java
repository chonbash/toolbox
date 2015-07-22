package ru.sbrf.oir.toolbox.toolbox;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
//		System.out.println("Hello World!");
//		SshHelper wpsSsh = new SshHelper("chonbash","192.168.56.101");
//		ArrayList<String> list = wpsSsh.getLogFileList("/tmp", "1");
//		wpsSsh.copyFile(list, "/tmp");
//		String httpUrlS = "http://google.com/";
		String httpsUrlS = "https://www.pcwebshop.co.uk";
//		System.out.println("HTTP:  " + WsdlCheckHelper.getHttpsPageCode(httpUrlS));
		System.out.println("HTTPS: " + WsdlCheckHelper.getHttpsPageCode(httpsUrlS));
	}
}
