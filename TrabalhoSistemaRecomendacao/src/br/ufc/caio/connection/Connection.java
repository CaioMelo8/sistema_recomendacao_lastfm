package br.ufc.caio.connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
	private static java.sql.Connection CONNECTION;
	
	private Connection(){};
	
	public static java.sql.Connection getConnection(){
		if (CONNECTION == null){
			try {
				Class.forName(ConnectionMessages.getString("Connection.driver")); //$NON-NLS-1$
			} catch (ClassNotFoundException e1) {				
				e1.printStackTrace();
			}
			
			String url = ConnectionMessages.getString("Connection.url"); //$NON-NLS-1$
			String usr = ConnectionMessages.getString("Connection.user"); //$NON-NLS-1$
			String password = ConnectionMessages.getString("Connection.password"); //$NON-NLS-1$
			
			try {
				Connection.CONNECTION = DriverManager.getConnection(url, usr, password);
			} catch (SQLException e) {		
				e.printStackTrace();
			}
		}
			
		return Connection.CONNECTION;
	}
}
