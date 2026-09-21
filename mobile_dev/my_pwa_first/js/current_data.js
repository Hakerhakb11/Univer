function updateDate() {
    const now = new Date();
    document.getElementById('dateDisplay').textContent = now.toLocaleString();
}
updateDate();
setInterval(updateDate, 1000);
