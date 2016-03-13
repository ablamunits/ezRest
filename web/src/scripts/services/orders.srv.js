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
        doAjaxGet('tables/order/' + tableId).done(function (response) {
            cb(response);
        });
    },
    updateTable: function (tableId, tableObj, cb) {
        doAjaxPost('tables/' + tableId, tableObj).done(function (response) {
            cb(response);
        });
    },
    closeOrderInfo: function (orderInfo, cb) {
        doAjaxPost('orders', orderInfo).done(function (response) {
            cb(response);
        });
    },
    closeOrderItems: function (orderItemsList, cb) {
        doAjaxPost('orderItems', orderItemsList).done(function (response) {
            cb(response);
        });
    },
    getOrdersInDate: function (date, cb) {
        doAjaxGet('orders/date/' + date).done(function (response) {
            cb(response);
        });
    },
    getOrderById: function (id, cb) {
        doAjaxGet('orders/' + id).done(function (response) {
            cb(response);
        });
    },
    getOrderItemsById: function (orderId, cb) {
        doAjaxGet('orderItems/' + orderId).done(function (response) {
            cb(response);
        });
    }
};
