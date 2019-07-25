$(document).ready(function () {

});

EntityController = {
    saveEntity : function(callbackWhenDone) {
        entity = collectFormData();
//        options = {
//            type: "POST",
//            url: "/entities/current",
//            data: entity,
//            "error": function() {
//                console.log(arguments);
//            }
//        };
//        $.ajax(options);
        $.post(entityPostLocation, entity)
            .done(callbackWhenDone)
            .fail(function(jqxhr) {
                console.log(jqxhr.responseText)
            });
    },

    changeEntity : function(entityId) {
        this.saveEntity(function() {
            window.location.replace("/entities/" + entityId);
        });
    }
};
