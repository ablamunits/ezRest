CREATE TABLE WorkingHours(
  Employee_id Integer NOT NULL,
  Record_id Integer NOT NULL,
  Clock_in TIMESTAMP default '0000-00-00 00:00:00',
  Clock_out TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary KEY(Record_id)
  /*foreign KEY(Employee_id) REFERENCES Employees(Employee_id)*/
);

CREATE TABLE MenuCategories (
  Cat_id Integer NOT NULL,
  Title VARCHAR(20) NOT NULL,
  Parent_id Integer NOT NULL,
  primary KEY(Cat_id)
);

CREATE TABLE MenuItems (
  Item_id Integer NOT NULL,
  Cat_id Integer NOT NULL,
  Title VARCHAR(40) NOT NULL,
  Price Integer NOT NULL,
  /*foreign KEY(Cat_id) REFERENCES MenuCategories(Cat_id),*/
  primary KEY(Item_id)
);

CREATE TABLE Orders(
  Order_id Integer NOT NULL,
  Employee_id Integer NOT NULL,
  Table_Num Integer NOT NULL,
  Order_Date Date NOT NULL,
  Total_sum Integer NOT NULL,
  /*foreign KEY(Employee_id) REFERENCES Employees(Employee_id),*/
  primary KEY(Order_id)
);

CREATE TABLE OrderItems(
  Order_id Integer NOT NULL,
  Item_id Integer NOT NULL,
  Quantity Integer NOT NULL
  /*foreign KEY(Order_id) REFERENCES Orders(Order_id)*/
);

CREATE TABLE VIP(
  Id Integer NOT NULL,
  First_Name VarChar(20) NOT NULL,
  Last_Name VarChar(20) NOT NULL,
  BirthDay Date NOT NULL,
  EMail VarChar(40) NOT NULL,
  primary KEY(Id)
);

CREATE TABLE Employees(
  Employee_id Integer NOT NULL,
  Permission_id Integer NOT NULL,
  First_Name VarChar(15) NOT NULL,
  Last_Name VarChar(15) NOT NULL,
  Position VarChar(15) NOT NULL,
  Age Integer NOT NULL,
  Gender VarChar(10) NOT NULL,
  City VarChar(20) NOT NULL,
  Address VarChar(20) NOT NULL,
  EMail VarChar(40) NOT NULL,
  Phone_Number VarChar(20) NOT NULL,
  Password VarChar(20) NOT NULL,
  Bank_Information VarChar(40) NOT NULL,
  /*foreign KEY(Permission_id) REFERENCES Permissions(Permission_id),*/
  primary KEY(Employee_id)
);

CREATE TABLE Permissions (
  Permission_id Integer NOT NULL,
  Title VarChar(30) NOT NULL,
  Add_Product Boolean,
  Add_Employee Boolean,
  Cancel_Order Boolean,
  Add_Discount Boolean,
  Edit_Menu Boolean,
  primary KEY(Permission_id)
);

/*  						Permissions								*/		 

Insert Into Permissions ( Permission_id, Title, Add_Product, Add_Employee, Cancel_Order, Add_Discount, Edit_Menu)
values ( 1, "Owner",  true, true, true, true, true);

Insert Into Permissions ( Permission_id, Title, Add_Product, Add_Employee, Cancel_Order, Add_Discount, Edit_Menu)
values ( 2, "Manager",  true, true, true, true, true);

Insert Into Permissions ( Permission_id, Title, Add_Product, Add_Employee, Cancel_Order, Add_Discount, Edit_Menu)
values ( 3, "Shift Manager",  false, false, true, true, false);

Insert Into Permissions ( Permission_id, Title, Add_Product, Add_Employee, Cancel_Order, Add_Discount, Edit_Menu)
values ( 4, "Chef",  true, false, false, false, true);

Insert Into Permissions ( Permission_id, Title, Add_Product, Add_Employee, Cancel_Order, Add_Discount, Edit_Menu)
values ( 5, "Waiter/Cook/WashDisher",  false, false, false, false, false);

/*  						MenuCategories								*/		 

insert into MenuCategories (Cat_id, Title, Parent_id) values (1000, "main", 0);
insert into MenuCategories (Cat_id, Title, Parent_id) values (1010, "pasta", 1000);
insert into MenuCategories (Cat_id, Title, Parent_id) values (1020, "pizza", 1000);
insert into MenuCategories (Cat_id, Title, Parent_id) values (1030, "Cold pizzas", 1020);
insert into MenuCategories (Cat_id, Title, Parent_id) values (1040, "Vegeterian", 1000);
insert into MenuCategories (Cat_id, Title, Parent_id) values (1050, "Burgers", 1000);
insert into MenuCategories (Cat_id, Title, Parent_id) values (1060, "Desserts", 1000);
insert into MenuCategories (Cat_id, Title, Parent_id) values (1070, "Drinks", 1000);
insert into MenuCategories (Cat_id, Title, Parent_id) values (1080, "Alcohol", 1070);
insert into MenuCategories (Cat_id, Title, Parent_id) values (1090, "Wine", 1080);
insert into MenuCategories (Cat_id, Title, Parent_id) values (1100, "Beers", 1080);

