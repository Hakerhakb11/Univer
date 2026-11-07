let isDark = false;

function toggleTheme() {
    isDark = !isDark;
    document.body.className = isDark ? 'dark' : 'light';

    const themeBtn = document.querySelector('.theme-toggle');
    themeBtn.textContent = isDark ? '🌙' : '☀️';
}
