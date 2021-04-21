$(document).ready(function() {
    $("#searchButton").click(() => searchBook())
})

function searchBook() {
    const name = $("#name").val()
    const author = $("#author").val()
    const genre = $("#genre").val()
    const price = $("#price").val()
    $(".bookPanel").css("display", "block")
    $.getJSON("/search", {
        name: name,
        author: author,
        genre: genre,
        price: price
    }, (response) => {
        let filteredBooksNames = [];
        for (let i = 0; i < response.length; i++) {
            filteredBooksNames.push(response[i].name)
        }
        $(".bookPanel").filter((index, element) => {
            for (let i = 0; i < filteredBooksNames.length; i++) {
                if (filteredBooksNames[i].trim() === element.textContent.trim())
                    return false;
            }
            return true;
        }).css("display", "none")
    })
}