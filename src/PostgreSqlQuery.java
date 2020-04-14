import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class PostgreSqlQuery {

	static Scanner sc = new Scanner(System.in);
	static Statement statement;

	private static void bookSearch() {
		System.out.println("Please enter search catageory:");
		String category = sc.nextLine(); // Read user input
		System.out.println("Please enter search criteria:");
		String criteria = sc.nextLine(); // Read user input

		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM Books where " + category + " = '" + criteria + "'");
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (resultSet.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = resultSet.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void customerMenu() {
		System.out.println("1. Purchase a book");
		System.out.println("2. Check order status");
		System.out.println("Please enter your selection:");
		Scanner sc = new Scanner(System.in);
		try {
			String input = sc.nextLine(); // Read user input
			int option = Integer.parseInt(input);
			switch (option) {
			case 1:
				System.out.println("Entering customer interface...");
				bookSearch();
				break;
			case 2:
				System.out.println("Entering owner interface...");
				break;
			default:
				System.out.println("Invalid option");
				break;
			}
		} catch (Exception e) {
			System.out.println("Invalid option");
		}
	}

	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test3", "postgres",
				"admin123")) {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("1. For bookstore customer");
			System.out.println("2. For bookstore Owner");
			System.out.println("Please enter your selection:");
			try {
				String input = sc.nextLine(); // Read user input
				int option = Integer.parseInt(input);
				switch (option) {
				case 1:
					System.out.println("Entering customer interface...");
					customerMenu();
					break;
				case 2:
					System.out.println("Entering owner interface...");
					break;
				default:
					System.out.println("Invalid option");
					break;
				}
			} catch (Exception e) {
				System.out.println("Invalid option");
			}

		}
	}
}