'use strict'

var allEmployees = [];
var activeEmployeeLis = [];

function updateEmployeeList() {
  doAjaxGet('employees').done(function(response) {
    allEmployees = response;
    console.log(allEmployees);

    doAjaxPost('employees/active', allEmployees[0]);
  });
};

$(document).ready(function() {
  updateEmployeeList();
});
