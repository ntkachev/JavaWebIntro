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
    }
};