package br.ufc.caio.model;

public class Recomendacao {
	private int id;
	private String userSha1;
	private String artName;
	
	public Recomendacao(int id, String userSha1, String artName) {
		super();
		this.id = id;
		this.userSha1 = userSha1;
		this.artName = artName;
	}

	public Recomendacao(String userSha1, String artName) {
		super();
		this.userSha1 = userSha1;
		this.artName = artName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserSha1() {
		return userSha1;
	}

	public void setUserSha1(String userSha1) {
		this.userSha1 = userSha1;
	}

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}	
}
