package ru.sbrf.oir.toolbox.toolbox;

import org.apache.commons.cli.*;


public class Runner {

	static Property prop = new Property();

	public static void main(String[] args) throws Exception {

		Options options = new Options();
		options.addOption("property", true, "path to file Property.xml");
		options.addOption("checkWsdl", false, "add option to run check wsdl");
		options.addOption("decode", false, "add option to decode massege");
		options.addOption("message", true, "path to message file to decode");
		options.addOption("sdec", true, "source encoding");
		options.addOption("tdec", true, "target encoding");
		options.addOption("encl", false, "show encoding list");
		
		CommandLineParser parser = new GnuParser();
		CommandLine cmd = parser.parse( options, args);
		
		if (cmd.hasOption("property")) {
			prop = PropertyHelper.read(cmd.getOptionValue("property"));
		} else {
			prop = PropertyHelper.read("Property.xml");
		}
		if (cmd.hasOption("checkWsdl")) {
			new WsdlCherManager(prop);
		}
	}

}
