$(document).ready(function (){
    $("#buyButton").click(function () {
        const login = $("#login").text().trim()
        $.getJSON("/buy", {login: login}, (isEmpty) => {
            showNotification(isEmpty);
        })
    })
})

function showNotification(isEmpty) {
    const notification = $("#notification");
    (!isEmpty)
        ? notification.addClass("success").text("Операция прошла успешно")
        : notification.addClass("fail").text("Ваша корзина пуста");
    window.setTimeout(() => {location.reload()}, 1500);
}

function addBookToCart(bookName) {
    const userLogin = $("#login").text().trim()
    $.getJSON("/addToCart", {bookName: bookName, login: userLogin}, () => {
        closeWindow();
        reloadCounter(userLogin);
    })
}

function reloadCounter(userLogin) {
    $.getJSON("/getNumberOfBooks", {login: userLogin}, (response) => {
        $("#counter").text(response);
    })
}

function deleteFromTheCart(name, userLogin) {
    $.getJSON("/deleteBookFromTheCart", {bookName: name, login: userLogin}, () => {
        location.reload()
        closeWindow();
    })
}

function getTotalPrice() {
    let prices = [];
    $(".bookPrice").each(function () {
        prices.push($(this).text());
    });

    let totalPrice = 0;
    for (let i = 0; i < prices.length; i++) {
        totalPrice = Number(totalPrice) + Number(prices[i]);
    }
    $("#totalPrice").text(totalPrice)
}

