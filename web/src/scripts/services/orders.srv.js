/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global doAjaxPost, doAjaxGet */

var OrdersService = {
    makeOrder: function (tableId, menuItemList, cb) {
        doAjaxPost('tables/order/' + tableId, menuItemList).done(function (response) {
            cb(response);
        });
    },
    
    getTableOrder: function (tableId, cb) {
       doAjaxGet('tables/order/' + tableId).done(function (response){
           cb(response);
       });
    }
};