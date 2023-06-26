window.onload=function() {
    let filterForAirlines = document.getElementById('filterForAirlines');
    let formFilter = document.getElementById('formFilter');
    let formBtns = document.getElementById('formBtns');
    let rating1 = document.getElementById('rating1');
    let rating2 = document.getElementById('rating2');
    let applyBtn = document.getElementById('applyBtn');

    filterForAirlines.addEventListener('click', function (event) {
        formFilter.style.display = 'block';
        formBtns.style.left = '0px';
    });

    rating1.oninput = function () {
        this.value = this.value.replace(/0|([6-9]+)|[1-9][0-9]+|(\d+\.\d\d+)|[Р-пр-џA-za-z]/g, '');
        this.value = this.value.substring(0, 16);
    }

    rating2.oninput = function () {
        this.value = this.value.replace(/0|([6-9]+)|[1-9][0-9]+|(\d+\.\d\d+)|[Р-пр-џA-za-z]/g, '');
        this.value = this.value.substring(0, 16);
    }

    applyBtn.addEventListener('click', function (event) {
        formFilter.action = '/airlines/filterByRating?from=' + rating1.value + '&to=' + rating2.value;
    });
}