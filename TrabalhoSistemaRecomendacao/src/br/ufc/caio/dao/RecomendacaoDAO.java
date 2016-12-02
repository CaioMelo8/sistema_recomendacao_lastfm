package br.ufc.caio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.ufc.caio.model.Recomendacao;

public class RecomendacaoDAO {
	private Connection connection;
	
	public RecomendacaoDAO(java.sql.Connection connection) {
		this.connection = connection;
	}
	
	public void insertRecomendacao(Recomendacao recomendacao){
		PreparedStatement stmt;
		
		String query = "INSERT INTO recomendacao (user_sha1, art_name) VALUES (?,?)";
		
		try{
			stmt = connection.prepareStatement(query);
			stmt.setString(1, recomendacao.getUserSha1());
			stmt.setString(2, recomendacao.getArtName());
			
			stmt.execute();
			
			stmt.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
}
