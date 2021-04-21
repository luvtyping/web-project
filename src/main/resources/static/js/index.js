$(document).ready(function () {
    const userLogin = $("#login").text().trim()
    reloadCounter(userLogin);
    getTotalPrice()

    $(".bookEntity").click(function () {
        const popup = $("#popup");
        const bookName = $(this).attr("value");
        openPopup(bookName, popup);
    });

    $(".closeIcon").click(() => {
        closeWindow();
    });

    $("#addToCart").click(function () {
        const bookName = $(this).attr("value");
        addBookToCart(bookName);
    })

    $("#deleteFromTheCart").click(function () {
        const bookName = $(this).attr("value");
        const userLogin = $("#login").text().trim()
        deleteFromTheCart(bookName, userLogin);
    })

    $("#deleteBook").click(function (){
           const bookName = $(this).attr("value");
           deleteBook(bookName);
    })
})