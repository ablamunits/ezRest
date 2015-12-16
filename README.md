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

# Employee

`api/employees` GET & POST

`api/employees/{id}`

`api/employee/delete/{id}`

# Permissions

`api/permissions` GET & POST

`api/permissions/{id}` specific permission by ID

# Menu

`api/menu/category/{catId}` Get & POST category

`api/menu/item` Add new Item

`api/menu/item/{itemId}` Get menu item

`api/menu/category/delete/{id}` Delete menu category by id.

# Orders

`api/orders/{orderId}` Get order

# Vip

`api/vip/{id}` Get vip
