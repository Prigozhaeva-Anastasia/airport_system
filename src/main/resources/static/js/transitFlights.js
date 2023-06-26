window.onload=function() {
    let formFilter = document.getElementById('formFilter');
    let formFilter2 = document.getElementById('formFilter2');
    let formBtns = document.getElementById('formBtns');
    let date1 = document.getElementById('date1');
    let date2 = document.getElementById('date2');
    let time1 = document.getElementById('time1');
    let time2 = document.getElementById('time2');
    let applyBtn = document.getElementById('applyBtn');
    let applyBtn2 = document.getElementById('applyBtn2');
    let byDate = document.getElementById('byDate');
    let byTime = document.getElementById('byTime');

    applyBtn.addEventListener('click', function (event) {
        formFilter.action = '/transitFlights/filterByDate?from=' + date1.value + '&to=' + date2.value;
    });

    byDate.addEventListener('click', function (event)  {
        formFilter2.style.display = 'none';
        formFilter.style.display = 'block';
        formBtns.style.left = '0px';
    });

    applyBtn2.addEventListener('click', function (event) {
        formFilter2.action = '/transitFlights/filterByTime?from=' + time1.value + '&to=' + time2.value;
    });

    byTime.addEventListener('click', function (event)  {
        formFilter.style.display = 'none';
        formFilter2.style.display = 'block';
        formBtns.style.left = '0px';
    });
}