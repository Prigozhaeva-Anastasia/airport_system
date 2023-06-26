window.onload=function() {
    let password = document.getElementById('password');
    let passwordConfirmationDiv = document.getElementById('passwordConfirmationDiv');
    let lastName = document.getElementById('lastName');
    let lastNameError = document.getElementById('lastNameError');
    let firstName = document.getElementById('firstName');
    let firstNameError = document.getElementById('firstNameError');
    let passwordError = document.getElementById('passwordError');
    let passwordConfirmation = document.getElementById('passwordConfirmation');
    let passwordConfirmationError = document.getElementById('passwordConfirmationError');
    let formUpdateAdmin = document.getElementById('formUpdateAdmin');

    password.addEventListener('click', function (event) {
        password.value = "";
        passwordConfirmationDiv.value = "";
    });

    password.addEventListener('keydown', function (e) {
        passwordConfirmationDiv.style.display = 'block';
    });

    lastName.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            lastNameError.style.display = 'block';
            lastNameError.style.marginLeft = '440px';
            lastName.classList.add('error');
        }
    });

    firstName.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            firstNameError.style.display = 'block';
            firstNameError.style.marginLeft = '440px';
            firstName.classList.add('error');
        }
    });

    password.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            passwordError.style.display = 'block';
            passwordError.style.marginLeft = '440px';
            password.classList.add('error');
        }
    });

    passwordConfirmation.addEventListener('change', function(event){
        event.preventDefault();
        if (passwordConfirmation.value !== password.value) {
            passwordConfirmationError.style.display = 'block';
            passwordConfirmationError.style.marginLeft = '440px';
            passwordConfirmation.classList.add('error');
        }
    });

    formUpdateAdmin.addEventListener('submit', function (e) {
        if (passwordConfirmation.value === "" && passwordConfirmationDiv.style.display === 'block') {
            e.preventDefault();
            passwordConfirmationError.style.display = 'block';
            passwordConfirmationError.style.marginLeft = '440px';
            passwordConfirmation.classList.add('error');
        }
    });
}