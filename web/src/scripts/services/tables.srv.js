/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global doAjaxGet, doAjaxPost */

var TablesService = {
    getActiveTables: function (cb) {
        doAjaxGet('tables').done(function (response) {
            cb(response);
        });
    },
    addNewTable: function (JSONData, cb) {
        doAjaxPost('tables', JSONData).done(function (response) {
            cb(response);
        });
    },
    getTableById: function (tableId, cb) {
        doAjaxGet('tables/' + tableId).done(function (response) {
            cb(response);
        });
    }
};