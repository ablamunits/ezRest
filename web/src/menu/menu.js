/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global EmployeeService, MenuService, OrdersService, TablesService, alertMechanism, PermissionService */
var tableId;
var sumBill;
var itemsCount;
var employeeObject;
var tableInfoEdit;
var totalDiscount;
var employeePermissions;
var latestTableOrders = {
    Array: [],
    Remove: function ($tableLine, itemId) {
        removeItem(latestTableOrders, $tableLine, itemId);
    },
    Add: function (itemId, quantity) {
        var isDuplicate = false;

        if (latestTableOrders.Array[0] !== undefined)
        {
            $.each(latestTableOrders.Array, function (index, order) {
                if (itemId === order.itemId)
                {
                    order.quantity += quantity;

                    $('.latestOrders').each(function () {

                        var $lineObj = $(this);
                        var summaryItemid = parseInt($lineObj.find('span').attr('item-id'));
                        if (summaryItemid === itemId) {
                            isDuplicate = true;
                            var price = parseInt($lineObj.find("td[name^='price']").text());
                            var $quantityObj = $lineObj.find("td[name^='quantity']");
                            var $sumObj = $lineObj.find("td[name^='sum']");
                            var title = $lineObj.find("td[name^='title']").text();

                            $quantityObj.html(toStrong(order.quantity));
                            var sum = price * order.quantity;
                            $sumObj.html(toStrong(sum));
                            sumBill += (quantity * price);
                            updateSum();


                            if (quantity > 0) {
                                alertMechanism.Success(toStrong(quantity) + " " + toStrong(title) + " added to the Summary");
                            }
                            return false;
                        }
                    });

                }
            });

            if (!isDuplicate) {
                //nothing the same
                MenuService.getMenuItemById(itemId,
                        function (menuItem) {
                            latestTableOrders.Array.push({'itemId': menuItem.itemId, 'quantity': quantity});
                            appendMenuItemToOrder(menuItem, quantity, "Not Submitted");
                            if (quantity > 0) {
                                alertMechanism.Success(toStrong(quantity) + " " + menuItem.title + " added to the Summary");
                            }
                        });
                return false;
            }
        } else {
            //empty array
            MenuService.getMenuItemById(itemId,
                    function (menuItem) {
                        latestTableOrders.Array.push({'itemId': menuItem.itemId, 'quantity': quantity});
                        appendMenuItemToOrder(menuItem, quantity, "Not Submitted");
                        if (quantity > 0) {
                            alertMechanism.Success(toStrong(quantity) + " " + menuItem.title + " added to the Summary");
                        }
                    });
        }
    }
};
var overAllTableOrders = {
    Array: [],
    Remove: function ($tableLine, itemId) {
        removeItem(overAllTableOrders, $tableLine, itemId);
    },
    Add: function (itemId, quantity) {
        if (overAllTableOrders.Array[0] !== undefined)
        {
            $.each(overAllTableOrders.Array, function (index, order) {
                if (itemId === order.itemId)
                {
                    order.quantity += quantity;
                    return false;
                } else {
                    overAllTableOrders.Array.push({'itemId': itemId, 'quantity': quantity});
                    return false;
                }
            });
        } else {
            overAllTableOrders.Array.push({'itemId': itemId, 'quantity': quantity});
            return false;
        }
    },
    GetItems: function (orderId) {
        var orderItems = [];
        $.each(overAllTableOrders.Array, function (index, order) {
            orderItems.push({'orderId': orderId, 'itemId': order.itemId, 'quantity': order.quantity});
        });

        return orderItems;
    }
};

function removeItem(array, $tableLine, itemId) {
    $.each(array.Array, function (index, order) {
        if (itemId === order.itemId) {
            var price = parseInt($tableLine.find("td[name^='price']").text());

            if (order.quantity > 1)
            {
                order.quantity--;
            } else {
                array.Array.splice(index, 1);
                $tableLine.remove();
            }
            latestTableOrders.Add(itemId, -1);
//            sumBill -= price;
            var title = $tableLine.find("td[name^='title']").text();
            updateSum();
            alertMechanism.Info("<strong>Removed</strong> 1 " + title + ", Press <strong>Submit</strong> to update server or <strong>Cancel</strong> for retrieving data");
            readySubmit();
            return false;
        }
    });
}

