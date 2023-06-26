window.onload = function () {
    let errorMessage1 = document.getElementById('errorMessage1');
    let errorMessage2 = document.getElementById('errorMessage2');

    if (document.getElementById('wrapper') == null) {
        errorMessage1.style.display = 'block';
        errorMessage2.style.display = 'block';
    }
}