var PermissionService = {
    getPermissionById: function (permissionId, cb) {
        doAjaxGet('permissions/' + permissionId).done(function (response) {
            cb(response);
        });
    },

    authorizedActions: {
      ADD_PRODUCT: false,
      ADD_EMPLOYEE: false,
      CANCEL_ORDER: false,
      ADD_DISCOUNT: false,
      EDIT_MENU: false,
    }
};
