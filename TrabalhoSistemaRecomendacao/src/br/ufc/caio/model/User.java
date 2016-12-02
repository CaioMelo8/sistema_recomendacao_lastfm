package br.ufc.caio.model;

import java.util.Date;

public class User {	
	private Long id;
	private String userSha1;
	private char gender;
	private int age;
	private String country;
	private Date signupDate;
	
	public User() {
		super();
	}
	public User(Long id, String userSha1, char gender, int age, String country, Date signupDate) {
		super();
		this.id = id;
		this.userSha1 = userSha1;
		this.gender = gender;
		this.age = age;
		this.country = country;
		this.signupDate = signupDate;
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
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Date getSignupDate() {
		return signupDate;
	}
	public void setSignupDate(Date signupDate) {
		this.signupDate = signupDate;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userSha1=" + userSha1 + ", gender=" + gender + ", age=" + age + ", country="
				+ country + ", signupDate=" + signupDate + "]";
	}	
	
	
}
