package ru.sbrf.oir.toolbox.toolbox;

import java.util.ArrayList;

public class Runner {

	static Property prop = new Property();

	public static void main(String[] args) throws Exception {

		Property prop = PropertyHelper.read("Property.xml");
		WsdlCherManager wcm = new WsdlCherManager(prop);
	}

}
