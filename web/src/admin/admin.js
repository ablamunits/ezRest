'use strict';

var authObject;
var employeeObject;
var authorizedActions;
var permissionTitle;
var allCategories = [];
var allEmployees = [];
var allPermissions = [];

$(document).ready(function() {
  MenuService.getAllCategories(function(responseObject) {
    allCategories = responseObject;
  });

	EmployeeService.getAllEmployees(function(responseObject) {
		allEmployees = responseObject;
	});

	PermissionService.getAllPermissions(function(responseObject) {
		allPermissions = responseObject;
	});

  AuthService.authState(function(responseObject) {
    authObject = responseObject;

    // EmployeeService.getEmployeeById(authObject.employeeId, function(responseObject) {
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
  });

  // Only display info after all ajax requests ended.
  $(this).ajaxStop(function() {
    displayInfo();
  });
});

function displayInfo() {
  $.each(allCategories, function(idx, category) {
    var option = $('<option>').attr('value', idx).attr('category-id', category.categoryId).text(category.title === 'DUMMY_ROOT' ? 'Root Category' : category.title);
    $('.select-category').append(option);
  });
	$('.select-category').selecter();

	$.each(allEmployees, function(idx, employee) {
		var option = $('<option>').attr('value', idx).attr('employee-id', employee.id).text(employee.firstName + ' ' + employee.lastName);
		$('.select-employee').append(option);
	});
	$('.select-employee').selecter();

  $('.user-name').text(employeeObject.firstName + ' ' + employeeObject.lastName);
  $('.user-age').text(employeeObject.age);
  $('.user-email').text(employeeObject.email);
  $('.user-position').text(employeeObject.position);
  $('.user-permission-title').text(permissionTitle);
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
};

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

  MenuService.addNewItem(newItem, function(response) {
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
};

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

  MenuService.addNewCategory(newCategory, function(response) {
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
		EmployeeService.deleteEmployee(employeeId, (response) => {
			displaySuccessAndRefresh('Alright!', 'We have deleted ' + employeeName + ' for you.');
		});
}

function editEmployeeClick(event) {};

function newEmployeeClick(event) {
	var $modal = $('.new-employee-modal');
	$.each(allPermissions, function(idx, permission) {
		var option = $('<option>').attr('value', idx).attr('permission-id', permission.permissionId).text(permission.title);
		$modal.find('.new-employee-permission-select').append(option);
	});
	$('select').selecter();

	$modal.find('button.cancel').click(() => {
		$modal.hide();
	});

	$modal.find('button.submit').click(() => {
		submitNewEmployee();
	});

	$modal.show();
};

function submitNewEmployee() {
	var $modal = $('.new-employee-modal');

	var firstName = $modal.find('input.first-name').val();
	var lastName = $modal.find('input.last-name').val();
	var email = $modal.find('input.email').val();
	var password = $modal.find('input.password').val();
	var age = $modal.find('input.age').val();
	var position = $modal.find('input.position').val();
	var gender = $modal.find('.new-employee-gender-select option:selected').attr('value');
	var permissionId = $modal.find('.new-employee-permission-select option:selected').attr('permission-id');

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

	EmployeeService.addNewEmployee(newEmployee, (response) => {
		$modal.hide();
		displaySuccessAndRefresh('Great!', 'Employee ' + newEmployee.firstName + ' ' + newEmployee.lastName + ' added!');
	});
}

function newVipClick(event) {};

function editVipClick(event) {};

// Admin: Utils for this section
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

	setTimeout(() => {
		location.reload();
	}, 2000)
}
