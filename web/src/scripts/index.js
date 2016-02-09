'use strict'

var allEmployees = [];
var activeEmployeeLis = [];

function updateEmployeeList() {
  var obj = doAjaxPost('employees')
  console.log(obj);
};

$(document).ready(function() {
  updateEmployeeList();
});
