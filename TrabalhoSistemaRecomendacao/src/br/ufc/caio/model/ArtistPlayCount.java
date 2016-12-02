package br.ufc.caio.model;

public class ArtistPlayCount {	
	private Long id;
	private String userSha1;
	private String artistMbid;
	private String artistName;
	private double playCount;
		
	public ArtistPlayCount() {
		super();
	}
	
	public ArtistPlayCount(Long id, String userSha1, String artistMbid, String artistName, double playCount) {
		super();
		this.id = id;
		this.userSha1 = userSha1;
		this.artistMbid = artistMbid;
		this.artistName = artistName;
		this.playCount = playCount;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserSha1() {
		return userSha1;
	}
	public void setUserSha1(String userSha1) {
		this.userSha1 = userSha1;
	}
	public String getArtistMbid() {
		return artistMbid;
	}
	public void setArtistMbid(String artistMbid) {
		this.artistMbid = artistMbid;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public double getPlayCount() {
		return playCount;
	}
	public void setPlayCount(double playCount) {
		this.playCount = playCount;
	}

	@Override
	public String toString() {
		return "ArtistPlayCount [id=" + id + ", userSha1=" + userSha1 + ", artistMbid=" + artistMbid + ", artistName="
				+ artistName + ", playCount=" + playCount + "]";
	}
}