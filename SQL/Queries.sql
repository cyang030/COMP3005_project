--Query all sales from specific Genere
SELECT COUNT(*)
FROM Order_Item natural
JOIN book
WHERE Genre = 'Technology';

--Query all sales from all genere Genere
SELECT COUNT(*), genre
FROM Order_Item natural
JOIN book group by genre

--Query number of sales for Auther John Smith
SELECT COUNT(*)
FROM Order_Item natural
JOIN book
WHERE Auther_name = 'John Smith';

--create an user
INSERT INTO User_table (Username)
VALUES ('default')

--create a book with specific isbn
INSERT INTO Book (ISBN)
VALUES (12345)

--create a book with data in csv...
INSERT INTO Book Values (1,23,4,5)

--remove a book with isbn
DELETE FROM Book WHERE isbn = 444

--show all books
SELECT * FROM BOOK

--Create an order
INSERT INTO Orders (
	Tracking_number,
	Username
	)
VALUES (
	1,
	'default'
	) RETURNING order_number

--Create an order item(book)
INSERT INTO Order_Item (
	Order_number,
	ISBN
	)
VALUES (
	1,
	'12345'
	)

--Find an order
SELECT *
FROM Orders natural
JOIN Order_Item
WHERE Order_number = 12345

--Decrement book stock by 1
UPDATE Book
SET stock = stock - 1
WHERE Isbn = 12345