$(document).ready(function () {
    $('[data-toggle="popover"]').popover();

    totalDiscount = 0;
    tableInfoEdit = false;
    itemsCount = 0;
    sumBill = 0;
    tableId = parseInt(getUrlParameter('tableId'));
    $('#tableNumberPanel').html(tableId);
    $('#table-headline').text('Table ' + tableId);

    $("#discountWrapper").hide();


    var employeeId = getUrlParameter('employeeId');
    EmployeeService.getActiveEmployeeById(employeeId,
            function (employee) {
                employeeObject = employee;
                $("#employeeName").attr('title', employee.firstName);
                $("#tableId").attr('title', tableId);
                $('[data-toggle="tooltip"]').tooltip();

                PermissionService.getPermissionById(employeeObject.permissionId, function (permissionResponse) {
                    employeePermissions = PermissionService.authorizedActions;
                    $.each(permissionResponse.authorizedActions, function (index, action) {
                        employeePermissions[action] = true;
                    });

                    initPermissionDiscount();
                });
            });

    var $aNode = $('<a/>').html('Main Menu')
            .addClass("breadCrumbButton")
            .attr('next-category-id', 1);
    $aNode.click(menuCategoryClick);
    $("#breadCrumbCategroies").append($('<li/>').append($aNode));

    $('#descriptionTextSummary').change(function () {
        tableInfoEdit = true;
        readySubmit();
    });
    $('#numOfGuestsSummary').change(function () {
        tableInfoEdit = true;
        readySubmit();
    });
    $('#serverIdSummary').change(function () {
        tableInfoEdit = true;
        readySubmit();
    });

    refreshMainMenu();
    refreshSummaryPanel();
});

function initPermissionDiscount() {
    $("#discountSummaryButton").click(function () {

        if (employeePermissions['ADD_DISCOUNT'] === true){
            $("#discountWrapper").slideToggle("slow");
        }
        else{
            alertMechanism.Error("You don't have permission to add discount");
        }
    });
}


function refreshSummaryPanel() {
    overAllTableOrders.Array = [];
    OrdersService.getTableOrder(tableId,
            function (orderTableList) {
                updatePanel(orderTableList);
            });

    TablesService.getTableById(tableId,
            function (table) {
                $('#descriptionTextSummary').val(table.description);
                $('#numOfGuestsSummary').val(table.numOfGuests);
                totalDiscount = table.discount;
                $('#totalDiscountLabel').val(totalDiscount);
                sumBill -= totalDiscount;
                updateSum();
                $('#discountAddInput').val('0');
                EmployeeService.getEmployeeById(table.serverId, function (employee) {
                    $('#serverIdSummary').val(employee.firstName + " " + employee.lastName);
                });
            });
}

