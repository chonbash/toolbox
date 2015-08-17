package ru.sbrf.oir.toolbox.toolbox;

public class Runner {
	
	public static void main(String[] args) throws Exception {
		
		Property prop = new Property();
		PropertyHelper.write(prop, "Property.xml");
		Property propTest = PropertyHelper.read("Property.xml");
		

	}

}
