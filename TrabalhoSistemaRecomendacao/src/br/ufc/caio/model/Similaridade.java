package br.ufc.caio.model;

public class Similaridade {
	private int id;
	private String user1;
	private String user2;
	private double similaridade;	
	
	public Similaridade(String user1, String user2, double similaridade) {
		super();
		this.user1 = user1;
		this.user2 = user2;
		this.similaridade = similaridade;
	}
	
	public Similaridade(int id, String user1, String user2, double similaridade) {
		super();
		this.id = id;
		this.user1 = user1;
		this.user2 = user2;
		this.similaridade = similaridade;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser1() {
		return user1;
	}
	public void setUser1(String user1) {
		this.user1 = user1;
	}
	public String getUser2() {
		return user2;
	}
	public void setUser2(String user2) {
		this.user2 = user2;
	}
	public double getSimilaridade() {
		return similaridade;
	}
	public void setSimilaridade(double similaridade) {
		this.similaridade = similaridade;
	}
	
	
}
