/* global EmployeeService, MenuService, OrdersService */

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
var menuList = [];
var menuItemsOverview = [];
var moment;
var monthArr = ['nothing', 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
var allOrders = [];

$(document).ready(function () {

    initDatePicker();
    $('.all-orders-table').empty();

    $('#order-tab-button').click(function(){
        $('.all-orders-table').empty();
    });

    moment().format();

    $('.all-hours-table').empty();

    initMonthSelecter();

    MenuService.getMenuItemsOverview(function (overviewObject) {
        menuItemsOverview = overviewObject;
    });

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
            EmployeeService.getEmployeeById(authObject.employeeId, function(responseObject) {
        // var tempId = 1;
        // EmployeeService.getEmployeeById(tempId, function (responseObject) {
            employeeObject = responseObject;
            PermissionService.getPermissionById(employeeObject.permissionId, function (permissionResponse) {
                authorizedActions = PermissionService.authorizedActions;

                permissionTitle = permissionResponse.title;
                $.each(permissionResponse.authorizedActions, function (index, action) {
                    authorizedActions[action] = true;
                });

                handleAuthorizedActions(authorizedActions);

                EmployeeService.getEmployeeAllWorkingHours(employeeObject.id, function (hoursObject) {
                    workingHours = hoursObject;
                });
            });
        });
    });

    $('.select-category').on('change', function () {
        initMenuItemsOptions(parseInt($('.select-category option:selected').attr('category-id')));
    });

    $('.select-item-wrapper').on('change', function () {
        if (parseInt($('.select-item-wrapper option:selected').attr('value')) !== -1) {
            $('#edit-item-button').removeAttr('disabled');
            $('#delete-item-button').removeAttr('disabled');
            $('#item-report-button').removeAttr('disabled');
            $('#item-report-button').removeClass('disabled-icon');
        } else if (parseInt($('.select-item-wrapper option:selected').attr('value')) === -1) {
            disableItemSelecter();
        }
    });

    initMenuItemsOptions(1);

    // Only display info after all ajax requests ended.
    $(this).ajaxStop(function () {
        displayInfo();
    });
});

function handleAuthorizedActions(actions) {
    // The function recieves an object with actions, and disables functionality of the page based on value.
		// If an employee is authorized to add new employees to the system, he is also authorized to add new permissions.
    if (!actions.ADD_EMPLOYEE) {
        var $editEmployeePanel = $('.edit-employees-wrapper');
				var $editPermissionsPanel = $('.edit-permissions-wrapper');

        $editEmployeePanel.addClass('unauthorized');
				$editPermissionsPanel.addClass('unauthorized');
    };

    if (!actions.EDIT_MENU) {
        var $editMenuPanel = $('.edit-menu-wrapper');
        $editMenuPanel.addClass('unauthorized');
    };
}

function displayInfo() {
    $.each(allCategories, function (idx, category) {
        var option = $('<option>').attr('value', idx)
                .attr('category-id', category.categoryId)
                .attr('parent-id', category.parentId)
                .text(category.title === 'DUMMY_ROOT' ? 'Root Category' : category.title);
        $('.select-category').append(option);
    });
    $('.select-category').selecter();

    $.each(allEmployees, function (idx, employee) {
        var option = $('<option>').attr('value', idx).attr('employee-id', employee.id).text(employee.firstName + ' ' + employee.lastName);
        $('.select-employee').append(option);
    });
    $('.select-employee').selecter();

    $.each(allVip, function (idx, vip) {
        var option = $('<option>').attr('value', idx).attr('vip-id', vip.id).text(vip.firstName + ' ' + vip.lastName);
        $('.select-vip').append(option);
    });
    $('.select-vip').selecter();

    $.each(allPermissions, function (idx, permission) {
        var option = $('<option>').attr('value', idx).attr('permission-id', permission.permissionId).text(permission.title);
        $('.select-permission').append(option);
    });
    $('.select-permission').selecter();

    $('.user-name').text(employeeObject.firstName + ' ' + employeeObject.lastName);
    $('.user-age').text(employeeObject.age);
    $('.user-email').text(employeeObject.email);
    $('.user-position').text(employeeObject.position);
    $('.user-permission-title').text(permissionTitle);

    initWorkingHours(getCurrentMonth());

    if (menuList.length === 0) {
        initMenuItemSelect();
        disableItemSelecter();
        $('.select-item').attr('disabled', 'disabled');
    } else {
        initMenuItemSelect();
        $.each(menuList, function (idx, menuItem) {
            if (!(menuItem.isCategory)) {
                var option = $('<option>').attr('value', idx)
                        .attr('item-id', menuItem.itemId)
                        .attr('item-price', menuItem.price)
                        .text(menuItem.title);
                $('.select-item').append(option);
            }
        });
    }
    $('.select-item').selecter();
}

