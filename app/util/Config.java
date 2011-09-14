package util;

import play.Play;

public class Config {

	public static String getEmailFromAddress() {
		return Play.configuration.getProperty("from.email");
	}

	public static String getEmailFromName() {
		return Play.configuration.getProperty("from.name");
	}
}
