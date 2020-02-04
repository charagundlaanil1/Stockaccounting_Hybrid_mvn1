package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil {
	public static String getValueForkey(String key) throws Throwable{
		Properties configproperties=new Properties();
		
		FileInputStream fis=new FileInputStream("D:\\anilch\\Stockaccounting_Hybrid_mvn\\PropertiesFile\\Environment.properties");
		
		configproperties.load(fis);
		return configproperties.getProperty(key);
	}
	
	
}
