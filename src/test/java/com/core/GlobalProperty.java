package com.core;

import java.io.FileInputStream;
import java.util.Properties;

public class GlobalProperty {

	public static String getProperty(String key) {
		Properties property = new Properties();
		String value = null;
		
		try {
			property.load(new FileInputStream("src/test/resources/environment.properties"));
			value = property.getProperty(key);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return value;
	}
}
