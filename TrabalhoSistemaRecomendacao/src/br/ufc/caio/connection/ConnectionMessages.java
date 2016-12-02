package br.ufc.caio.connection;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConnectionMessages {
	private static final String BUNDLE_NAME = "br.ufc.caio.connection.connection_messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private ConnectionMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
