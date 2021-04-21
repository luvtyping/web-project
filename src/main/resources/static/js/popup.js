function closeWindow() {
    $("#popup").css({"display": "none", "opacity": "0"});
    $("#popup").animate({top: "+300px", opacity: "0"}, "slow");
}

function openPopup(bookName, popup) {
    $.getJSON("/popup", {name: bookName}, (response) => {
        $('#window').css("display", "flex");
        $('#bookNameInPopup').text(response.name);
        $('#bookAuthorInPopup').text(response.author);
        $('#bookPublishYearInPopup').text(response.publishYear);
        $('#bookGenreInPopup').text(response.genre);
        $('#bookDescriptionInPopup').text(response.description);
        $('#bookPriceInPopup').text(response.price);
        $('#addToCart').attr("value", bookName);
        $('#deleteFromTheCart').attr("value", bookName);
        $('#deleteBook').attr("value", bookName);
    })

    popup.css("display", "flex");
    popup.animate({top: "+100px", opacity: "1"}, "normal");
}

function deleteBook(bookName){
    $.getJSON("/deleteBook", {bookName: bookName}, () => {
        location.reload();
        closeWindow();
    })
}