window.onload=function(){
    let chooseFile = document.getElementById('log');
    let img = document.getElementById('logotype');
    let hidden = document.getElementById('hiddenLog');
    let input = document.getElementById('rating');
    let errorLabel = document.getElementById('errorLabel');

    chooseFile.addEventListener('change', function (e) {
        let fileName = "/images/" + chooseFile.files[0].name;
        img.srcset = fileName;
        hidden.value = "";
        localStorage.setItem('fileName', fileName);
    });

    if (localStorage.getItem('fileName')) {
        img.srcset = localStorage.getItem('fileName');
    }

    input.addEventListener('invalid', function(event){
        event.preventDefault();
        if ( ! event.target.validity.valid ) {
            errorLabel.style.display = 'block';
            input.classList.add('error');
        }
    });
}
