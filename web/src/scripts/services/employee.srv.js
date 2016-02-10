/* global doAjaxGet */

var EmployeeService = {
    getAllEmployees: function (cb) {
        doAjaxGet('employees').done(function (response) {
            cb(response);
        });
    },
    getActiveEmployees: function (cb) {
        doAjaxGet('employees/active').done(function (response) {
            cb(response);
        });
    },
    
    getEmployeeById: function (employeeId, cb) {
        doAjaxGet('employees/active/' + employeeId).done(function (response) {
            cb(response);
        });
    }
};
