window.onload=function() {
    let nameOfCity = document.getElementById('nameOfCity');
    let nameOfCityError = document.getElementById('nameOfCityError');
    let transferTime = document.getElementById('transferTime');
    let transferError = document.getElementById('transferError');
    let form = document.getElementById('form');
    let btnNext = document.getElementById('next');

    nameOfCity.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            nameOfCityError.style.display = 'block';
            nameOfCity.classList.add('error');
        }
    });

    transferTime.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            transferError.style.display = 'block';
            transferTime.classList.add('error');
        }
    });

    if (localStorage.getItem('countOfTransfers') == 1 || localStorage.length === 0) btnNext.innerText = 'Добавить';
    else btnNext.innerText = 'Следующая';

    form.addEventListener('submit', function (event) {
        let countOfTransfers = localStorage.getItem('countOfTransfers');
        countOfTransfers--;
        localStorage.setItem('countOfTransfers', countOfTransfers);
    });

}