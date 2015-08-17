package ru.sbrf.oir.toolbox.toolbox;

public class TestRunner {

	public static void main(String[] args) throws Exception {
		// Host h1 = new Host();
		// Host h2 = new Host();
		// h1.hostname = "hostname1.ca.sbrf.ru";
		// h1.port = "9443";
		// h2.hostname = "hostname2.cgs.sbrf.ru";
		// h2.port = "8080";
		// Hosts hosts = new Hosts();
		// hosts.hostList.add(h1);
		// hosts.hostList.add(h2);
		// File f = new File("NewFile.xml");
		// JAXBContext jc = JAXBContext.newInstance(Host.class);
		// Marshaller m = jc.createMarshaller();
		// m.marshal(hosts, f);

		Property prop = new Property();
		prop.hostsList.add(new Host().setHostname("dnepr1.cgs.sbrf.ru")
				.setPort("9443").setRole("WPS").setLogPath("//logs"));
		prop.hostsList.add(new Host().setHostname("dnepr1.cgs.sbrf.ru")
				.setPort("9444").setRole("WPS").setLogPath("//logs"));
		prop.hostsList.add(new Host().setHostname("dnepr2.cgs.sbrf.ru")
				.setPort("9443").setRole("WPS").setLogPath("//logs"));
		prop.hostsList.add(new Host().setHostname("dnepr2.cgs.sbrf.ru")
				.setPort("9444").setRole("WPS").setLogPath("//logs"));

		prop.hostsList.add(new Host().setHostname("b77-01.ca.sbrf.ru")
				.setPort("9443").setRole("WAS").setLogPath("//logs"));
		prop.hostsList.add(new Host().setHostname("b82-01.ca.sbrf.ru")
				.setPort("9443").setRole("WAS").setLogPath("//logs"));

		prop.hostsList.add(new Host().setHostname("b156-14.ca.sbrf.ru")
				.setPort("9443").setRole("WASP").setLogPath("//logs"));
		prop.hostsList.add(new Host().setHostname("b158-14.ca.sbrf.ru")
				.setPort("9443").setRole("WASP").setLogPath("//logs"));

		prop.hostsList.add(new Host().setHostname("bm170-06.ca.sbrf.ru")
				.setPort("9443").setRole("WASR").setLogPath("//logs"));
		prop.hostsList.add(new Host().setHostname("b151-14.ca.sbrf.ru")
				.setPort("9443").setRole("WASR").setLogPath("//logs"));
		prop.wasUiWsdlFilename = "c://filepath";
		prop.wasProcessWsdlFilename = "c://filepath";
		prop.wasReportWsdlFilename = "c://filepath";
		
		PropertyHelper.write(prop, "Property.xml");
		Property propTest = PropertyHelper.read("Property.xml");
		System.out.println(propTest.hostsList.size());

	}
}
