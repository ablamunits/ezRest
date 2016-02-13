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

function managerAccessOpen(event) {
  $('.login-container').fadeIn();
};

function authenticateUser() {
  $('.alert.login').hide();
  $('.login-pending').show();

  var loginEmail = $('.login.email').val();
  var loginPassword = $('.login.password').val();

  AuthService.login(loginEmail, loginPassword, function() {
    AuthService.authState(function(authObject) {
      if (authObject.loggedIn === false) {
        handleFailedLogin();
      } else {
        handleSuccessLogin(authObject);
      }
    });
  });
}

function handleFailedLogin() {
  $('.alert.login').hide();
  $('.alert.login-failed').show();
};

function handleSuccessLogin(authObject) {
  console.log('login ok!');

  $('.alert.login').hide();
  $('.alert.login-success').append('<span>Hey ' + authObject.firstName + '! We will redirect you to your admin section in a second!</span>').show();

  setTimeout(function() {
    window.location.href = 'admin.html';
  }, 3000);
}
