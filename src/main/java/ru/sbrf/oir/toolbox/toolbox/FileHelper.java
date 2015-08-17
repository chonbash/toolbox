package ru.sbrf.oir.toolbox.toolbox;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHelper {
	
	public void makeDirDefect(String path) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-hh-mm");
		Date date = new Date();
		String dirPath = path + "//" + "Defect//" + dateFormat.format(date);
		File dir = new File(dirPath);
		if (!dir.exists()){
			dir.mkdirs();
			System.out.println("Directory " + dirPath + " created.");
			return;
		} else {
			System.out.println("Directory exist.");
		}		
	}
	
	public void getFileFromFtp(Property prop) {
		
		
	}

}
