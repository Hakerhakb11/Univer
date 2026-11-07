window.addEventListener('DOMContentLoaded', () => {
    const savedData = localStorage.getItem('userNote');
    if (savedData) {
        document.getElementById('savedNote').textContent = 'Сохранённая заметка: ' + savedData;
    }
});

function saveNote() {
    const input = document.getElementById('noteInput');
    const text = input.value.trim();

    if (text !== '') {
        localStorage.setItem('userNote', text);
        
        document.getElementById('savedNote').textContent = 'Сохранённая заметка: ' + text;
        
        input.value = '';
    }
}
