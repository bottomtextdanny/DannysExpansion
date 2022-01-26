package net.bottomtextdanny.dannys_expansion.core.Util.setup;

import java.io.File;
import java.net.URL;

public class Dwayne {
	
	public static void findDwayne() {
		String dir = getCurrentJARDirectory();
		
		
		if (!(dir != null && new File(dir + "dwayne.png").exists())) {
			System.out.println(dir);
			System.exit(-1);
		}
	}
	
	public static String getCurrentJARDirectory()
	{
		Class klass = Dwayne.class;
		URL location = ClassLoader.getSystemClassLoader().getResource('/' + klass.getName().replace('.', '/') + ".class");

		String path = location.toString();
		return path.substring(0, path.lastIndexOf("net/"));
	}
}