function initDatePicker() {
    var date_input = $('#date'); //our date input has the name "date"
    var container = $('.orders-tab form').length > 0 ? $('.orders-tab form').parent() : "body";
    var options = {
        format: 'dd-mm-yyyy',
        container: container,
        todayHighlight: true,
        autoclose: true
    };
    date_input.datepicker(options);
}

function onSubmitOrderClick() {
    allOrders = [];
    $('.date-wrapper .input-error').fadeOut();
    $(document).unbind('ajaxStop');
    var inputDateString = $('.date-wrapper input').val();
    var day = inputDateString.substring(0, 2);
    var month = inputDateString.substring(3, 5);
    var year = inputDateString.substring(6, 10);
    var inputDate = parseInt(day + month + year);

    $('.all-orders-table').empty();
    $.get("http://webedu3.mtacloud.co.il:8080/ezRest/api/orders/date/" + inputDate, function (idList) {

        if (idList !== undefined) {

            $.each(idList.data, function (idx, idString) {
                var id = parseInt(idString);
                OrdersService.getOrderById(id, function (responseOrderInfo) {

                    var orderInfo = responseOrderInfo;

                    EmployeeService.getEmployeeById(orderInfo.employeeId, function (employee) {

                        OrdersService.getOrderItemsById(orderInfo.orderId, function (responseOrderItems) {

                            var orderItems = [];

                            $.each(responseOrderItems, function (idx, item) {
                                MenuService.getMenuItemById(item.itemId, function (menuItem) {
                                    orderItems.push({itemName: menuItem.title, quantity: item.quantity});

                                    if (idx === responseOrderItems.length - 1)
                                    {
                                        allOrders.push({
                                            orderDate: orderInfo.orderDate,
                                            orderTableNum: orderInfo.tableNum,
                                            orderEmployeeName: employee.firstName + ' ' + employee.lastName,
                                            orderTableTotal: orderInfo.totalSum,
                                            orderDiscount: orderInfo.discount,
                                            orderItems: orderItems
                                        });
                                        displayAllOrders();
                                        $(document).bind('ajaxStop');
                                    }
                                });
                            });
                        });
                    });
                });
            });
        } else {
            $('.date-wrapper date-info').text(" " + inputDateString);
            $('.date-wrapper .input-error').fadeIn();
        }
    });
}

function displayAllOrders() {
    $.each(allOrders, function (idx, order) {

        var $tableDate = $('<td/>').text(order.orderDate);
        var $tableDiscount = $('<td/>').text(order.orderDiscount);
        var $tableEmployeeName = $('<td/>').text(order.orderEmployeeName);
        var $tableNum = $('<td/>').text(order.orderTableNum);
        var $tableTotal = $('<td/>').text(order.orderTableTotal);

        var $selectItems = $('<select/>').addClass('select-order-item-' + idx);
        $selectItems.append($('<option/>').attr('value', '-1').text('Items'));
        $.each(order.orderItems, function (idx, item) {
            $selectItems.append($('<option/>').attr('value', '0').text(item.itemName + " | " + item.quantity));
        });
        var $tableItems = $('<td/>').append($selectItems);

        var $table = $('<tr/>').append($tableDate)
                .append($tableEmployeeName)
                .append($tableNum)
                .append($tableItems)
                .append($tableDiscount)
                .append($tableTotal);
        $('.all-orders-table').append($table);

        $('.select-order-item-' + idx).selecter();
    });

}

