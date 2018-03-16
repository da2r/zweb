package com.cpssoft.dev.zweb.util;

import java.net.URL;

import org.reflections.util.ClasspathHelper;

public class SystemUtil {

	private static String classPath = null;

	public static String getOperatingSystem() {
		String os = System.getProperty("os.name").toLowerCase();
		return os;
	}

	public static boolean isWindows() {
		return getOperatingSystem().indexOf("win") >= 0;
	}

	public static boolean isMac() {
		return getOperatingSystem().indexOf("mac") >= 0;
	}

	public static boolean isLinux() {
		String os = getOperatingSystem();
		return os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0;
	}

	/** WARNING: for Windows only **/
	public static String getAppDataFolder() {
		if (!isWindows()) {
			return "";
		}

		// return System.getenv("APPDATA");
		return System.getProperty("user.home") + "\\Local Settings\\ApplicationData";
	}

	public static String getSourceFolder() {
		if (classPath == null) {
			URL url = ClasspathHelper.forJavaClassPath().iterator().next();
			classPath = url.toString();
			if (classPath.startsWith("file:")) {
				classPath = classPath.substring(5);
			}
		}
		
		return classPath;
	}

}
