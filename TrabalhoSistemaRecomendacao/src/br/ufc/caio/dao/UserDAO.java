package br.ufc.caio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufc.caio.model.User;

public class UserDAO {
	private Connection connection;

	public UserDAO(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public List<User> getAllUsers(){
		PreparedStatement stmt;
		ResultSet result;		
		List<User> users = new ArrayList<>();
		User user;
		
		String query = DAOMessages.getString("UserDAO.select_all_query"); //$NON-NLS-1$
		
		Long id;
		int age;
		char gender;
		Date signupDate;
		String userSha1, country;			
		
		try{
			stmt = connection.prepareStatement(query);
			
			result = stmt.executeQuery();
			
			while (result.next()){								
				id = result.getLong(DAOMessages.getString("UserDAO.field_id")); //$NON-NLS-1$
				userSha1 = result.getString(DAOMessages.getString("UserDAO.field_user_sha1")); //$NON-NLS-1$
				age = result.getInt(DAOMessages.getString("UserDAO.field_age")); //$NON-NLS-1$
				gender = result.getString(DAOMessages.getString("UserDAO.field_gender")).charAt(0); //$NON-NLS-1$
				country = result.getString(DAOMessages.getString("UserDAO.field_country")); //$NON-NLS-1$
				signupDate = result.getDate(DAOMessages.getString("UserDAO.field_signup")); //$NON-NLS-1$
				
				user = new User(id, userSha1, gender, age, country, signupDate);
				
				users.add(user);
			}
			
			return users;
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return null;
	}
}
