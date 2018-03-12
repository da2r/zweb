package com.cpssoft.dev.zweb.util;

public class SystemUtil {

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

}
