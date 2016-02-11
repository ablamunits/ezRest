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
                        .addClass('btn btn-success');
                $node.click(activeTableSelected);
                $('.available-tables-list').append($node);

            } else {
                var $node = $('<li />').html(tableNum)
                        .attr('table-id', tableNum)
                        .addClass('btn');
                $node.click(addTablePopup);
                $('.all-tables-list').append($node);
            }
        }
    });
}
$(document).ready(function () {
    employeeId = getUrlParameter("employeeId");
    EmployeeService.getEmployeeById(employeeId,
            function (response) {
                $("#employeeName").attr('title', response.firstName);
                $('[data-toggle="tooltip"]').tooltip(); 
            });
    refreshTables();
});

function activeTableSelected(event) {
    var $target = $(event.target);
    var tableId = $target.attr('table-id');

    console.log('Navigate to menu for table id ' + tableId);
    window.location.href = 'menu.html?employeeId=' + employeeId + '&tableId=' + tableId; //Maybe change here to first name
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
    $target.css("background-color", "#767676");
    $('#serverIdInput').val(getUrlParameter("employeeId"));
}

function adjustPopup(event) {
    var width = $(window).width();
    var addTablePopupSize = $('.modal-content').width();
    var listItemTablesSize = $('ul li').width();

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
    $('.all-tables-list').children('li').css("background-color", "#aab2bd");
}

function onAddTableClick() {
    var numOfGuests = $('#numOfGuestsInput').val();
    var description = $('#descriptionText').val() || "";
    var serverId = $('#serverIdInput').val();
    var tableId = $('#tableNumber').text();
    var validFields = validateFields(numOfGuests, serverId);

    if (validFields === true) {
        TablesService.addNewTable(JSON.stringify({
            description: description,
            id: tableId,
            numOfGuests: numOfGuests,
            serverId: serverId
        }),
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