/* global doAjaxGet */

var AuthService = {
  login: function (email, password, cb) {
      doAjaxPost('auth/login', {email: email, password: password}).done(function (response) {
          cb(response);
      });
  },

  logout: function (cb) {
    doAjaxPost('auth/logout').done(function (response) {
      cb(response);
    });
  },

  authState: function (cb) {
    doAjaxGet('auth').done(function (response) {
      cb(response);
    });
  }
};