/*  						MenuItems								*/		 

insert into MenuItems (Cat_id, Title, Price, Item_id) values (1020, "Fungi", 67, 101);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1020, "Mozarela", 66, 102);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1010, "Bolonez", 59, 103);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1010, "Pesto", 64, 104);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1040, "Adashim", 53, 105);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1040, "Salad Hasa", 33, 106);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1040, "Tomato Salad", 26, 107);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1050, "CheeseBurger", 62, 108);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1050, "Greek Burger", 60, 109);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1060, "Cheese Cake", 37, 110);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1060, "Chocolate Shel Para", 26, 111);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1070, "Sprite", 12, 112);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1070, "Fanta", 12, 113);			  
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1070, "Coke", 12, 114);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1080, "Vodka", 24, 115);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1080, "Campari", 22, 116);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1080, "Wisky", 26, 117);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1080, "Arak", 18, 118);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1100, "Goldstar", 24, 119);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1100, "Stella", 27, 120);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1100, "Guiness", 28, 121);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1090, "Gevurtztraminner", 31, 122);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1090, "Kavarne Souvinion", 32, 123);
insert into MenuItems (Cat_id, Title, Price, Item_id) values (1090, "Chardone", 34, 124);

/*  						Employees								*/		 

Insert into Employees ( Employee_id, Permission_id, First_Name, Last_Name, Position, Age, Gender, City, Address, Email, Phone_Number, Password, Bank_Information )
              values ( 341455379, 1, "Jane", "Hildebrand", "Waiter", 23, "Female", "Houston", "34 Granville St.", "JaneTheBrain@LoveMe.com", 5105554209 , "Qwerty", "Franklin Bank 396632" );

Insert into Employees ( Employee_id, Permission_id, First_Name, Last_Name, Position, Age, Gender, City, Address, Email, Phone_Number, Password, Bank_Information )
              values ( 291551380, 2, "Shane", "Bor", "Waiter", 21, "Female", "New York", "124 Brooklyn St.", "shasa@gmail.com", 5123241344 , "Qw12312", "Hogwartz Bank 396632" );

Insert into Employees ( Employee_id, Permission_id, First_Name, Last_Name, Position, Age, Gender, City, Address, Email, Phone_Number, Password, Bank_Information )
              values ( 213690840, 3, "Moshe", "Roman", "Manager", 48, "Male", "Paris", "23 Korason St.", "bagget@rest.com", 9526125704 , "zhetem123", "Eifel Bank 466830" );
			  
Insert into Employees ( Employee_id, Permission_id, First_Name, Last_Name, Position, Age, Gender, City, Address, Email, Phone_Number, Password, Bank_Information )
              values ( 409453826, 2, "Harry", "Potter", "Waiter", 21, "Male", "London", "165 Magic St.", "HarryP@magic.com", 502016031 , "ilovemagic", "ClockTower Bank 112735" );

Insert into Employees ( Employee_id, Permission_id, First_Name, Last_Name, Position, Age, Gender, City, Address, Email, Phone_Number, Password, Bank_Information )
              values ( 341780439, 3, "Leo", "Dicaprio", "Shift Manager", 32, "Male", "Los Angeles", "56 Hollyood St.", "LeoTheDic@hotmail.com", 0105553168 , "titanic246", "ShutterIsland Bank 553466" );

Insert into Employees ( Employee_id, Permission_id, First_Name, Last_Name, Position, Age, Gender, City, Address, Email, Phone_Number, Password, Bank_Information )
              values ( 236710939, 6, "Sergio", "Alejandro", "Chef", 37, "Male", "Buenos Aires", "8 Holla St.", "argSerg@hotmail.com", 4323583190 , "boca1978", "Buenos Bank 33920100" );			  


/*  						VIP								*/		 
			  
insert into VIP (Id, First_Name, Last_Name, BirthDay, EMail) 
         values (1, "Boris", "Ablamunitz", "1988-01-28", "BorisAb@gmail.com");
         
insert into VIP (Id, First_Name, Last_Name, BirthDay, EMail) 
         values (2, "Shay", "Jerby", "1990-03-21", "ShayJe@gmail.com");
 		
insert into VIP (Id, First_Name, Last_Name, BirthDay, EMail) 
         values (3, "Moshe", "Cohen", "1994-06-11", "MoshC@walla.co.il");
		 
insert into VIP (Id, First_Name, Last_Name, BirthDay, EMail) 
         values (4, "Leonid", "Berman", "1984-01-28", "Leo@gmail.com");
		 
insert into VIP (Id, First_Name, Last_Name, BirthDay, EMail) 
         values (5, "Kinneret", "Tveria", "1992-05-03", "KinneretT@ynet.com");      
		 
