window.onload=function() {
    let occupiedSeatNumbers = document.querySelectorAll('.occupiedSeatNumbers');
    let seatNumbers = document.querySelectorAll('.seatNumbers');
    console.log(seatNumbers.length);

    for (let i = 0; i < occupiedSeatNumbers.length; i++) {
        for (let j = 0; j < seatNumbers.length; j++) {
            let occupied = occupiedSeatNumbers[i].innerText;
            let seat = seatNumbers[j].innerText;
            if (occupied === seat)  {
                seatNumbers[j].style.backgroundColor = 'rgba(143, 52, 228, 0.1)';
                seatNumbers[j].disabled = true;
            }
        }
    }
}