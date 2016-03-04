'use strict';

var authObject;
var employeeObject;
var authorizedActions;
var permissionTitle;
var allCategories = [];
var allEmployees = [];
var allPermissions = [];
var workingHours = [];
var allVip = [];
var monthArr = ['nothing', 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

$(document).ready(function () {

//    $('#vipClick').click(function(){
//       newVipClick(); 
//    });
//    
    $('.all-hours-table').empty();

    initMonthSelecter();

    MenuService.getAllCategories(function (responseObject) {
        allCategories = responseObject;
    });

    EmployeeService.getAllEmployees(function (responseObject) {
        allEmployees = responseObject;
    });

    PermissionService.getAllPermissions(function (responseObject) {
        allPermissions = responseObject;
    });

    VipService.getAllVip(function (responseObject) {
        allVip = responseObject;
    });

    AuthService.authState(function (responseObject) {
        authObject = responseObject;

//     EmployeeService.getEmployeeById(authObject.employeeId, function(responseObject) {
        var tempId = 1;
        EmployeeService.getEmployeeById(tempId, function (responseObject) {
            employeeObject = responseObject;
            PermissionService.getPermissionById(employeeObject.permissionId, function (permissionResponse) {
                authorizedActions = PermissionService.authorizedActions;

                $.each(permissionResponse.authorizedActions, function (index, action) {
                    authorizedActions[action] = true;
                });

                permissionTitle = permissionResponse.title;

                EmployeeService.getEmployeeAllWorkingHours(tempId, function (hoursObject) {
                    workingHours = hoursObject;
                });
            });
        });
    });

    // Only display info after all ajax requests ended.
    $(this).ajaxStop(function () {
        displayInfo();
    });
});

function displayInfo() {
    $.each(allCategories, function (idx, category) {
        var option = $('<option>').attr('value', idx).attr('category-id', category.categoryId).text(category.title === 'DUMMY_ROOT' ? 'Root Category' : category.title);
        $('.select-category').append(option);
    });
    $('.select-category').selecter();

    $.each(allEmployees, function (idx, employee) {
        var option = $('<option>').attr('value', idx).attr('employee-id', employee.id).text(employee.firstName + ' ' + employee.lastName);
        $('.select-employee').append(option);
    });
    $('.select-employee').selecter();

    $('.user-name').text(employeeObject.firstName + ' ' + employeeObject.lastName);
    $('.user-age').text(employeeObject.age);
    $('.user-email').text(employeeObject.email);
    $('.user-position').text(employeeObject.position);
    $('.user-permission-title').text(permissionTitle);

    initWorkingHours(getCurrentMonth());

    $.each(allVip, function (idx, vip) {
        var option = $('<option>').attr('value', idx).attr('vip-id', vip.id).text(vip.firstName + ' ' + vip.lastName);
        $('.select-vip').append(option);
    });
    $('.select-vip').selecter();
}

function initMonthSelecter() {
    $('.select-month').selecter({
        label: "Choose Month"
    });
    $('.select-month').on('change', function () {
        initWorkingHours(parseInt(this.value));
    });
}

function initWorkingHours(chosenMonth) {

//                                    <tr>
//                                        <td>timestamp in</td>
//                                        <td>timestamp out</td>
//                                        <td>duration</td>
//                                    </tr>
//                          

    var monthPara = chosenMonth === getCurrentMonth() ? "Your work this month:" : "Your work in month " + monthArr[chosenMonth] + ":";
    $('#month-para').text(monthPara);
    $('.all-hours-table').empty();
    $.each(workingHours, function (index, shift) {

        if (shift.clockInTimestamp !== undefined && shift.clockOutTimestamp !== undefined && chosenMonth === getMonthFromClockIn(shift.clockInTimestamp)) {

            var $tableClockIn = $('<td>').text(shift.clockInTimestamp);
            var $tableClockOut = $('<td>').text(shift.clockOutTimestamp);
            var $tableDuration = $('<td>').text(getDifferenceDate(shift));
            var $tableLine = $('<tr/>').append($tableClockIn).append($tableClockOut).append($tableDuration);

            $('.all-hours-table').append($tableLine);

        }
    });
}

function getDifferenceDate(shift) {
    var startDate = new Date(shift.clockInTimestamp);
    var endDate = new Date(shift.clockOutTimestamp);
    var diff = endDate - startDate;

    return msToTime(diff);
}

function msToTime(duration) {
    var milliseconds = parseInt((duration % 1000) / 100)
            , seconds = parseInt((duration / 1000) % 60)
            , minutes = parseInt((duration / (1000 * 60)) % 60)
            , hours = parseInt((duration / (1000 * 60 * 60)) % 24);

    hours = (hours < 10) ? "0" + hours : hours;
    minutes = (minutes < 10) ? "0" + minutes : minutes;
    seconds = (seconds < 10) ? "0" + seconds : seconds;

    return hours + ":" + minutes + ":" + seconds + "." + milliseconds;
}

function getCurrentMonth() {
    var month = new Date().getMonth();
    return ++month;
}

function getMonthFromClockIn(clockIn)
{
    var month = new Date(clockIn).getMonth();
    return ++month;
}

function newVipClick() {

//    if (authorizedActions['ADD_DISCOUNT'] === true) {
    $('.vip-title').html('<strong>Add Vip!</strong>');
    $('#handle-vip-button').text('Submit new Vip!');

    $('.handle-vip').find('input.first-name').val("");
    $('.handle-vip').find('input.last-name').val("");
    $('.handle-vip').find('input.email').val("");
    $('.handle-vip').find('input.birthday').val("");

    hideContentVip();
    showVipHandle();
//    } else {
////        alertMechanism.Error("Sorry, You can't perform this kind of action");
//    }
}

function editVipClick() {
//    if (authorizedActions['ADD_DISCOUNT'] === true) {
    $('.vip-title').html('<strong>Edit Vip!</strong>');
    $('#handle-vip-button').text('Edit Vip!');
    var vipId = parseInt($('.select-vip option:selected').attr('vip-id'));

    VipService.getVipById(vipId, function (vipObject) {
        $('.handle-vip').find('input.first-name').val(vipObject.firstName);
        $('.handle-vip').find('input.last-name').val(vipObject.lastName);
        $('.handle-vip').find('input.email').val(vipObject.email);
        $('.handle-vip').find('input.birthday').val(vipObject.birthday);
    });

    hideContentVip();
    showVipHandle();
//    } else {
//        alertMechanism.Error("Sorry, You can't perform this kind of action");
//    }
}

function deleteVip() {

//    if (authorizedActions['ADD_DISCOUNT'] === true) {
    var vipId = parseInt($('.select-vip option:selected').attr('vip-id'));

    VipService.getVipById(vipId, function (vipObject) {
        VipService.deleteVip(vipId, function (response) {
            if (response === undefined) {
                displaySuccessAndRefresh('', 'Vip ' + vipObject.firstName + ' ' + vipObject.lastName + ' deleted :(');
            }
        });
    });
//    } else {
//        alertMechanism.Error("Sorry, You can't perform this kind of action");
//    }
}

function submitVip() {
    var handle = $('.vip-title').text();

    var firstName = $('.handle-vip').find('input.first-name').val();
    var lastName = $('.handle-vip').find('input.last-name').val();
    var email = $('.handle-vip').find('input.email').val();
    var birthday = $('.handle-vip').find('input.birthday').val();

    if (handle === 'Edit Vip!')
    {
        var vipId = parseInt($('.select-vip option:selected').attr('vip-id'));
        var vipObject = {id: vipId, firstName: firstName, lastName: lastName, email: email, birthday: birthday};
        VipService.updateVip(vipId, vipObject, function (response) {
            if (response === undefined) {
                displaySuccessAndRefresh('Great!', 'Vip ' + vipObject.firstName + ' ' + vipObject.lastName + ' was updated!');
            }
        });
    } else {
        var vipObject = {firstName: firstName, lastName: lastName, email: email, birthday: birthday};
        VipService.addNewVip(vipObject, function (response) {
            if (response === undefined) {
                displaySuccessAndRefresh('Great!', 'New vip ' + firstName + ' ' + lastName + ' was added!');
            }
        });
    }
}

function closeVip() {
    $('.handle-vip').slideUp();
    $('.content-vip').slideDown();
}

function hideContentVip() {
    $('.content-vip').slideUp();
}

function showVipHandle() {
    $('.handle-vip').slideDown();
}

// Admin: Menu edit
var selectedCategory = {};
function newItemClick(event) {
    selectedCategory = {
        id: $('.select-category option:selected').attr('category-id'),
        title: $('.select-category option:selected').text()
    };

    $('.selected-category').text(selectedCategory.title);
    hideEditMenuMain();
    showAddNewItem();
}
;

function showAddNewItem() {
    $('.add-new-item').slideDown();
}

function submitNewItem(event) {
    var newItemTitle = $('input.item-title').val();
    var newItemPrice = $('input.item-price').val();

    if (!isValidString(newItemTitle) || !Number.isInteger(parseInt(newItemPrice))) {
        $('.add-new-item .input-error').fadeIn();
        return;
    }

    var newItem = {
        title: newItemTitle,
        price: parseInt(newItemPrice),
        categoryId: selectedCategory.id,
        type: 'menuItem',
        isCategory: false,
        nextCategoryId: 0,
    }

    MenuService.addNewItem(newItem, function (response) {
        closeEverything();
        displaySuccessAndRefresh('Sweet!', 'Item ' + newItem.title + ' added!');
    });
}

function newCategoryClick(event) {
    selectedCategory = {
        id: $('.select-category option:selected').attr('category-id'),
        title: $('.select-category option:selected').text()
    };

    $('.selected-category').text(selectedCategory.title);
    hideEditMenuMain();
    showAddNewCategory();
}
;

function showAddNewCategory() {
    $('.add-new-category').slideDown();
}

function submitNewCategory(event) {
    var newCategoryTitle = $('input.item-category-title').val();

    if (!isValidString(newCategoryTitle)) {
        $('.add-new-category .input-error').fadeIn();
        return;
    }

    var newCategory = {
        title: newCategoryTitle,
        parentId: selectedCategory.id,
        type: 'menuCategory',
        isCategory: true,
    }

    MenuService.addNewCategory(newCategory, function (response) {
        closeEverything();
        displaySuccessAndRefresh('Awesome!', 'Category ' + newCategory.title + ' added!');
    });
}

function showEditMenuMain() {
    $('.edit-menu-main').slideDown();
}

function hideEditMenuMain() {
    $('.edit-menu-main').slideUp();
}

// Admin: Employees edit
function deleteEmployeeClick(event) {
    var employeeId = $('.select-employee option:selected').attr('employee-id');
    var employeeName = $('.select-employee option:selected').text();

//		EmployeeService.deleteEmployee(employeeId, (response) => {
//			displaySuccessAndRefresh('Alright!', 'We have deleted ' + employeeName + ' for you.');
//		});

    EmployeeService.deleteEmployee(employeeId, function (response) {
        displaySuccessAndRefresh('Alright!', 'We have deleted ' + employeeName + ' for you.');
    });
}

function editEmployeeClick(event) {
    var employeeId = $('.select-employee option:selected').attr('employee-id');
    var $modal = $('.update-employee-modal');

//	EmployeeService.getEmployeeById(employeeId, (employee) => {
//		$modal.find('input.first-name').val(employee.firstName);
//		$modal.find('input.last-name').val(employee.lastName);
//		$modal.find('input.email').val(employee.email);
//		$modal.find('input.password').val(employee.password);
//		$modal.find('input.age').val(employee.age);
//		$modal.find('input.position').val(employee.position);
//		$modal.find('.new-employee-gender-select').val(employee.gender);
//
//		$modal.find('.edit-employee-name').text(employee.firstName + ' ' + employee.lastName);
//	});

    EmployeeService.getEmployeeById(employeeId, function (employee) {
        $modal.find('input.first-name').val(employee.firstName);
        $modal.find('input.last-name').val(employee.lastName);
        $modal.find('input.email').val(employee.email);
        $modal.find('input.password').val(employee.password);
        $modal.find('input.age').val(employee.age);
        $modal.find('input.position').val(employee.position);
        $modal.find('.new-employee-gender-select').val(employee.gender);

        $modal.find('.edit-employee-name').text(employee.firstName + ' ' + employee.lastName);
    });

    $.each(allPermissions, function (idx, permission) {
        var option = $('<option>').attr('value', idx).attr('permission-id', permission.permissionId).text(permission.title);
        $modal.find('.new-employee-permission-select').empty().append(option);
    });
    $('select').selecter();

//	$modal.find('button.cancel').click(() => {
//		$modal.hide();
//	});

    $modal.find('button.cancel').click(function () {
        $modal.hide();
    });

    $modal.show();
}
;

function submitEditedEmployee() {
    var $modal = $('.update-employee-modal');
    var employeeId = $('.select-employee option:selected').attr('employee-id');

    var editedEmployee = $.extend(employeeFromModal($modal), {id: employeeId}); // Quick fix, edit not working well on server it seems.

//	EmployeeService.editEmployee(employeeId, editedEmployee, (response) => {
//		$modal.hide();
//		displaySuccessAndRefresh('Great!', 'Employee ' + editedEmployee.firstName + ' ' + editedEmployee.lastName + ' has been updated!');
//	});

    EmployeeService.editEmployee(employeeId, editedEmployee, function (response) {
        $modal.hide();
        displaySuccessAndRefresh('Great!', 'Employee ' + editedEmployee.firstName + ' ' + editedEmployee.lastName + ' has been updated!');
    });
}
;

function newEmployeeClick(event) {
    var $modal = $('.new-employee-modal');
    $.each(allPermissions, function (idx, permission) {
        var option = $('<option>').attr('value', idx).attr('permission-id', permission.permissionId).text(permission.title);
        $modal.find('.new-employee-permission-select').append(option);
    });
    $('select').selecter();

//	$modal.find('button.cancel').click(() => {
//		$modal.hide();
//	});

    $modal.find('button.cancel').click(function () {
        $modal.hide();
    });

    // $modal.find('button.submit').click(() => {
    // 	submitNewEmployee();
    // });

    $modal.show();
}
;

function submitNewEmployee() {
    var $modal = $('.new-employee-modal');

    var newEmployee = employeeFromModal($modal);

//	EmployeeService.addNewEmployee(newEmployee, (response) => {
//		$modal.hide();
//		displaySuccessAndRefresh('Great!', 'Employee ' + newEmployee.firstName + ' ' + newEmployee.lastName + ' added!');
//	});

    EmployeeService.addNewEmployee(newEmployee, function (response) {
        $modal.hide();
        displaySuccessAndRefresh('Great!', 'Employee ' + newEmployee.firstName + ' ' + newEmployee.lastName + ' added!');
    });
}

// Admin: Utils for this section
function employeeFromModal($modal) {
    var firstName = $modal.find('input.first-name').val();
    var lastName = $modal.find('input.last-name').val();
    var email = $modal.find('input.email').val();
    var password = $modal.find('input.password').val();
    var age = parseInt($modal.find('input.age').val());
    var position = $modal.find('input.position').val();
    var gender = $modal.find('.new-employee-gender-select option:selected').attr('value');
    var permissionId = parseInt($modal.find('.new-employee-permission-select option:selected').attr('permission-id'));

    var newEmployee = {
        firstName: firstName,
        lastName: lastName,
        age: age,
        email: email,
        password: password,
        gender: gender,
        position: position,
        permissionId: permissionId,
    };

    return newEmployee;
}

function closeEverything() {
    $('.modal').hide();

    $('.add-new-item').slideUp();
    $('.add-new-category').slideUp();

    showEditMenuMain();
}

function displaySuccessAndRefresh(strongText, regularText) {
    $('.alert.data-submit-ok p').empty().append('<strong>' + strongText + '</strong> ' + regularText);
    $('.alert.data-submit-ok').show();
    $('body').scrollTo('.alert.data-submit-ok');

//	setTimeout(() => {
//		location.reload();
//	}, 2000);
    setTimeout(function () {
        location.reload();
    }, 2000);
}

function logoutUser() {
    AuthService.logout(function (response) {
        if (response === undefined) {
            employeeObject = null;
            authorizedActions = null;
            authObject = null;
            permissionTitle = null;
            allCategories = [];
            allEmployees = [];
            allPermissions = [];
            workingHours = [];

            window.location.href = '../index.html';
        }
    });
}