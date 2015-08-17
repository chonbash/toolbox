package ru.sbrf.oir.toolbox.toolbox;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "prop")
public class Property {

	public ArrayList<Host> hostsList = new ArrayList<Host>();
	public String wpsWsdlFilename = new String();
	public String wasUiWsdlFilename = new String();
	public String wasProcessWsdlFilename = new String();
	public String wasReportWsdlFilename = new String();
}
