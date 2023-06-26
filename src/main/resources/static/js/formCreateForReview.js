window.onload = function () {
    const ratingItemsList1 = document.querySelectorAll('.rating_item1');
    const ratingItemsArray1 = Array.prototype.slice.call(ratingItemsList1);
    const ratingItemsList2 = document.querySelectorAll('.rating_item2');
    const ratingItemsArray2 = Array.prototype.slice.call(ratingItemsList2);
    const ratingItemsList3 = document.querySelectorAll('.rating_item3');
    const ratingItemsArray3 = Array.prototype.slice.call(ratingItemsList3);
    const ratingItemsList4 = document.querySelectorAll('.rating_item4');
    const ratingItemsArray4 = Array.prototype.slice.call(ratingItemsList4);
    let checkInSpeed = document.getElementById('checkInSpeed');
    let state_of_salon = document.getElementById('state_of_salon');
    let crewWork = document.getElementById('crewWork');
    let qualityOfFlightMeals = document.getElementById('qualityOfFlightMeals');

    ratingItemsArray1.forEach(item =>
        item.addEventListener('click', () => {
            const {itemValue} = item.dataset;
            item.parentNode.dataset.totalValue = item.dataset.itemValue;
            checkInSpeed.value = item.parentNode.dataset.totalValue;
        })
    );

    ratingItemsArray2.forEach(item =>
        item.addEventListener('click', () => {
            const {itemValue} = item.dataset;
            item.parentNode.dataset.totalValue = item.dataset.itemValue;
            state_of_salon.value = item.parentNode.dataset.totalValue;
        })
    );

    ratingItemsArray3.forEach(item =>
        item.addEventListener('click', () => {
            const {itemValue} = item.dataset;
            item.parentNode.dataset.totalValue = item.dataset.itemValue;
            crewWork.value = item.parentNode.dataset.totalValue;
        })
    );

    ratingItemsArray4.forEach(item =>
        item.addEventListener('click', () => {
            const {itemValue} = item.dataset;
            item.parentNode.dataset.totalValue = item.dataset.itemValue;
            qualityOfFlightMeals.value = item.parentNode.dataset.totalValue;
        })
    );
}