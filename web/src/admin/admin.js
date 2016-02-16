'use strict';

var authObject;
var employeeObject;
var authorizedActions;
var permissionTitle;
var allCategories = [];

$(document).ready(function() {
  MenuService.getAllCategories(function(responseObject) {
    allCategories = responseObject;
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
  })

  $('select').selecter();

  $('.user-name').text(employeeObject.firstName + ' ' + employeeObject.lastName);
  $('.user-age').text(employeeObject.age);
  $('.user-email').text(employeeObject.email);
  $('.user-position').text(employeeObject.position);
  $('.user-permission-title').text(permissionTitle);
  console.log(authorizedActions);
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
    displaySuccess('Sweet!', 'Item ' + newItem.title + ' added!');
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
    displaySuccess('Awesome!', 'Category ' + newCategory.title + ' added!');
  });
}

function showEditMenuMain() {
  $('.edit-menu-main').slideDown();
}

function hideEditMenuMain() {
  $('.edit-menu-main').slideUp();
}

// Admin: Employees edit
function newEmployeeClick(event) {};
function editEmployeeClick(event) {};
function newVipClick(event) {};
function editVipClick(event) {};

// Admin: Utils for this section
function closeEverything() {
  $('.add-new-item').slideUp();
  $('.add-new-category').slideUp();

  showEditMenuMain();
}

function displaySuccess(strongText, regularText) {
  $('.alert.data-submit-ok p').empty().append('<strong>' + strongText + '</strong> ' + regularText).show();
}
