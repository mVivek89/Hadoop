package com.jpmorgan.cri.ifm.hadoop;

import java.util.Arrays;
import java.util.List;

public class UserAverageTest {
	private static List<User> users = Arrays.asList(
			new User(1l, "Steve", "Vai", 40l),
			new User(4l, "Joe", "Smith", 32l), 
			new User(3l, "Steve", "Johnson", 57l), 
			new User(9l, "Mike", "Stevens", 18l), 
			new User(10l, "George", "Armstrong", 24l), 
			new User(2l, "Jim", "Smith", 40l), 
			new User(8l, "Chuck", "Schneider", 34l), 
			new User(5l, "Jorje", "Gonzales", 22l), 
			new User(6l, "Jane", "Michaels", 47l),
			new User(7l, "Kim", "Berlie", 60l));

	public static void main(String[] args) {
		oldJavaWay();
		newJavaWay();
	}

	private static void oldJavaWay() {
		int total = 0;

		for (User u : users) {
			total += u.getAge();
		}

		double average = (double) total / (double) users.size();

		System.out.println("OLDWAY Average User Age: " + average);
	}

	private static void newJavaWay() {
		double average = users.parallelStream().mapToDouble(u -> u.getAge()).average().getAsDouble();

		System.out.println("NEWWAY Average User Age: " + average);
	}
}