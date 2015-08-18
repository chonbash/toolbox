package ru.sbrf.oir.toolbox.toolbox;

import java.util.ArrayList;

public class WsdlCherManager {

	public WsdlCherManager(Property property) {
		try {
		for (Host host : property.hostsList) {
			if (host.getRole().equals("WPS")) {
					checkWsdl(host, property.wpsWsdlFilename);
				} else if (host.getRole().equals("WAS")) {
					checkWsdl(host, property.wasUiWsdlFilename);
				} else if (host.getRole().equals("WASP")) {
					checkWsdl(host, property.wasProcessWsdlFilename);
				} else if (host.getRole().equals("WASR")) {
					checkWsdl(host, property.wasReportWsdlFilename);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void checkWsdl(Host host, String wsdlListFilePath) throws Exception {
		WsdlCheckHelper helper = new WsdlCheckHelper();
		ArrayList<String> wsdlList = helper.addHostPortToWsdlList(
				helper.getWsdlListFromFile(wsdlListFilePath),
				host.getHostname(), host.getPort());
		for (String wsdlUrl : wsdlList) {
			System.out.println(helper.getHttpsPageCode(wsdlUrl));
		}

	}

}
