
function ajaxCall(type,url) {

    $.ajax({
        type: type,
        url: url,
        success: function(msg){
            location.reload();
        }
    });
}

function ajaxDelete(id,thingToDelete) {
    ajaxCall("DELETE","/REST/delete"+thingToDelete+"?"+"projectId="+id);
}


