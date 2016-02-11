/* global EmployeeService */

'use strict';

var allEmployees = [];
var activeEmployeeList = [];

$(document).ready(function() {
  // Get employee data from service ...
  EmployeeService.getAllEmployees(function(data) {
    allEmployees = data;
    $.each(allEmployees, function (index, employee) {
      var $node = $('<li/>').html(employee.firstName).attr('employee-id', employee.id).addClass('btn');
      $node.click(employeeFromListSelected);
      $('.all-employees-list').append($node);
    });
  });

  EmployeeService.getActiveEmployees(function(data) {
    activeEmployeeList = data;
    $.each(activeEmployeeList, function (index, employee) {
      var $node = $('<li data-toggle="tooltip" />').html(employee.firstName)
                            .attr('data-original-title', employee.position)
                            .tooltip({'placement': 'top'})
                            .attr('employee-id', employee.id)
                            .addClass('btn btn-success');
      $node.click(activeEmployeeSelected);
      $('.available-employees-list').append($node);
    });
  });
});

function activeEmployeeSelected(event) { 
  var $target = $(event.target);
  var employeeId = $target.attr('employee-id');

  console.log('Navigate to table for employee id ' + employeeId);
  window.location.href = 'tables.html?employeeId=' + employeeId; //Maybe change here to first name
}

function employeeFromListSelected(event) {
  var $target = $(event.target);
  var employeeId = $target.attr('employee-id');

  console.log(employeeId);
}
