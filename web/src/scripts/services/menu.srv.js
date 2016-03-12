var MenuService = {
    getMenuCategoryById: function (catId, cb) {
        doAjaxGet('menu/category/' + catId).done(function (response) {
            cb(response);
        });
    },
    getMenuItemById: function (itemId, cb) {
        doAjaxGet('menu/item/' + itemId).done(function (response) {
            cb(response);
        });
    },
    getAllCategories: function (cb) {
        doAjaxGet('menu/category').done(function (response) {
            cb(response);
        });
    },
    addNewItem: function (item, cb) {
        doAjaxPost('menu/item', item).done(function (response) {
            cb(response);
        });
    },
    addNewCategory: function (category, cb) {
        doAjaxPost('menu/category', category).done(function (response) {
            cb(response);
        });
    },
    deleteMenuItem: function (itemId, cb) {
        doAjaxPost('menu/item/delete/' + itemId).done(function (response) {
            cb(response);
        });
    },
    deleteCategoryById: function (categoryId, cb) {
        doAjaxPost('menu/category/delete/' + categoryId).done(function (response) {
            cb(response);
        });
    },
    updateItem: function (itemId, item, cb) {
        doAjaxPost('menu/item/' + itemId, item).done(function (response) {
            cb(response);
        });
    },
    updateCategory: function (categoryId, category, cb) {
        doAjaxPost('menu/category/' + categoryId, category).done(function (response) {
            cb(response);
        });
    },
    getMenuItemsOverview: function (cb) {
        doAjaxGet('menu/item/report').done(function (response) {
            cb(response);
        });
    }
};
