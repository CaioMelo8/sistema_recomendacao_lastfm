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
		
		String query = "INSERT INTO similaridade(user1, user2, similaridade) VALUES (?,?,?)";
		
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
		
		String query  = "SELECT user2 FROM similaridade WHERE user1 = ? AND similaridade >= ?";
		
		List<String> usuariosSimilares = new ArrayList<>();
				
		String user2;		
		
		try{
			stmt = connection.prepareStatement(query);
			stmt.setString(1, user.getUserSha1());
			stmt.setDouble(2, grauSimilaridade);
			
			result = stmt.executeQuery();
			
			while (result.next()){				
				user2 = result.getString("user2");											
				
				usuariosSimilares.add(user2);
			}
			
			return usuariosSimilares;
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return null;
	}
}
