/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* global EmployeeService */

$(document).ready(function () {
    var tableId = getUrlParameter('tableId');
    var employeeId = getUrlParameter('employeeId');

    EmployeeService.getEmployeeById(employeeId,
            function (response) {
                $("#employeeName").text("Hi " + response.firstName);
                $("#tableId").text(tableId);
            });


});

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};