--delete from Publisher;
--delete from Book;
--delete from User_table;
--delete from Orders;
--delete from Order_Item;


insert into Publisher values ('Dog Publisher', '123 Zoo Rd', 'editor@somewhere.com', '12378473892784');

insert into Book values ('How to train your dog', 'John Smith', 123456789000, 312, 12.95, 'Animal', 'Dog Publisher', 10.2, 10);
insert into Book values ('How to train your cat', 'Jane Smith', 123456789222, 123, 13.95, 'Animal', 'Dog Publisher', 15.0, 15);

insert into User_table values ('default', 'password', 'invalid@user.org');
insert into User_table values ('default2', 'password', 'invalid2@user.org');

insert into Orders values (22, 'CA777766543', 'default', '321 Owls cabin Rd', 1273879, '123 Marsh Sparrow RD');

insert into Order_Item values (22, 123456789000);
insert into Order_Item values (22, 123456789222);