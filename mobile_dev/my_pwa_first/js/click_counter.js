let count = 0;
document.getElementById('counterBtn').addEventListener('click', function() {
count++;
this.textContent = 'Нажатий: ' + count;
});