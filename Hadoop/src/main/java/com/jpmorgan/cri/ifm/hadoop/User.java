package com.jpmorgan.cri.ifm.hadoop;

public class User {

	private Long Id;
	private String firstName;
	private String lastName;
	private double age;

	public User(Long id, String firstName, String lastName, double age) {
		super();
		Id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

}
