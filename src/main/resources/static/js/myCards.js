window.onload = function () {
    let cardNumber = document.querySelectorAll('.cardNumber');
    let newCardBtn = document.getElementById("newCardBtn");
    let hidden = document.getElementById("hidden");
    let number = document.getElementById("number");
    let month = document.getElementById("month");
    let year = document.getElementById("year");
    let error = document.getElementById('error');
    let addNewCard = document.getElementById('addNewCard');
    let cardBalance = document.getElementById('card-balance');
    let cardNumberWithDelimiter = "";
    let k = 0;

    for (let j = 0; j < cardNumber.length; j++) {
        cardNumberWithDelimiter = "";
        for (let i = 0; i < cardNumber[j].innerText.length; i++) {
            if (k == 4) {
                k = 0;
                cardNumberWithDelimiter += " " + cardNumber[j].innerText.charAt(i);
            } else cardNumberWithDelimiter += cardNumber[j].innerText.charAt(i);
            k++;
        }
        cardNumber[j].innerText = cardNumberWithDelimiter;
    }

    newCardBtn.addEventListener('click', function (event) {
        if (hidden.style.display !== 'block'){
            hidden.style.display = 'block';
            number.value = "";
            month.value = "";
            year.value = "";
        }
        else hidden.style.display = 'none';
    });

    month.addEventListener('mouseover', function (event) {
        if (number.value) {
            $.get({
                url: '/api/cards/byNumber/' + number.value,
                success: (data) => {
                    if (data !== "") {
                        error.style.display = 'block';
                    }
                },
                error: (err) => {
                    alert(err);
                }
            });
        }
    });

    addNewCard.addEventListener('click', function (event) {
        if (error.style.display === 'block') event.preventDefault();
    });

    number.addEventListener('change', function (event) {
        error.style.display = 'none';
    });

    $('#modalReplenishment').on('shown.bs.modal', function (event) {
        let button = $(event.relatedTarget)
        let cardId = button.data('cardid')

        if (cardId) {
            $.get({
                url: '/api/cards/' + cardId,
                success: (data) => {
                    let modal = $(this)
                    modal.find('#card-id').val(data.id)
                    modal.find('#card-balance').val("")
                },
                error: (err) => {
                    alert(err);
                }
            });
        }
    });

    $('#add').click(function () {
        let modal = $('#modalReplenishment')
        let card = {
            id: modal.find('#card-id').val(),
            balance: modal.find('#card-balance').val(),
        };
        $.ajax({
            url: '/api/cards',
            type: 'POST',
            data: JSON.stringify(card),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: () => {
                location.reload()
            },
            error: (err) => {
                alert(err);
            }
        })
        modal.modal('hide');
    });

    number.oninput = function () {
        const  value = this.value
        this.value = value.replace(/\D/g, '');
        this.value = this.value.substring(0, 16);

    }

    month.oninput = function () {
        const  value = this.value
        if (value.length === 1) {
            if (!/^(0|1)$/.test(value)) {
                this.value = value.replace(value.charAt(value.length - 1), '');
            }
        } else {
            if (!/^(0[0-9]|1[0-2])$/.test(value)) {
                let reverseVal = value.toString().split('').reverse().join('');
                let delVal = reverseVal.replace(value.charAt(value.length - 1), '');
                this.value = delVal.split('').reverse().join('');
            }
        }
    }

    year.oninput = function () {
        const  value = this.value
        let today = new Date();
        let year = today.getFullYear();
        if (value.length === 4 && value < year) this.value = "";
        this.value = this.value.substring(0, 4);
    }

    number.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid ) {
            number.classList.add('error');
        }
    });

    cardBalance.oninput = function () {
        this.value = this.value.replace(/(\d+\.\d\d\d+)|[Р-пр-џA-za-z]/g, '');
    }

    cardBalance.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid ) {
            cardBalance.classList.add('error');
            if (!event.target.validity.valid) {
                month.classList.add('error');
            }
        }
    });

    year.addEventListener('invalid', function (event) {
        event.preventDefault();
        if (!event.target.validity.valid ) {
            year.classList.add('error');
        }
    });
}