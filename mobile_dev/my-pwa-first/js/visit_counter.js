window.addEventListener('DOMContentLoaded', () => {
    let visitCount = localStorage.getItem('visitCount');

    if (visitCount) {
        visitCount = parseInt(visitCount, 10);
    } else {
        visitCount = 0;
    }

    visitCount++;

    localStorage.setItem('visitCount', visitCount);
    document.getElementById('visitCount').textContent = visitCount;
})