function updatePanel(orderTableList) {
    $.each(orderTableList,
            function (index, order) {
                MenuService.getMenuItemById(order.itemId,
                        function (menuItem) {
                            overAllTableOrders.Add(parseInt(order.itemId), parseInt(order.quantity));
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
    var $lineObj;

    if (status === "Submitted")
    {
        $tableCellRemove = getCellRemove(menuItem.itemId, quantity, "Submitted");
        $tableCellIndex = $('<td/>').html(++itemsCount);
        $tableCellItemName = $('<td/>').html(menuItem.title).attr('name', 'title');
        $tableCellQuantity = $('<td/>').html(quantity).attr('name', 'quantity');
        $tableCellPriceForOne = $('<td/>').html(menuItem.price).attr('name', 'price');
        sumOfItemPrice = quantity * menuItem.price;
        $tableCellSum = $('<td/>').html(sumOfItemPrice).attr('name', 'sum');
        sumBill += sumOfItemPrice;
        updateSum();


        $lineObj = $('<tr>').append($tableCellRemove)
                .append($tableCellIndex)
                .append($tableCellItemName)
                .append($tableCellQuantity)
                .append($tableCellPriceForOne)
                .append($tableCellSum);

    } else if (status === "Not Submitted") {
        $tableCellRemove = getCellRemove(menuItem.itemId, quantity, "Not Submitted");
        $tableCellIndex = $('<td/>').html(toStrong(++itemsCount));
        $tableCellItemName = $('<td/>').html(toStrong(menuItem.title)).attr('name', 'title');
        $tableCellQuantity = $('<td/>').html(toStrong(quantity)).attr('name', 'quantity');
        $tableCellPriceForOne = $('<td/>').html(toStrong(menuItem.price)).attr('name', 'price');
        sumOfItemPrice = quantity * menuItem.price;
        $tableCellSum = $('<td/>').html(toStrong(sumOfItemPrice)).attr('name', 'sum');
        sumBill += sumOfItemPrice;
        updateSum();
        $('#sumBillSummary').addClass('toRed');

        $lineObj = $('<tr>').append($tableCellRemove)
                .append($tableCellIndex)
                .append($tableCellItemName)
                .append($tableCellQuantity)
                .append($tableCellPriceForOne)
                .append($tableCellSum);
        $lineObj.addClass('latestOrders');
    }

    $('.all-orders-table').append($lineObj);
}

function initMenuPopups() {
    var $elements = $('.menu-item');
    $elements.each(function () {
        var $element = $(this);
        $('#menuContent').children('button:last-child').attr('item-id', $(this).attr('id'));

        $element.popover({
            html: true,
            placement: 'bottom',
            container: $('body'), // This is just so the btn-group doesn't get messed up... also makes sorting the z-index issue easier
            content: $('#menuContent').html()
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
                    popover.hide();
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

function getCellRemove(itemId, quantity, isSubmitted) {
    var onClick;
    if (isSubmitted === "Submitted") {
        onClick = onRemoveOverAllListClick;
    } else {
        onClick = onRemoveLatestClick;
    }
    return $('<td><span/>').addClass('fa fa-remove removeItem')
            .click(onClick)
            .attr('item-id', itemId);
}

function onRemoveOverAllListClick(event) {
    var $target = $(event.target);
    var $tableLine = $target.parent();
    var itemId = parseInt($target.attr('item-id'));
    var hasPermission = false;

    if (employeePermissions["CANCEL_ORDER"] === true){
        overAllTableOrders.Remove($tableLine, itemId);
    }
    else{
        alertMechanism.Error("You don't have permission for this action");
    }
}

function onRemoveLatestClick(event) {
    var $target = $(event.target);
    var $tableLine = $target.parent();
    var itemId = parseInt($target.attr('item-id'));

    latestTableOrders.Remove($tableLine, itemId);
}

function toStrong(html) {
    return "<strong>" + html + "</strong>";
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
                            .addClass('btn btn-category');
                    $node.click(menuCategoryClick);
                    $('.menu-category-list').append($node);

                } else if (!(menuEntry.isCategory)) {
                    var $node = $('<li/>').html(menuEntry.title)
                            .attr('data-title', 'Amount:')
                            .attr('id', menuEntry.itemId)
                            //in initMenuPopups() it will take the 'id' and put it in 'content' of the popover
                            .addClass('menu-item')
                            .addClass('btn btn-item');
                    $('.menu-item-list').append($node);
                }
            });
    initMenuPopups();
}

function refreshMainMenu() {
    // $("#menuCategoryTitle").html("<strong>Category: </strong>Main Menu");
    MenuService.getMenuCategoryById(1,
            function (menuCategoryList) {
                menuCategoryList.shift(); //Because of the DUMMY_ROOT
                updateMenuLists(menuCategoryList);
            });
}

function menuCategoryClick(event) {
    var $target = $(event.target);
    var categoryTitle = $target.html();
    var nextCategoryId = $target.attr('next-category-id');
    // $("#menuCategoryTitle").html("<strong>Category: </strong>" + categoryTitle);

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
    var itemId = parseInt($target.attr('item-id'));
    var quantity = parseInt($target.siblings("input").val());
    latestTableOrders.Add(itemId, quantity);
    readySubmit();
}

function onSummarySubmitButton() {
    $('#modalTitle').text('Are you sure you want to Submit all the latest updates?');
    $("#confirmModal").modal('show').one('click', '#yesConfirm', function (e) {
        if (latestTableOrders.Array.length > 0 || tableInfoEdit) {

            var description = $('#descriptionTextSummary').val();
            var numOfGuests = $('#numOfGuestsSummary').val();
            var serverId = $('#serverIdSummary').val();

            var tableObj = {'id': tableId, 'serverId': serverId, 'numOfGuests': numOfGuests, 'description': description, 'discount': totalDiscount};

            OrdersService.updateTable(tableId, tableObj,
                    function (response) {
                        if (response === undefined) {
                            OrdersService.makeOrder(tableId, latestTableOrders.Array,
                                    function (response) {
                                        if (response === undefined)
                                        {
                                            refreshGlobal();
                                            refreshSummaryPanel();
                                            cancelSubmit();
                                            $('#sumBillSummary').removeClass('toRed');
                                            alertMechanism.Success("Order has been submitted and placed");
                                        } else {
                                            alertMechanism.Error("An error was occured, please try again");
                                        }
                                    });
                        }
                    });
        }
    });
}

function refreshGlobal() {
    tableInfoEdit = false;
    $('.all-orders-table').empty();
    itemsCount = 0;
    sumBill = 0;
    latestTableOrders.Array = [];
}

function onSummaryCancelButton() {
    $('#modalTitle').text('Are you sure you want to Cancel all the latest updates?');
    $("#confirmModal").modal('show').one('click', '#yesConfirm', function (e) {
        refreshGlobal();
        refreshSummaryPanel();
        cancelSubmit();
    });
}

function readySubmit() {
    $('#submitSummaryButton').addClass('unsaved-changes');
}

function cancelSubmit() {
    $('#submitSummaryButton').removeClass('unsaved-changes');
}

function onSummaryCloseButton() {
    $('#modalTitle').text('Print Bill?');
    $("#confirmModal").modal('show').one('click', '#yesConfirm', function (e) {

        var d = new Date();
        var date = d.toLocaleString();
        $('#tot').text(sumBill);
        $('#dis').text(totalDiscount);
        $('#dat').text(date);
        $("#allTable").clone().appendTo('#billModalBody');
        $('#billModal').modal('show').one('click', '#closeBill', function (e) {

            $('.login-pending').show();
            $('#sendingModal').modal('show');
            var date = new Date();
            var month = date.getUTCMonth();
            month++;
            var orderDate = date.getFullYear() + '-' + month + '-' + date.getUTCDate();
            var orderInfo = {
                'employeeId': employeeObject.id,
                'tableNum': tableId,
                'orderDate': orderDate,
                'totalSum': sumBill,
                'discount': totalDiscount
            };

            OrdersService.closeOrderInfo(orderInfo,
                    function (response) {
                        if (response !== undefined) {
                            var orderId = response;
                            var ordersItemsList = overAllTableOrders.GetItems(orderId);
                            OrdersService.closeOrderItems(ordersItemsList,
                                    function (response) {
                                        if (response === undefined) {
                                            TablesService.deleteTableById(tableId, function (response) {
                                                if (response === undefined) {

                                                    $('.alert.login').hide();
                                                    $('.alert.login-success').show()
                                                    refreshGlobal();
                                                    setTimeout(closeTableAnimation, 1500);

                                                } else {
                                                    $('.alert.login').hide();
                                                    $('.alert.login-failed').show();
                                                }
                                            });
                                        } else {
                                            $('.alert.login').hide();
                                            $('.alert.login-failed').show();
                                        }
                                    });
                        } else {
                            $('.alert.login').hide();
                            $('.alert.login-failed').show();
                        }
                    });
        });
    });
    $('#billModalBody').empty();
}

function closeTableAnimation() {
    setTimeout(function () {
        window.location.href = '../index.html';
    }, 1000);
}

function onClockOutClick() {
    $('#modalTitle').text('Are you sure you want to Clock Out?');
    $("#confirmModal").modal('show').one('click', '#yesConfirm', function (e) {
        var tempEmployeeId = parseInt(employeeId);
        EmployeeService.deleteActiveEmployee(tempEmployeeId, function (response) {
            if (response === undefined) {
                EmployeeService.clockOut(tempEmployeeId, function (response) {
                    if (response !== -1) {
                        alertMechanism.Success(employeeObject.firstName + " " + employeeObject.lastName + " Clocked Out, Bye Bye");
                        setTimeout(window.location.href = '../index.html', 1500);
                    } else {
                        //if didnt succedd restore employee to active
                        alertMechanism.Error("Error! Didn't succedd to ClockOut, please try again");
                        EmployeeService.getEmployeeById(employeeId, function (employee) {
                            if (employee !== undefined) {
                                EmployeeService.addActiveEmployee(employeeId, function (response) {
                                });
                            }
                        });
                    }
                });
            }
        });
    });
}

function onDiscountAddButton() {

    var discountAmount = parseInt($('#discountAddInput').val());
    sumBill -= discountAmount;
    totalDiscount += discountAmount;
    updateSum();
    tableInfoEdit = true;
    readySubmit();

}

function updateSum() {
    $('#sumBillSummary').text("Total: " + sumBill);
}
