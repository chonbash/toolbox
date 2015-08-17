package ru.sbrf.oir.toolbox.toolbox;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "hosts")
public class Hosts {
	public ArrayList<Host> hostList = new ArrayList<Host>();
}
