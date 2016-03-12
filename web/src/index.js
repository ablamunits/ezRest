/* global EmployeeService */

'use strict';

var allEmployees = [];
var activeEmployeeList = [];
var availableEmployees = [];

$(document).ready(function () {
    resetEmployees();
});

function resetEmployees() {
    $('.available-employees-list').empty();
    $('.all-employees-list').empty();
    allEmployees = [];
    activeEmployeeList = [];
    availableEmployees = [];
    // Get employee data from service ...
    EmployeeService.getAllEmployees(function (data) {
        allEmployees = data;
        EmployeeService.getActiveEmployees(function (data) {
            activeEmployeeList = data;

            for (var i = 0; i < allEmployees.length; i++) {
                if (!contains(allEmployees[i].id, activeEmployeeList)) {
                    availableEmployees.push(allEmployees[i]);
                }
            }

            $.each(activeEmployeeList, function (index, employee) {
                var $node = $('<li data-toggle="tooltip" />').html(employee.firstName)
                        .attr('data-original-title', employee.position)
                        .tooltip({'placement': 'top'})
                        .attr('employee-id', employee.id)
                        .addClass('btn btn-square');
                $node.click(activeEmployeeSelected);
                $('.available-employees-list').append($node);
            });

            $.each(availableEmployees, function (index, employee) {
                var $node = $('<li/>').html(employee.firstName).attr('employee-id', employee.id).addClass('btn btn-square');
                $node.click(employeeFromListSelected);
                $('.all-employees-list').append($node);
            });
        });

    });
}

function contains(id, activeEmployeeList) {
    for (var i = 0; i < activeEmployeeList.length; i++) {
        if (activeEmployeeList[i].id === id)
            return true;
    }
    return false;
}

function activeEmployeeSelected(event) {
    var $target = $(event.target);
    var employeeId = $target.attr('employee-id');

    console.log('Navigate to table for employee id ' + employeeId);
    window.location.href = './tables/tables.html?employeeId=' + employeeId; //Maybe change here to first name
}

function employeeFromListSelected(event) {
    var $target = $(event.target);
    var employeeId = parseInt($target.attr('employee-id'));

    $.each(allEmployees, function (index, employee) {
        if (employeeId === employee.id) {
            EmployeeService.clockIn(employeeId, function () {
                EmployeeService.addActiveEmployee(employee, function () {
                    resetEmployees();
                    return;
                });
            });

        }
    });
    console.log(employeeId);
}

function managerAccessOpen(event) {
	var $loginModal =  $('.login-container');
	$loginModal.find('a.cancel').click(function () {
		$loginModal.hide();
		loginAgain();
	});

	$loginModal.fadeIn();
};

function loginAgain() {
	$('.login-container .alert').hide();
	$('.login-form').show();
};

function authenticateUser() {
		$('.login-form').hide();

    $('.alert.login').hide();
    $('.login-pending').show();

    var loginEmail = $('.login.email').val();
    var loginPassword = $('.login.password').val();

    AuthService.login(loginEmail, loginPassword, function () {
        AuthService.authState(function (authObject) {
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
}
;

function handleSuccessLogin(authObject) {
    console.log('login ok!');

    $('.alert.login').hide();
    $('.alert.login-success').append('<span>Hey ' + authObject.firstName + '! We will redirect you to your admin section in a second!</span>').show();

    setTimeout(function () {
        window.location.href = './admin/admin.html';
    }, 3000);
}
