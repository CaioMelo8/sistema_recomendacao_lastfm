package br.ufc.caio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufc.caio.model.ArtistPlayCount;
import br.ufc.caio.model.User;

public class ArtistPlayCountDAO {
	private Connection connection;

	public ArtistPlayCountDAO(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public Double[] getPlayCountArrayByUser(User user){
		PreparedStatement stmt;
		ResultSet result;
		
		String query = DAOMessages.getString("ArtistPlayCountDAO.get_count_array_by_user_query"); //$NON-NLS-1$
		
		List<Double> inteiros = new ArrayList<>();				
		try{
			stmt = connection.prepareStatement(query);
			stmt.setString(1, user.getUserSha1());
			
			result = stmt.executeQuery();
			
			while (result.next()){
				inteiros.add(result.getDouble(DAOMessages.getString(DAOMessages.getString("ArtistPlayCountDAO.field_play_count"))));  //$NON-NLS-1$
			}
			
			return (Double[]) inteiros.toArray(new Double[inteiros.size()]);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<String> getTopArtistsBySimilarUser(String user, String userSimilar){
		PreparedStatement stmt;
		
		String query = DAOMessages.getString("ArtistPlayCountDAO.get_top_artists_query"); //$NON-NLS-1$
		
		List<String> artNames = new ArrayList<>();
		String artName;
		
		try{
			stmt = connection.prepareStatement(query);
			stmt.setString(1, userSimilar);
			stmt.setString(2, user);
			
			ResultSet result = stmt.executeQuery();
			
			while (result.next()){
				artName = result.getString("art_name"); //$NON-NLS-1$
				
				artNames.add(artName);
			}
			
			return artNames;
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void completeArtistPlayCountAtRow(User user){
		ArtistPlayCount artistPlayCount;
		PreparedStatement stmt;
		ResultSet result;		
		
		String query = DAOMessages.getString("ArtistPlayCountDAO.complete_artist_query"); //$NON-NLS-1$
		
		String artistName = null;
		String userSha1 = user.getUserSha1();
		String artisMbid = ""; //$NON-NLS-1$
		double playCount = 0;
				
		try{
			stmt = connection.prepareStatement(query);
			stmt.setString(1, userSha1);
			
			result = stmt.executeQuery();
			
			while (result.next()){
				artistName = result.getString(DAOMessages.getString("ArtistPlayCountDAO.field_art_name")); //$NON-NLS-1$							
				artistPlayCount = new ArtistPlayCount(null, userSha1, artisMbid, artistName, playCount);
								
				this.insertArtistPlayCount(artistPlayCount);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}			
	}
	
	public void insertArtistPlayCount(ArtistPlayCount artisttPlayCount){
		PreparedStatement stmt;
		
		String query = DAOMessages.getString("ArtistPlayCountDAO.insert_artist_play_count_query"); //$NON-NLS-1$
		
		try{
			stmt = connection.prepareStatement(query);
			
			stmt.setString(1, artisttPlayCount.getUserSha1());
			stmt.setString(2, artisttPlayCount.getArtistMbid());
			stmt.setString(3, artisttPlayCount.getArtistName());
			stmt.setDouble(4, artisttPlayCount.getPlayCount());
			
			stmt.execute();
			
			stmt.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public List<ArtistPlayCount> gerAllArtistPlayCounts(){
		PreparedStatement stmt;
		ResultSet result;		
		List<ArtistPlayCount> artistPlayCounts = new ArrayList<>();
		ArtistPlayCount artisttPlayCount;
		
		String query = DAOMessages.getString("ArtistPlayCountDAO.select_all_query"); //$NON-NLS-1$
		
		Long id;
		double playCount;
		String userSha1, artistMbid, artistName;
		
		try{
			stmt = connection.prepareStatement(query);
			
			result = stmt.executeQuery();
			
			while (result.next()){
				id = result.getLong(DAOMessages.getString("ArtistPlayCountDAO.field_id")); //$NON-NLS-1$
				userSha1 = result.getString(DAOMessages.getString("ArtistPlayCountDAO.field_user_sha1")); //$NON-NLS-1$
				artistMbid = result.getString(DAOMessages.getString("ArtistPlayCountDAO.field_art_mbid")); //$NON-NLS-1$
				artistName = result.getString(DAOMessages.getString("ArtistPlayCountDAO.field_art_name")); //$NON-NLS-1$
				playCount = result.getDouble(DAOMessages.getString("ArtistPlayCountDAO.field_play_count")); //$NON-NLS-1$
				
				artisttPlayCount = new ArtistPlayCount(id, userSha1, artistMbid, artistName, playCount);
				
				artistPlayCounts.add(artisttPlayCount);
			}
			
			return artistPlayCounts;
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return null;
	}
}
