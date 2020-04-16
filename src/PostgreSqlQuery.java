import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PostgreSqlQuery {

	static Scanner sc = new Scanner(System.in);
	static Statement statement;

	private static void checkBookStock(String isbn, int stock) {
		if (stock < 10) {
			// email the publisher...
		}
	}

	private static void bookSearch() {
		System.out.println("Please enter search catageory:");
		String category = sc.nextLine(); // Read user input
		System.out.println("Please enter search criteria:");
		String criteria = sc.nextLine(); // Read user input

		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM Book where " + category + " = '" + criteria + "'");
			dumpAllResult(resultSet, "book");
		} catch (SQLException e) {
			//TODO: add proper error message...
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void checkOrder() {
		System.out.println("Please enter order number");
		String order_number = sc.nextLine(); // Read user input
		try {
			ResultSet resultSet = statement.executeQuery("SELECT *\r\n" + "FROM Orders natural\r\n"
					+ "JOIN Order_Item\r\n" + "WHERE Order_number = " + order_number);
			dumpAllResult(resultSet, "order");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Order not found");
		}
	}

	private static void dumpAllResult(ResultSet resultSet, String type) throws SQLException {
		if (!resultSet.isBeforeFirst()) {
			System.out.println("No such " + type);
		}
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (resultSet.next()) {
		    for (int i = 1; i <= columnsNumber; i++) {
		        if (i > 1) System.out.print(",  ");
		        String columnValue = resultSet.getString(i);
		        System.out.print(columnValue + " " + rsmd.getColumnName(i));
		    }
		    System.out.println("");
		}
	}

	private static void purchaseAbook() {
		System.out.println("Please enter the ISBN of the book to be purchased:");
		String isbn = sc.nextLine(); // Read user input

		try {
			// create an order, get order number
			ResultSet resultSet = statement.executeQuery(
					"Insert into Orders (Tracking_number, Username) values (SOME_TRACKING#, 'default') RETURNING order_number");
			resultSet.next();
			int order_number = resultSet.getInt(1);
			// decrement the book stock by 1
			statement.executeQuery("UPDATE Book\r\n" + "SET stock = stock - 1\r\n" + "WHERE Isbn =" + isbn);
			// add the book into the order with the isbn and order number
			resultSet = statement.executeQuery("Insert into Order_Item (Order_number, ISBN) values (" + order_number
					+ ", " + isbn + ") RETURNING Stock");
			resultSet.next();
			int stock = resultSet.getInt(1);
			checkBookStock(isbn, stock);
			System.out.println("Order has been created for book " + isbn + ", order number is " + order_number);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void customerMenu() {
		System.out.println("1. Search for a book");
		System.out.println("2. Check order status");
		System.out.println("3. Order a book");
		System.out.println("Please enter your selection:");
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test3", "postgres",
				"admin123")) {
			statement = connection.createStatement();
			String input = sc.nextLine(); // Read user input
			int option = Integer.parseInt(input);
			switch (option) {
			case 1:
				bookSearch();
				break;
			case 2:
				checkOrder();
				break;
			case 3:
				purchaseAbook();
				break;
			default:
				System.out.println("Invalid option");
				break;
			}
		} catch (Exception e) {
			System.out.println("Invalid option");
		}
	}

	private static void showSales() {
		try {
			// create an order, get order number
			ResultSet resultSet = statement.executeQuery(
					"SELECT COUNT(*), genre\r\n" + "FROM Order_Item natural\r\n" + "JOIN book group by genre");
			dumpAllResult(resultSet, "N/A");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addBook() {
		System.out.println(
				"Please enter Name, Auther_name, ISBN, Pages, Price, Publisher_name, Publisher_%, stock in csv");
		String input = sc.nextLine(); // Read user input

		try {
			statement.executeUpdate("INSERT INTO Book Values (" + input + ")");
			System.out.println("Book has been added");
		} catch (SQLException e) {
			//TODO: add proper error message...
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deleteBOok() {
		System.out.println(
				"ISBN of the book to be removed");
		String isbn = sc.nextLine(); // Read user input

		try {
			statement.executeUpdate("DELETE FROM Book WHERE isbn = " + isbn);
			System.out.println("Book has been removed");
		} catch (SQLException e) {
			//TODO: add proper error message...
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void showAllBooks() {
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM BOOK");
			dumpAllResult(resultSet, "N/A");
		} catch (SQLException e) {
			//TODO: add proper error message...
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void ownerMenu() {
		System.out.println("1. Show sales per genre");
		System.out.println("2. Add a new book");
		System.out.println("3. Remove a book");
		System.out.println("4. Show all books");
		System.out.println("Please enter your selection:");
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test3", "postgres",
				"admin123")) {
			statement = connection.createStatement();
			String input = sc.nextLine(); // Read user input
			int option = Integer.parseInt(input);
			switch (option) {
			case 1:
				showSales();
				break;
			case 2:
				addBook();
				break;
			case 3:
				deleteBOok();
				break;
			case 4:
				showAllBooks();
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
					ownerMenu();
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