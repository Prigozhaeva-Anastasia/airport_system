window.onload=function() {
    let arrivalCity = document.getElementById('arrivalCity');
    let arrivalCityError = document.getElementById('arrivalCityError');
    let departureCity = document.getElementById('departureCity');
    let departureCityError = document.getElementById('departureCityError');
    let arrivalDate = document.getElementById('arrivalDate');
    let arrivalDateError = document.getElementById('arrivalDateError');
    let hasTransfers = document.getElementById('hasTransfers');
    let form = document.getElementById('formMainForUser');

    arrivalCity.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            arrivalCityError.style.display = 'block';
            arrivalCity.classList.add('error');
        }
    });

    departureCity.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            departureCityError.style.display = 'block';
            departureCity.classList.add('error');
        }
    });

    arrivalDate.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            arrivalDateError.style.display = 'block';
            arrivalDate.classList.add('error');
        }
    });

    hasTransfers.addEventListener('click', function (e) {
        if (hasTransfers.checked) form.action = "/mainForUser/foundTransitFlights";
        else form.action = "/mainForUser/foundDirectFlights";
    });
}