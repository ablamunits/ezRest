'use strict';

var authObject;
var employeeObject;
var authorizedActions;
var permissionTitle;

$(document).ready(function() {
  AuthService.authState(function(responseObject) {
    authObject = responseObject;
  });

  // TODO: Change the number 1 to authObject.id
  EmployeeService.getEmployeeById(1, function(responseObject) {
    employeeObject = responseObject;
    PermissionService.getPermissionById(employeeObject.permissionId, function(permissionResponse){
      authorizedActions = PermissionService.authorizedActions;

      $.each(permissionResponse.authorizedActions, function(index, action) {
        authorizedActions[action] = true;
      });

      permissionTitle = permissionResponse.title;
    });
  });

  // Only display info after all ajax requests ended.
  $(this).ajaxStop(function() {
    displayInfo();
  });
});

function displayInfo() {
  $('.user-name').text(employeeObject.firstName + ' ' + employeeObject.lastName);
  $('.user-age').text(employeeObject.age);
  $('.user-email').text(employeeObject.email);
  $('.user-position').text(employeeObject.position);
  $('.user-permission-title').text(permissionTitle);
  console.log(authorizedActions);
}
