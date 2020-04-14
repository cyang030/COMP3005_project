CREATE TABLE Publisher (
	Name VARCHAR(60),
	Address VARCHAR(80),
	Email VARCHAR(320),
	Phone_number VARCHAR(30),
	Banking_account VARCHAR(40),
	PRIMARY KEY (Name)
	);

CREATE TABLE Book (
	Name VARCHAR(60),
	Auther_name VARCHAR(60),
	ISBN NUMERIC(13, 0),
	Pages INT,
	Price NUMERIC(20, 2),
	Genre VARCHAR(20),
	Publisher_Name VARCHAR(60),
	Publisher_Percentage NUMERIC(4, 2),
	Stock INT,
	PRIMARY KEY (ISBN),
	FOREIGN KEY (Publisher_Name) REFERENCES Publisher(Name)
	);

CREATE TABLE User_table (
	Username VARCHAR(60),
	Password VARCHAR(20),
	Email VARCHAR(320),
	PRIMARY KEY (Username)
	);

CREATE TABLE Order_Item (
	Order_number SERIAL,
	ISBN NUMERIC(13, 0),
	FOREIGN KEY (ISBN) REFERENCES Book,
	FOREIGN KEY (Order_number) REFERENCES Orders
	);

CREATE TABLE Orders (
	Order_number SERIAL,
	Tracking_number VARCHAR(60),
	Username VARCHAR(60),
	Billing_Address VARCHAR(80),
	Credit_card INT,
	Shipping_Address VARCHAR(80),
	PRIMARY KEY (Order_number),
	FOREIGN KEY (Username) REFERENCES User_table
	);