insert into VIP (Id, First_Name, Last_Name, BirthDay, EMail) 
         values (7, "Anton", "Tzah", "1996-09-25", "Ant@amazon.com");      
		 
/*  						WorkingHours								*/		 
		 
insert into WorkingHours (Employee_id, Record_id, Clock_in, Clock_out) 
         values (291551380, 1, '2012-06-18 10:34:09', '2012-06-18 16:36:19');

insert into WorkingHours (Employee_id, Record_id, Clock_in, Clock_out) 
         values (291551380, 2, '2012-06-20 09:12:04', '2012-06-20 13:33:29');
      
insert into WorkingHours (Employee_id, Record_id, Clock_in, Clock_out) 
         values (341455379, 3,'2012-05-28 19:32:04', '2012-05-28 23:56:14');
         
insert into WorkingHours (Employee_id, Record_id, Clock_in, Clock_out) 
         values (341455379, 4, '2012-06-18 11:00:23', '2012-06-18 15:31:42');

insert into WorkingHours (Employee_id, Record_id, Clock_in, Clock_out) 
         values (341455379, 5, '2012-06-13 11:22:13', '2012-06-20 18:35:09');
     			  
insert into WorkingHours (Employee_id, Record_id, Clock_in, Clock_out) 
         values (409453826, 31, '2012-06-15 14:01:14', '2012-06-15 18:40:58');
		 
insert into WorkingHours (Employee_id, Record_id, Clock_in, Clock_out) 
         values (409453826, 25, '2012-06-08 20:00:16', '2012-06-08 23:42:19');
		 
insert into WorkingHours (Employee_id, Record_id, Clock_in, Clock_out) 
         values (213690840, 15, '2012-05-28 09:20:19', '2012-05-28 14:33:29');
		 
insert into WorkingHours (Employee_id, Record_id, Clock_in, Clock_out) 
         values (213690840, 18, '2012-06-02 16:34:16', '2012-06-02 21:35:19');

/*  						Orders								*/		 

Insert into Orders(Order_id, Employee_id, Table_Num, Order_date, Total_sum)
        values (54, 236710939, 22, '2012-06-18', 254);
        
Insert into Orders(Order_id, Employee_id, Table_Num, Order_date, Total_sum)
        values (32, 341780439, 14, '2012-06-13', 115);
        
Insert into Orders(Order_id, Employee_id, Table_Num, Order_date, Total_sum)
        values (86, 236710939, 8, '2012-06-13', 95);
        
Insert into Orders(Order_id, Employee_id, Table_Num, Order_date, Total_sum)
        values (4, 341780439, 24, '2012-06-18', 208);

Insert into Orders(Order_id, Employee_id, Table_Num, Order_date, Total_sum)
        values (15, 291551380, 18, '2012-07-15', 325);
		
Insert into Orders(Order_id, Employee_id, Table_Num, Order_date, Total_sum)
        values (12, 291551380, 43, '2011-05-10', 132);

Insert into Orders(Order_id, Employee_id, Table_Num, Order_date, Total_sum)
        values (98, 341455379, 31, '2012-12-25', 54);
		
Insert into Orders(Order_id, Employee_id, Table_Num, Order_date, Total_sum)
        values (64, 341455379, 12, '2011-02-12', 109);
		
Insert into Orders(Order_id, Employee_id, Table_Num, Order_date, Total_sum)
        values (78, 291551380, 31, '2012-07-08', 408);
		
		
/*  						OrderItems								*/		

Insert into OrderItems(Order_id, Item_id, Quantity)
        values (54, 102, 24);
		
Insert into OrderItems(Order_id, Item_id, Quantity)
        values (4, 111, 8);

Insert into OrderItems(Order_id, Item_id, Quantity)
        values (4, 110, 11);
		
Insert into OrderItems(Order_id, Item_id, Quantity)
        values (32, 123, 3);
		
Insert into OrderItems(Order_id, Item_id, Quantity)
        values (86, 122, 2);

Insert into OrderItems(Order_id, Item_id, Quantity)
        values (32, 100, 4);

Insert into OrderItems(Order_id, Item_id, Quantity)
        values (4, 120, 12);

Insert into OrderItems(Order_id, Item_id, Quantity)
        values (54, 102, 9);		

Insert into OrderItems(Order_id, Item_id, Quantity)
        values (15, 104, 2);
		
Insert into OrderItems(Order_id, Item_id, Quantity)
        values (12, 113, 4);

Insert into OrderItems(Order_id, Item_id, Quantity)
        values (64, 110, 6);
		
Insert into OrderItems(Order_id, Item_id, Quantity)
        values (78, 122, 1);
		
Insert into OrderItems(Order_id, Item_id, Quantity)
        values (4, 121, 2);

Insert into OrderItems(Order_id, Item_id, Quantity)
        values (98, 109, 1);

Insert into OrderItems(Order_id, Item_id, Quantity)
        values (86, 107, 2);

Insert into OrderItems(Order_id, Item_id, Quantity)
        values (54, 119, 3);		