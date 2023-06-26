window.onload = function () {
    let errorMessage1 = document.getElementById('errorMessage1');
    let errorMessage2 = document.getElementById('errorMessage2');
    let cardBtn = document.querySelectorAll('.cardBtn');
    let airTicketPrice = document.querySelectorAll('.airTicketPrice');
    let balance = document.querySelectorAll('.balance');
    let insufficientFunds = document.querySelectorAll('.insufficientFunds');

    if (document.getElementById('wrapper') == null) {
        errorMessage1.style.display = 'block';
        errorMessage2.style.display = 'block';
    }

    for (let i = 0; i < cardBtn.length; i++) {
        cardBtn[i].addEventListener('click', function (event) {
            if (Number(airTicketPrice[i].innerText) > Number(balance[i].innerText)) {
                event.preventDefault();
                insufficientFunds[0].style.display = 'block';
                insufficientFunds[1].style.display = 'block';
            } else alert("Оплата прошла успешно!");
        });
    }
}