function disableItemSelecter() {
    $('#edit-item-button').attr('disabled', 'disabled');
    $('#delete-item-button').attr('disabled', 'disabled');
    $('#item-report-button').attr('disabled', 'disabled');
    $('#item-report-button').addClass('disabled-icon');
}

function initMenuItemSelect() {
    $('.select-item-wrapper').find('select').remove();
    $('.select-item-wrapper').find('div').remove();
    $('.select-item-wrapper').find('span').remove();
    $('.select-item-wrapper').append($('<select>').addClass('select-item'));
    $('.select-item').append($('<option>').attr('value', '-1').text('Choose Item'));
}

function initMenuItemsOptions(categoryId) {
    menuList = [];
    MenuService.getMenuCategoryById(categoryId,
            function (menuCategoryList) {
                $.each(menuCategoryList, function (idx, menuObject) {
                    if (!(menuObject.isCategory)) {
                        MenuService.getMenuItemById(menuObject.itemId, function (menuItem) {
                            menuList.push({title: menuItem.title, price: menuItem.price, itemId: menuItem.itemId});
                        });
                    }
                    ;
                });
            });
}

function initMonthSelecter() {
    $('.select-month').selecter();

    $('.select-month').on('change', function () {
        initWorkingHours(parseInt(this.value));
    });
}

