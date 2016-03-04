/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var VipService = {
  getAllVip: function (cb) {
      doAjaxGet('vip/').done(function (response) {
          cb(response);
      });
  },
  getVipById: function(vipId, cb){
      doAjaxGet('vip/' + vipId).done(function (response) {
          cb(response);
      });
  },
  updateVip: function(vipId, vipObject, cb){
      doAjaxPost('vip/' + vipId, vipObject).done(function (response){
         cb(response); 
      });
  },
  addNewVip: function(vipObject, cb){
      doAjaxPost('vip', vipObject).done(function (response){
         cb(response); 
      });
  },
  deleteVip: function (vipId, cb){
      doAjaxPost('vip/delete' + vipId).done(function (response){
         cb(response); 
      });
  }
};
