package com.uzibma.util;

public class Utility {
	
	public static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
	}

}
