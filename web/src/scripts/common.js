var API_URL = 'http://webedu3.mtacloud.co.il:8080/ezRest/api/';

function doAjaxGet(destination, JSONData) {
    console.log('Trying to ajax GET...');

    return $.ajax({
        method: 'GET',
        url: API_URL + destination,
        contentType: 'application/json',
        data: JSONData
    }).done(function (result) {
        return result;
    });
}

function doAjaxPost(destination, JSONData) {
    console.log('Trying to ajax POST...');

    return $.ajax({
        url: API_URL + destination,
        method: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSONData
    }).done(function (result)
    {
        return result;
    }).error(function () {
        return -1;
    });
}

//to use it, in the html page put <ul class="alerts-list"></ul>
alertMechanism = function () {
},
        alertMechanism.Error = function (message) {
            $('.alerts-list').append('<div class="alert alert-danger fade in"><strong>Error!</strong> ' + message + '</div>');
            alertTimeout(5000);
        },
        alertMechanism.Info = function (message) {
            $('.alerts-list').append('<div class="alert alert-info fade in">' + message + '</div>');
            alertTimeout(5000);
        },
        alertMechanism.Success = function (message) {
            $('.alerts-list').append('<div class="alert alert-success fade in"><strong>' + message + '</strong></div>');
            alertTimeout(5000);
        };

function alertTimeout(wait) {
    setTimeout(function () {
        $('.alerts-list').children('.alert:first-child').remove();
    }, wait);
}

//example : menu.html?tableId=8 
//use : var tableId = getUrlParameter("tableId");
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