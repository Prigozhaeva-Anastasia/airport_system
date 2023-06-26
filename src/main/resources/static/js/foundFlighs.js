 window.onload=function() {
     let errorMessage1 = document.getElementById('errorMessage1');
     let errorMessage2 = document.getElementById('errorMessage2');
     let numbOfFlights = document.getElementById('wrapDiv').childNodes.length - 1;
     let elements = document.querySelectorAll('.position-top');
     let countOfTransfers = document.getElementById('countOfTransfers');
     let spanOfTransfers = document.getElementById('spanOfTransfers');

     for (let i = 0; i < elements.length; i++) {
         if (numbOfFlights / 2 === 1) elements[i].style.marginTop = '-280px';
         else {
             let top = 260 + (numbOfFlights / 2 - 1) * 290;
             let topStr = '-' + top + 'px';
             elements[i].style.top = topStr;
         }
         if (i === elements.length - 1) {
             elements[i].style.marginBottom = '0px';
         }
     }

     if (document.getElementById('wrapper') == null) {
         errorMessage1.style.display = 'block';
         errorMessage2.style.display = 'block';
     }
     if (countOfTransfers.innerText >= 2 && countOfTransfers.innerText <= 4) {
         spanOfTransfers.innerText = ' пересадки';
     }
     else if (countOfTransfers.innerText != 1) {
         spanOfTransfers.innerText = ' пересадок';
     }
 }