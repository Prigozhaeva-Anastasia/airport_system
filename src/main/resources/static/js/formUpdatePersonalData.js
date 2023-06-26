window.onload=function() {
    let lastName = document.getElementById('lastName');
    let lastNameError = document.getElementById('lastNameError');
    let firstName = document.getElementById('firstName');
    let firstNameError = document.getElementById('firstNameError');
    let gender = document.getElementById('gender');
    let male = document.getElementById('male');
    let female = document.getElementById('female');
    let date = document.getElementById('date');
    let dateError = document.getElementById('dateError');
    let citizenship = document.getElementById('citizenship');
    let citizenshipError = document.getElementById('citizenshipError');
    let passportNumber = document.getElementById('passportNumber');
    let passportNumberError = document.getElementById('passportNumberError');
    let phoneNumber = document.getElementById('phoneNumber');
    let phoneNumberError = document.getElementById('phoneNumberError');
    let password = document.getElementById('password');
    let passwordError = document.getElementById('passwordError');
    let passwordConfirmation = document.getElementById('passwordConfirmation');
    let passwordConfirmationError = document.getElementById('passwordConfirmationError');
    let editBtn = document.getElementById('editBtn');
    let changePassword = document.getElementById('changePassword');
    let hiddenTR1 = document.getElementById('hiddenTR1');
    let hiddenTR2 = document.getElementById('hiddenTR2');
    let hiddenTR3 = document.getElementById('hiddenTR3');


    password.value = "";
    if (gender.value === "мужской") male.checked = true;
    else female.checked = true;

    lastName.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            lastNameError.style.display = 'block';
            lastName.classList.add('error');
        }
    });

    firstName.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            firstNameError.style.display = 'block';
            firstName.classList.add('error');
        }
    });
    date.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            dateError.style.display = 'block';
            date.classList.add('error');
        }
    });
    citizenship.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            citizenshipError.style.display = 'block';
            citizenship.classList.add('error');
        }
    });
    passportNumber.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            passportNumberError.style.display = 'block';
            passportNumber.classList.add('error');
        }
    });
    phoneNumber.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            phoneNumberError.style.display = 'block';
            phoneNumber.classList.add('error');
        }
    });

    male.addEventListener('click', function (event) {
        if (male.checked) gender.value = "мужской";
    });

    female.addEventListener('click', function (event) {
        if (female.checked) gender.value = "женский";
    });

    lastName.addEventListener('ch', function (event) {
        let to = lastName.value.search(',');
        lastName.value = lastName.substring(0, to);
    });

    changePassword.addEventListener('click', function (event) {
        hiddenTR1.style.visibility = 'visible';
        hiddenTR2.style.visibility = 'visible';
        hiddenTR3.style.display = 'none';

    });

    password.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            passwordError.style.display = 'block';
            password.classList.add('error');
        }
    });

    editBtn.addEventListener('click', function (event) {
        if (password.value !== passwordConfirmation.value) {
            event.preventDefault();
            passwordConfirmationError.style.display = 'block';
            passwordConfirmation.classList.add('error');
        }
    });
}