package br.ufc.caio.main;

import java.util.List;

import br.ufc.caio.connection.Connection;
import br.ufc.caio.dao.ArtistPlayCountDAO;
import br.ufc.caio.dao.UserDAO;
import br.ufc.caio.model.User;

public class ArtistPlayCountCompleteMain {
	public static void main(String[] args) {
		UserDAO dao = new UserDAO(Connection.getConnection());
		List<User> users = dao.getAllUsers();		
		
		ArtistPlayCountDAO artistPlayCountDAO = new ArtistPlayCountDAO(Connection.getConnection());			
		
		for (int i = 0; i < users.size(); i++){
			artistPlayCountDAO.completeArtistPlayCountAtRow(users.get(i));					
		}
	}
}