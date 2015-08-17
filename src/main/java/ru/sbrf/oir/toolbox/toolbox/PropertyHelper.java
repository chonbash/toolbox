package ru.sbrf.oir.toolbox.toolbox;

//import java.beans.XMLDecoder;
//import java.beans.XMLEncoder;
import java.io.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class PropertyHelper {
	public static void write(Property prop, String filename) throws Exception {
		// XMLEncoder encoder =
		// new XMLEncoder(
		// new BufferedOutputStream(
		// new FileOutputStream(filename)));
		// encoder.writeObject(prop);
		// encoder.close();
		File f = new File(filename);
		JAXBContext jc = JAXBContext.newInstance(Property.class);
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(prop, f);
	}

	public static Property read(String filename) throws Exception {
//		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(
//				new FileInputStream(filename)));
//		Property o = (Property) decoder.readObject();
//		decoder.close();
//		return o;
		File f = new File(filename);
		JAXBContext jc = JAXBContext.newInstance(Property.class);
		Unmarshaller u = jc.createUnmarshaller();
		Property prop = (Property) u.unmarshal(f);
		return prop;
	}

}
