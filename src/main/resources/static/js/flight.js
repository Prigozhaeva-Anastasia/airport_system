window.onload=function() {
    let input = document.getElementById('priceOfTickets');
    let errorLabel = document.getElementById('errorLabel');
    let errorLabelTransfer = document.getElementById('errorLabelTransfer');
    let airlines = document.querySelector("select");
    let form = document.getElementById('form');
    let countOfTransfers = document.getElementById('countOfTransfers');

    form.addEventListener('click', function (e) {
        let arr = [];
        for(let option of airlines.options) {
            arr.push(option.value);
            arr.push(option.text);
        }
        let airline = airlines.options[airlines.selectedIndex].value;
        localStorage.setItem('airlines', JSON.stringify(arr));
        localStorage.setItem('airline', airline);
    });

    if(localStorage.getItem('airlines')) {
        let array = JSON.parse(localStorage.getItem("airlines"));
        for(let i = 0; i < array.length; i++) {
            let option = document.createElement('option');
            option.value = array[i];
            option.text = array[i+1];
            if(option.value === localStorage.getItem('airline')) {
                option.selected = true;
            }
            ++i;
            document.getElementById("airlines").add(option);
        }
    }
    input.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            errorLabel.style.display = 'block';
            input.classList.add('error');
        }
    });

    countOfTransfers.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            errorLabelTransfer.style.display = 'block';
            countOfTransfers.classList.add('error');
        }
    });
    form.addEventListener('submit', function (event){
        if (countOfTransfers.validity.valid) {
            localStorage.removeItem("countOfTransfers");
            localStorage.setItem("countOfTransfers", countOfTransfers.value);
        }
    });


}