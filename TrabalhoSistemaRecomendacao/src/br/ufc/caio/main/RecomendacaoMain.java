package br.ufc.caio.main;

import java.util.List;

import br.ufc.caio.connection.Connection;
import br.ufc.caio.dao.ArtistPlayCountDAO;
import br.ufc.caio.dao.RecomendacaoDAO;
import br.ufc.caio.dao.SimilaridadeDAO;
import br.ufc.caio.dao.UserDAO;
import br.ufc.caio.model.Recomendacao;
import br.ufc.caio.model.User;

public class RecomendacaoMain {
	public static void main(String[] args) {
		UserDAO userDAO = new UserDAO(Connection.getConnection()); 			
		ArtistPlayCountDAO artistPlayCountDAO = new ArtistPlayCountDAO(Connection.getConnection());
		SimilaridadeDAO similaridadeDAO = new SimilaridadeDAO(Connection.getConnection());
		RecomendacaoDAO recomendacaoDAO = new RecomendacaoDAO(Connection.getConnection());
		
		List<User> users = userDAO.getAllUsers();		
		List<String> usuariosSimilares, artistasTop5;		
		
		User user, userSimilar;
		Recomendacao recomendacao;
		
		double grauSimilaridade = 0.4d;
		
		for (int i = 0; i < users.size(); i++){
			user = users.get(i);
			
			usuariosSimilares = similaridadeDAO.getSimilarUsers(user, grauSimilaridade);
			
			for (int j = 0; j < usuariosSimilares.size(); j++){
				userSimilar = users.get(j);
				
				artistasTop5 = artistPlayCountDAO.getTopArtistsBySimilarUser(user.getUserSha1(), userSimilar.getUserSha1());
				
				for (String artName : artistasTop5){
					recomendacao = new Recomendacao(user.getUserSha1(), artName);
					recomendacaoDAO.insertRecomendacao(recomendacao);
				}
			}
			
			System.out.println("Artista " + i + " concluido");
		}
	}
}