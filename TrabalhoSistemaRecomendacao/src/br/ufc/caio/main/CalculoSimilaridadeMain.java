package br.ufc.caio.main;

import java.util.ArrayList;
import java.util.List;

import br.ufc.caio.calculator.CosineDistanceCalculator;
import br.ufc.caio.connection.Connection;
import br.ufc.caio.dao.ArtistPlayCountDAO;
import br.ufc.caio.dao.SimilaridadeDAO;
import br.ufc.caio.dao.UserDAO;
import br.ufc.caio.model.Similaridade;
import br.ufc.caio.model.User;

public class CalculoSimilaridadeMain {		
	public static void main(String[] args) {
		CosineDistanceCalculator calculator = new CosineDistanceCalculator();
		UserDAO userDAO = new UserDAO(Connection.getConnection()); 
		List<User> users = userDAO.getAllUsers();
		
		ArtistPlayCountDAO artistPlayCountDAO = new ArtistPlayCountDAO(Connection.getConnection());
		
		SimilaridadeDAO similaridadeDAO = new SimilaridadeDAO(Connection.getConnection());
		
		User user1, user2;
		double valorSimilaridade;
		Similaridade similaridade;
		
		List<Double[]> artistUser = new ArrayList<>();
		
		for (int i = 0; i < users.size(); i++)
			artistUser.add(artistPlayCountDAO.getPlayCountArrayByUser(users.get(i)));		
		
		for (int i = 0; i < users.size(); i++){
			user1 = users.get(i);
			
			Double[] artistsUser1 = artistUser.get(i);
			
			for (int j = i + 1; j < users.size(); j++){
				user2 = users.get(j);
				
				Double[] artistsUser2 = artistUser.get(j);
				
				valorSimilaridade = calculator.calculateDistance(artistsUser1, artistsUser2);
				
				similaridade = new Similaridade(user1.getUserSha1(), user2.getUserSha1(), valorSimilaridade);
				
				similaridadeDAO.insertSimilaridade(similaridade);				
				
				similaridade = new Similaridade(user2.getUserSha1(), user1.getUserSha1(), valorSimilaridade);
				
				similaridadeDAO.insertSimilaridade(similaridade);
			}
			
			System.out.println("Usuario " + (i+1) + " concluido.");
		}			
	}
}
