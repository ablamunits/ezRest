/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global EmployeeService, MenuService, OrdersService, TablesService, alertMechanism */
var tableId;
var sumBill;
var itemsCount;
var tableOrders = [];

$(document).ready(function () {
    itemsCount = 0;
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

function initPopups() {
    var $elements = $('.my-popover');
    $elements.each(function () {
        var $element = $(this);
        $('#content').children('button:last-child').attr('item-id', $(this).attr('id'));

        $element.popover({
            html: true,
            placement: 'bottom',
            container: $('body'), // This is just so the btn-group doesn't get messed up... also makes sorting the z-index issue easier
            content: $('#content').html()
        });

        $element.on('shown.bs.popover', function () {
            var popover = $element.data('bs.popover');
            if (typeof popover !== "undefined") {
                var $tip = popover.tip();
                zindex = $tip.css('z-index');

                $tip.find('.close').bind('click', function () {
                    popover.hide();
                });

                $tip.find('.add').bind('click', function (event) {
                    onPopupAddClick(event);
                });


                $tip.mouseover(function () {
                    $tip.css('z-index', function () {
                        return zindex + 1;
                    });
                })
                        .mouseout(function () {
                            $tip.css('z-index', function () {
                                return zindex;
                            });
                        });
            }
        });
    });
}

function refreshSummaryPanel() {
    OrdersService.getTableOrder(tableId,
            function (orderTableList) {
                updatePanel(orderTableList);
            });

    TablesService.getTableById(tableId,
            function (table) {
                $('#descriptionTextSummary').val(table.description);
                $('#numOfGuestsSummary').val(table.numOfGuests);
                $('#serverIdSummary').val(table.serverId);
            });
}

function updatePanel(orderTableList) {
    $.each(orderTableList,
            function (index, order) {
                MenuService.getMenuItemById(order.itemId,
                        function (menuItem) {
                            appendMenuItemToOrder(menuItem, order.quantity, "Submitted");
                        });
            });
}

function getMenuItemById(itemId) {
    MenuService.getMenuItemById(itemId,
            function (menuItem) {
                return menuItem;
            });
}
function appendMenuItemToOrder(menuItem, quantity, status) {
    var $tableCellIndex;
    var $tableCellItemName;
    var $tableCellQuantity;
    var $tableCellPriceForOne;
    var sumOfItemPrice;
    var $tableCellSum;
    var $tableCellRemove;

    if (status === "Submitted") {
//        $tableCellRemove = $('<span/>').addClass('glyphicon glyphicon-remove').click(onRemoveItemClick);
        $tableCellIndex = $('<td/>').html(++itemsCount);
        $tableCellItemName = $('<td/>').html(menuItem.title);
        $tableCellQuantity = $('<td/>').html(quantity);
        $tableCellPriceForOne = $('<td/>').html(menuItem.price);
        sumOfItemPrice = quantity * menuItem.price;
        $tableCellSum = $('<td/>').html(sumOfItemPrice);
        sumBill += sumOfItemPrice;
        $('#sumBillSummary').text("Sum: " + sumBill).removeClass('toRed');

    } else if (status === "Not Submitted") {
//        $tableCellRemove = $('<span/>').addClass('glyphicon glyphicon-remove').click(onRemoveItemClick);
        $tableCellIndex = $('<td/>').html(toStrong(++itemsCount));
        $tableCellItemName = $('<td/>').html(toStrong(menuItem.title));
        $tableCellQuantity = $('<td/>').html(toStrong(quantity));
        $tableCellPriceForOne = $('<td/>').html(toStrong(menuItem.price));
        sumOfItemPrice = quantity * menuItem.price;
        $tableCellSum = $('<td/>').html(toStrong(sumOfItemPrice));
        sumBill += sumOfItemPrice;
        $('#sumBillSummary').html("Sum: " + sumBill).addClass('toRed');
    }

    $('.all-orders-table').append($('<tr>').append($tableCellRemove)
            .append($tableCellIndex)
            .append($tableCellItemName)
            .append($tableCellQuantity)
            .append($tableCellPriceForOne)
            .append($tableCellSum));
}

function toStrong(html) {
    return "<strong>" + html + "</strong>";
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
    $('.popover').remove();
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
                            .attr('data-title', 'Amount:')
                            .attr('id', menuEntry.itemId)
                            //in initPopups() it will take the 'id' and put it in 'content' of the popover
                            .addClass('my-popover')
                            .css("background-color", "#87D37C")  //WTF not working in css
                            .addClass('btn btn-item');
                    $('.menu-item-list').append($node);
                }
            });
    initPopups();
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

function onPopupAddClick(event) {
    event.preventDefault();
    var $target = $(event.target);
    var itemId = $target.attr('item-id');
    var quantity = $target.siblings("input").val();
    tableOrders.push({'itemId': itemId, 'quantity': quantity});
    $('#submitSummaryButton').removeClass('btn-warning').addClass('btn-danger');

    MenuService.getMenuItemById(itemId,
            function (menuItem) {
                appendMenuItemToOrder(menuItem, quantity, "Not Submitted");
                alertMechanism.Success(menuItem.title + " was added to the Summary");
            });
}

function onSummarySubmitButton(event) {
    if (tableOrders.length > 0) {
        OrdersService.makeOrder(tableId, tableOrders,
                function (response) {
                    if (response === undefined) {
                        $('.all-orders-table').empty();
                        itemsCount = 0;
                        sumBill = 0;
                        refreshSummaryPanel();
                        tableOrders = [];
                        $('#submitSummaryButton').removeClass('btn-danger').addClass('btn-warning');
                        alertMechanism.Success("Order has been submitted and placed");
                    } else {
                        alertMechanism.Error("An error was occured, please try again");
                    }
                });
    }
}