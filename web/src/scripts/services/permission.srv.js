var PermissionService = {
    getPermissionById: function (permissionId, cb) {
        doAjaxGet('permissions/' + permissionId).done(function (response) {
            cb(response);
        });
    },

		getAllPermissions: function (cb) {
        doAjaxGet('permissions').done(function (response) {
            cb(response);
        });
    },
		
    authorizedActions: {
      ADD_PRODUCT: false,
      ADD_EMPLOYEE: false,
      CANCEL_ORDER: false,
      ADD_DISCOUNT: false,
      EDIT_MENU: false,
    },

		permissionNames: {
			ADD_PRODUCT: 'Add Product',
			ADD_EMPLOYEE: 'Add Employee',
			CANCEL_ORDER: 'Cancel Order',
			ADD_DISCOUNT: 'Add Discount',
			EDIT_MENU: 'Edit Menu',
		}
};
