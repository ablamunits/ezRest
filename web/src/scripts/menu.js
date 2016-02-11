/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global EmployeeService, MenuService, OrdersService, TablesService */
var tableId;
var sumBill;

$(document).ready(function () {
    sumBill = 0;
    tableId = getUrlParameter('tableId');
    $('#tableNumberPanel').html(tableId);

    var employeeId = getUrlParameter('employeeId');
    EmployeeService.getEmployeeById(employeeId,
            function (employee) {
                $("#employeeName").attr('title', employee.firstName);
                $("#tableId").attr('title', tableId);
                $('[data-toggle="tooltip"]').tooltip();
            });

    var $aNode = $('<a/>').html('Main Menu')
            .addClass("breadCrumbButton")
            .attr('next-category-id', 1);
    $aNode.click(menuCategoryClick);
    $("#breadCrumbCategroies").append($('<li/>').append($aNode));

    refreshMainMenu();
    refreshSummaryPanel();
});

function refreshSummaryPanel() {
    OrdersService.getTableOrder(tableId,
            function (orderTableList) {
                $.each(orderTableList,
                        function (index, order) {
                            appendMenuItemToOrder(order, index);
                        });
            });

    TablesService.getTableById(tableId,
            function (table) {
                $('#descriptionTextSummary').val(table.description);
                $('#numOfGuestsInputSummary').val(table.numOfGuests);
                $('#serverIdSummary').val(table.serverId);
            });
}

function appendMenuItemToOrder(order, index) {
    MenuService.getMenuItemById(order.itemId,
            function (menuItem) {
                var quantity = order.quantity;
                var $tableCellIndex = $('<td/>').html(index + 1);
                var $tableCellItemName = $('<td/>').html(menuItem.title);
                var $tableCellQuantity = $('<td/>').html(quantity);
                var $tableCellPriceForOne = $('<td/>').html(menuItem.price);
                var sumOfItemPrice = quantity * menuItem.price;
                var $tableCellSum = $('<td/>').html(sumOfItemPrice);
                sumBill += sumOfItemPrice;
                $('#sumBillSummary').text("Sum: " + sumBill);
                
                $('.all-orders-table').append($('<tr>').append($tableCellIndex)
                        .append($tableCellItemName)
                        .append($tableCellQuantity)
                        .append($tableCellPriceForOne)
                        .append($tableCellSum));
            });
            
    
}

function refreshMainMenu() {
    $("#menuCategoryTitle").html("<strong>Category: </strong>Main Menu");
    MenuService.getMenuCategoryById(1,
            function (menuCategoryList) {
                menuCategoryList.shift(); //Because of the DUMMY_ROOT
                updateMenuLists(menuCategoryList);
            });
}

function updateMenuLists(menuCategoryList) {
    $('.menu-category-list').empty();
    $('.menu-item-list').empty();
    $.each(menuCategoryList,
            function (index, menuEntry) {

                if (menuEntry.isCategory) {
                    var $node = $('<li/>').html(menuEntry.title)
                            .attr('category-id', menuEntry.categoryId)
                            .attr('next-category-id', menuEntry.nextCategoryId)
                            .css("background-color", "#006442")  //WTF not working in css
                            .addClass('btn btn-category');
                    $node.click(menuCategoryClick);
                    $('.menu-category-list').append($node);

                } else if (!(menuEntry.isCategory)) {
                    var $node = $('<li/>').html(menuEntry.title)
                            .attr('category-id', menuEntry.categoryId)
                            .css("background-color", "#87D37C")  //WTF not working in css
                            .addClass('btn btn-item');
                    $node.click(menuItemClick);
                    $('.menu-item-list').append($node);
                }
            });
}

function menuCategoryClick(event) {
    var $target = $(event.target);
    var categoryTitle = $target.html();
    var nextCategoryId = $target.attr('next-category-id');
    $("#menuCategoryTitle").html("<strong>Category: </strong>" + categoryTitle);

    updateBreadCrumb(nextCategoryId, categoryTitle);

    if (nextCategoryId === "1") { //Main Menu
        refreshMainMenu();   //because of the DUMMY_ROOT
    } else {
        MenuService.getMenuCategoryById(nextCategoryId,
                function (menuCategoryList) {
                    updateMenuLists(menuCategoryList);
                });
    }
}

function updateBreadCrumb(nextCategoryId, categoryTitle) {
    var goBack = false;

    var breadCrumbList = $("#breadCrumbCategroies").children();
    $.each(breadCrumbList, function (index, itemList) {
        var categoryId = $(itemList).find('a').attr('next-category-id');
        if (categoryId === nextCategoryId) {
            goBack = true;
            $(itemList).nextAll().remove();
        }
    });

    if (goBack === false) {
        var $aNode = $('<a/>').html(categoryTitle)
                .addClass("breadCrumbButton")
                .attr('next-category-id', nextCategoryId);
        $aNode.click(menuCategoryClick);
        $("#breadCrumbCategroies").append($('<li/>').append($aNode));
    }
}

function menuItemClick(event) {
    var $target = $(event.target);

    addToSummaryPanel($target);
//    OrdersService.makeOrder(tableId,
}

function addToSummaryPanel() {

}