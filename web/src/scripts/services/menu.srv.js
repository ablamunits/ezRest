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
    },

    getAllCategories: function(cb) {
      doAjaxGet('menu/category').done(function(response) {
        cb(response);
      });
    },

    addNewItem: function(item, cb) {
      doAjaxPost('menu/item', item).done(function(response) {
        cb(response);
      })
    },

    addNewCategory: function(category, cb) {
      doAjaxPost('menu/category', category).done(function(response) {
        cb(response);
      })
    }
};
