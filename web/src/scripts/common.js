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

    $.ajax({
        url: API_URL + destination,
        method: 'POST',
        contentType: 'application/json',
        data: JSONData
    }).done(function (result)
    {
      return result;
    });
}
