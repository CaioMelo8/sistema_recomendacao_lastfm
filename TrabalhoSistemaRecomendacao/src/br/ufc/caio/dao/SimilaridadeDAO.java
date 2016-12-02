package br.ufc.caio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufc.caio.model.Similaridade;
import br.ufc.caio.model.User;

public class SimilaridadeDAO {
	private Connection connection;
	
	public SimilaridadeDAO(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public void insertSimilaridade(Similaridade similaridade){
		PreparedStatement stmt;
		
		String query = DAOMessages.getString("SimilaridadeDAO.insert_similaridade_query"); //$NON-NLS-1$
		
		try{
			stmt = connection.prepareStatement(query);
			stmt.setString(1, similaridade.getUser1());
			stmt.setString(2, similaridade.getUser2());
			stmt.setDouble(3, similaridade.getSimilaridade());
			
			stmt.execute();
			
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public List<String> getSimilarUsers(User user, double grauSimilaridade){
		PreparedStatement stmt;
		ResultSet result;
		
		String query  = DAOMessages.getString("SimilaridadeDAO.get_similaridade_by_user_query"); //$NON-NLS-1$
		
		List<String> usuariosSimilares = new ArrayList<>();
				
		String user2;		
		
		try{
			stmt = connection.prepareStatement(query);
			stmt.setString(1, user.getUserSha1());
			stmt.setDouble(2, grauSimilaridade);
			
			result = stmt.executeQuery();
			
			while (result.next()){				
				user2 = result.getString(DAOMessages.getString("SimilaridadeDAO.field_user"));											 //$NON-NLS-1$
				
				usuariosSimilares.add(user2);
			}
			
			return usuariosSimilares;
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return null;
	}
}
