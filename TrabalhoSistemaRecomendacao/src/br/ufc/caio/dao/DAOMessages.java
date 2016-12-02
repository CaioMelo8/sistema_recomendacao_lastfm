package br.ufc.caio.dao;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DAOMessages {
	private static final String BUNDLE_NAME = "br.ufc.caio.dao.dao_messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private DAOMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
