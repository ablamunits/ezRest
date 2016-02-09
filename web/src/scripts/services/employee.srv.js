var EmployeeService = {
  getAllEmployees: function(cb) {
    doAjaxGet('employees').done(function(response) {
      cb(response);
    });
  },

  getActiveEmployees: function(cb) {
    doAjaxGet('employees/active').done(function(response) {
      cb(response);
    });
  }
}
