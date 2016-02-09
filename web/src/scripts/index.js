'use strict'

var allEmployees = [];
var activeEmployeeLis = [];

function updateEmployeeList() {
  doAjaxGet('employees').done(function(response) {
    allEmployees = response;
    console.log(allEmployees);
  });
};

$(document).ready(function() {
  updateEmployeeList();
});
