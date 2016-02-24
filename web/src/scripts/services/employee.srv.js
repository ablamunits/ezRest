/* global doAjaxGet */

var EmployeeService = {
    getAllEmployees: function (cb) {
        doAjaxGet('employees').done(function (response) {
            cb(response);
        });
    },
    addNewEmployee: function (newEmployee, cb) {
        doAjaxPost('employees', newEmployee).done(function (response) {
            cb(response);
        });
    },
    editEmployee: function (id, editedEmployee, cb) {
        doAjaxPost('employees/' + id, editedEmployee).done(function (response) {
            cb(response);
        });
    },
    getActiveEmployees: function (cb) {
        doAjaxGet('employees/active').done(function (response) {
            cb(response);
        });
    },
    getEmployeeById: function (employeeId, cb) {
        doAjaxGet('employees/' + employeeId).done(function (response) {
            cb(response);
        });
    },
    getActiveEmployeeById: function (employeeId, cb) {
        doAjaxGet('employees/active/' + employeeId).done(function (response) {
            cb(response);
        });
    },
    deleteEmployee: function (employeeId, cb) {
        doAjaxPost('/employees/delete/' + employeeId).done(function (response) {
            cb(response);
        });
    }
};
