--Query all sales from specific Genere
SELECT COUNT(*) FROM Order_Item natural join book where Genre='Technology';

--Query number of sales for Auther John Smith
SELECT COUNT(*) FROM Order_Item natural join book where Auther_name='John Smith';