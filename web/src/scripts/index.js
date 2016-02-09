'use strict'

var allEmployees = [];
var activeEmployeeList = [];

$(document).ready(function() {
  // Get employee data from service ...
  EmployeeService.getAllEmployees(function(data) {
    allEmployees = data;
    $.each(allEmployees, function (index, employee) {
      var $node = $('<li/>').html(employee.firstName);
      $('.all-employees-list').append($node);
    });
  });

  EmployeeService.getActiveEmployees(function(data) {
    activeEmployeeList = data;
    $.each(activeEmployeeList, function (index, employee) {
      var $node = $('<li/>').html(employee.firstName);
      $('.available-employees-list').append($node);
    });
  });
});
