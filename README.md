# ezRest

Web-based POS system for restaurants!
----

#### Important updates:
In permission table, each action title (column) needs to be uppercase. Make sure you have these: ADD_PRODUCT, ADD_EMPLOYEE, CANCEL_ORDER, ADD_DISCOUNT, EDIT_MENU

<b>Currently working on:</b> MySql tables, connections and REST

----

<b>Tools to use:</b>

1. Chrome extension to test GET / POST requests: https://chrome.google.com/webstore/detail/advanced-rest-client/hgmloofddffdnphfgcellkdfbfbjeloo
  
    How? For GET requests, just enter a GET url like `../api/employees` and see the response.
    For POST, choose the POST option, enter the url, and add Query Parameters (like Id, Name etc..) by clicking the little arrow on the side.

--

<b>Configuration:</b> Files are in src.config, just change what you need there to connect to your own tables.

<b>Utilities:</b> MySqlUtils has some functions to easily do queries. use `getQuery` for SELECT, and `updateQuery` for things like INSERT, DELETE - which do not return a result set.

<b>Debugging:</b> If things are not working, look in the log (in netbeans) for the exceptions that are thrown, if something is wrong with mySQL queries for example..

--

Here is a <b>list of all API request</b>, which we will also need to submit with EX2, so lets update here with what is working.

# Authentication

`api/auth` GET is logged in ? if logged in - some information.

`api/auth/login` POST login with {email, password}

`api/auth/logout` POST logout

# Employee

`api/employees` GET all employee

`api/employees/{id}` GET employee {id}

`api/employees` POST add new employee

`api/employees/{id}` POST update employee {id}

`api/employee/delete/{id}` POST delete employee 


# Permissions

`api/permissions` GET all permissions

`api/permissions/{id}` GET specific permission by {id}

`api/permissions` POST add new permission

`api/permissions/{id}` POST update permission {id}

`api/permissions/delete/{id}` POST delete permission {id}

# Menu

# Category

  `api/menu/category/{catId}` Get menu category by {catId}

  `api/menu/category` Get all menu categories

  `api/menu/category/{catId}` POST update menu category by {catId}

  `api/menu/category/delete/{catId}` POST Delete menu category by {catId}

  `api/menu/category` POST new menu category

# Item

  `api/menu/item/{itemId}` Get menu item

  `api/menu/item` GET menu item overview (each menu item will have row with: title, item id, number of tables, quantity)

  `api/menu/item` POST new menu item

  `api/menu/item/{itemId}` POST update menu item by {itemId}

  `api/menu/item/delete/{itemId}` POST delete menu item by {itemId}

# Orders

`api/orders/{id}` Get order by {id}

`api/orders` POST new order 

`api/orders/{id}` POST update order by {id}

`api/orders/delete/{id}` POST Delete order by {id}

# Vip

`api/vip/{id}` GET vip by {id}

`api/vip` GET all vip

`api/vip` POST new vip

`api/vip/delete/{id}` POST Delete vip by {id}

# OrderItems

`api/orderItems/{id}` GET items of {id}

`api/orderItems` POST new orderItems to db (will use this function after order was closed - otherwise use Redis)

`api/orderItems/delete/{orderId}` Delete all lines of orderItems by orderId

# WorkingHours

`api/workingHours/{employeeId}` GET all working hours of employee {id}

`api/workingHours/record/{employeeId}?recordId={recordId}` GET working hours by recordId

`api/workingHours/durationRecord/{employeeId}?recordId={recordId}` GET the duration working hours by {recordId} of {employeeId}

`api/workingHours/durationMonth/{employeeId}?month={month[1-12]}` GET the duration month {1-12} working hours of {employeeId}

`api/workingHours/clockIn/{employeeId}` POST employee {employeeId} clock in shift (Saving to session)

`api/workingHours/clockOut/{employeeId}` POST employee {employeeId} clock out shift (Deleting session)

`api/workingHours/delete/{recordId}` POST Delete record {recordId}
