var PermissionService = {
    MANAGER_PERMISSION_ID: 1,
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
    addNewPermission: function (permissionObject, cb) {
        doAjaxPost('permissions', permissionObject).done(function (response) {
            cb(response);
        });
    },
    updatePermission: function (permissionId, permissionObject, cb) {
        doAjaxPost('permissions/' + permissionId, permissionObject).done(function (response) {
            cb(response);
        });
    },
    deletePermissionById: function (permissionId, cb) {
        doAjaxPost('permissions/delete/' + permissionId).done(function (response) {
            cb(response);
        });
    },
    
    authorizedActions: {
        ADD_PRODUCT: false,
        ADD_EMPLOYEE: false,
        CANCEL_ORDER: false,
        ADD_DISCOUNT: false,
        EDIT_MENU: false
    },
    permissionNames: {
        ADD_PRODUCT: 'Add Product',
        ADD_EMPLOYEE: 'Add Employee',
        CANCEL_ORDER: 'Cancel Order',
        ADD_DISCOUNT: 'Add Discount',
        EDIT_MENU: 'Edit Menu'
    }
};
