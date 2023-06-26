window.onload=function() {
    let directionTH = document.getElementById('directionTH');
    let timeTH = document.getElementById('timeTH');
    let time = document.querySelectorAll('.time');
    let status = document.querySelectorAll('.status');
    let today = new Date();

    if (window.location.href === "http://localhost:8082/mainForUser/scoreboard") {
        directionTH.innerText = 'Откуда';
        timeTH.innerText = 'Время прилета';

        for (let i = 0; i < time.length; i++) {
            let t = time[i].innerText.split(":");
            let dTime = new Date();
            dTime.setHours(Number(t[0]));
            dTime.setMinutes(Number(t[1]));
            let between = Date.parse(dTime.toString()) - Date.parse(today.toString());
            if (dTime < today) status[i].innerText = "Прибыл";
            else if (between/1000 <= 300) status[i].innerText = "Посадка";
            else status[i].innerText = "В пути";
        }
    } else {
        directionTH.innerText = 'Куда';
        timeTH.innerText = 'Время отправления';

        for (let i = 0; i < time.length; i++) {
            let t = time[i].innerText.split(":");
            let dTime = new Date();
            dTime.setHours(Number(t[0]));
            dTime.setMinutes(Number(t[1]));
            let between = Date.parse(dTime.toString()) - Date.parse(today.toString());
            if (between/1000 <= 300 && between/1000 >= 0) status[i].innerText = "Взлтает";
            else  if (dTime < today) status[i].innerText = "Вылетел";
            else status[i].innerText = "Заплонирован";
        }
    }
}