$(document).ready(function () {

});

UserController = {
    addUserEvent : function() {
        data = collectFormData();
        $.post("/admin/users/add", data)
        .done(function() {
            window.location.replace("/admin/users");
        }).fail(function(jqxhr) {
            console.log(jqxhr.responseText)
        });
    },

//    editUserEvent : function() {
//        data = collectFormData();
//        $.post("/admin/users/edit", data)
//        .done(function() {
//            window.location.replace("/admin/users");
//        }).fail(function(jqxhr) {
//            console.log(jqxhr.responseText)
//        });
//    },

    block : function() {
        id = $("input[name='id']")[0].value;
        $.post("/admin/users/" + id + "/block")
        .done(function() {
            window.location.replace("/admin/users");
        }).fail(function(jqxhr) {
            console.log(jqxhr.responseText)
        });
    },

    unblock : function() {
        id = $("input[name='id']")[0].value;
        $.post("/admin/users/" + id + "/unblock")
        .done(function() {
            window.location.replace("/admin/users");
        }).fail(function(jqxhr) {
            console.log(jqxhr.responseText)
        });
    }
};