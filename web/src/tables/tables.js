/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global TablesService, alertMechanism, EmployeeService */

var activeTablesList = [];
var maxTables = 20;
var employeeId;

function refreshTables() {
    $('#addTablePopup').hide();

    TablesService.getActiveTables(function (data) {
        activeTablesList = data;
        var activeTableNum = 0;

        for (var tableNum = 1; tableNum <= maxTables; tableNum++) {

            if (isInActiveList(tableNum, activeTablesList)) {
                var table = activeTablesList[activeTableNum++];
                var $node = $('<li data-toggle="tooltip" />').html(table.id)
                        .attr('data-original-title', table.numOfGuests + " Diners")
                        .tooltip({'placement': 'top'})
                        .attr('server-id', table.serverId)
                        .attr('table-id', table.id)
                        .addClass('btn btn-square').css('text-align', 'center');
                $node.click(activeTableSelected);

                var tempServerId = table.serverId;
                if (tempServerId.toString() === employeeId) {
                    var $star = $('<span/>').addClass('glyphicon glyphicon-star-empty');
                    $node.append($star);
                }
                $('.available-tables-list').append($node);

            } else {
                var $node = $('<li />').html(tableNum)
                        .attr('table-id', tableNum)
                        .addClass('btn btn-square');
                $node.click(addTablePopup);
                $('.all-tables-list').append($node);
            }
        }
    });
}

$(document).ready(function () {
    employeeId = getUrlParameter("employeeId");

    EmployeeService.getActiveEmployeeById(employeeId,
            function (employee) {
                $("#employeeName").attr('title', employee.firstName);
                $('.header-name-display').text(employee.firstName);
                $('[data-toggle="tooltip"]').tooltip();
            });
    refreshTables();
});

function activeTableSelected(event) {
    var $target = $(event.target);
    var tableId = $target.attr('table-id');

    console.log('Navigate to menu for table id ' + tableId);
    window.location.href = '../menu/menu.html?employeeId=' + employeeId + '&tableId=' + tableId; //Maybe change here to first name
}

function isInActiveList(tableNum, activeTablesList) {
    var inList = false;

    $.each(activeTablesList, function (index, table) {
        if (tableNum === table.id) {
            inList = true;
        }
    });

    return inList;
}

function addTablePopup(event) {
    refreshPopup();
    refreshTableColors();
    adjustPopup(event);

    var $target = $(event.target);
    var tableId = $target.attr('table-id');
    $('#tableNumber').html(tableId);
    $target.addClass('popup-open');
    $('#serverIdInput').val(getUrlParameter("employeeId"));
}

function adjustPopup(event) {
    var width = $(window).width();
    var addTablePopupSize = $('#addTablePopup').width();
    var listItemTablesSize = $('.panel-body li').width();

    var $target = $(event.target);
    var left = $target.offset().left;
    var right;

    var popupOffset = width - left - addTablePopupSize;
    if (popupOffset >= 0) { //from the left
        right = $target.offset().left + addTablePopupSize;
        left = $target.offset().left;
    } else {                   //from the right
        right = $target.offset().left + listItemTablesSize;
        left = right - addTablePopupSize;
    }

    $("#addTablePopup").css({
        'position': 'absolute',
        'left': left,
        'right': right,
        'top': $target.offset().top + $target.height() + 7


    }).show("slow").delay(1000);
}

function onClosePopup() {
    $("#addTablePopup").hide("1000");
    refreshTableColors();
}

function refreshTableColors() {
    $('.all-tables-list').children('li').removeClass('popup-open');
}

function onAddTableClick() {
    var numOfGuests = $('#numOfGuestsInput').val();
    var description = $('#descriptionText').val() || "";
    var serverId = $('#serverIdInput').val();
    var tableId = $('#tableNumber').text();
    var validFields = validateFields(numOfGuests, serverId);

    if (validFields === true) {
        TablesService.addNewTable({
            description: description,
            id: tableId,
            numOfGuests: numOfGuests,
            serverId: serverId,
            discount: 0
        },
                function (response) {
                    if (response === undefined) {
                        //success
                        onClosePopup();
                        $('.available-tables-list').empty();
                        $('.all-tables-list').empty();
                        refreshTables();

                        alertMechanism.Success("Table id: " + tableId + " was added");
                    } else {
                        //error
                        alertMechanism.Error("Something went wrong, please try again");
                    }
                });
    }
}

function validateFields(numOfGuests, serverId) {
    var validFields = true;

    if (numOfGuests === "") {
        $('#numOfGuestsInput').css("border-color", "red");
        $('#numOfGuestsInput').attr("placeholder", "Mandatory Field");
        validFields = false;
    }
    if (serverId === "") {
        $('#serverIdInput').css("border-color", "red");
        $('#serverIdInput').attr("placeholder", "Mandatory Field");
        validFields = false;
    }

    return validFields;
}

function refreshPopup() {
    $('#numOfGuestsInput').val("");
    $('#numOfGuestsInput').attr("placeholder", "Number of Guests");
    $('#numOfGuestsInput').css("border-color", "");

    $('#serverIdInput').val("");
    $('#serverIdInput').attr("placeholder", employeeId);
    $('#serverIdInput').css("border-color", "");

    $('#descriptionText').val("");
}

function onClockOutClick() {
    $('#modalTitle').text('Are you sure you want to Clock Out?');
    $("#confirmModal").modal('show').one('click', '#yesConfirm', function (e) {
        var tempEmployeeId = parseInt(employeeId);
        EmployeeService.deleteActiveEmployee(tempEmployeeId, function (response) {
            if (response === undefined) {
                EmployeeService.clockOut(tempEmployeeId, function (response) {
                    if (response !== -1) {
                        EmployeeService.getEmployeeById(employeeId, function (employee) {
                            alertMechanism.Success(employee.firstName + " " + employee.lastName + " Clocked Out, Bye Bye");
                            setTimeout(window.location.href = '../index.html', 1500);
                        });
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
