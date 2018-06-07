

function closePopup() {
    if(window.opener.isPopup() == true) {
        window.opener.doRefresh();
        window.close();
    }
}