function initWorkingHours(chosenMonth) {
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

function getMonthFromClockIn(clockIn) {
    var month = new Date(clockIn).getMonth();
    return ++month;
}

function newVipClick() {
    $('.edit-vip-wrapper .vip-title').text('Add a new VIP');
    $('#vip-save-button').unbind().click(submitNewVip);

    $('.handle-vip').find('input.first-name').val('');
    $('.handle-vip').find('input.last-name').val('');
    $('.handle-vip').find('input.email').val('');
    $('.handle-vip').find('input.birthday').val('');

    hideContentVip();
    showVipHandle();
}

function newPermissionClick() {
    $('.permission-input .permission-title').text('Add a new permission');
    $('#permission-save-button').unbind().click(submitNewPermission);
    $('.permission-input input[type="checkbox"]').prop('checked', false);
    $('.permission-input .input-permission-title').val("");

    $('#permission-save-button').text('Add it!');
    hideContentPermissions();
    showPermissionInputs();
}

function submitNewPermission() {
    var title = $('.permission-input .input-permission-title').val();
    var permissions = [];
    $.map($('.permission-input input[type="checkbox"]:checked'), function (elem, idx) {
        permissions.push($(elem).val());
    });
    var permissionObject = {authorizedActions: permissions, title: title};
    PermissionService.addNewPermission(permissionObject, function (response) {
        if (response === undefined) {
            displaySuccessAndRefresh('Great!', 'New Permission ' + permissionObject.title + ' was added!');
        } else {

        }
    });
}

function isValidBirthDay(birthday) {
    return moment(birthday, 'YYYY-MM-DD', true).isValid();
}

function isValidEmail(email) {
    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return filter.test(email);
}

function submitNewVip() {
    var vipObject = vipFromForm();

    if (!isValidString(vipObject.firstName) || !isValidString(vipObject.lastName) ||
            !isValidBirthDay(vipObject.birthday) || !isValidEmail(vipObject.email)) {
        $('.handle-vip .input-error').fadeIn();
        return;
    }

    VipService.addNewVip(vipObject, function (response) {
        displaySuccessAndRefresh('Great!', 'New vip ' + vipObject.firstName + ' ' + vipObject.lastName + ' was added!');
    });
}
;

function editVipClick() {
    $('.edit-vip-wrapper .vip-title').text('Edit VIP');
    $('#vip-save-button').unbind().click(submitEditedVip);
    var vipId = parseInt($('.select-vip option:selected').attr('vip-id'));

    VipService.getVipById(vipId, function (vipObject) {
        $('.handle-vip').find('input.first-name').val(vipObject.firstName);
        $('.handle-vip').find('input.last-name').val(vipObject.lastName);
        $('.handle-vip').find('input.email').val(vipObject.email);
        $('.handle-vip').find('input.birthday').val(vipObject.birthday);
    });

    hideContentVip();
    showVipHandle();
}

function editPermissionClick() {
    var title = $('.select-permission option:selected').text();
    $('.permission-input .permission-title').text('Edit Permission ' + title);
    $('#permission-save-button').unbind().click(submitEditedPermission);
    $('.permission-input .input-permission-title').val(title);
    $('#permission-save-button').text('Save');

    var permissionId = parseInt($('.select-permission option:selected').attr('permission-id'));

    PermissionService.getPermissionById(permissionId, function (permissionObject) {
        var $permissionCheckboxes = $('.permission-input input[type="checkbox"]').prop('checked', false);
        $.each(permissionObject.authorizedActions, function (idx, action) {
            $permissionCheckboxes.filter(function (idx, check) {
                return $(check).attr('value') === action;
            }).prop('checked', true);
        });
    });

    hideContentPermissions();
    showPermissionInputs();
}

function deletePermissionClick() {
    var permissionId = parseInt($('.select-permission option:selected').attr('permission-id'));
    var permissonTitle = $('.select-permission option:selected').text();

    PermissionService.deletePermissionById(permissionId, function (response) {
        if (response === undefined) {
            displaySuccessAndRefresh('Alright', 'Permission ' + permissonTitle + ' was deleted!');
        } else {

        }
    });
}

function submitEditedVip() {
    var vipId = parseInt($('.select-vip option:selected').attr('vip-id'));
    var vipObject = $.extend(vipFromForm(), {id: vipId});

    if (!isValidString(vipObject.firstName) || !isValidString(vipObject.lastName) ||
            !isValidBirthDay(vipObject.birthday) || !isValidEmail(vipObject.email)) {
        $('.handle-vip .input-error').fadeIn();
        return;
    }

    VipService.updateVip(vipId, vipObject, function (response) {
        if (response === undefined) {
            displaySuccessAndRefresh('Great!', 'Vip ' + vipObject.firstName + ' ' + vipObject.lastName + ' was updated!');
        }
    });
}
;

function submitEditedPermission() {
    var permissionId = parseInt($('.select-permission option:selected').attr('permission-id'));
    var title = $('.permission-input .input-permission-title').val();
    var permissions = [];
    $.map($('.permission-input input[type="checkbox"]:checked'), function (elem, idx) {
        permissions.push($(elem).val());
    }).join('');
    var permissionObject = {authorizedActions: permissions, title: title, permissionId: permissionId};
    PermissionService.updatePermission(permissionId, permissionObject, function (response) {
        if (response === undefined) {
            displaySuccessAndRefresh('Great!', 'Permission ' + permissionObject.title + ' was updated!');
        } else {

        }
    });
}
;

function deleteVipClick() {
    var vipId = parseInt($('.select-vip option:selected').attr('vip-id'));
    var vipName = $('.select-vip option:selected').text();

    VipService.deleteVip(vipId, function (response) {
        if (response === undefined) {
            displaySuccessAndRefresh('Alright!', 'We have deleted ' + vipName + ' for you.');
        }
    });
}

function closeVip() {
    $('.handle-vip').slideUp();
    $('.content-vip').slideDown();
}

function closePermission() {
    $('.permission-input').slideUp();
    $('.permission-content').slideDown();
}

function hideContentVip() {
    $('.content-vip').slideUp();
}

function hideContentPermissions() {
    $('.permission-content').slideUp();
}

function showPermissionInputs() {
    $('.permission-input').slideDown();
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
        nextCategoryId: 0
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
    var employeeId = parseInt($('.select-employee option:selected').attr('employee-id'));
    var employeeName = $('.select-employee option:selected').text();

    EmployeeService.deleteEmployee(employeeId, function (response) {
        if (response === undefined) {
            displaySuccessAndRefresh('Alright!', 'We have deleted ' + employeeName + ' for you.');
        }
    });
}

function editEmployeeClick(event) {
    var employeeId = $('.select-employee option:selected').attr('employee-id');
    var $modal = $('.update-employee-modal');
    var $permissionSelect = $modal.find('.edit-employee-permission-select').empty();

    $.each(allPermissions, function (idx, permission) {
        var option = $('<option>').attr('value', permission.permissionId).attr('permission-id', permission.permissionId).text(permission.title);
        $permissionSelect.append(option);
    });

    EmployeeService.getEmployeeById(employeeId, function (employee) {
        $modal.find('input.first-name').val(employee.firstName);
        $modal.find('input.last-name').val(employee.lastName);
        $modal.find('input.email').val(employee.email);
        $modal.find('input.password').val(employee.password);
        $modal.find('input.age').val(employee.age);
        $modal.find('input.position').val(employee.position);
        $modal.find('input.city').val(employee.city);
        $modal.find('input.address').val(employee.address);
        $modal.find('.edit-employee-name').text(employee.firstName + ' ' + employee.lastName);

        var $genderSelect = $modal.find('.edit-employee-gender-select');
        if (employee.gender === 'MALE') {
            $genderSelect.find('option:first-child').attr('selected', 'selected');
        } else {
            $genderSelect.find('option:last-child').attr('selected', 'selected');
        }
        $genderSelect.selecter();

        $modal.find('.edit-employee-permission-select').val(employee.permissionId);
        $permissionSelect.selecter();
    });

    $modal.find('button.cancel').click(function () {
        $modal.find('select').selecter('destroy');
        $modal.hide();
    });

    $modal.fadeIn('fast');
}
;

function submitEditedEmployee() {
    var $modal = $('.update-employee-modal');
    var employeeId = $('.select-employee option:selected').attr('employee-id');

    var editedEmployee = $.extend(employeeFromModal($modal), {id: employeeId}); // Quick fix, edit not working well on server it seems.

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

    $modal.find('button.cancel').click(function () {
        $modal.hide();
    });

    $modal.show();
}
;

function submitNewEmployee() {
    var $modal = $('.new-employee-modal');
    var newEmployee = employeeFromModal($modal);

    EmployeeService.addNewEmployee(newEmployee, function (response) {
        $modal.hide();
        displaySuccessAndRefresh('Great!', 'Employee ' + newEmployee.firstName + ' ' + newEmployee.lastName + ' added!');
    });
}

// Admin: Utils for this section
function vipFromForm() {
    var firstName = $('.handle-vip').find('input.first-name').val();
    var lastName = $('.handle-vip').find('input.last-name').val();
    var email = $('.handle-vip').find('input.email').val();
    var birthday = $('.handle-vip').find('input.birthday').val();

    return {
        firstName: firstName,
        lastName: lastName,
        birthday: birthday,
        email: email
    };
}
;

function employeeFromModal($modal) {
    var firstName = $modal.find('input.first-name').val();
    var lastName = $modal.find('input.last-name').val();
    var email = $modal.find('input.email').val();
    var password = $modal.find('input.password').val();
    var age = parseInt($modal.find('input.age').val());
    var position = $modal.find('input.position').val();
    var gender = $modal.find('.new-employee-gender-select option:selected').attr('value');
    var permissionId = parseInt($modal.find('.new-employee-permission-select option:selected').attr('permission-id'));
    var city = $modal.find('input.city').val();
    var address = $modal.find('input.address').val();

    var newEmployee = {
        firstName: firstName,
        lastName: lastName,
        age: age,
        email: email,
        password: password,
        gender: gender,
        position: position,
        permissionId: permissionId,
        city: city,
        address: address
    };

    return newEmployee;
}

function closeEverything() {
    $('.modal').hide();

    $('.add-new-item').slideUp();
    $('.add-new-category').slideUp();

    $('.edit-item').slideUp();
    $('.edit-category').slideUp();
    $('.overview-wrapper').slideUp();

    showEditMenuMain();
}

function displaySuccessAndRefresh(strongText, regularText) {
    $('.alert.data-submit-ok p').empty().append('<strong>' + strongText + '</strong> ' + regularText);
    $('.alert.data-submit-ok').show();
    $('body').scrollTo(0);

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

function editItemClick(event) {
    selectedCategory = {
        id: parseInt($('.select-category option:selected').attr('category-id'))
    };

    var selectedItem = {
        price: $('.select-item option:selected').attr('item-price'),
        title: $('.select-item option:selected').text()
    };

    $('.edit-selected-item').text(selectedItem.title);
    $('.edit-item-title').val(selectedItem.title);
    $('.edit-item-price').val(selectedItem.price);
    hideEditMenuMain();
    showEditItem();
}

function editCategoryClick(event) {
    selectedCategory = {
        id: $('.select-category option:selected').attr('category-id'),
        parentId: $('.select-category option:selected').attr('parent-id'),
        title: $('.select-category option:selected').text()
    };

    $('.edit-selected-category').text(selectedCategory.title);
    $('.edit-category-title').val(selectedCategory.title);
    hideEditMenuMain();
    showEditCategory();
}

function showEditCategory() {
    $('.edit-category').slideDown();
}

function showEditItem() {
    $('.edit-item').slideDown();
}

function submitEditCategory(event) {
    var editCategoryTitle = $('input.edit-category-title').val();

    if (!isValidString(editCategoryTitle)) {
        $('.edit-category .input-error').fadeIn();
        return;
    }

    var editedCategory = {
        title: editCategoryTitle,
        parentId: parseInt(selectedCategory.parentId),
        categoryId: parseInt(selectedCategory.id),
        type: 'menuCategory',
        isCategory: true
    };

    MenuService.updateCategory(editedCategory.categoryId, editedCategory, function (response) {
        closeEverything();
        displaySuccessAndRefresh('Awesome!', 'Category ' + editedCategory.title + ' Updated!');
    });
}

function submitEditItem(event) {
    var editItemTitle = $('input.edit-item-title').val();
    var editItemPrice = $('input.edit-item-price').val();
    var editItemId = parseInt($('.select-item option:selected').attr('item-id'));

    if (!isValidString(editItemTitle) || !Number.isInteger(parseInt(editItemPrice))) {
        $('.edit-item .input-error').fadeIn();
        return;
    }

    var editedItem = {
        title: editItemTitle,
        price: parseInt(editItemPrice),
        categoryId: selectedCategory.id,
        type: 'menuItem',
        isCategory: false,
        nextCategoryId: 0,
        itemId: editItemId
    };

    MenuService.updateItem(editItemId, editedItem, function (response) {
        closeEverything();
        displaySuccessAndRefresh('Sweet!', 'Item ' + editedItem.title + ' Updated!');
    });
}

function deleteItemClick(event) {
    var itemId = parseInt($('.select-item option:selected').attr('item-id'));
    var itemName = $('.select-item option:selected').text();

    MenuService.deleteMenuItem(itemId, function (response) {
        if (response === undefined) {
            displaySuccessAndRefresh('Alright!', 'We have deleted ' + itemName + ' for you.');
        }
    });
}

function deleteCategoryClick(event) {
    var categoryId = parseInt($('.select-category option:selected').attr('category-id'));
    var categoryName = $('.select-category option:selected').text();

    MenuService.getMenuCategoryById(categoryId, function (menuCategoryList) {
        if (menuCategoryList.length === 0) {
            MenuService.deleteCategoryById(categoryId, function (response) {
                if (response === undefined) {
                    displaySuccessAndRefresh('Alright!', 'We have deleted ' + categoryName + ' for you.');
                }
            });
        } else {
            $('.edit-menu-wrapper .delete-category-error').fadeIn();
            setTimeout(function () {
                $('.edit-menu-wrapper .delete-category-error').fadeOut();
            }, 3000);
        }
    });
}

function overviewClick(event) {
    var selectedItem = {
        price: $('.select-item option:selected').attr('item-price'),
        title: $('.select-item option:selected').text(),
        id: parseInt($('.select-item option:selected').attr('item-id'))
    };

    $.each(menuItemsOverview, function (idx, overview) {
        if (overview.itemId === selectedItem.id) {
            $('.overview-title').text(selectedItem.title);
            $('.tables-amount-overview').text(overview.numOfTables);
            $('.quantity-overview').text(overview.quantity);
            $('.price-overview').text(selectedItem.price + '$');
            $('.overall-price-overview').text(selectedItem.price * overview.quantity + '$');
        }
    });

    hideEditMenuMain();
    showItemReport();
}

function showItemReport() {
    $('.overview-wrapper').slideDown();
}
