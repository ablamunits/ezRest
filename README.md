# ezRest

Web-based POS system for restaurants!
----

MTA 2015-6 Internet and Web Applications course project.
---

By Shay Jerby (200949261) and Boris Ablamunits (310370689)

#### Updates since the first submission:

1. The `workingHours` table now has a `recordId` column, to differentiate between clock-in/out records.
2. A foreign key has been added to the `Parent_id` field in `MenuCategories`, to reference `Cat_id` in the same table.
3. Permissions that were chosen for the `Permissions` table: ADD_PRODUCT, ADD_EMPLOYEE, CANCEL_ORDER, ADD_DISCOUNT, EDIT_MENU.
4. Employee working hours will be calculated differently than specified in the original document - the total working hours on
a specific month will be retrieved from the server and calculated client-side.

----

Notes:

1. When using `api/auth` - authentication is done with the employee's email (mandatory field in the database) and password.

Full list of API requests available in `documentation/APIDocument`.