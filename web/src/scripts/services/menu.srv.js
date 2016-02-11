/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global doAjaxGet */

var MenuService = {
    getMenuCategoryById: function (catId, cb) {
        doAjaxGet('menu/category/' + catId).done(function (response) {
            cb(response);
        });
    },
    
    getMenuItemById: function(itemId, cb) {
      doAjaxGet('menu/item/' + itemId).done(function(response){
            cb(response);
      });
    }
};
